import java.awt.Desktop;
import java.net.URI;
import java.*;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.etrade.etws.account.Account;
 import com.etrade.etws.account.AccountListResponse;
 import com.etrade.etws.oauth.sdk.client.IOAuthClient;
 import com.etrade.etws.oauth.sdk.client.OAuthClientImpl;
 import com.etrade.etws.oauth.sdk.common.Token;
import com.etrade.etws.sdk.client.AccountsClient;
import com.etrade.etws.sdk.client.ClientRequest;
import com.etrade.etws.sdk.client.Environment;
import com.etrade.etws.sdk.common.ETWSException;

 public class basic {
	public static void main(String[] args) throws IOException, ETWSException {		
		
		
		
		IOAuthClient client = null;
		ClientRequest request = null;
		Token token = null;
		String oauth_consumer_key = "5c1068d4219f2d6cdca5c4f5aa59c358"; // Your consumer key
		String oauth_consumer_secret = "1e196e445736f14199aa7d13577f2312"; // Your consumer secret
		//String oauth_consumer_key           = args[0]; // Your consumer key
	    //String oauth_consumer_secret        = args[1]; // Your consumer secret
		String oauth_request_token = null; // Request token 
		String oauth_request_token_secret = null;
		String oauth_verify_code            = null;
        String oauth_access_token           = null;
        String oauth_access_token_secret    = null;

		 // Request token secret

		client = OAuthClientImpl.getInstance(); // Instantiate IOAUthClient
		request = new ClientRequest(); // Instantiate ClientRequest
		request.setEnv(Environment.SANDBOX); // Use sand box environment
			 
		request.setConsumerKey(oauth_consumer_key); //Set consumer key
		request.setConsumerSecret(oauth_consumer_secret); // Set consumer secret
		token= client.getRequestToken(request); // Get request-token object
		
		oauth_request_token  = token.getToken(); // Get token string
		oauth_request_token_secret = token.getSecret(); // Get token secret
		
		request.setToken(oauth_request_token);
        request.setTokenSecret(oauth_request_token_secret);

        String authorizeURL = null;
        authorizeURL = client.getAuthorizeUrl(request);
        System.out.println(authorizeURL);
        System.out.println("Copy the URL into your browser. Get the verification code and type here");
        oauth_verify_code = get_verification_code();
        //oauth_verify_code = Verification(client,request);

        request.setVerifierCode(oauth_verify_code);
        token = client.getAccessToken(request);
        oauth_access_token = token.getToken();
        oauth_access_token_secret = token.getSecret();

        request.setToken(oauth_access_token);
        request.setTokenSecret(oauth_access_token_secret);
		
        // Get Account List
        AccountsClient account_client = new AccountsClient(request);
        AccountListResponse response = account_client.getAccountList();
        List<Account> alist = response.getResponse();
        Iterator<Account> al = alist.iterator();
        while (al.hasNext()) {
            Account a = al.next();
            System.out.println("===================");
            System.out.println("Account: " + a.getAccountId());
            System.out.println("===================");
        }
	}
	
	public static String get_verification_code() {

        try{
            BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

            String input;

            input=br.readLine();
            return input;


        }catch(IOException io){
            io.printStackTrace();
            return "";
        }
    }
	
 }

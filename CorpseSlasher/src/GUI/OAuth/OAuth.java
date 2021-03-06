package GUI.OAuth;

import CorpseSlasher.ClientConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.io.DataOutputStream;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301
 *
 * OAuth - Handles the social login of facebook and google+ using OAuth
 * Authentication.
 */
public class OAuth {

    private static BufferedReader in;

    /**
     *
     * acceptCode receive a string that is sent to localhost:8080
     *
     * @return returns the code that was received from facebook or google+, for
     * example the access token(which consist of a code)
     */
    private static String acceptCode() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080, 0, InetAddress.getByName("localhost"));
            //while (true) {
            Socket socialSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(
                    socialSocket.getInputStream()));
            String code = in.readLine();
            return code.substring(code.indexOf("=") + 1).split(" ")[0];
            //}
        } catch (Exception exc) {
            System.out.println("Internal OAuth server error: " + exc.toString());
            return "";
        }
    }

    /**
     *
     * facebookLogin opens the user default browser at the facebook login
     * screen, so that the user can login and accept the games access
     * permission.
     *
     * @return return true if the user logs in and accept the games access
     * permission.
     * @throws OAuthSystemException throws an exception if there is an OAuth
     * error.
     * @throws IOException throws an exception if there is and IO error.
     */
    public static boolean facebookLogin() throws OAuthSystemException, IOException {
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .authorizationProvider(OAuthProviderType.FACEBOOK)
                    .setClientId("131804060198305")
                    .setRedirectURI("http://localhost:8080/")
                    .buildQueryMessage();
            try {
                URI domain = new URI(request.getLocationUri());
                java.awt.Desktop.getDesktop().browse(domain);
            } catch (Exception exc) {
                System.out.println("URI error: " + exc.toString());
                return false;
            }
            String code = acceptCode();
            request = OAuthClientRequest
                    .tokenProvider(OAuthProviderType.FACEBOOK)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId("131804060198305")
                    .setClientSecret("3acb294b071c9aec86d60ae3daf32a93")
                    .setRedirectURI("http://localhost:8080/")
                    .setCode(code)
                    .buildBodyMessage();
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            //Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is
            //application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that
            //Own response class is an easy way to deal with oauth providers that introduce modifications to
            //OAuth specification
            GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);
            /*System.out.println(
                    "Access Token: " + oAuthResponse.getAccessToken() + ", Expires in: " + oAuthResponse
                    .getExpiresIn());*/
            //get user facebook detail.
            JSONObject userProfileObj;
            try {
                URL url = new URL("https://graph.facebook.com/me?access_token=" + oAuthResponse.getAccessToken());
                URLConnection conn = url.openConnection();
                String line;
                String userProfile = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    userProfile += line;
                }
                userProfileObj = new JSONObject(userProfile);
                System.out.println("Facebook user id and name: " + userProfileObj.get("id") + " " + userProfileObj.get("name"));
                //add to database
                ClientConnection.StartClientConnection();
                if (ClientConnection.checkOauthIdAvailable(userProfileObj.get("id").toString()))
                {
                    ClientConnection.AddOAuthUser(userProfileObj.get("id").toString(), userProfileObj.get("name").toString());
                }
                ClientConnection.userId = userProfileObj.get("id").toString();
                
                reader.close();
            } catch (Exception e) {
                System.out.println("Facebook retrieve user details error: " + e.toString());
                return false;
            }
            //Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            
            //test code for wall post
            /*try
            {
            String data = URLEncoder.encode("access_token", "UTF-8") + "=" + URLEncoder.encode(oAuthResponse.getAccessToken(), "UTF-8");
            data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode("finally", "UTF-8");
            System.out.println("data is\n"+data);
            // Send data
           URLConnection connection = new URL("https://graph.facebook.com/me/feed").openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(data);
            out.flush();
            out.close();
            }
            catch (Exception exc)
            {
                System.out.println("Wall port error: " + exc.toString());
            }*/
            
            
            return true;
        } catch (OAuthProblemException e) {
            System.out.println("OAuth error: " + e.getError());
            System.out.println("OAuth error description: " + e.getDescription());
            return false;
        }
    }

    /**
     *
     * googleLogin opens the user default browser at the google+ login screen,
     * so that the user can login and accept the games access permission.
     *
     * @return return true if the user logs in and accept the games access
     * permission.
     * @throws OAuthSystemException throws an exception if there is an OAuth
     * error.
     * @throws IOException throws an exception if there is and IO error.
     */
    public static boolean googleLogin() throws OAuthSystemException, IOException {
        try {
            URI domain = new URI("https://accounts.google.com/o/oauth2/auth?scope=email%20profile&redirect_uri=http://localhost:8080&response_type=code&client_id=505441356969-kqpsidp2kfv0udl5vopauroupumm7401.apps.googleusercontent.com");
            java.awt.Desktop.getDesktop().browse(domain);
        } catch (Exception exc) {
            System.out.println("URI error: " + exc.toString());
            return false;
        }
        String code = acceptCode();
        if (code.compareTo("access_denied") == 0) {
            //Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            return false;
        }
        //Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        //System.out.println(code);
        
        //bug fix: Code is to old and that is why it is not working.
        /*String url = "https://accounts.google.com/o/oauth2/token";
         String in = "";
        
         try {       
         HttpClient client = new HttpClient();
         System.out.println("test");
         PostMethod method = new PostMethod(url);
            
         method.addParameter("code", code);
         method.addParameter("client_id", "505441356969-kqpsidp2kfv0udl5vopauroupumm7401.apps.googleusercontent.com");
         method.addParameter("client_secret", "WJHODsVIxt1-aqQpx_rHxf04");
         method.addParameter("redirect_uri", "http://localhost:8080");
         method.addParameter("grant_type", "authorization_code");
            
         int statusCode = client.executeMethod(method);
            
         if (statusCode != -1) {

         in = method.getResponseBodyAsString();

         }

         System.out.println("Access Token: " + in);

         } catch (Exception e) {
         System.out.println("Google login error: " + e.toString());
         return false;
         }*/

        //Newer working code to get access token
        JSONObject userDetailsObj;
        try {
            URL url = new URL("https://accounts.google.com/o/oauth2/token");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write("code=" + code + "&client_id=505441356969-kqpsidp2kfv0udl5vopauroupumm7401.apps.googleusercontent.com&client_secret=WJHODsVIxt1-aqQpx_rHxf04&redirect_uri=http://localhost:8080&grant_type=authorization_code");
            writer.flush();
            String line;
            String userDetails = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                userDetails+= line;
            }
            userDetailsObj = new JSONObject(userDetails);
            writer.close();
            reader.close();

        } catch (Exception e) {
            System.out.println("Google login error: " + e.toString());
            return false;
        }
        
        //get user google+ Profile.
        JSONObject userProfileObj;
            try {
                URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + userDetailsObj.get("access_token"));
                URLConnection conn = url.openConnection();
                String line;
                String userProfile = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    userProfile += line;
                }
                userProfileObj = new JSONObject(userProfile);
                System.out.print("Google+ user id and name: " + userProfileObj.get("id") + " " + userProfileObj.get("name"));
                
                //add to database
            if (ClientConnection.checkOauthIdAvailable(userProfileObj.get("id").toString()))
                {
                    ClientConnection.AddOAuthUser(userProfileObj.get("id").toString(), userProfileObj.get("name").toString());
                }
                ClientConnection.userId = userProfileObj.get("id").toString();
                
                reader.close();
            } catch (Exception e) {
                System.out.println("Google+ retrieve user details error: " + e.toString());
                return false;
            }
            
        return true;
    }
    //Could not get Twitter Oauth working
    /*public static boolean twitterLogin() throws OAuthSystemException, IOException {

     String url = "https://api.twitter.com/oauth/request_token";
     String in = "";
     String timeStamp = Long.toString(System.currentTimeMillis()/1000);
        
     //create a nonce for twitter by random 32 characters string, convert string to UTF8 and base64 encode the UTF8
     //generate random key. need to add check for duplicate keys, by storing each key.
     char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
     StringBuilder sb = new StringBuilder();
     Random random = new Random();
     for (int i = 0; i < 32; i++) {
     char c = chars[random.nextInt(chars.length)];
     sb.append(c);
     }
     String output = sb.toString();
     byte utfText[] = output.getBytes("UTF8");
     byte[] encoded = Base64.encodeBase64(utfText);

     try {
     HttpClient client = new HttpClient();
     PostMethod method = new PostMethod(url);
            

     //method.addParameter("OAuth oauth_nonce", new String(encoded));
     //method.addParameter("oauth_callback", "http://localhost:8080");
     //method.addParameter("client_secret", "Y9FtcLtelh1XVaJfFsfFhQayUJd37Kv9QL2UDtjwl0DiWx5584");
     //method.addParameter("oauth_signature_method", "HMAC-SHA1");
     //method.addParameter("oauth_timestamp", timeStamp);
     //method.addParameter("oauth_consumer_key", "44u5xy95Dpet3iYfxX7hcrlf2");
     method.addParameter("oauth_signature", "");
     //method.addParameter("oauth_version", "1.0");
            
     int statusCode = client.executeMethod(method);

     if (statusCode != -1) {

     in = method.getResponseBodyAsString();

     }

     System.out.println(in);

     } catch (Exception e) {
     e.printStackTrace();
     return false;
     }

     return true;
     }*/
}
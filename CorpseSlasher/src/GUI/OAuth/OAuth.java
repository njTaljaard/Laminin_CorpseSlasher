package GUI.OAuth;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

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

            // while (true) {
            Socket socialSocket = serverSocket.accept();

            in = new BufferedReader(new InputStreamReader(socialSocket.getInputStream()));

            String code = in.readLine();

            return code.substring(code.indexOf("=") + 1).split(" ")[0];

            // }
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
            OAuthClientRequest request = OAuthClientRequest.authorizationProvider(
                                             OAuthProviderType.FACEBOOK).setClientId("131804060198305").setRedirectURI(
                                             "http://localhost:8080/").buildQueryMessage();

            try {
                URI domain = new URI(request.getLocationUri());

                java.awt.Desktop.getDesktop().browse(domain);
            } catch (Exception exc) {
                System.out.println("URI error: " + exc.toString());

                return false;
            }

            String code = acceptCode();

            request = OAuthClientRequest.tokenProvider(OAuthProviderType.FACEBOOK).setGrantType(
                GrantType.AUTHORIZATION_CODE).setClientId("131804060198305").setClientSecret(
                "3acb294b071c9aec86d60ae3daf32a93").setRedirectURI("http://localhost:8080/").setCode(
                code).buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            // Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is
            // application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that
            // Own response class is an easy way to deal with oauth providers that introduce modifications to
            // OAuth specification
            GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);

            System.out.println("Access Token: " + oAuthResponse.getAccessToken() + ", Expires in: "
                               + oAuthResponse.getExpiresIn());

            // Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
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
            URI domain =
                new URI(
                    "https://accounts.google.com/o/oauth2/auth?scope=email%20profile&redirect_uri=http://localhost:8080&response_type=code&client_id=505441356969-kqpsidp2kfv0udl5vopauroupumm7401.apps.googleusercontent.com");

            java.awt.Desktop.getDesktop().browse(domain);
        } catch (Exception exc) {
            System.out.println("URI error: " + exc.toString());

            return false;
        }

        String code = acceptCode();

        if (code.compareTo("access_denied") == 0) {

            // Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            return false;
        }

        // Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
        System.out.println(code);

        // bug fix

        /*
         * String url = "https://accounts.google.com/o/oauth2/token";
         * String in = "";
         *
         * try {
         *   HttpClient client = new HttpClient();
         *   PostMethod method = new PostMethod(url);
         *
         *   method.addParameter("code", code);
         *   method.addParameter("client_id", "505441356969-kqpsidp2kfv0udl5vopauroupumm7401.apps.googleusercontent.com");
         *   method.addParameter("client_secret", "WJHODsVIxt1-aqQpx_rHxf04");
         *   method.addParameter("redirect_uri", "http://localhost:8080");
         *   method.addParameter("grant_type", "authorization_code");
         *
         *   int statusCode = client.executeMethod(method);
         *
         *   if (statusCode != -1) {
         *
         *       in = method.getResponseBodyAsString();
         *
         *   }
         *
         *   System.out.println(in);
         *
         * } catch (Exception e) {
         *   e.printStackTrace();
         *   return false;
         * }
         */
        return true;
    }

    // Could not get Twitter Oauth working

    /*
     * public static boolean twitterLogin() throws OAuthSystemException, IOException {
     *
     * String url = "https://api.twitter.com/oauth/request_token";
     * String in = "";
     * String timeStamp = Long.toString(System.currentTimeMillis()/1000);
     *
     * //create a nonce for twitter by random 32 characters string, convert string to UTF8 and base64 encode the UTF8
     * //generate random key. need to add check for duplicate keys, by storing each key.
     * char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
     * StringBuilder sb = new StringBuilder();
     * Random random = new Random();
     * for (int i = 0; i < 32; i++) {
     * char c = chars[random.nextInt(chars.length)];
     * sb.append(c);
     * }
     * String output = sb.toString();
     * byte utfText[] = output.getBytes("UTF8");
     * byte[] encoded = Base64.encodeBase64(utfText);
     *
     * try {
     * HttpClient client = new HttpClient();
     * PostMethod method = new PostMethod(url);
     *
     *
     * //method.addParameter("OAuth oauth_nonce", new String(encoded));
     * //method.addParameter("oauth_callback", "http://localhost:8080");
     * //method.addParameter("client_secret", "Y9FtcLtelh1XVaJfFsfFhQayUJd37Kv9QL2UDtjwl0DiWx5584");
     * //method.addParameter("oauth_signature_method", "HMAC-SHA1");
     * //method.addParameter("oauth_timestamp", timeStamp);
     * //method.addParameter("oauth_consumer_key", "44u5xy95Dpet3iYfxX7hcrlf2");
     * method.addParameter("oauth_signature", "");
     * //method.addParameter("oauth_version", "1.0");
     *
     * int statusCode = client.executeMethod(method);
     *
     * if (statusCode != -1) {
     *
     * in = method.getResponseBodyAsString();
     *
     * }
     *
     * System.out.println(in);
     *
     * } catch (Exception e) {
     * e.printStackTrace();
     * return false;
     * }
     *
     * return true;
     * }
     */
}


//~ Formatted by Jindent --- http://www.jindent.com

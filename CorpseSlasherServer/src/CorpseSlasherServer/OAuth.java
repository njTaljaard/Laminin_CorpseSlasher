package CorpseSlasherServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;



/**
 *
 * @author Martin
 */
public class OAuth {
    
    private static BufferedReader in;
    
    private static String acceptCode()
    {
        try {
        ServerSocket serverSocket = new ServerSocket(8080, 0, InetAddress.getByName("localhost"));
        
        //while (true) {
                Socket socialSocket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(
                    socialSocket.getInputStream()));
                String code = in.readLine();
                return code.substring(code.indexOf("=")+1).split(" ")[0];
            //}
        }
        catch (Exception exc) {
            System.out.println("Internal OAuth server error: " + exc.toString());
            return "";
        }
    }
    
    public static boolean facebookLogin()throws OAuthSystemException, IOException {

        try {
            OAuthClientRequest request = OAuthClientRequest
                .authorizationProvider(OAuthProviderType.FACEBOOK)
                .setClientId("131804060198305")
                .setRedirectURI("http://localhost:8080/")
                .buildQueryMessage();

            //in web application you make redirection to uri:
            //System.out.println("Visit: " + request.getLocationUri() + "\nand grant permission");
            try {
            URI domain = new URI(request.getLocationUri());
            java.awt.Desktop.getDesktop().browse(domain);
            }
            catch (Exception exc) {
                System.out.println("URI error: " + exc.toString());
                return false;
            }
            
            String code = acceptCode();
            
            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
           
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

            System.out.println(
                "Access Token: " + oAuthResponse.getAccessToken() + ", Expires in: " + oAuthResponse
                    .getExpiresIn());
            return true;
        } catch (OAuthProblemException e) {
            System.out.println("OAuth error: " + e.getError());
            System.out.println("OAuth error description: " + e.getDescription());
            return false;
        }
    }
}

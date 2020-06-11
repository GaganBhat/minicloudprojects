
import com.microsoft.azure.management.Azure;
import com.microsoft.graph.auth.confidentialClient.ClientCredentialProvider;
import com.microsoft.graph.auth.enums.NationalCloud;
import com.microsoft.graph.models.extensions.DirectoryObject;
import com.microsoft.graph.models.extensions.DirectoryRole;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IDirectoryObjectCollectionWithReferencesPage;
import com.microsoft.graph.requests.extensions.IDirectoryRoleCollectionPage;
import com.microsoft.rest.LogLevel;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AzureMFACheck {

	private static String auth_token = "";

	private static final String CLIENT_ID = "9713ec7c-c96e-489f-a780-6271d674863c";
	private static final String CLIENT_SECRET = "";
	private static final String TENANT = "674ebde0-b2ea-425f-a349-10ef155575c0";
	private static final String SCOPE = "https://graph.microsoft.com/.default";

	public static void main(String[] args) {

		getAuthToken();

		ClientCredentialProvider authorizationCodeProvider =
				new ClientCredentialProvider(
						CLIENT_ID,
						Arrays.asList("https://graph.microsoft.com/.default"),
						CLIENT_SECRET,
						TENANT,
						NationalCloud.Global
				);


		IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(authorizationCodeProvider).buildClient();


		IDirectoryRoleCollectionPage directoryRoleCollectionPage = graphClient.directoryRoles().buildRequest().get();
		for(DirectoryRole role : directoryRoleCollectionPage.getCurrentPage()){
			if(role.displayName.toLowerCase().contains("administrator")){
				IDirectoryObjectCollectionWithReferencesPage members = graphClient.directoryRoles(role.id).members()
						.buildRequest()
						.get();

				for(DirectoryObject object : members.getCurrentPage()){
					isMFAEnabled(object.getRawObject().get("id").getAsString());
				}
			}
		}


		System.exit(0);
	}

	public static void getAuthToken(){
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(String.format("https://login.microsoftonline.com/%s/oauth2/v2.0/token", TENANT));

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("grant_type", "client_credentials"));
			params.add(new BasicNameValuePair("client_id", CLIENT_ID));
			params.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
			params.add(new BasicNameValuePair("scope", SCOPE));
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = client.execute(httpPost);
			assert response.getStatusLine().getStatusCode() == 200;

			InputStream inputStream = response.getEntity().getContent();
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(
					new InputStreamReader(inputStream, "UTF-8"));

			auth_token = (String) jsonObject.get("access_token");

			client.close();
		} catch (Exception e ){e.printStackTrace();}
	}

	public static boolean isMFAEnabled(String displayName) {

		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(String.format(
					"https://graph.microsoft.com/beta/reports/credentialUserRegistrationDetails?$filter=startswith(id,'%s')",
					displayName));
			httpGet.setHeader("Authorization", "Bearer " + auth_token);

			CloseableHttpResponse response = client.execute(httpGet);

			InputStream inputStream = response.getEntity().getContent(); //Read from a file, or a HttpRequest, or whatever.
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(
					new InputStreamReader(inputStream, "UTF-8"));

			System.out.println(jsonObject.toString());

		} catch (Exception e ){e.printStackTrace();}

		return false;
	}

}

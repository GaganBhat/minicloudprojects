
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

	private static final String CLIENT_ID = "5883638a-eea1-4ef2-ab57-b4c8547f2696";
	private static final String CLIENT_SECRET = "v6Nx7o27JD7-yW9sy6lWr4~PcspD_~ZbNf";
	private static final String TENANT = "5c661468-61e8-450a-9561-52f21b84afa8";
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


		try {
			IDirectoryRoleCollectionPage directoryRoleCollectionPage = graphClient.directoryRoles().buildRequest().get();
			for (DirectoryRole role : directoryRoleCollectionPage.getCurrentPage()) {
				if (role.displayName.toLowerCase().contains("administrator")) {
					IDirectoryObjectCollectionWithReferencesPage members = graphClient.directoryRoles(role.id).members()
							.buildRequest()
							.get();

					for (DirectoryObject object : members.getCurrentPage()) {
						isMFAEnabled(object.getRawObject().get("id").getAsString());
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Make sure to update your permissions! \n " + e);
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

			System.out.println("NOTE - AUTHENTICATION TOKEN GENERATED SUCCESSFULLY!");

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
			JSONObject mainJSONResult = (JSONObject) new JSONParser().parse(
					new InputStreamReader(inputStream, "UTF-8"));

			String userValUnformatted = mainJSONResult.get("value").toString();
			JSONObject userValues = (JSONObject) new JSONParser().parse(
					userValUnformatted.substring(1, userValUnformatted.length() - 1));

			if(userValues.get("isMfaRegistered").equals("true"))
				return true;


		} catch (Exception e ){
			System.out.println("Neither tenant is B2C or tenant doesn't have premium license possibly");
			e.printStackTrace();
		}

		return false;
	}

}

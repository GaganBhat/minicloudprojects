
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.management.graphrbac.RoleDefinition;
import com.microsoft.azure.management.graphrbac.implementation.UserInner;
import com.microsoft.graph.auth.confidentialClient.ClientCredentialProvider;
import com.microsoft.graph.auth.enums.NationalCloud;
import com.microsoft.graph.models.extensions.DirectoryObject;
import com.microsoft.graph.models.extensions.DirectoryRole;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IDirectoryObjectCollectionPage;
import com.microsoft.graph.requests.extensions.IDirectoryObjectCollectionWithReferencesPage;
import com.microsoft.graph.requests.extensions.IDirectoryRoleCollectionPage;
import com.microsoft.rest.LogLevel;
import com.sun.security.auth.UserPrincipal;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AzureMFACheck {

	String auth_token = "";

	private static String clientId = "9713ec7c-c96e-489f-a780-6271d674863c";
	private static String tenant = "674ebde0-b2ea-425f-a349-10ef155575c0";

	public static void main(String[] args) {

		System.out.println(String.format("https://login.microsoftonline.com/%s/oauth2/v2.0/token", tenant));

		ClientCredentialProvider authorizationCodeProvider =
				new ClientCredentialProvider(
						clientId,
						Arrays.asList("https://graph.microsoft.com/.default"),
						clientSecret,
						tenant,
						NationalCloud.Global
				);


		IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(authorizationCodeProvider).buildClient();
		graphClient = (GraphServiceClient) graphClient;

//		graphClient.setServiceRoot("https://graph.microsoft.com/beta/");
//		graphClient.customRequest("privilegedRoles").buildRequest().get();

		Azure azureClient = null;

		try {
			azureClient = Azure
					.configure()
					.withLogLevel(LogLevel.NONE)
					.authenticate(new File("/Users/gaganbhat/Documents/Programming/Keys/azureauth.properties"))
					.withDefaultSubscription();
		} catch (Exception e) {
			e.printStackTrace();
		}


		assert azureClient != null;


		IDirectoryRoleCollectionPage directoryRoleCollectionPage = graphClient.directoryRoles().buildRequest().get();
		for(DirectoryRole role : directoryRoleCollectionPage.getCurrentPage()){
			if(role.displayName.toLowerCase().contains("administrator")){
				IDirectoryObjectCollectionWithReferencesPage members = graphClient.directoryRoles(role.id).members()
						.buildRequest()
						.get();

				for(DirectoryObject object : members.getCurrentPage()){
					System.out.println(object.getRawObject().get("displayName"));

				}
			}
		}

		/*
		for(UserInner user : azureClient.accessManagement().activeDirectoryUsers().inner().list())
			for(RoleAssignment assignment : azureClient.accessManagement().roleAssignments().listByScope("/subscriptions/" +
					azureClient.getCurrentSubscription().subscriptionId() + "/")) {
				assignment.inner().name();


				RoleDefinition definition = azureClient.accessManagement().roleDefinitions().getById(assignment.roleDefinitionId());
			}

		 */


		System.exit(0);
	}


	public void getAuthToken(){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(String.format("https://login.microsoftonline.com/%s/oauth2/v2.0/token", tenant));

			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			try {
				System.out.println(response1.getStatusLine());
				HttpEntity entity1 = response1.getEntity();
				// do something useful with the response body
				// and ensure it is fully consumed
				EntityUtils.consume(entity1);
			} finally {
				response1.close();
			}
		} catch (IOException e ){e.printStackTrace();}
	}

}


import com.amazonaws.services.identitymanagement.model.UserDetail;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.graphrbac.ActiveDirectoryGroup;
import com.microsoft.azure.management.graphrbac.ActiveDirectoryUser;
import com.microsoft.azure.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.management.graphrbac.ServicePrincipals;
import com.microsoft.azure.management.graphrbac.implementation.UserInner;
import com.microsoft.graph.auth.confidentialClient.AuthorizationCodeProvider;
import com.microsoft.graph.auth.confidentialClient.ClientCredentialProvider;
import com.microsoft.graph.auth.enums.NationalCloud;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IUserCollectionPage;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class AzureMFACheck {

	public static void main(String[] args) {

		ClientCredentialProvider authorizationCodeProvider =
				new ClientCredentialProvider(
						"9713ec7c-c96e-489f-a780-6271d674863c",
						Arrays.asList("https://graph.microsoft.com/.default"),
						"[zfw-T'_fDaiKI73$9cjAgU/e,R7c5Rb",
						"674ebde0-b2ea-425f-a349-10ef155575c0",
						NationalCloud.Global
				);

		IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(authorizationCodeProvider).buildClient();

		IUserCollectionPage users = graphClient.users()
				.buildRequest()
				.get();

		System.out.println(users.toString());


		Azure azureClient = null;

		try {


			azureClient = Azure
					.configure()
					.withLogLevel(LogLevel.NONE)
					.authenticate(new File("/Users/gaganbhat/Documents/Programming/Keys/azureauth.properties"))
//					.authenticate(credentials)
					.withDefaultSubscription();
		} catch (Exception e) {
			e.printStackTrace();
		}


		assert azureClient != null;

		for(UserInner user : azureClient.accessManagement().activeDirectoryUsers().inner().list())
			System.out.println(user.displayName());




		System.exit(0);

	}


}

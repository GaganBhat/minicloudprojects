
import com.amazonaws.services.identitymanagement.model.UserDetail;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.graphrbac.ActiveDirectoryGroup;
import com.microsoft.azure.management.graphrbac.ActiveDirectoryUser;
import com.microsoft.azure.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.management.graphrbac.ServicePrincipals;
import com.microsoft.azure.management.graphrbac.implementation.UserInner;
import com.microsoft.rest.LogLevel;

import java.io.File;


public class AzureMFACheck {

	public static void main(String[] args) {
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

//		for(UserInner user : azureClient.accessManagement().activeDirectoryUsers().inner().list())
//			System.out.println(azureClient.accessManagement().roleAssignments().getByScope("Global administrator"));

		System.out.println(azureClient.accessManagement().roleDefinitions().getByScope("--all", "Owner"));



		System.exit(0);

	}


}

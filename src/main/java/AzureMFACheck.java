
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.management.graphrbac.RoleDefinition;
import com.microsoft.azure.management.graphrbac.implementation.UserInner;
import com.microsoft.rest.LogLevel;
import com.sun.security.auth.UserPrincipal;

import java.io.File;


public class AzureMFACheck {

	public static void main(String[] args) {


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

		for(UserInner user : azureClient.accessManagement().activeDirectoryUsers().inner().list())
			for(RoleAssignment assignment : azureClient.accessManagement().roleAssignments().listByScope("/subscriptions/" +
					azureClient.getCurrentSubscription().subscriptionId() + "/")) {
				assignment.inner().name();
				System.out.println(assignment.inner().principalId());
				System.out.println(assignment.inner().name());
				System.out.println(assignment.inner().canDelegate());
				System.out.println(assignment.inner().id());
				System.out.println(assignment.inner().type());
				System.out.println("-----");
				System.out.println();

				RoleDefinition definition = azureClient.accessManagement().roleDefinitions().getById(assignment.roleDefinitionId());
			}

		System.exit(0);
	}

}

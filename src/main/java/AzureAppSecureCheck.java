import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.FunctionApp;
import com.microsoft.azure.management.appservice.WebApp;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AzureAppSecureCheck {

	public static void main(String[] args) {
		checkServices(
				new ArrayList<>(Arrays.asList(
					"/subscriptions/f0643b6b-3d4f-45b4-b8bf-2a7679c85eba/resourceGroups/central-us-resources/providers/Microsoft.Web/sites/test-app-central-us",
					"/subscriptions/f0643b6b-3d4f-45b4-b8bf-2a7679c85eba/resourcegroups/central-us-resources/providers/Microsoft.Web/sites/japanapp"
				)),
				new ArrayList<>(Arrays.asList(
					"/subscriptions/f0643b6b-3d4f-45b4-b8bf-2a7679c85eba/resourcegroups/central-us-resources/providers/Microsoft.Web/sites/japanapp"
				)),
				new File("/Users/gaganbhat/Documents/Programming/Keys/azureauth.properties")
		);
		System.out.println("Finished processing.");
		System.exit(0);
	}

	public static void checkServices(List<String> resourceIDs, List<String> blacklistIDs, File pathToServicePrincipal){
		resourceIDs.removeAll(blacklistIDs);
		for(String resourceID : resourceIDs)
			checkServiceValidity(resourceID, pathToServicePrincipal);
	}

	public static void checkServiceValidity(String resourceID, File pathToServicePrincipal){
		Azure client = null;
		try {
			client = Azure.configure()
					.withLogLevel(LogLevel.BASIC)
					.authenticate(pathToServicePrincipal)
					.withDefaultSubscription();

		} catch (IOException e) {
			e.printStackTrace();
		}

		assert client != null;

		WebApp webPlan;
		try {
			webPlan = client.appServices().webApps().getById(resourceID);
		} catch (InvalidParameterException e) {
			System.out.println("RESOURCE ID APP - " + resourceID + " NOT FOUND");
			return;
		}

		if(webPlan == null){
			System.out.println("RESOURCE ID APP - " + resourceID + " NOT FOUND");
			return;
		}

		System.out.println("---------------------------");
		String str = "App Service " + webPlan.name() + " has HTTPS only";
		if(webPlan.httpsOnly())
			System.out.println(str + " ENABLED");
		else
			System.out.println(str + " DISABLED");
		System.out.println("---------------------------");

	}

}

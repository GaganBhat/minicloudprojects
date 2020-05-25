import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.network.NetworkSecurityRule;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AzureVNETAuthorization {

	public static void main(String[] args) {
		init(
				new String[]{"/subscriptions/f0643b6b-3d4f-45b4-b8bf-2a7679c85eba/resourceGroups/central-us-resources/providers/Microsoft.Compute/virtualMachines/test-virtual-machine"},
				new String[]{""},
				new String()
			);
		System.exit(0);
	}

	public static void init(String[] resourceIDs, String[] authorizedVNets, String servicePrincipal){
		Azure client = null;
		try {
			client = Azure.configure()
					.withLogLevel(LogLevel.BASIC)
					.authenticate(new File(
							"/Users/gaganbhat/Documents/Programming/Keys/azureauth.properties"
					))
					.withDefaultSubscription();

		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String resourceID : resourceIDs)
			checkResource(resourceID, authorizedVNets, client);

	}

	public static void checkResource(String resourceID, String[] authorizedVNets, Azure client){
		VirtualMachine resource = client.virtualMachines().getById(resourceID);
		assert resource != null;
		Map<String, NetworkSecurityRule> rule = resource.getPrimaryNetworkInterface().getNetworkSecurityGroup().securityRules();
		System.out.println(rule);
	}

}

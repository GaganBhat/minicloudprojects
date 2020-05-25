import com.microsoft.azure.Resource;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.credentials.AzureTokenCredentialsInterceptor;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.graphrbac.ServicePrincipal;
import com.microsoft.azure.management.network.Access;
import com.microsoft.azure.management.network.NetworkSecurityRule;
import com.microsoft.azure.management.network.SecurityRuleAccess;
import com.microsoft.rest.LogLevel;
import com.microsoft.rest.RestClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AzureVNETAuthorization {

	public static void main(String[] args) {
		// Some test cases
		init(
				new String[]{"/subscriptions/f0643b6b-3d4f-45b4-b8bf-2a7679c85eba/resourceGroups/central-us-resources/providers/Microsoft.Compute/virtualMachines/test-virtual-machine"},
				new String[]{"/subscriptions/f0643b6b-3d4f-45b4-b8bf-2a7679c85eba/resourceGroups/central-us-resources/providers/Microsoft.Network/virtualNetworks/virtual-network"},
				new File("your-service-principal-here")
		);
		System.exit(0);
	}

	public static void init(String[] resourceIDs, String[] authorizedVNets, File pathToServicePrincipal){
		Azure client = null;
		try {
			client = Azure.configure()
					.withLogLevel(LogLevel.BASIC)
					.authenticate(pathToServicePrincipal)
					.withDefaultSubscription();

		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String resourceID : resourceIDs)
			checkResource(resourceID, new ArrayList<String>(Arrays.asList(authorizedVNets)), client);

	}

	public static void checkResource(String resourceID, ArrayList<String> authorizedVNets, Azure client){
		VirtualMachine resource = client.virtualMachines().getById(resourceID);
		assert resource != null;
		Map<String, NetworkSecurityRule> rules = resource.getPrimaryNetworkInterface().getNetworkSecurityGroup().securityRules();
		if(rules.containsKey("HTTP") || rules.containsKey("HTTPS")){
			boolean allowsInternetInbound = false;
			if(rules.containsKey("HTTP") && rules.get("HTTP").access().toString().equals("Allow"))
				allowsInternetInbound = true;
			if(rules.containsKey("HTTPS") && rules.get("HTTPS").access().toString().equals("Allow"))
				allowsInternetInbound = true;

			if(allowsInternetInbound && authorizedVNets.contains(resource.getPrimaryNetworkInterface().primaryIPConfiguration().getNetwork().id()))
				System.out.println("Resource: " + resource.name() + " IS COMPLIANT and follows rules");
			else if (allowsInternetInbound)
				System.out.println("Resource: " + resource.name() + " IS NOT COMPLIANT and allows internet inbound when it shouldn't!");
			else
				System.out.println("Resource: " + resource.name() + " does not allow inbound internet");

		}
	}

}

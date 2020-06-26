import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.implementation.VirtualMachineInner;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class AzureVMLogEnabled {

	public static void main(String[] args) {
		Azure client = null;
		try {
			client = Azure.configure()
					.withLogLevel(LogLevel.BASIC)
					.authenticate(new File("/Users/gaganbhat/Documents/Programming/Keys/azureauth.properties"))
					.withDefaultSubscription();

		} catch (IOException e) {
			e.printStackTrace();
		}

		VirtualMachineInner inner = client.virtualMachines().getById(
				"/subscriptions/f0643b6b-3d4f-45b4-b8bf-2a7679c85eba/resourceGroups/central-us-resources/providers/Microsoft.Compute/virtualMachines/test-machine")

				.inner();

		System.out.println(Date.from(Instant.now()));

		System.out.println(client.activityLogs().manager().activityLogs().inner().list("resourceID eq " + inner.id()) + " and startTime " + Date.from(Instant.now()));
	}

}

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.LogLevel;

public class AzureLogsEvaluation {

	public static void main(String[] args) {
		Azure azureClient = null;
		try {
			ApplicationTokenCredentials credentials = new ApplicationTokenCredentials(
					"5883638a-eea1-4ef2-ab57-b4c8547f2696",
					"5c661468-61e8-450a-9561-52f21b84afa8",
					"v6Nx7o27JD7-yW9sy6lWr4~PcspD_~ZbNf",
					AzureEnvironment.AZURE);

			azureClient = Azure
					.configure()
					.withLogLevel(LogLevel.NONE)
					.authenticate(credentials)
					.withDefaultSubscription();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assert azureClient != null;



		System.exit(0);
	}

}

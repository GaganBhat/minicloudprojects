import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;

import java.util.List;

public class RDSBackupChecker {

	public static void main(String[] args) {
		// Some test cases
		checkRDSInstances(new String[]{"database-1", "database-2"}, 2, "06:20-06:50");
	}

	public static void checkRDSInstances(String[] instanceIDs, int retentionPeriod, String backupWindow){
		for(String instanceID : instanceIDs)
			checkConfigMatch(instanceID, retentionPeriod, backupWindow);
	}

	public static void checkConfigMatch(String instanceID, int retentionPeriod, String backupWindow){
		AmazonRDS client = AmazonRDSClientBuilder.standard().build();
		DescribeDBInstancesRequest request = new DescribeDBInstancesRequest().withDBInstanceIdentifier(instanceID);
		List<DBInstance> dbInstances = client.describeDBInstances(request).getDBInstances();
		if(dbInstances.isEmpty()) {
			System.out.println("Instance " + instanceID + " does not exist");
			return;
		}
		for(DBInstance instance : dbInstances)
			if(instance.getBackupRetentionPeriod() != 0) {
				StringBuilder builder = new StringBuilder("Instance " + instance.getDBInstanceIdentifier()
						+ " has backup enabled \n");
			if(instance.getBackupRetentionPeriod().equals(retentionPeriod))
				builder
						.append("Satisfies backup retention periodic of ")
						.append(instance.getBackupRetentionPeriod())
						.append("\n");

			if(instance.getPreferredBackupWindow().equals(backupWindow))
				builder
						.append("Satisfies backup window of ")
						.append(instance.getPreferredBackupWindow())
						.append("\n");

			} else
				System.out.println("Instance " + instance.getDBInstanceIdentifier() + "backup disabled ");

	}

}

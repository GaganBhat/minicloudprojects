import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;

import java.util.List;

public class RDSBackupChecker {

	public static void main(String[] args) {
		checkRDSInstances(new String[]{"database-1", "database-2"});
	}

	public static void checkRDSInstances(String[] instanceIDs){
		for(String instanceID : instanceIDs)
			checkConfigMatch(instanceID);
	}

	public static void checkConfigMatch(String instanceID){
		AmazonRDS client = AmazonRDSClientBuilder.standard().build();
		DescribeDBInstancesRequest request = new DescribeDBInstancesRequest().withDBInstanceIdentifier(instanceID);
		List<DBInstance> dbInstances = client.describeDBInstances(request).getDBInstances();
		if(dbInstances.isEmpty()) {
			System.out.println("Instance " + instanceID + " does not exist");
			return;
		}
		for(DBInstance instance : dbInstances)
			if(instance.getBackupRetentionPeriod() != 0)
				System.out.println("Instance " + instance.getDBInstanceIdentifier()
						+ " has backup enabled with retention period "
						+ instance.getBackupRetentionPeriod());
			 else
				System.out.println("Instance " + instance.getDBInstanceIdentifier() + "backup disabled ");

	}

}

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.amazonaws.services.rds.model.Filter;

import java.util.List;

public class RDSBackupChecker {

	public static void main(String[] args) {
		// Some test cases
		checkRDSInstances(new String[]{"db-3XUOGWDVC3RPP7LN7TCBPF5K7Q", "db-6PXGXJHJSWYEELNVG5MKGTTNSI"}, 7, "06:20-06:50");
	}

	public static void checkRDSInstances(String[] instanceIDs, int retentionPeriod, String backupWindow){
		for(String instanceID : instanceIDs)
			checkConfigMatch(instanceID, retentionPeriod, backupWindow);
	}

	public static void checkConfigMatch(String instanceID, int retentionPeriod, String backupWindow){

		AmazonRDS client = null;
		List<DBInstance> dbInstances = null;
		DescribeDBInstancesRequest request = null;

		for(Regions region : Regions.values()){
			client = AmazonRDSClientBuilder.standard().withRegion(region).build();
			request = new DescribeDBInstancesRequest().withFilters(new Filter().withName("dbi-resource-id").withValues(instanceID));
			try{
				dbInstances = client.describeDBInstances(request).getDBInstances();
				if(dbInstances.size() > 0)
					break;
			} catch (Exception ignored) { }
		}

		assert dbInstances != null;
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
						.append(" days\n");
			else
				builder.append("Does not satisfy backup retention period \n");

			if(instance.getPreferredBackupWindow().equals(backupWindow))
				builder
						.append("Satisfies backup window of ")
						.append(instance.getPreferredBackupWindow())
						.append("\n");
			else
				builder.append("Does not satisfy backup window \n");

			System.out.println(builder.toString());

			} else
				System.out.println("Instance " + instance.getDBInstanceIdentifier() + " backup disabled ");

	}

}

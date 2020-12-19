import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTinkering {

	public static final String TEST_1 =
			"{\n" +
					"  \"@odata.context\":\"https://graph.microsoft.com/beta/reports/$metadata#Collection(microsoft.graph.credentialUserRegistrationDetails)\",\n" +
					"  \"value\":[\n" +
					"    {\n" +
					"      \"id\" : \"id-value\",\n" +
					"      \"userPrincipalName\":\"userPrincipalName\",\n" +
					"      \"userDisplayName\": \"userDisplayName-value\",\n" +
					"      \"authMethods\": [\"email\", \"mobileSMS\"],\n" +
					"      \"isRegistered\" : false,\n" +
					"      \"isEnabled\" : true,\n" +
					"      \"isCapable\" : false,\n" +
					"      \"isMfaRegistered\" : true\n" +
					"    }\n" +
					"  ]\n" +
					"}";

	public static final String FORCED_FAILURE =
			"{\n" +
					"  \"error\": {\n" +
					"    \"code\": \"Authentication_RequestFromNonPremiumTenantOrB2CTenant\",\n" +
					"    \"message\": \"Neither tenant is B2C or tenant doesn't have premium license\",\n" +
					"    \"innerError\": {\n" +
					"      \"request-id\": \"7d92def0-e7a1-42c6-be20-95eb0db28fe1\",\n" +
					"      \"date\": \"2020-06-11T16:00:24\"\n" +
					"    }\n" +
					"  }\n" +
					"}";

	public static final String ROLE_POLICY_AWS =
			"{\n" +
					"    \"Version\": \"2012-10-17\",\n" +
					"    \"Statement\": [\n" +
					"        {\n" +
					"            \"Action\": \"*\",\n" +
					"            \"Resource\": \"*\",\n" +
					"            \"Effect\": \"Allow\"\n" +
					"        }\n" +
					"    ]\n" +
					"}";

	public static void main(String[] args)  {

		try {
			JSONArray statements = (JSONArray) new JSONObject(ROLE_POLICY_AWS).get("Statement");
			for (int i = 0; i < statements.length(); i++){
				JSONObject statement = (JSONObject) statements.get(i);
				if(statement.get("Action").equals("*") && statement.get("Resource").equals("*") && statement.get("Effect").equals("Allow")){
					System.out.println("Admin Access");
				}
			}

		} catch (Exception e ) {
			System.out.println("unfortunate " + e);
		}

	}

}

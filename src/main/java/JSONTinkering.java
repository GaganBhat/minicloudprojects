import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

	public static void main(String[] args)  {

		try {
			JSONObject mainJSONResult = (JSONObject) new JSONParser().parse(FORCED_FAILURE);
			String userValUnformatted = mainJSONResult.get("value").toString();
			JSONObject userValues = (JSONObject) new JSONParser().parse(
					userValUnformatted.substring(1, userValUnformatted.length() - 1));

			System.out.println(userValues.get("isMfaRegistered"));
		} catch (Exception e ) {
			System.out.println("unfortunate " + e);
		}

	}

}

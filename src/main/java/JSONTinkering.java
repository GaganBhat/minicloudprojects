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

	public static void main(String[] args) throws ParseException {

		JSONObject mainJSONResult = (JSONObject) new JSONParser().parse(TEST_1);
		String userValUnformatted = mainJSONResult.get("value").toString();
		JSONObject userValues = (JSONObject) new JSONParser().parse(
				userValUnformatted.substring(1, userValUnformatted.length() - 1));

		System.out.println(userValues.get("isMfaRegistered"));

	}

}

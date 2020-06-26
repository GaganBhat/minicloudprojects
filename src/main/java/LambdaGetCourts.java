import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;


public class LambdaGetCourts implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
	{
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		response.setIsBase64Encoded(false);
		response.setStatusCode(200);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "text/html");
		response.setHeaders(headers);
		response.setBody("<!DOCTYPE html><html><head><title>AWS Lambda sample</title></head><body>"+
				"<h1>Welcome</h1><p>Page generated by a Lambda function.</p>" +
				"</body></html>");

		return response;
	}
}
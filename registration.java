//Created by Kyle on 8/25/16.

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;


public class RegistrationChallenge {

    //The HTTP request
    public String getTokenHttpRequest () throws Exception
    {
        String url = "http://challenge.code2040.org/api/register";
        String githubUrl = "https://github.com/kyleromero98/code2040-technical-assessment";
        String apiToken = "048276b974a3a1d8993982aec12b1ad9";

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        JSONObject jsonObject = new JSONObject();

            jsonObject.put("token", apiToken);
            jsonObject.put("github", githubUrl);

        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonObject.toString());

        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        String body = responseHandler.handleResponse(response);
        String[] parse = body.split(":");
        String token = parse [1];
        return token.substring(1, token.length() - 3);
    }
}

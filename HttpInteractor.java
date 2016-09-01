//Created by Kyle on 8/31/16

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

public class HttpInteractor
{
    public String postAndRequestData (JSONObject jsonObject, String requestUrl) throws Exception
    {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost httpPost = new HttpPost(requestUrl);

        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();

        HttpResponse response = client.execute(httpPost);

        return responseHandler.handleResponse(response);
    }
}

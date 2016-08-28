//Created by Kyle on 8/28/16.

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

public class ReverseString {

    private final String userToken = "048276b974a3a1d8993982aec12b1ad9";
    private final String requestUrl = "http://challenge.code2040.org/api/reverse";
    private final String postUrl = "http://challenge.code2040.org/api/reverse/validate";

    public static void main (String[] args) throws Exception
    {
        ReverseString reverseString = new ReverseString();

        String toReverse = "";

        try
        {
            toReverse = reverseString.requestString(reverseString.getUserToken()); 
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }

        String reversed = new StringBuffer(toReverse).reverse().toString();

        try
        {
            reverseString.postString(reversed, reverseString.getUserToken());
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    public String requestString (String token) throws Exception
    {
        if (token == null)
            return null;

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);

        HttpPost httpPost = new HttpPost(requestUrl);

        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        String body = responseHandler.handleResponse(response);

        return body;
    }

    public boolean postString (String reversedString, String token) throws Exception
    {
        if (reversedString == null || token == null)
            return false;

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", userToken);
            jsonObject.put("string", reversedString);

        HttpPost httpPost = new HttpPost(postUrl);

        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        String body = responseHandler.handleResponse(response);

        System.out.println("Response: " + body);

        return true;
    }

    public String getUserToken () {
        return userToken;
    }
}

//Created by Kyle on 8/29/16.

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import java.time.Instant;

public class Dating {

    private final String userToken = "048276b974a3a1d8993982aec12b1ad9";
    private final String requestUrl = "http://challenge.code2040.org/api/dating";
    private final String postUrl = "http://challenge.code2040.org/api/dating/validate";

    public static void main (String[] args)
    {
        Dating dating = new Dating();

        String finalDate = "";
        try
        {
            finalDate = dating.requestDate();
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }

        try
        {
            dating.postDate(finalDate);
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    public boolean postDate (String date) throws Exception
    {
        if (date == null)
            return false;

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", userToken);
            jsonObject.put("datestamp", date);

        HttpPost httpPost = new HttpPost(postUrl);

        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        String body = responseHandler.handleResponse(response);

        System.out.println(body);

        return true;
    }

    public String requestDate () throws Exception
    {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", userToken);

        HttpPost httpPost = new HttpPost(requestUrl);

        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        String body = responseHandler.handleResponse(response);

        String[] bodySplit = body.split(",");

        String startingTime = bodySplit[0].split("\"")[3];

        String interval = bodySplit[1].split(":")[1];
        interval = interval.substring(0, interval.length() - 1);

        return addInterval(startingTime, interval);
    }

    private String addInterval (String date, String interval)
    {
        if (date == null || interval == null)
            return null;

        Instant firstTime = Instant.parse(date);

        firstTime = firstTime.plusSeconds(Long.parseLong(interval));

        return firstTime.toString();
    }
}

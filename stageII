//Created by Kyle on 8/28/16.

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

public class NeedleAndHaystack {

    private final String userToken = "048276b974a3a1d8993982aec12b1ad9";
    private final String requestHaystackUrl = "http://challenge.code2040.org/api/haystack";
    private final String postNeedleUrl = "http://challenge.code2040.org/api/haystack/validate";

    public static void main (String[] args)
    {
        NeedleAndHaystack needleAndHaystack = new NeedleAndHaystack();

        int index = -1;

        try
        {
            index = needleAndHaystack.requestAndFindNeedle();
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        }

        try
        {
            needleAndHaystack.postResponse(String.valueOf(index));
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public boolean postResponse (String index) throws Exception
    {
        if (index == null)
            return false;

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", userToken);
            jsonObject.put("needle", index);

        HttpPost httpPost = new HttpPost(postNeedleUrl);

        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        String body = responseHandler.handleResponse(response);

        System.out.print(body);

        return true;
    }

    public int requestAndFindNeedle () throws Exception
    {
        int index = 0;

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", userToken);

        HttpPost httpPost = new HttpPost(requestHaystackUrl);

        StringEntity entity = new StringEntity(jsonObject.toString());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(httpPost);

        String body = responseHandler.handleResponse(response);

        String[] hay = body.split(",");

        String[] needleSection = hay[0].split(":");
        String needle = needleSection[1];

        hay[1] = hay[1].split(":")[1].substring(1);

        hay[hay.length - 1] = hay[hay.length - 1].substring(0, hay[hay.length - 1].length() - 2);

        for (int i = 1; i < hay.length; i++)
        {
            if (needle.equals(hay[i]))
            {
                index = i - 1;
                break;
            }
        }
        return index;
    }
}

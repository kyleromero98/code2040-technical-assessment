//Created by Kyle on 8/28/16.

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Prefixer {

    private static final String userToken = "048276b974a3a1d8993982aec12b1ad9";
    private static final String requestUrl = "http://challenge.code2040.org/api/prefix";
    private static final String postUrl = "http://challenge.code2040.org/api/prefix/validate";

    public static void main (String[] args)
    {
        Prefixer prefixer = new Prefixer();

        ArrayList<String> noPrefixes = new ArrayList<String>();

        try
        {
            noPrefixes = prefixer.requestAndFindNoPrefix();
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }

        try
        {
            prefixer.postPrefixes(noPrefixes);
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    public boolean postPrefixes (ArrayList<String> noPrefix) throws Exception
    {
        if (noPrefix == null)
            return false;

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", userToken);

        JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(noPrefix);
            jsonObject.put("array", jsonArray);

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

    public ArrayList<String> requestAndFindNoPrefix () throws Exception
    {
        ArrayList<String> noPrefixes = new ArrayList<String>();

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

        String[] splitBody = body.split(","); //Split the body of the request into smaller pieces

        String prefix = splitBody[0].split(":")[1]; //Parse the prefix
        prefix = prefix.substring(0, prefix.length() - 1); //Remove trailing quotation from prefix

        splitBody[1] = splitBody[1].split(":")[1].substring(1); //Remove unecessary parts from first string

        splitBody[splitBody.length - 1] = splitBody[splitBody.length - 1].substring(0, splitBody[splitBody.length - 1].length() - 2); //Remove unecessary parts from last string

        for (int i = 1; i < splitBody.length; i++)
        {
            if (!splitBody[i].startsWith(prefix))
                noPrefixes.add(splitBody[i].substring(1, splitBody[i].length() - 1)); //Remove extra quotations
        }

        return noPrefixes;
    }

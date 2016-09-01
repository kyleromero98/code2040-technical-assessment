//Created by Kyle on 8/31/16

import org.json.simple.JSONObject;

public class StageI
{
    private final String requestUrl = "http://challenge.code2040.org/api/reverse";
    private final String postUrl = "http://challenge.code2040.org/api/reverse/validate";

    public static void main (String[] args)
    {
        Registration registration = new Registration();
        HttpInteractor httpInteractor = new HttpInteractor();

        StageI stageI = new StageI();

        JSONObject jsonObjectRequest = new JSONObject();
        jsonObjectRequest.put("token", registration.getUserToken());

        String toReverse = null;

        try
        {
            toReverse = httpInteractor.postAndRequestData(jsonObjectRequest, stageI.getRequestUrl());
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }

        String reversedString = new StringBuffer(toReverse).reverse().toString();

        JSONObject jsonObjectPost = new JSONObject();
        jsonObjectPost.put("token", registration.getUserToken());
        jsonObjectPost.put("string", reversedString);

        try
        {
            System.out.println(httpInteractor.postAndRequestData(jsonObjectPost, stageI.getPostUrl()));
        }
        catch (Exception e)
        {
            e.getLocalizedMessage();
        }
    }

    public String getRequestUrl ()
    {
        return requestUrl;
    }

    public String getPostUrl ()
    {
        return postUrl;
    }
}

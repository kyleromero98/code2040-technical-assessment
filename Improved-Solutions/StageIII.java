//Created by Kyle on 8/31/16

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class StageIII
{
    private static final String requestUrl = "http://challenge.code2040.org/api/prefix";
    private static final String postUrl = "http://challenge.code2040.org/api/prefix/validate";

    public static void main (String[] args)
    {
        Registration registration = new Registration();
        StageIII stageIII = new StageIII();

        HttpInteractor httpInteractor = new HttpInteractor();

        JSONObject jsonObjectRequest = new JSONObject();
        jsonObjectRequest.put("token", registration.getUserToken());

        ArrayList<String> noPrefixes = new ArrayList<String>();

        try
        {
            noPrefixes = stageIII.findNoPrefix(httpInteractor.postAndRequestData(
                    jsonObjectRequest, stageIII.getRequestUrl()
            ));
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }

        JSONObject jsonObjectPost = new JSONObject();
        jsonObjectPost.put("token", registration.getUserToken());

        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(noPrefixes);
        jsonObjectPost.put("array", jsonArray);

        try
        {
            System.out.println(httpInteractor.postAndRequestData(jsonObjectPost, stageIII.getPostUrl()));
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    public ArrayList<String> findNoPrefix (String body)
    {
        ArrayList<String> noPrefixes = new ArrayList<>();

        String[] splitBody = body.split(","); //Split the body of the request into smaller pieces

        String prefix = splitBody[0].split(":")[1]; //Parse the prefix
        prefix = prefix.substring(0, prefix.length() - 1); //Remove trailing quotation from prefix

        splitBody[1] = splitBody[1].split(":")[1].substring(1); //Remove unecessary parts from first string

        splitBody[splitBody.length - 1] = splitBody[splitBody.length - 1].substring(
                0, splitBody[splitBody.length - 1].length() - 2
        ); //Remove unecessary parts from last string

        for (int i = 1; i < splitBody.length; i++)
        {
            if (!splitBody[i].startsWith(prefix))
                noPrefixes.add(splitBody[i].substring(1, splitBody[i].length() - 1)); //Remove extra quotations
        }

        return noPrefixes;
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

//Created by Kyle on 8/31/16

import org.json.simple.JSONObject;

public class StageII
{
    private final String requestUrl = "http://challenge.code2040.org/api/haystack";
    private final String postUrl = "http://challenge.code2040.org/api/haystack/validate";

    public static void main (String[] args)
    {
        Registration registration = new Registration();
        StageII stageII = new StageII();

        HttpInteractor httpInteractor = new HttpInteractor();

        JSONObject jsonObjectRequest = new JSONObject();
        jsonObjectRequest.put("token", registration.getUserToken());

        int indexOfNeedle = -1;

        try
        {
            indexOfNeedle = stageII.findIndexOfNeedle(
                    httpInteractor.postAndRequestData(jsonObjectRequest, stageII.requestUrl)
            );
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }

        JSONObject jsonObjectPost = new JSONObject();
        jsonObjectPost.put("token", registration.getUserToken());
        jsonObjectPost.put("needle", indexOfNeedle);

        try
        {
            System.out.println(httpInteractor.postAndRequestData(jsonObjectPost, stageII.getPostUrl()));
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    public int findIndexOfNeedle (String body)
    {
        int index = -1; //Initialization of index variable

        String[] hay = body.split(","); //Split the body into manageable segements

        String needle = hay[0].split(":")[1]; //Isolate the  needle

        hay[1] = hay[1].split(":")[1].substring(1); //Removes excess junk from first string

        hay[hay.length - 1] = hay[hay.length - 1].substring(0, hay[hay.length - 1].length() - 2);
        //Line above removes excess junk from last string

        for (int i = 1; i < hay.length; i++)
        {
            if (needle.equals(hay[i]))
            {
                index = i - 1; //Set the index to the actual index
                break;
            }
        }
        return index;
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

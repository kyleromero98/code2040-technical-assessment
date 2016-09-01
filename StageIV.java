//Created by Kyle on 8/31/16

import org.json.simple.JSONObject;

import java.time.Instant;

public class StageIV
{
    private final String requestUrl = "http://challenge.code2040.org/api/dating";
    private final String postUrl = "http://challenge.code2040.org/api/dating/validate";

    public static void main (String[] args)
    {
        Registration registration = new Registration();
        StageIV stageIV = new StageIV();

        HttpInteractor httpInteractor = new HttpInteractor();

        JSONObject jsonObjectRequest = new JSONObject();
        jsonObjectRequest.put("token", registration.getUserToken());

        String finalDate = "";

        try
        {
            finalDate = stageIV.manageRetreivedDateData(httpInteractor.postAndRequestData(
                    jsonObjectRequest, stageIV.getRequestUrl()
            ));
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }

        JSONObject jsonObjectPost = new JSONObject();
        jsonObjectPost.put("token", registration.getUserToken());
        jsonObjectPost.put("datestamp", finalDate);

        try
        {
            System.out.println(httpInteractor.postAndRequestData(jsonObjectPost, stageIV.getPostUrl()));
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    public String manageRetreivedDateData (String body)
    {
        String[] bodySplit = body.split(",");

        String startingTime = bodySplit[0].split("\"")[3];

        String interval = bodySplit[1].split(":")[1];
        interval = interval.substring(0, interval.length() - 1);

        return addInterval(startingTime, interval);
    }

    public String addInterval (String date, String interval)
    {
        if (date == null || interval == null)
            return null;

        Instant firstTime = Instant.parse(date);

        firstTime = firstTime.plusSeconds(Long.parseLong(interval));

        return firstTime.toString();
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

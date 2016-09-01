//Created by Kyle on 8/31/16

import org.json.simple.JSONObject;

public class Registration
{
    private final String userToken = "048276b974a3a1d8993982aec12b1ad9";

    private final String postUrl = "http://challenge.code2040.org/api/register";
    private final String githubUrl = "https://github.com/kyleromero98/code2040-technical-assessment";

    public static void main (String[] args)
    {
        Registration registration = new Registration();
        HttpInteractor httpInteractor = new HttpInteractor();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", registration.getUserToken());
        jsonObject.put("github", registration.getGithubUrl());

        try
        {
            System.out.println(httpInteractor.postAndRequestData(jsonObject, registration.getPostUrl()));
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    public String getUserToken ()
    {
        return userToken;
    }

    public String getPostUrl ()
    {
        return postUrl;
    }

    public String getGithubUrl ()
    {
        return githubUrl;
    }
}

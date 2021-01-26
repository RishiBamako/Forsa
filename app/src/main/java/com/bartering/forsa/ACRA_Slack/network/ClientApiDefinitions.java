package com.bartering.forsa.ACRA_Slack.network;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by rishiojha on 11/3/19.
 */
public interface ClientApiDefinitions {

    @Headers("Content-Type: application/json, Content-Length: 8190")
    @POST(Urls.SLACK_CRASH_CHANNEL)
    void sendCrash(
            @Body SlackMessageWrapper wrapper,
            Callback<String> callback
    );

}

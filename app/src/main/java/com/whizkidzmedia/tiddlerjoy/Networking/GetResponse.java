package com.whizkidzmedia.tiddlerjoy.Networking;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;

import java.io.IOException;

/**
 * Created by Sourabh Dixit on 20-05-2016.
 */
public class GetResponse {

    public static Response PostToServer(String url, String json)
    {
        OkHttpClient client = new OkHttpClient();
        Response response = null;

        RequestBody body = RequestBody.create(AppConstants.JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response getServerResponse(String url)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
           return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

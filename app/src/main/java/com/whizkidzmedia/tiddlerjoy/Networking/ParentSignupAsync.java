package com.whizkidzmedia.tiddlerjoy.Networking;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.whizkidzmedia.tiddlerjoy.Utilities.API;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sourabh Dixit on 15-03-2016.
 */
public class ParentSignupAsync extends AsyncTask<Void,Void,String> {
    String userName, userEmail,userContactNumber;
    public static final String USER_REGISTER_FIRSTNAME="first_name";
    public static final String USER_REGISTER_EMAIL="email";
    public static final String USER_REGISTER_MOBILE="mobile_number";
    public static final String USER_REGISTER_USERID="id";
    public static final String USER_REGISTER_TOKEN="token";

    public ParentSignupAsync(String username,String useremail,String usercontact)
    {
        this.userName = username;
        this.userEmail = useremail;
        this.userContactNumber = usercontact;
    }

    @Override
    protected String doInBackground(Void... params) {
        String url = API.USER_REGISTER_API;
        JSONObject jsonObject = new JSONObject();
        String responseString = "";
        try {
            JSONObject jobj = new JSONObject();
            jobj.put(USER_REGISTER_FIRSTNAME,userName); // user contact number
            jobj.put(USER_REGISTER_EMAIL,userEmail);            //user email
            jobj.put(USER_REGISTER_MOBILE,userContactNumber);             //username or first_name
            final String resp = postToServer(url,jobj.toString());
            JSONObject obj = new JSONObject(resp);
            responseString = resp.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public static String postToServer(String url, String json)
    {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(AppConstants.JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

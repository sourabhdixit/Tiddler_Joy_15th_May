package com.whizkidzmedia.tiddlerjoy.Networking;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.Utilities.API;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sourabh Dixit on 05-07-2016.
 */
public class UpdateChildProfileAsync extends AsyncTask<Void,Void,ChildProfile> {

        String childId,maxDailyTimeMins,hoursFrom,hourTo;
    public static final String CHILD_ID="id",PARENT = "parent",CHILD_NAME = "name",CHILD_GENDER = "gender",CHILD_DOB="dob",CHILD_IMAGE="image",
    TIME_LIMIT="time_limit",PLAY_START= "play_start",PLAY_END="play_end",SLEEP_START = "sleep_start",SLEEP_END = "sleep_end";
    public UpdateChildProfileAsync(String childid, String maxMins,String startHrs,String endHrs)
        {

        this.childId = childid;
        this.maxDailyTimeMins=maxMins;
        this.hoursFrom=startHrs;
        this.hourTo=endHrs;

        }

@Override
protected ChildProfile doInBackground(Void... params) {
        String url = API.UPDATE_CHILD_PROFILE+childId+"/";
        String responseString = "";
        JSONObject jObj = new JSONObject();
        try{
        jObj.put(PLAY_START,hoursFrom+":00:00");
        jObj.put(PLAY_END,hourTo+":00:00");
        //jObj.put(,String.valueOf(maxDailyTimeMins));
        }
        catch(JSONException je){}
        String resp = postToServer(url,jObj.toString());
        responseString = resp.toString();
        //ChildProfile profile = parseResponse(responseString);
        return null;

        }


private void parseResponse(String responseString) {

////        ChildProfile childProfile = new ChildProfile();
////
////        if (responseString.contains(AppConstants.CHILD_NAME) && responseString.contains(AppConstants.CHILD_ID) && responseString.contains(AppConstants.CHILD_GENDER)
////        && responseString.contains(AppConstants.CHILD_DOB) && responseString.contains(AppConstants.CHILD_IMAGE) && responseString.contains(AppConstants.CHILD_PARENT_ID)) {
////        try {
////        JSONObject obj = new JSONObject(responseString);
////        ChildProfile child = new ChildProfile();
////        String name = obj.getString(AppConstants.CHILD_NAME);
////        String parentId = obj.getString(AppConstants.CHILD_PARENT_ID);
////        String gender = obj.getString(AppConstants.CHILD_GENDER);
////        String dob = obj.getString(AppConstants.CHILD_DOB);
////        String childId = obj.getString(AppConstants.CHILD_ID);
////        String img = obj.getString(AppConstants.CHILD_IMAGE);
////        child.childID =(childId);
////        child.childName=name;
////        child.sex=(gender);
////        child.dob = (dob);
////        child.childParentId=(parentId);
////        child.childUsageStartHour = Integer.parseInt(obj.getString(AppConstants.AVAIL_START).split(":")[0]);
////        child.childUsageEndHour = Integer.parseInt(obj.getString(AppConstants.AVAIL_END).split(":")[0]);
////        child.maxDailyTimeHours = Integer.parseInt(obj.getString(AppConstants.MAX_DAILY_TIME))/60;
////        child.maxDailyTimeMins = Integer.parseInt(obj.getString(AppConstants.MAX_DAILY_TIME))%60;
////        childProfile=child;
////        } catch (JSONException ex) {
////        }
//
//        }
//        return childProfile;
        }

public static String postToServer(String url, String json)
        {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(AppConstants.JSON, json);
        Request request = new Request.Builder()
        .url(url)
        .patch(body)
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


package com.whizkidzmedia.tiddlerjoy.Networking;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.Utilities.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Sourabh Dixit on 27-12-2015.
 */
public class GetAllChildProfilesAsyncTask extends AsyncTask<Void,Void,ArrayList<ChildProfile>> {

        String userId;
public GetAllChildProfilesAsyncTask(String userid)
        {
        this.userId = userid;

        }

@Override
protected ArrayList<ChildProfile> doInBackground(Void... voids) {
        String url = API.CHILD_PROFILES_LISTINGS;
        JSONObject jsonObject = new JSONObject();
        String responseString = "";
        OkHttpClient client= new OkHttpClient();

        String resp = getApiContents(url);
        responseString = resp.toString();
        ArrayList<ChildProfile> profiles = parseResponse(responseString);
        return profiles;
        }

private ArrayList<ChildProfile> parseResponse(String responseString) {

        ArrayList<ChildProfile> childrenProfiles = new ArrayList<ChildProfile>();

        if (responseString.contains(ChildProfile.CHILD_COUNT) && responseString.contains(ChildProfile.CHILD_RESULTS)) {
        try {
            JSONObject jObj = new JSONObject(responseString);
            String results = jObj.getString(ChildProfile.CHILD_RESULTS);
        JSONArray childObjArray = new JSONArray(results);
            if(childObjArray!=null)
        for(int i=0;i<childObjArray.length();i++)
        {
        JSONObject obj = childObjArray.getJSONObject(i);
        ChildProfile child = new ChildProfile();
        String name = obj.getString(ChildProfile.CHILD_NAME);
        String parentid = obj.getString(ChildProfile.CHILD_PARENT_ID);
        String gender = obj.getString(ChildProfile.CHILD_GENDER);
        String dob = obj.getString(ChildProfile.CHILD_DOB);
        String childid = parentid+dob;//obj.getString(ChildProfile.CHILD_ID);
        String img = obj.getString(ChildProfile.CHILD_IMAGE);
        child.childID = (childid);
        child.childName = (name);
        child.gender = (gender);
        child.dob = (dob);
        child.childParentId = (parentid);
                child.actualChildId = Calendar.getInstance().getTimeInMillis()+Long.parseLong(childid);
               // child.childUsageStartHour = Integer.parseInt(obj.getString(AppConstants.AVAIL_START).split(":")[0]);
               // child.childUsageEndHour = Integer.parseInt(obj.getString(AppConstants.AVAIL_END).split(":")[0]);
               // child.maxDailyTimeHours = Integer.parseInt(obj.getString(AppConstants.MAX_DAILY_TIME))/60;
               // child.maxDailyTimeMins = Integer.parseInt(obj.getString(AppConstants.MAX_DAILY_TIME))%60;
        //child.setChildImage(img

        childrenProfiles.add(child);
        }
        } catch (JSONException ex) {
        }

        }
        return childrenProfiles;
        }

public static String getApiContents(String url)
        {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
        .url(url)
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




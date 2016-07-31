package com.whizkidzmedia.tiddlerjoy.Networking;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.Utilities.API;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Sourabh Dixit on 26-12-2015.
 */
public class AddChildProfileAsyncTask extends AsyncTask<Void,Void,ChildProfile> {

        String parentId,childName,childGender,childDob,maxDailyTimeMins,hoursFrom,hourTo,childImage;
        int gender;
public AddChildProfileAsyncTask(String parentId, String name, int gender, String dob)
        {
        this.parentId = parentId;
        this.childName = name;
                if(gender==1)
                this.childGender = "1";
                else
                this.childGender = "2";
        this.childDob = dob;


        }

@Override
protected ChildProfile doInBackground(Void... voids) {
        String url = API.CHILD_PROFILE_REGISTER;
        JSONObject jsonObject = new JSONObject();
        String responseString = "";
        OkHttpClient client= new OkHttpClient();
        JSONObject jObj = new JSONObject();
        try{
        jObj.put(ChildProfile.CHILD_PARENT_ID,parentId);
        jObj.put(ChildProfile.CHILD_NAME,childName);
        jObj.put(ChildProfile.CHILD_GENDER,childGender);
        jObj.put(ChildProfile.CHILD_DOB,childDob);
        }catch(JSONException ex){}
        String resp = postToServer(url,jObj.toString());
        responseString = resp.toString();
        ChildProfile profile = parseResponse(responseString);
        return profile;
        }

private ChildProfile parseResponse(String responseString) {

        ChildProfile childProfile = new ChildProfile();

        if (responseString.contains(ChildProfile.CHILD_NAME)  && responseString.contains(ChildProfile.CHILD_GENDER)  && responseString.contains(ChildProfile.CHILD_ID)
        && responseString.contains(ChildProfile.CHILD_DOB) && responseString.contains(ChildProfile.CHILD_IMAGE) && responseString.contains(ChildProfile.CHILD_PARENT_ID)) {
        try {
        JSONObject obj = new JSONObject(responseString);
        ChildProfile child = new ChildProfile();
        String name = obj.getString(ChildProfile.CHILD_NAME);
        String parentId = obj.getString(ChildProfile.CHILD_PARENT_ID);
        String gender = obj.getString(ChildProfile.CHILD_GENDER);
        String dob = obj.getString(ChildProfile.CHILD_DOB);
        String childId = obj.getString(ChildProfile.CHILD_ID);
        String img = obj.getString(ChildProfile.CHILD_IMAGE);
        child.childID =(childId);
        child.childName=name;
        child.gender=(gender);
        child.dob = (dob);
        child.childParentId=(parentId);
        childProfile=child;
        } catch (JSONException ex) {
        }

        }
        else{
                childProfile.childName = responseString;
        }
        return childProfile;
        }

        private boolean storeImage(Bitmap imageData, String filename) {
                //get path to external storage (SD card)
                String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDir/myImages/";
                File sdIconStorageDir = new File(iconsStoragePath);

                //create storage directories, if they don't exist
                sdIconStorageDir.mkdirs();

                try {
                        String filePath = sdIconStorageDir.toString() + filename;
                        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
                        //choose another format if PNG doesn't suit you
                        imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);

                        bos.flush();
                        bos.close();

                } catch (FileNotFoundException e) {
                        Log.w("TAG", "Error saving image file: " + e.getMessage());
                        return false;
                } catch (IOException e) {
                        Log.w("TAG", "Error saving image file: " + e.getMessage());
                        return false;
                }

                return true;
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
            response.code();
        return response.body().string();
        } catch (IOException e) {
        e.printStackTrace();
        }
        return "";
        }

        }

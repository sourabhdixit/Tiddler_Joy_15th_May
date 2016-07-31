package com.whizkidzmedia.tiddlerjoy.Networking;

import android.os.AsyncTask;

import com.squareup.okhttp.Response;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.Utilities.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourabh Dixit on 21-05-2016.
 */
public class FetchVideosByUiTagAsync extends AsyncTask {

    public static final String VIDEO_UI_TAG_ID="tag_id";
    public static final String USER_DEVICE_ID="device_id";
    public static final String VIDEO_ID = "id";
    public static final String VIDEO_URL = "url";
    public static final String IS_YOUTUBE = "is_youtube";
    public static final String IS_CHANGE = "is_change";
    public static final String VIDEO_STATUS = "status";
    public static final int RESPONSE_CODE_OK = 200;
    public static final String RESPONSE_STRING_ERROR = "ERROR";

    public String uiTagString;
    public boolean isFirstTime;
    public String deviceId;
    public String childId;
    String url ;
    public List<ChildVideo> videoContent;
    public long transactionId;

    public FetchVideosByUiTagAsync(String uitag, Boolean bool, String deviceid, String childid, List<ChildVideo> videos,long transactioTimeStamp)
    {
        uiTagString = uitag;
        isFirstTime = bool;
        deviceId = deviceid;
        childId = childid;
        url = API.CONTENT_CHILD_PROFILES_LISTINGS;
        videoContent = videos;
        this.transactionId = transactioTimeStamp;
    }

    @Override
    protected Object doInBackground(Object[] params)
    {

        JSONObject jsonObject;
        String responseString = "";
        if(isFirstTime)
        jsonObject= getFirstTimeJsonObj();
        else
        jsonObject = getJsonObj();
        try {
            Response response = GetResponse.PostToServer(url, jsonObject.toString());
            if (response.code() == RESPONSE_CODE_OK) {
                responseString = (response.body().string());
                ArrayList<ChildVideo> videos = parseResponse(responseString);
                return videos;
            }
            else
                responseString = RESPONSE_STRING_ERROR;
        }catch(IOException e){
            responseString = RESPONSE_STRING_ERROR;
        }
        return responseString;
    }

    private ArrayList<ChildVideo> parseResponse(String response) {

        ArrayList<ChildVideo> videos = new ArrayList<ChildVideo>();

        try{
              JSONObject jsonObject = new JSONObject(response);
            for(int i=0;i<4;i++) {
                ChildVideo video = new ChildVideo();
                JSONObject object = jsonObject.getJSONObject(String.valueOf(i));
                video.videoUrl = object.getString(VIDEO_URL);
                video.videoId = object.getString(VIDEO_ID);
                video.youTubeId = video.videoUrl.split("=")[1];
                video.isVideoYouTube = object.getBoolean(IS_YOUTUBE);
                video.videoUiTag = uiTagString;
                video.childId = childId;
                if(!isFirstTime)
                video.videoIteration=videoContent.get(i).videoIteration;
                else
                video.videoIteration = 0;
                video.transactionId = this.transactionId;
                video.save();
                videos.add(video);
            }

        }
        catch (JSONException jEx){}
        return  videos;
    }

    private JSONObject getJsonObj() {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put(VIDEO_UI_TAG_ID, uiTagString); // video category id
            jobj.put("child_id", childId);
            JSONObject videoJsonObj=null;
            for(int i=0;i<4;i++)
            {videoJsonObj = (getVideoJsonObj(i));
            jobj.put(String.valueOf(i),videoJsonObj);}
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jobj;
    }

    private JSONObject getVideoJsonObj(int i) {
        JSONObject videoObj = new JSONObject();
        ChildVideo video = videoContent.get(i);
        try {
            videoObj.put(VIDEO_ID, video.videoId);
            videoObj.put(VIDEO_URL,video.videoUrl);
            videoObj.put(IS_YOUTUBE,video.isVideoYouTube);
            videoObj.put(IS_CHANGE,video.isVideoChange);
            videoObj.put(VIDEO_STATUS,video.videoWatchStatus);
        }catch(JSONException j)
        {}
        return videoObj;
    }

    private JSONObject getFirstTimeJsonObj() {

        JSONObject jobj = new JSONObject();
        try {
            jobj.put(VIDEO_UI_TAG_ID,uiTagString); // video category id
            jobj.put("child_id", childId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jobj;
    }
}

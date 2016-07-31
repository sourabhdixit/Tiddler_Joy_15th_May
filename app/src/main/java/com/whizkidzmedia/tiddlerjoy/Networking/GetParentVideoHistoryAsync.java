package com.whizkidzmedia.tiddlerjoy.Networking;

import android.os.AsyncTask;

import com.squareup.okhttp.Response;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildHistoryVideo;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.Utilities.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourabh Dixit on 28-06-2016.
 */
public class GetParentVideoHistoryAsync extends AsyncTask {

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
    public String eldaId;
    String url ;
    public List<ChildVideo> videoContent;
    public long transactionId;

    public GetParentVideoHistoryAsync(String childid,String eldaid)
    {
        childId = childid;
        eldaId = eldaid;
        url = API.CONTENT_PARENT_VIDEO_HISTORY+childId+"/"+eldaId+"/";


    }

    @Override
    protected Object doInBackground(Object[] params)
    {

        JSONObject jsonObject;
        String responseString = "";
        try {
            Response response = GetResponse.getServerResponse(url);
            if (response.code() == RESPONSE_CODE_OK) {
                responseString = (response.body().string());
                ArrayList<ChildHistoryVideo> videos = parseResponse(responseString);
                return videos;
            }
            else {
                responseString = RESPONSE_STRING_ERROR;
                return responseString;
            }
        }catch(IOException e){
            responseString = RESPONSE_STRING_ERROR;
            return responseString;
        }
//        return responseString;
    }

    private ArrayList<ChildHistoryVideo> parseResponse(String response) {

        ArrayList<ChildHistoryVideo> videos = new ArrayList<ChildHistoryVideo>();

        try{
            JSONObject jsonObject = new JSONObject(response);
            int count = Integer.parseInt(jsonObject.getString("count"));

            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int j=0;j<jsonArray.length();j++) {
                ChildHistoryVideo video = new ChildHistoryVideo();
                JSONObject object = jsonArray.getJSONObject(j);
                JSONObject jObject = object.getJSONObject("contentv2");
                video.videoId = jObject.getString(VIDEO_ID);
                video.videoUrl = jObject.getString(VIDEO_URL);
                video.youTubeId = video.videoUrl.split("=")[1];
                video.isVideoYouTube = jObject.getBoolean(IS_YOUTUBE);
                video.videoUiTag = uiTagString;
                video.childId = childId;
                video.save();
                videos.add(video);
            }

        }
        catch (JSONException jEx){}
        return  videos;
    }
}

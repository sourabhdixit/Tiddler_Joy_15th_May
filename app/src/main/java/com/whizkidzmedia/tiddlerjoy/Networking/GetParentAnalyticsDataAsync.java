package com.whizkidzmedia.tiddlerjoy.Networking;

import android.os.AsyncTask;

import com.activeandroid.query.Select;
import com.squareup.okhttp.Response;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildHistoryVideo;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.DataModels.EarlyLearningDomainData;
import com.whizkidzmedia.tiddlerjoy.DataModels.LearningAreaData;
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
public class GetParentAnalyticsDataAsync extends AsyncTask<Void,Void,Boolean> {

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
    static final boolean SUCCESS = Boolean.TRUE, FAIL = Boolean.FALSE;

    public GetParentAnalyticsDataAsync(String childid)
    {
        childId = childid;
        url = API.CONTENT_PARENT_ANALYTICS+childId+"/";
    }

    @Override
    protected Boolean doInBackground(Void[] params)
    {

        String responseString = "";
        Boolean isSuccess=false;
        try {
            Response response = GetResponse.getServerResponse(this.url);
            if (response.code() == RESPONSE_CODE_OK) {
                responseString = (response.body().string());
               isSuccess = parseResponse(responseString);
                return isSuccess;
            }
            else
                return isSuccess;
        }catch(IOException e){
            responseString = RESPONSE_STRING_ERROR;
            return false;
        }

    }

    private boolean parseResponse(String response) {

        ArrayList<ChildVideo> videos = new ArrayList<ChildVideo>();
        boolean isSuccess=false;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jObj = jsonObject.getJSONObject(LearningAreaData.ALL_ABOUT_ME);
            if(Integer.parseInt(jObj.getString("count"))>0)
            isSuccess = setLearningAreaData(jObj, LearningAreaData.ALL_ABOUT_ME);
            //else setEmptyLearningAreaData(LearningAreaData.ALL_ABOUT_ME);
            JSONObject jObj1 = jsonObject.getJSONObject(LearningAreaData.COMMUNICATION);
            if(Integer.parseInt(jObj1.getString("count"))>0)
            isSuccess=setLearningAreaData(jObj1, LearningAreaData.COMMUNICATION);
            //else setEmptyLearningAreaData(LearningAreaData.COMMUNICATION);
            JSONObject jObj2 = jsonObject.getJSONObject(LearningAreaData.CREATIVITY);
            if(Integer.parseInt(jObj2.getString("count"))>0)
            isSuccess=setLearningAreaData(jObj2, LearningAreaData.CREATIVITY);
            //else setEmptyLearningAreaData(LearningAreaData.CREATIVITY);
            JSONObject jObj3 = jsonObject.getJSONObject(LearningAreaData.BEAUTIFUL_WORLD);
            if(Integer.parseInt(jObj3.getString("count"))>0)
            isSuccess=setLearningAreaData(jObj3, LearningAreaData.BEAUTIFUL_WORLD);
            //else setEmptyLearningAreaData(LearningAreaData.BEAUTIFUL_WORLD);
            JSONObject jObj4 = jsonObject.getJSONObject(LearningAreaData.EARLY_MATHS);
            if(Integer.parseInt(jObj4.getString("count"))>0)
            isSuccess=setLearningAreaData(jObj4, LearningAreaData.EARLY_MATHS);
            //else setEmptyLearningAreaData(LearningAreaData.EARLY_MATHS);
        }

        catch (JSONException jEx)
        {
            isSuccess=false;
        }
    return isSuccess;
    }

//    private void setEmptyLearningAreaData(String areaString) {
//        LearningAreaData data = new LearningAreaData();
//        data.learningDomain = areaString;
//        data.currentVideoCount = 0;
//        data.save();
//    }

    private boolean setLearningAreaData(JSONObject jObj, String learningAreaName) {
        try {
            String count = jObj.getString(LearningAreaData.COUNT);
            int intCount = Integer.parseInt(count);
            boolean success = false;
            int id = jObj.getInt(LearningAreaData.LEARNING_AREA_ID);
            JSONArray jsonArray = jObj.getJSONArray(LearningAreaData.ELDA_SUB_AREAS);
            LearningAreaData data = new Select().from(LearningAreaData.class).where(" Name = ?",learningAreaName).executeSingle();
            data.currentVideoCount = Integer.parseInt(count);
            data.domainId = id;
            data.totalVideoCount = LearningAreaData.TOTAL_VIDEO_COUNT;
            data.learningDomain = learningAreaName;
            data.totalEldaCount = 0;
            data.save();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                success = setEldaData(jsonObject, learningAreaName, id);
            }
            return success;
        }catch(JSONException ex){
            return false;
            }
    }

    private boolean setEldaData(JSONObject jsonObject, String learningAreaName, int learningAreaId) {
        try {
            int count = jsonObject.getInt(LearningAreaData.COUNT);
            String name = jsonObject.getString(EarlyLearningDomainData.ELDA_NAME);
            int id = jsonObject.getInt(EarlyLearningDomainData.ELDA_ID);
            int totalCount = EarlyLearningDomainData.MAX_VIDEO_COUNT_VALUE;
            EarlyLearningDomainData data = new Select().from(EarlyLearningDomainData.class).where(" Name = ?",name).executeSingle();
//            EarlyLearningDomainData data = new EarlyLearningDomainData();
            data.currentVideoCount=count;
            data.eldaId=id;
            data.eldaName = name;
            data.learningAreaId = learningAreaId;
            data.learningAreaName = learningAreaName;
            data.totalVideoCount = totalCount;
            data.save();
            return true;
            }
        catch(JSONException ex){
            return false;
        }

    }

    private JSONObject getJsonObj() {
        JSONObject jobj = new JSONObject();
        try {
            jobj.put(VIDEO_UI_TAG_ID, uiTagString); // video category id
            jobj.put(USER_DEVICE_ID, deviceId);
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
            jobj.put(USER_DEVICE_ID, deviceId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jobj;
    }


}

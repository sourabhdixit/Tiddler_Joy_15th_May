package com.whizkidzmedia.tiddlerjoy.Networking;

import android.os.AsyncTask;

import com.activeandroid.query.Select;
import com.squareup.okhttp.Response;
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
 * Created by Sourabh Dixit on 09-07-2016.
 */
public class GetEldaListingsAsync extends AsyncTask<Void,Void,Boolean> {

    public static final String VIDEO_UI_TAG_ID="tag_id";
    public static final String USER_DEVICE_ID="device_id";
    public static final String ELDA_ID = "id";
    public static final String ELDA_NAME = "name";
    public static final String ELDA = "elda";
    public static final String LEARNING_AREA_NAME = "name";
    public static final String LEARNING_AREA_ID = "id";
    public static final int RESPONSE_CODE_OK = 200;
    public static final String RESPONSE_STRING_ERROR = "ERROR";

    public String uiTagString;
    public boolean isFirstTime;
    public String deviceId;
    public String childId;
    String url ;
    public List<ChildVideo> videoContent;
    public long transactionId;

//    public GetEldaListingsAsync(String childid)
//    {
//        childId = childid;
//        url = API.CONTENT_PARENT_ANALYTICS+childId+"/";
//    }

    @Override
    protected Boolean doInBackground(Void[] params)
    {

        String responseString = "";
        Boolean isSuccess=false;
        try {
            Response response = GetResponse.getServerResponse(API.ELDA_LISTING_URL);
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
        boolean isSuccess=true;

        try {
            JSONObject jsonObject = new JSONObject(response);
            int eldaCount = jsonObject.getInt("count");
            JSONArray resultsJson = new JSONArray(jsonObject.getString("results"));
            for(int i =0;i<resultsJson.length();i++)
            {
                JSONObject jObj = resultsJson.getJSONObject(i);
                String eldaName = jObj.getString(ELDA_NAME);
                String eldaId = jObj.getString(ELDA_ID);
                EarlyLearningDomainData eldaData = new Select().from(EarlyLearningDomainData.class).where(" EldaId = ?",eldaId).executeSingle();
                if(eldaData==null) {
                    eldaData = new EarlyLearningDomainData();
                    eldaData.eldaName = eldaName;
                    eldaData.eldaId = Integer.parseInt(eldaId);
                    eldaData.currentVideoCount = 0;
                    eldaData.totalVideoCount = EarlyLearningDomainData.MAX_VIDEO_COUNT_VALUE;
                }


                JSONObject obj = new JSONObject(jObj.getString(ELDA));
                String learningDomain = obj.getString(LEARNING_AREA_NAME);
                String leaningDomainId = obj.getString(LEARNING_AREA_ID);
                eldaData.learningAreaName = learningDomain;
                eldaData.learningAreaId = Integer.parseInt(leaningDomainId);
                eldaData.save();

                LearningAreaData learningAreaData = new Select().from(LearningAreaData.class).where(" DomainId = ?",leaningDomainId).executeSingle();
                if(learningAreaData==null) {
                    learningAreaData = new LearningAreaData();
                    learningAreaData.learningDomain = learningDomain;
                    learningAreaData.domainId = Integer.parseInt(leaningDomainId);
                    learningAreaData.currentVideoCount=0;
                    learningAreaData.save();
                }

            }
//            JSONObject jObj = jsonObject.getJSONObject(LearningAreaData.ALL_ABOUT_ME);
//            if(Integer.parseInt(jObj.getString("count"))>0)
//                isSuccess = setLearningAreaData(jObj, LearningAreaData.ALL_ABOUT_ME);
//            else setEmptyLearningAreaData(LearningAreaData.ALL_ABOUT_ME);
//            JSONObject jObj1 = jsonObject.getJSONObject(LearningAreaData.COMMUNICATION);
//            if(Integer.parseInt(jObj1.getString("count"))>0)
//                isSuccess=setLearningAreaData(jObj1, LearningAreaData.COMMUNICATION);
//            else setEmptyLearningAreaData(LearningAreaData.COMMUNICATION);
//            JSONObject jObj2 = jsonObject.getJSONObject(LearningAreaData.CREATIVITY);
//            if(Integer.parseInt(jObj2.getString("count"))>0)
//                isSuccess=setLearningAreaData(jObj2, LearningAreaData.CREATIVITY);
//            else setEmptyLearningAreaData(LearningAreaData.CREATIVITY);
//            JSONObject jObj3 = jsonObject.getJSONObject(LearningAreaData.BEAUTIFUL_WORLD);
//            if(Integer.parseInt(jObj3.getString("count"))>0)
//                isSuccess=setLearningAreaData(jObj3, LearningAreaData.BEAUTIFUL_WORLD);
//            else setEmptyLearningAreaData(LearningAreaData.BEAUTIFUL_WORLD);
//            JSONObject jObj4 = jsonObject.getJSONObject(LearningAreaData.EARLY_MATHS);
//            if(Integer.parseInt(jObj4.getString("count"))>0)
//                isSuccess=setLearningAreaData(jObj4, LearningAreaData.EARLY_MATHS);
//            else setEmptyLearningAreaData(LearningAreaData.EARLY_MATHS);
        }

        catch (JSONException jEx)
        {
            isSuccess=false;
        }
        return isSuccess;
    }

}


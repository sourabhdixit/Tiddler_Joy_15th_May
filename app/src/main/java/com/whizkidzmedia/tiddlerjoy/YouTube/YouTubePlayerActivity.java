package com.whizkidzmedia.tiddlerjoy.YouTube;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.whizkidzmedia.tiddlerjoy.ChildInterface.ChildWatchActivity;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildHistoryVideo;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.Networking.FetchVideosByUiTagAsync;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.ConnectionDetector;
import com.whizkidzmedia.tiddlerjoy.Utilities.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class YouTubePlayerActivity extends YouTubeFailureRecoveryActivity {

    YouTubePlayer player;
    YouTubePlayerView playerView;
    String videoId;
    long totalVideoLength,transactionId;
    int videoIndex;
    public List<ChildVideo> videoContent;
    boolean updatingResponse=false,videoStarted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_you_tube_player);
        playerView = (YouTubePlayerView) findViewById(R.id.player_view);
        playerView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        transactionId = this.getSharedPreferences(SharedPrefs.SHARED_PREFS_NAME, 0).getLong(SharedPrefs.TRANSACTION_ID, 0);
        videoContent = new Select().from(ChildVideo.class).where("VideoUiTag = ? ", ChildWatchActivity.CURRENT_TAG).execute();
        if(videoContent!=null)
            videoContent = videoContent.subList(videoContent.size()-4,videoContent.size());
        videoId= getIntent().getStringExtra("VideoId");
        videoIndex = getIntent().getIntExtra("VideoIndex", 0);
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        this.player=player;
        this.player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        this.player.setPlaybackEventListener(playbackEventListener);
        this.player.setPlayerStateChangeListener(playerStateChangeListener);
        if (!wasRestored)
        {
            this.player.loadVideo(videoId);
            totalVideoLength = this.player.getDurationMillis();
            //Toast.makeText(getApplicationContext()," Length :"+totalVideoLength/1000*60,Toast.LENGTH_LONG).show();
        }

    }

    protected YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    protected YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

            //Toast.makeText(getApplicationContext(),"OnLOaded:"+s,Toast.LENGTH_LONG).show();
            totalVideoLength = player.getDurationMillis();
            videoStarted=true;


        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

            totalVideoLength = player.getDurationMillis();
            videoStarted=true;


        }

        @Override
        public void onVideoEnded() {
            videoStarted=false;
            onBackPressed();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onBackPressed() {

        if(videoIndex>=0&&!updatingResponse&&videoStarted)
        updateVideoResponse();
        else
        super.onBackPressed();
    }

    private synchronized void updateVideoResponse() {
        updatingResponse=true;
        long currentTime = player.getCurrentTimeMillis();
        long rejectThreshold = totalVideoLength/3;
        long acceptMinimum = totalVideoLength*2/3;
        String statusVal = "";
        for(int i=0;i<4;i++) {
            if (currentTime >= rejectThreshold && currentTime < acceptMinimum)
                statusVal = "2";
            else if (currentTime >= acceptMinimum)
                statusVal = "3";
            else
                statusVal = "1";
            //Toast.makeText(getApplicationContext(), "Total:" + totalVideoLength + "\n Current:" + currentTime + "\n" + statusVal, Toast.LENGTH_LONG).show();
            ChildVideo videoClicked;
            if (i == videoIndex) {
                videoClicked = videoContent.get(videoIndex);//new Select().from(ChildVideo.class).where("YouTubeVideoId = ?",String.valueOf(videoId)).executeSingle();
                videoClicked.isVideoWatched = true;
                videoClicked.videoWatchStatus = statusVal;
                saveToChildHistory(videoClicked);
            } else {
                videoClicked = videoContent.get(i);
                videoClicked.videoWatchStatus = "2";
                statusVal = "2";
            }

            int iteration = videoClicked.videoIteration;
            if (iteration > 2) {
                videoClicked.isVideoChange = true;
                videoClicked.videoIteration =0;
            }

            else if (iteration <= 2 && statusVal.equals("2"))
                videoClicked.isVideoChange = false;
            else
                videoClicked.isVideoChange = true;
            videoClicked.save();
        }
        updateVideoResponseOnServer();

    }

    private void saveToChildHistory(ChildVideo videoClicked) {

        ChildHistoryVideo childHistoryVideo = new ChildHistoryVideo();
        childHistoryVideo.childId = videoClicked.childId;
        childHistoryVideo.videoId = videoClicked.videoId;
        childHistoryVideo.videoUiTag = videoClicked.videoUiTag;
        childHistoryVideo.videoUrl = videoClicked.videoUrl;
        childHistoryVideo.videoWatchStatus = videoClicked.videoWatchStatus;
        childHistoryVideo.youTubeId = videoClicked.youTubeId;
        childHistoryVideo.save();

    }

    private void updateVideoResponseOnServer() {

        if(ConnectionDetector.isConnectedToInternet(YouTubePlayerActivity.this))
        new FetchVideosByUiTagAsync("1",false,"",getChildId(),videoContent,transactionId)
        {

            @Override
            protected void onPreExecute() {
                updatingResponse=true;
            }

            @Override
            protected void onPostExecute(Object response) {
                if(!response.equals(FetchVideosByUiTagAsync.RESPONSE_STRING_ERROR))
                saveToChildVideo( response );
                updatingResponse=false;
                startActivity(new Intent(YouTubePlayerActivity.this,ChildWatchActivity.class));
                finish();
            }}.execute();
        else
            updatingResponse=false;
    }

    private String getChildId() {

        ChildProfile profile = new Select().all().from(ChildProfile.class).executeSingle();
        if(profile!=null)
            return profile.childID;
        else
            return "";

    }

    private void saveToChildVideo(Object o) {
        ArrayList<ChildVideo> childVideos = (ArrayList<ChildVideo>)o;
        //new Delete().from(ChildVideo.class).where("VideoUiTag = ?", ChildWatchActivity.RHYTHM).execute();
        int i=0;
        for(ChildVideo video: childVideos) {
            if(i==videoIndex)
                video.videoIteration = 0;
            else
            video.videoIteration = childVideos.get(i).videoIteration;
            video.save();
            i++;
        }
    }

    @Override
    protected void onResume() {
       // Toast.makeText(getApplicationContext(),"OnResume",Toast.LENGTH_LONG).show();
        super.onResume();
    }

    @Override
    protected void onStop() {
       // Toast.makeText(getApplicationContext(),"OnStop",Toast.LENGTH_LONG).show();
        super.onStop();
    }

    @Override
    protected void onPause() {
        //Toast.makeText(getApplicationContext(),"OnPause",Toast.LENGTH_LONG).show();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
       // Toast.makeText(getApplicationContext(),"OnDestroy",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}

package com.whizkidzmedia.tiddlerjoy.YouTube;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.whizkidzmedia.tiddlerjoy.R;

public class YouTubePlayerActivity extends YouTubeFailureRecoveryActivity {

    YouTubePlayer player;
    YouTubePlayerView playerView;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_you_tube_player);
        playerView = (YouTubePlayerView) findViewById(R.id.player_view);
        playerView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        videoId= getIntent().getStringExtra("VideoId");
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return playerView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        this.player=player;
        this.player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        if (!wasRestored)
        {
            this.player.loadVideo(videoId);
        }

    }
}

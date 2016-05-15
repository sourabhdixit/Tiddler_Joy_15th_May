package com.whizkidzmedia.tiddlerjoy.ChildInterface;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.whizkidzmedia.tiddlerjoy.Adapters.VideoHistoryGridAdapter;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.YouTube.YouTubePlayerActivity;

import java.util.ArrayList;

public class ChildVideoHistoryActivity extends AppCompatActivity {
    ArrayList<ChildVideo> watchedVideos;
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_child_video_history);

        watchedVideos = new ArrayList<ChildVideo>();
        watchedVideos = (ArrayList) new Select().all().from(ChildVideo.class).where("IsVideoWatched = ?", true).execute();
        VideoHistoryGridAdapter adapter = new VideoHistoryGridAdapter(ChildVideoHistoryActivity.this, watchedVideos);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String vidId = watchedVideos.get(position).videoUrl;
                Intent intent = new Intent(ChildVideoHistoryActivity.this, YouTubePlayerActivity.class);
                intent.putExtra("VideoId", vidId);
                startActivity(intent);
            }
        });
    }
}

package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.whizkidzmedia.tiddlerjoy.Adapters.ParentVideoHistoryListAdapter;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.YouTube.YouTubePlayerActivity;

import java.util.ArrayList;

public class ParentVideoHistoryActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String All_ABOUT_MYSELF_VIEW_ID = "1";
    public static final String BEAUTIFUL_WORLD_VIEW_ID = "2";
    public static final String COMMUNICATION_VIEW_ID = "3";
    public static final String EARLY_MATHS_VIEW_ID = "4";
    public static final String CREATIVITY_VIEW_ID = "5";
    public static String CURRENT_SELECTED_BOGIE=" ";
    ListView videoList;
    ParentVideoHistoryListAdapter listAdapter;
    ArrayList<ChildVideo> watchedVideos;
    int[] selectorImgs,unSelectedImgs,categoryImgViewIds;
    TextView categoryTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_parent_video_history);
        CURRENT_SELECTED_BOGIE = getIntent().getStringExtra("ClickedDomain");
        setupWindowAnimations();
        initUI();
        initData();
        updateImgViews();
        //Toast.makeText(getApplicationContext(),CURRENT_SELECTED_BOGIE,Toast.LENGTH_SHORT).show();
    }

    @TargetApi(23)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(3000);
        getWindow().setEnterTransition(fade);
    }

    private void updateImgViews() {

            int index=1;
            for(int id : categoryImgViewIds)
            {
                ImageView imgView = (ImageView) findViewById(id);
                if(index==Integer.parseInt(CURRENT_SELECTED_BOGIE))
                    imgView.setImageResource(selectorImgs[index-1]);
                else
                    imgView.setImageResource(unSelectedImgs[index-1]);
                index++;
            }


    }

    private void initData() {

        selectorImgs = new int[]{R.drawable.highlighted_all_about_me,R.drawable.highlighted_early_maths,R.drawable.highlighted_creativity,R.drawable.highlighted_beautiful_world,
                R.drawable.highlighted_communication};
        unSelectedImgs = new int[]{R.drawable.aboutme_button,R.drawable.earlymaths_button,R.drawable.creativity_button,
                R.drawable.beautifulworld_button,R.drawable.communication_button};
        categoryImgViewIds = new int[]{R.id.all_about_me,R.id.early_maths,R.id.creativity,R.id.beautiful_world,R.id.communication};
        watchedVideos = new ArrayList<ChildVideo>();
        watchedVideos = (ArrayList) new Select().all().from(ChildVideo.class).where("IsVideoWatched = ?", true).execute();
        updateListView(CURRENT_SELECTED_BOGIE);
//        listAdapter = new ParentVideoHistoryListAdapter(ParentVideoHistoryActivity.this,watchedVideos);
//        videoList.setAdapter(listAdapter);


    }

    private void initUI() {

        videoList = (ListView)findViewById(R.id.video_listview);
        categoryTextView = (TextView)findViewById(R.id.category_tview);
        findViewById(R.id.all_about_me).setOnClickListener(this);
        findViewById(R.id.beautiful_world).setOnClickListener(this);
        findViewById(R.id.communication).setOnClickListener(this);
        findViewById(R.id.early_maths).setOnClickListener(this);
        findViewById(R.id.creativity).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        String categorySelected = "";
        switch (v.getId())
        {
            case R.id.all_about_me: categorySelected = All_ABOUT_MYSELF_VIEW_ID;
                CURRENT_SELECTED_BOGIE = All_ABOUT_MYSELF_VIEW_ID;
                break;
            case R.id.beautiful_world : categorySelected = EARLY_MATHS_VIEW_ID;
                CURRENT_SELECTED_BOGIE = EARLY_MATHS_VIEW_ID;
                break;
            case R.id.communication : categorySelected = CREATIVITY_VIEW_ID;
                CURRENT_SELECTED_BOGIE = CREATIVITY_VIEW_ID;
                break;
            case R.id.early_maths : categorySelected = BEAUTIFUL_WORLD_VIEW_ID;
                CURRENT_SELECTED_BOGIE = BEAUTIFUL_WORLD_VIEW_ID;
                break;
            case R.id.creativity : categorySelected = COMMUNICATION_VIEW_ID;
                CURRENT_SELECTED_BOGIE = COMMUNICATION_VIEW_ID;
                break;
        }
        updateImgViews();
        updateListView(categorySelected);
    }

    private void updateListView(String learningArea) {

        final ArrayList<ChildVideo> categoryVideos = new ArrayList<ChildVideo>();

        for(ChildVideo video: watchedVideos)
        {
            if(!video.allAboutMeTags.equals(" ")&&learningArea.equals(All_ABOUT_MYSELF_VIEW_ID))
            {categoryVideos.add(video);categoryTextView.setText("About Me");}
            if(!video.beautifulWorldTags.equals(" ")&&learningArea.equals(BEAUTIFUL_WORLD_VIEW_ID))
            {categoryVideos.add(video);categoryTextView.setText("Early Maths");}
            if(!video.communicationTags.equals(" ")&&learningArea.equals(COMMUNICATION_VIEW_ID))
            {categoryVideos.add(video);categoryTextView.setText("Creativity");}
            if(!video.earlyMathsTags.equals(" ")&&learningArea.equals(EARLY_MATHS_VIEW_ID))
            {categoryVideos.add(video);categoryTextView.setText("Beautiful World");}
            if(!video.creativityTags.equals(" ")&&learningArea.equals(CREATIVITY_VIEW_ID))
            {categoryVideos.add(video);categoryTextView.setText("Communication");}
        }
//        listAdapter = new ParentVideoHistoryListAdapter(ParentVideoHistoryActivity.this,watchedVideos);
        videoList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ParentVideoHistoryActivity.this, YouTubePlayerActivity.class);
                intent.putExtra("VideoId",categoryVideos.get(position).videoUrl);
                startActivity(intent);
            }
        });
    }
}

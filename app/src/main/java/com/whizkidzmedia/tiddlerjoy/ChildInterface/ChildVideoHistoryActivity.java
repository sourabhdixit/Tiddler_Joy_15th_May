package com.whizkidzmedia.tiddlerjoy.ChildInterface;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.activeandroid.query.Select;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildHistoryVideo;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.Networking.GetChildVideoHistoryAsync;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.ConnectionDetector;
import com.whizkidzmedia.tiddlerjoy.YouTube.YouTubePlayerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChildVideoHistoryActivity extends AppCompatActivity {
    ArrayList<ChildHistoryVideo> watchedVideos;
    GridView grid;
    //ViewPager childVideoPager;
    ImageView viewPagerLeft,viewPagerRight;
    List<Fragment> fragmentList;
    ArrayList<String> watchedVideosIds;
    private boolean isNextItemPresent,isPreviousItemPresent;
    HorizontalScrollView videoHistoryScrollView;
    int screenWidth,screenHeight;
    AnimationDrawable[] animationDrawables, characterAnimDrawable ;
    int[] animatedViewIds,bogieViewIds ;
    ProgressBar progress;
    LinearLayout hsvParentLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_child_video_history);


        initUI();

       // VideoHistoryGridAdapter adapter = new VideoHistoryGridAdapter(ChildVideoHistoryActivity.this, watchedVideos);
       // grid=(GridView)findViewById(R.id.grid);
        //grid.setAdapter(adapter);
//        initViewPager();
//        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                String vidId = watchedVideos.get(position).videoUrl;
//                Intent intent = new Intent(ChildVideoHistoryActivity.this, YouTubePlayerActivity.class);
//                intent.putExtra("VideoId", vidId);
//                startActivity(intent);
//            }
//        });
    }

    private void initUI() {

        ImageView backBtn = (ImageView)findViewById(R.id.child_vid_history_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progress = (ProgressBar)findViewById(R.id.progress);

        videoHistoryScrollView = (HorizontalScrollView)findViewById(R.id.child_scrollview);
        watchedVideos = new ArrayList<ChildHistoryVideo>();
        //watchedVideos = (ArrayList) new Select().all().from(ChildVideo.class).where("IsVideoWatched = ?", true).execute();
        getChildVideoHistory();
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        bogieViewIds = new int[watchedVideos.size()];
    }

    public void getChildVideoHistory()
    {
        String childId = getChildId();
        if(ConnectionDetector.isConnectedToInternet(ChildVideoHistoryActivity.this))
        new GetChildVideoHistoryAsync(childId){
            @Override
            protected void onPreExecute() {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Object o) {
                if(!o.equals("ERROR"))
                watchedVideos = (ArrayList<ChildHistoryVideo>)o;
                initVideoIds();
                progress.setVisibility(View.GONE);
                initTrainLayout();
            }
        }.execute();
        else {
            ConnectionDetector.displayNoConnectionDialog(ChildVideoHistoryActivity.this);
        }
    }

    private void initVideoIds() {

        watchedVideosIds = new ArrayList<>();
        if(watchedVideos.size()>50)
            watchedVideos = (ArrayList)watchedVideos.subList(0,50);
        for(ChildHistoryVideo video : watchedVideos)
            watchedVideosIds.add(video.videoId);
        Collections.reverse(watchedVideos);
        Collections.reverse(watchedVideosIds);
    }

    private String getChildId() {

        ChildProfile profile = new Select().all().from(ChildProfile.class).executeSingle();
        if(profile!=null)
        return profile.childID;
        else
            return "";

    }

    private void initTrainLayout()
    {
        hsvParentLinear = (LinearLayout)findViewById(R.id.hsv_ll);
        animationDrawables = new AnimationDrawable[watchedVideos.size()*2+1];
        animatedViewIds = new int[watchedVideos.size()*2+1];
        int i =0;
        getEngineLayout();
        for(i=0;i<watchedVideos.size();i++) {
            RelativeLayout bogieRelativeLayout = getBogieRelativeLayout(i+1);
            LinearLayout.LayoutParams bogieParentParams = new LinearLayout.LayoutParams(screenWidth * 33 / 100, LinearLayout.LayoutParams.MATCH_PARENT);
            bogieParentParams.setMargins(-(screenWidth*7/300),0,0,0);
            hsvParentLinear.addView(bogieRelativeLayout, bogieParentParams);
        }
    }

    private RelativeLayout getBogieRelativeLayout(int index) {
        RelativeLayout bogieRelativeLayout = new RelativeLayout(this);
        ImageView wheelsImgView = new ImageView(this);
        wheelsImgView.setId(Integer.parseInt("1000")+index*10);
//        animatedViewIds[2*index]=wheelsImgView.getId();
        RelativeLayout.LayoutParams wheelsParams = new RelativeLayout.LayoutParams(screenWidth*9/100,screenWidth*9/100);
        //wheelsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        wheelsImgView.setBackgroundResource(R.drawable.child_history_bogie_wheel);
        wheelsParams.setMargins(screenWidth * 8 / 100, screenHeight*55/100, 0, 0);
        ImageView wheelsImgView1 = new ImageView(this);
        wheelsImgView1.setId(Integer.parseInt("1001")+index*10+1);
//        animatedViewIds[2*index+1]=wheelsImgView1.getId();
        RelativeLayout.LayoutParams wheelsParams1 = new RelativeLayout.LayoutParams(screenWidth * 9 / 100, screenWidth * 9 / 100);
        //wheelsParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        wheelsImgView1.setBackgroundResource(R.drawable.child_history_bogie_wheel);
        wheelsParams1.setMargins(screenWidth * 19 / 100, screenHeight*55/100, 0, 0);
//        animationDrawables[2*index]=(AnimationDrawable)wheelsImgView.getBackground();
//        animationDrawables[2*index+1]=(AnimationDrawable)wheelsImgView1.getBackground();

        RelativeLayout.LayoutParams playParams = new RelativeLayout.LayoutParams(screenWidth*6/100,screenHeight*10/100);
        ImageView playImgView = new ImageView(this);
        playImgView.setImageResource(R.drawable.bw_button);
        playParams.setMargins(screenWidth * 29 / 200, screenHeight * 40 / 100, screenWidth * 5 / 100, 0);
//        playImgView.setAlpha(0.3f);


        ImageView bogieImgView = new ImageView(this);
        RelativeLayout.LayoutParams bogieParams = new RelativeLayout.LayoutParams(screenWidth*33/100,screenHeight*45/100);
        bogieParams.setMargins(0, screenHeight * 17 / 100, screenWidth*3/200, 0);
        if(index%2==0)
            bogieImgView.setBackgroundResource(R.drawable.green_history_bogey);
        else
            bogieImgView.setBackgroundResource(R.drawable.brown_bogey);

        bogieImgView.setLayoutParams(bogieParams);
        setOnClickListeners(bogieImgView, index);
        bogieRelativeLayout.addView(bogieImgView, bogieParams);
        bogieRelativeLayout.addView(wheelsImgView, wheelsParams);
        bogieRelativeLayout.addView(wheelsImgView1,wheelsParams1);
        bogieRelativeLayout.addView(playImgView,playParams);



        return bogieRelativeLayout;

    }

    private void setOnClickListeners(ImageView bogieImgView, int index) {

        bogieImgView.setId(Integer.parseInt(watchedVideos.get(index-1).videoId));
        bogieImgView.setPadding(screenWidth * 13 / 200, screenHeight * 5 / 100, screenWidth * 2 / 100, screenHeight * 4 / 100);
            bogieImgView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imgUrl = "http://img.youtube.com/vi/" + watchedVideos.get(index-1).youTubeId + "/0.jpg";
            Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.play_video_icon).into(bogieImgView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        bogieImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildVideoHistoryActivity.this,YouTubePlayerActivity.class);
                String vidId = watchedVideos.get(watchedVideosIds.indexOf(String.valueOf(v.getId()))).youTubeId;
                intent.putExtra("VideoId", vidId);
                intent.putExtra("VideoIndex",-1);
                startActivity(intent);
            }
        });
    }

    private RelativeLayout getEngineLayout() {
        RelativeLayout engineRelativeLayout = new RelativeLayout(this);
        LinearLayout.LayoutParams engineParentParams = new LinearLayout.LayoutParams(screenWidth*35/100,LinearLayout.LayoutParams.MATCH_PARENT);
        engineRelativeLayout.setLayoutParams(engineParentParams);
        ImageView engineImgView = new ImageView(this);
        engineImgView.setScaleType(ImageView.ScaleType.FIT_XY);
        engineImgView.setId(Integer.parseInt("1111"));
        RelativeLayout.LayoutParams engineParams = new RelativeLayout.LayoutParams(screenWidth*35/100,screenHeight*75/100);
        engineImgView.setBackgroundResource(R.drawable.child_history_train);
        animationDrawables[0]= (AnimationDrawable)engineImgView.getBackground();
        animatedViewIds[0]=engineImgView.getId();
        engineRelativeLayout.addView(engineImgView, engineParams);
        hsvParentLinear.addView(engineRelativeLayout, engineParentParams);
        return engineRelativeLayout;

    }

    private void initViewPager() {
        //childVideoPager = (ViewPager)findViewById(R.id.child_viewpager);
        viewPagerLeft = (ImageView)findViewById(R.id.viewpager_left);
        viewPagerRight = (ImageView)findViewById(R.id.viewpager_right);
        List<Fragment> fragments = getFragments();
        if(fragments.size()<=1)
            toggleScrollerVisibility(View.GONE,View.GONE);
//        MyPagerAdapter pageAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
//        childVideoPager.setAdapter(pageAdapter);
//        childVideoPager.setOnPageChangeListener(this);
//        childVideoPager.setPageTransformer(false, new DepthPageTransformer());

//        viewPagerRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isNextItemPresent)
//                    childVideoPager.setCurrentItem(childVideoPager.getCurrentItem() + 1, true);
//            }
//        });
//        viewPagerLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPreviousItemPresent)
//                    childVideoPager.setCurrentItem(childVideoPager.getCurrentItem() - 1, true);
//            }
//        });
    }

    private void toggleScrollerVisibility(int leftVisibility, int rightVisibility) {
        viewPagerLeft.setVisibility(leftVisibility);
        viewPagerRight.setVisibility(rightVisibility);
    }

    private List<Fragment> getFragments() {

        fragmentList = new ArrayList<Fragment>();
        LinearLayout hsvLinearLayout = (LinearLayout)findViewById(R.id.hsv_ll);
//        for(ChildVideo video:watchedVideos)
//        {
//            ImageView imgView = new ImageView(this);
//            Picasso.with(this).load( "http://img.youtube.com/vi/" + video.youTubeId + "/0.jpg").into(imgView);
//            hsvLinearLayout.addView(imgView);
//            fragmentList.add(ChildWatchedVideoFragment.newInstance(video.youTubeId,video.videoTitle));
//        }
        return fragmentList;
    }

//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        if(position==0&&position<fragmentList.size()) {
//            toggleScrollerVisibility(View.GONE, View.VISIBLE);
//            updateItemAvailability(true,false);
//
//        }
//        else if(position==fragmentList.size()-1) {
//            toggleScrollerVisibility(View.VISIBLE, View.GONE);
//            updateItemAvailability(false,true);
//        }
//        else if(position<fragmentList.size()) {
//            toggleScrollerVisibility(View.VISIBLE, View.VISIBLE);
//            updateItemAvailability(true,true );
//        }
//    }

    private void updateItemAvailability(boolean nextItemPresent, boolean previousItemPresent) {
        isNextItemPresent = nextItemPresent;
        isPreviousItemPresent = previousItemPresent;
    }

//    @Override
//    public void onPageSelected(int position) {
//
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }

//    class MyPagerAdapter extends FragmentStatePagerAdapter {
//        private List<Fragment> fragments;
//        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
//            super(fm);
//            this.fragments = fragments;
//        }
//        @Override
//        public Fragment getItem(int position) {
//            return this.fragments.get(position);
//        }
//        @Override
//        public int getCount() {
//            return this.fragments.size();
//        }
//    }

//    public class DepthPageTransformer implements ViewPager.PageTransformer {
//        private static final float MIN_SCALE = 0.75f;
//
//        public void transformPage(View view, float position) {
//            int pageWidth = view.getWidth();
//
//            if (position < -1) { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                view.setAlpha(0);
//
//            } else if (position <= 0) { // [-1,0]
//                // Use the default slide transition when moving to the left page
//                view.setAlpha(1);
//                view.setTranslationX(0);
//                view.setScaleX(1);
//                view.setScaleY(1);
//
//            } else if (position <= 1) { // (0,1]
//                // Fade the page out.
//                view.setAlpha(1 - position);
//
//                // Counteract the default slide transition
//                view.setTranslationX(pageWidth * -position);
//
//                // Scale the page down (between MIN_SCALE and 1)
//                float scaleFactor = MIN_SCALE
//                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
//                view.setScaleX(scaleFactor);
//                view.setScaleY(scaleFactor);
//
//            } else { // (1,+Infinity]
//                // This page is way off-screen to the right.
//                view.setAlpha(0);
//            }
//        }
    }


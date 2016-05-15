package com.whizkidzmedia.tiddlerjoy.ChildInterface;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.ExoPlayer.ExoPlayerActivity;
import com.whizkidzmedia.tiddlerjoy.ParentInterface.NewParentWatchPage;
import com.whizkidzmedia.tiddlerjoy.ParentInterface.ParentWatchPageActivity;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;
import com.whizkidzmedia.tiddlerjoy.Utilities.ConnectionDetector;
import com.whizkidzmedia.tiddlerjoy.Utilities.DialogBox;
import com.whizkidzmedia.tiddlerjoy.YouTube.YouTubePlayerActivity;

import java.util.ArrayList;

public class ChildWatchActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout bogieRelativeLayout;
    int screenWidth,screenHeight;
    HorizontalScrollView hsv;
    LinearLayout hsvParentLinear;
    Boolean isAnimPlaying=true;
    AnimationDrawable[] animationDrawables, characterAnimDrawable ;
    int[] animatedViewIds,bogieViewIds ;
    int videoStartIndex=0;
    boolean isVideoClicked=false,isThumbnailLoaded=false;
    ImageView playPauseBtn;
    MediaPlayer mpp;
    static final String ELEPHANT_CHAR_ID = "111100";
    final int ElephantIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_child_watch);
        //                MediaPlayer mpp = MediaPlayer.create(MainActivity.this, R.raw.xylophone_jingle);
//                mpp.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isVideoClicked)
        {
            hsvParentLinear.removeAllViews();
            initTrainLayout();
            initAnims();
            if(videoStartIndex==0)
            videoStartIndex=4;
            else
            videoStartIndex=0;
            isVideoClicked=false;
        }
        else {
            videoStartIndex = 0;
            initUI();
            initTrainLayout();
            initAnims();
        }
        initVideos();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mpp!=null){
            mpp.stop();
            mpp.release();
            mpp=null;
        }
        hsv.fullScroll(View.FOCUS_BACKWARD);

    }

    private void initVideos() {

        AppConstants.addVideos();
        ArrayList<ChildVideo> childVideos = AppConstants.CHILDVIDEOS;
        int k=0;
        for(int i=videoStartIndex;i<4+videoStartIndex;i++,k++) {
            ImageView bogieImgView = (ImageView)findViewById(bogieViewIds[k]);
            if(k==0)
            bogieImgView.setPadding(screenWidth * 11 / 200, screenHeight * 14 / 100, screenWidth * 4 / 100, screenHeight *4 / 100);
            else
            bogieImgView.setPadding(screenWidth * 14 / 200, screenHeight * 14 / 100, screenWidth * 3 / 100, screenHeight *4 / 100);
            bogieImgView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imgUrl = "http://img.youtube.com/vi/" + childVideos.get(i).videoUrl + "/0.jpg";
            Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.play_video_icon).into(bogieImgView, new Callback() {
                @Override
                public void onSuccess() {
                    isThumbnailLoaded = true;
                }

                @Override
                public void onError() {
                    isThumbnailLoaded = false;
                }
            });
        }

    }

    private void initAnims() {


        playPauseBtn = (ImageView)findViewById(R.id.play_pause_train);
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimPlaying) {
                    hsv.fullScroll(View.FOCUS_BACKWARD);
                    //playSound(0);
                    ObjectAnimator transAnimation = ObjectAnimator.ofFloat(hsvParentLinear, View.TRANSLATION_X, -(screenWidth * 87 / 100), -(7 * screenWidth / 2));
                    playPauseBtn.setBackgroundResource(R.drawable.green_light_button);
                    transAnimation.setDuration(8000);
                    transAnimation.start();
                    startDrawables();
                    isAnimPlaying = true;
                    transAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimPlaying = true;

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimPlaying = false;
                            hsvParentLinear.removeAllViews();
                            initTrainLayout();
                            initAnims();
                            if (videoStartIndex == 0)
                                videoStartIndex = 4;
                            else
                                videoStartIndex = 0;

                            initVideos();

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
            }
        });

        ObjectAnimator transAnimation= ObjectAnimator.ofFloat(hsvParentLinear, View.TRANSLATION_X, 150, -(screenWidth*87/100));
        transAnimation.setDuration(8000);
        //playSound(0);
        playPauseBtn.setBackgroundResource(R.drawable.green_light_button);
        transAnimation.start();
        isAnimPlaying = true;
        startDrawables();

        transAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
//                playPause.setEnabled(false);
//                playPause.setClickable(false);
//                playPause.setFocusable(false);
                isAnimPlaying = true;

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                playPause.setEnabled(true);
//                playPause.setClickable(true);
//                playPause.setFocusable(true);
                isAnimPlaying = false;
                if(mpp!=null)
                mpp.stop();
                stopDrawables();
                playPauseBtn.setBackgroundResource(R.drawable.red_light_button);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void playSound(int i) {

        mpp=null;
//        if(i==1) {
//            mpp = MediaPlayer.create(ChildWatchActivity.this, R.raw.steam_engine_full);
//        }
//        else
            mpp = MediaPlayer.create(ChildWatchActivity.this, R.raw.steam_engine_initial);
        if(mpp!=null)
        mpp.start();

    }

    private void startDrawables() {
        for(int i=0;i<animationDrawables.length;i++)
        {
            animationDrawables[i].start();
        }
    }
    private void stopDrawables() {
        for(int i=0;i<animationDrawables.length;i++)
        {
            animationDrawables[i].stop();
            ImageView imgView = (ImageView)findViewById(animatedViewIds[i]);
            imgView.setBackground(null);
            if(i==10)
            imgView.setBackgroundResource(R.drawable.movie);
            else
                imgView.setBackgroundResource(R.drawable.bogie_wheel);
            animationDrawables[i]=(AnimationDrawable)imgView.getBackground();
        }
        for(int i=0;i<animationDrawables.length;i++)
        {
            animationDrawables[i].stop();

        }
    }


    private void initTrainLayout() {

        hsvParentLinear = (LinearLayout)findViewById(R.id.hsv_parent_linear);

        if(hsvParentLinear.getChildCount()>0)
            hsvParentLinear.removeAllViews();
        LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout engineRelativeLayout = new RelativeLayout(this);
        LinearLayout.LayoutParams engineParentParams = new LinearLayout.LayoutParams(screenWidth*45/100,LinearLayout.LayoutParams.MATCH_PARENT);
        engineRelativeLayout.setLayoutParams(engineParentParams);
        ImageView engineImgView = new ImageView(this);
        engineImgView.setScaleType(ImageView.ScaleType.FIT_XY);
        engineImgView.setId(Integer.parseInt("1111"));
        RelativeLayout.LayoutParams engineParams = new RelativeLayout.LayoutParams(screenWidth*45/100,screenHeight*95/100);
        engineImgView.setBackgroundResource(R.drawable.movie);
        animationDrawables[10]= (AnimationDrawable)engineImgView.getBackground();
        animatedViewIds[10]=engineImgView.getId();
        engineParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ImageView charImgView = new ImageView(this);
        RelativeLayout.LayoutParams charParams = new RelativeLayout.LayoutParams(screenWidth*23/100,screenHeight*44/100);
        charImgView.setBackgroundResource(R.drawable.elephant_anim);
        characterAnimDrawable[0]= (AnimationDrawable)charImgView.getBackground();
        charImgView.setId(Integer.parseInt(ELEPHANT_CHAR_ID));
        charImgView.setOnClickListener(this);
        //charParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //charParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        charParams.setMargins(screenWidth*21/100, screenHeight * 7 / 100, screenWidth * 2 / 100, 0);
        engineRelativeLayout.addView(engineImgView, engineParams);
        engineRelativeLayout.addView(charImgView,charParams);
        hsvParentLinear.addView(engineRelativeLayout, engineParentParams);

        /*-----------------------------------------------------------------------*/

        int i=0;
        for(i=0;i<4;i++) {
            RelativeLayout bogieRelativeLayout = getBogieRelativeLayout(i);
            LinearLayout.LayoutParams bogieParentParams = new LinearLayout.LayoutParams(screenWidth * 41 / 100, LinearLayout.LayoutParams.MATCH_PARENT);
            if(i==0)
            bogieParentParams.setMargins(-(screenWidth * 7 / 200), 0, 0, screenHeight * 1 / 100);
            else
                bogieParentParams.setMargins(-(screenWidth * 12 / 200), 0, 0, screenHeight * 1 / 100);
            hsvParentLinear.addView(bogieRelativeLayout, bogieParentParams);
        }
        //hsvParentLinear.addView(engineRelativeLayout, engineParentParams);
        /*-------------------------------------------------------------------------------------------------*/
        int index=i;
        RelativeLayout guardBogieLayout = new RelativeLayout(this);
//        guardBogieLayout.setBackgroundColor(Color.parseColor("#80000000"));
        LinearLayout.LayoutParams bogieParentParams = new LinearLayout.LayoutParams(screenWidth * 40 / 100, LinearLayout.LayoutParams.MATCH_PARENT);
        bogieParentParams.setMargins(-(screenWidth * 11 / 200), 0, 0, screenHeight * 1 / 100);
        ImageView wheelsImgView = new ImageView(this);
        wheelsImgView.setId(Integer.parseInt("1000") + index * 10);
        animatedViewIds[2*index]=wheelsImgView.getId();
        RelativeLayout.LayoutParams wheelsParams = new RelativeLayout.LayoutParams(screenWidth*9/100,screenWidth*9/100);
        wheelsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        wheelsImgView.setBackgroundResource(R.drawable.bogie_wheel);
        wheelsParams.setMargins(screenWidth * 10 / 100, 0, 0, 0);
        ImageView wheelsImgView1 = new ImageView(this);
        wheelsImgView1.setId(Integer.parseInt("1001")+index*10+1);
        animatedViewIds[2*index+1]=wheelsImgView1.getId();
        RelativeLayout.LayoutParams wheelsParams1 = new RelativeLayout.LayoutParams(screenWidth*9/100,screenWidth* 9 / 100);
        wheelsParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        wheelsImgView1.setBackgroundResource(R.drawable.bogie_wheel);
        wheelsParams1.setMargins(screenWidth * 26 / 100, 0, 0, 0);
        animationDrawables[2*index]=(AnimationDrawable)wheelsImgView.getBackground();
        animationDrawables[2*index+1]=(AnimationDrawable)wheelsImgView1.getBackground();

        ImageView bogieImgView = new ImageView(this);
        RelativeLayout.LayoutParams bogieParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,screenHeight*24/100);
        bogieParams.setMargins(0, screenHeight * 40 / 100, 0, 0);
        bogieImgView.setBackgroundResource(R.drawable.guard_boggy);
        bogieImgView.setLayoutParams(bogieParams);
        setOnClickListeners(bogieImgView, index);
        ImageView charImgView1 = new ImageView(this);
        charImgView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Yo",Toast.LENGTH_SHORT).show();
            }
        });
        RelativeLayout.LayoutParams charParams1;
        charParams1 = new RelativeLayout.LayoutParams(screenWidth * 35 / 100, screenHeight * 42 / 100);
        charImgView1.setBackgroundResource(R.drawable.rabbit);
        charParams1.setMargins(screenWidth*23/100, screenHeight * 18 / 100,0, 0);
        guardBogieLayout.addView(bogieImgView, bogieParams);
        guardBogieLayout.addView(wheelsImgView, wheelsParams);
        guardBogieLayout.addView(wheelsImgView1, wheelsParams1);
        guardBogieLayout.addView(charImgView1,charParams1);
        hsvParentLinear.addView(guardBogieLayout, bogieParentParams);


    }

    private RelativeLayout getBogieRelativeLayout(int index) {
        RelativeLayout bogieRelativeLayout = new RelativeLayout(this);
        ImageView wheelsImgView = new ImageView(this);
        wheelsImgView.setId(Integer.parseInt("1000")+index*10);
        animatedViewIds[2*index]=wheelsImgView.getId();
        RelativeLayout.LayoutParams wheelsParams = new RelativeLayout.LayoutParams(screenWidth*9/100,screenWidth*9/100);
        //wheelsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        wheelsImgView.setBackgroundResource(R.drawable.bogie_wheel);
        wheelsParams.setMargins(screenWidth * 9 / 100, screenHeight*60/100, 0, 0);
        ImageView wheelsImgView1 = new ImageView(this);
        wheelsImgView1.setId(Integer.parseInt("1001")+index*10+1);
        animatedViewIds[2*index+1]=wheelsImgView1.getId();
        RelativeLayout.LayoutParams wheelsParams1 = new RelativeLayout.LayoutParams(screenWidth * 9 / 100, screenWidth * 9 / 100);
       //wheelsParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        wheelsImgView1.setBackgroundResource(R.drawable.bogie_wheel);
        wheelsParams1.setMargins(screenWidth * 23 / 100, screenHeight*60/100, 0, 0);
        animationDrawables[2*index]=(AnimationDrawable)wheelsImgView.getBackground();
        animationDrawables[2*index+1]=(AnimationDrawable)wheelsImgView1.getBackground();;

        ImageView charImgView = new ImageView(this);
        RelativeLayout.LayoutParams charParams;
        if(index==0) {
            charParams = new RelativeLayout.LayoutParams(screenWidth * 30 / 100, screenHeight * 40 / 100);
            charParams.setMargins(0,screenHeight*12/100,screenWidth*5/100,0);
            charImgView.setBackgroundResource(R.drawable.char_2);
        }
        else if(index==1) {
            charParams = new RelativeLayout.LayoutParams(screenWidth * 32 / 100, screenHeight * 30 / 100);
            charParams.setMargins(0,screenHeight*17/100,screenWidth*5/100,0);
            charImgView.setBackgroundResource(R.drawable.char_3);
        }
        else if(index==2) {
            charParams = new RelativeLayout.LayoutParams(screenWidth * 30 / 100, screenHeight * 45 / 100);
            charImgView.setBackgroundResource(R.drawable.char_4);
            charParams.setMargins(0,screenHeight*3/100,screenWidth*1/100,0);
        }
        else if(index==3) {
            charParams = new RelativeLayout.LayoutParams(screenWidth * 25 / 100, screenHeight * 40 / 100);
            charImgView.setBackgroundResource(R.drawable.char_5);
            charParams.setMargins(0,0,screenWidth*5/100,0);
        }
        else if(index==4) {
            charParams = new RelativeLayout.LayoutParams(screenWidth * 25 / 100, screenHeight * 40 / 100);
            charImgView.setBackgroundResource(R.drawable.char_6);
            charParams.setMargins(0,0,screenWidth*5/100,0);
        }
        else
            charParams = new RelativeLayout.LayoutParams(screenWidth*25/100,screenHeight*40/100);
        charParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        charParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        ImageView bogieImgView = new ImageView(this);
        RelativeLayout.LayoutParams bogieParams = new RelativeLayout.LayoutParams(screenWidth*37/100,screenHeight*50/100);
        bogieParams.setMargins(0, screenHeight * 20 / 100, 0, 0);
        if(index==0)
            bogieImgView.setBackgroundResource(R.drawable.red_boggy);
        else if(index==1)
            bogieImgView.setBackgroundResource(R.drawable.yellow_boggy);
        else if(index==2)
            bogieImgView.setBackgroundResource(R.drawable.blue_boggy);
        else if(index==3)
            bogieImgView.setBackgroundResource(R.drawable.green_boggy);
        else
        bogieImgView.setBackgroundResource(R.drawable.bogie_2);
        bogieImgView.setLayoutParams(bogieParams);
        setOnClickListeners(bogieImgView, index);
        bogieRelativeLayout.addView(bogieImgView, bogieParams);
        bogieRelativeLayout.addView(wheelsImgView, wheelsParams);
        bogieRelativeLayout.addView(wheelsImgView1,wheelsParams1);
        //bogieRelativeLayout.addView(charImgView,charParams);
        return bogieRelativeLayout;
    }

    private void setOnClickListeners(ImageView bogieImgView, int index) {

        bogieImgView.setId(index*100);
        bogieViewIds[index]=bogieImgView.getId();
        bogieImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionDetector.isConnectedToInternet(ChildWatchActivity.this)&&isThumbnailLoaded) {
                    int viewId = v.getId();
                    isVideoClicked = true;
                    Intent intent = new Intent(ChildWatchActivity.this, YouTubePlayerActivity.class);
                    if (viewId / 100 == 0) {
                        // Toast.makeText(getApplicationContext(), "Clicked: Frog Layout", Toast.LENGTH_SHORT).show();

                        String vidId = AppConstants.CHILDVIDEOS.get(videoStartIndex).videoUrl;
                        updateVideo(vidId);
                        intent.putExtra("VideoId", vidId);
                        startActivity(intent);
                    }
                    if (viewId / 100 == 1) {
//                    Toast.makeText(getApplicationContext(), "Clicked: Spidey Layout", Toast.LENGTH_SHORT).show();
                        String vidId = AppConstants.CHILDVIDEOS.get(videoStartIndex + 1).videoUrl;
                        updateVideo(vidId);
                        intent.putExtra("VideoId", vidId);
                        startActivity(intent);
                    }
                    if (viewId / 100 == 2) {
//                    Toast.makeText(getApplicationContext(), "Clicked: Pup Layout", Toast.LENGTH_SHORT).show();
                        String vidId = AppConstants.CHILDVIDEOS.get(videoStartIndex + 2).videoUrl;
                        updateVideo(vidId);
                        intent.putExtra("VideoId", vidId);
                        startActivity(intent);
                    }
                    if (viewId / 100 == 3) {
//                    Toast.makeText(getApplicationContext(), "Clicked: Sparrow Layout", Toast.LENGTH_SHORT).show();
                        String vidId = AppConstants.CHILDVIDEOS.get(videoStartIndex + 3).videoUrl;
                        updateVideo(vidId);
                        intent.putExtra("VideoId", vidId);
                        startActivity(intent);
                    }
//                startActivity(intent);
                    if (viewId / 100 == 4) {
                   // Toast.makeText(getApplicationContext(), "Clicked: Guard Layout", Toast.LENGTH_SHORT).show();
                    //String vidId=AppConstants.CHILDVIDEOS.get(videoStartIndex+1).videoUrl;
                    //intent.putExtra("VideoId", vidId);
                        startActivity(new Intent(ChildWatchActivity.this,ChildVideoHistoryActivity.class));
                    }
                }
                else if(!isThumbnailLoaded)
                {
                    new DialogBox(ChildWatchActivity.this,"Thumbnail not loaded");
                }
                else
                    ConnectionDetector.displayNoConnectionDialog(ChildWatchActivity.this);
            }

        });
    }

    private void updateVideo(String vidId) {
        ChildVideo videoClicked = new Select().from(ChildVideo.class).where("VideoUrl = ?",String.valueOf(vidId)).executeSingle();
        videoClicked.isVideoWatched=true;
        videoClicked.save();

    }

    private void initUI() {

        animationDrawables = new AnimationDrawable[11];
        characterAnimDrawable = new AnimationDrawable[5];
        bogieViewIds = new int[5];
        animatedViewIds = new int[11];
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        hsv = (HorizontalScrollView)findViewById(R.id.hsv);
//        hsv.removeAllViews();
        ImageView parentalIcon = (ImageView)findViewById(R.id.parental_icon);
        parentalIcon.setOnClickListener(this);
        ImageView parentalIcon1=(ImageView)findViewById(R.id.parental_icon_1);
        parentalIcon1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int idE = Integer.parseInt("111100");
        switch(v.getId())
        {
            case R.id.parental_icon: startActivity(new Intent(ChildWatchActivity.this, NewParentWatchPage.class));
                break;
            case R.id.parental_icon_1: startActivity(new Intent(ChildWatchActivity.this, ExoPlayerActivity.class));
                break;
            case 111100: //startCharAnim(ELEPHANT_CHAR_ID);
                break;
        }
    }

    private void startCharAnim(String charAnimId) {

        String charId = charAnimId;
        if(charId.equals(ELEPHANT_CHAR_ID))
        {
            characterAnimDrawable[ElephantIndex].start();
        }

    }
}

package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.makeramen.roundedimageview.RoundedImageView;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParentWatchPageActivity extends AppCompatActivity {

    LinearLayout childTopicTrainLayout;
    LinearLayout childKnowledgeMapLayout;
    int screenWidth,screenHeight;
    public static final String All_ABOUT_MYSELF_VIEW_ID = "1";
    public static final String BEAUTIFUL_WORLD_VIEW_ID = "2";
    public static final String COMMUNICATION_VIEW_ID = "3";
    public static final String EARLY_MATHS_VIEW_ID = "4";
    public static final String CREATIVITY_VIEW_ID = "5";
    public static String CURRENT_SELECTED_BOGIE="1";
    private int[] selectorImgs,unSelectedImgs, learningTopicBubbles,layoutRules ;
    List<ChildProfile> profiles;
    ArrayList<ChildVideo> watchedVideos;
    ArrayList<String> myselfTopics,mathsTopics,creativityTopics,worldAroundMeTopics,languageTopics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_parent_watch_page);
        initFont();
//        initData();
//        initUI();
//        setupChildList();

    }

    private void initFont() {
        String fontPath = "fonts/Amaranth-BoldItalic.otf";

        // text view label
        TextView txtGhost = (TextView) findViewById(R.id.app_title);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtGhost.setTypeface(tf);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initData();
//        initUI();
//        setupChildList();
    }

    private void initData() {
        selectorImgs = new int[]{R.drawable.all_about_me_selected,R.drawable.beautiful_world_selected,R.drawable.communication_selected,R.drawable.early_maths_selected,R.drawable.creativity_selected};
        unSelectedImgs = new int[]{R.drawable.all_about_me,R.drawable.beautiful_world,R.drawable.communication,R.drawable.early_maths,R.drawable.creativity};
        learningTopicBubbles = new int[]{R.drawable.sub_cat_1,R.drawable.sub_cat_2,R.drawable.sub_cat_3,R.drawable.sub_cat_4,R.drawable.sub_cat_5,R.drawable.sub_cat_6
                ,R.drawable.sub_cat_7,R.drawable.sub_cat_8,R.drawable.sub_cat_9,R.drawable.sub_cat_10,R.drawable.sub_cat_11,R.drawable.sub_cat_12,R.drawable.sub_cat_13,
                R.drawable.sub_cat_14,R.drawable.sub_cat_15,R.drawable.sub_cat_16,R.drawable.sub_cat_17,R.drawable.sub_cat_18,R.drawable.sub_cat_19,R.drawable.sub_cat_20};
        if(profiles==null)
        profiles = new Select().from(ChildProfile.class).execute();
        watchedVideos = new ArrayList<ChildVideo>();
        watchedVideos = (ArrayList) new Select().all().from(ChildVideo.class).where("IsVideoWatched = ?", true).execute();
        myselfTopics = new ArrayList<String>();
        myselfTopics.add("Food");
        myselfTopics.add("Hygiene");
        myselfTopics.add("Safety");
        myselfTopics.add("Security");
        myselfTopics.add("Fitness");
        myselfTopics.add("Self Care");
        myselfTopics.add("Self Control");
        myselfTopics.add("Myself");
        myselfTopics.add("Social Skills");
        myselfTopics.add("Celebrate Diversity");
        mathsTopics = new ArrayList<String>();
        mathsTopics.add("Critical Thinking");
        mathsTopics.add("Numbers");
        mathsTopics.add("Visual Spatial");
        creativityTopics = new ArrayList<String>();
        creativityTopics.add("Problem Solving");
        creativityTopics.add("Role Play");
        creativityTopics.add("Visual Arts");
        creativityTopics.add("Performing Arts");
        worldAroundMeTopics = new ArrayList<String>();
        worldAroundMeTopics.add("World Around Me");
        worldAroundMeTopics.add("Living World");
        worldAroundMeTopics.add("My Planet");
        worldAroundMeTopics.add("People & Places");
        worldAroundMeTopics.add("How Things Work");
        worldAroundMeTopics.add("Technology");
        languageTopics = new ArrayList<String>();
        languageTopics.add("Music");
        languageTopics.add("Sound");
        languageTopics.add("Dance");
        languageTopics.add("Expression");
        languageTopics.add("Gesture");
        languageTopics.add("Sign Language");
        languageTopics.add("Graphic");
        languageTopics.add("Color");
        languageTopics.add("Symbol");
        languageTopics.add("Maps");
        languageTopics.add("English");
        languageTopics.add("Hindi");
        languageTopics.add("Literacy");
        //languageTopics.add("Music");


        layoutRules=new int[]{RelativeLayout.RIGHT_OF};
    }

    private void setupChildList() {
        LinearLayout parentLinearLayout = (LinearLayout)findViewById(R.id.hsv_parent_layout);
        parentLinearLayout.removeAllViews();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        for(final ChildProfile profile:profiles)
        {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            linearLayout.setWeightSum(5.0f);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LLParams.setMargins(-(screenWidth*10/100),0,0,0);
            linearLayout.setLayoutParams(LLParams);
            ImageView childImage = getChildImageView(profile.image,profile.gender);
            TextView childName = getChildTextView(profile.childName);
            linearLayout.addView(childImage);
            linearLayout.addView(childName);
            linearLayout.setId(Integer.parseInt(profile.childID));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),profile.childName,Toast.LENGTH_SHORT).show();
                   // Intent intent = new Intent(Pare.this,ChildWatchActivity.class);
                   // startActivity(intent);
                }
            });
            parentLinearLayout.addView(linearLayout);
        }
    }

    private ImageView getChildImageView(byte[] image, String gender) {

        RoundedImageView childImage = new RoundedImageView(this);
        childImage.setCornerRadius((float) 10);
        childImage.mutateBackground(true);
        if(image!=null)
            childImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length, new BitmapFactory.Options()));
        else if(image==null&&gender.equals("1"))
            childImage.setImageResource(R.drawable.male);
        else
            childImage.setImageResource(R.drawable.female);
        childImage.setPadding(5,5,5,5);
        childImage.setBackgroundResource(R.drawable.wheel);
        childImage.setOval(true);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        lParams.weight=4.3f;
        childImage.setLayoutParams(lParams);
        childImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return childImage;
    }

    private TextView getChildTextView(String childNameString) {
        TextView childName= new TextView(this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        llParams.weight=.7f;
        llParams.gravity= Gravity.CENTER;
        childName.setLayoutParams(llParams);
        childName.setTextSize(12f);
        childName.setText(childNameString);
        childName.setTextColor(Color.parseColor("#ff0000"));
        childName.setEllipsize(TextUtils.TruncateAt.END);
        //childName.setBackgroundResource(android.R.color.darker_gray);
        return childName;
    }

    private void initUI() {

        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        float dense = getResources().getDisplayMetrics().density;
        //float dense = getResources().getDisplayMetrics().density;
        //RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight*50/100);
        //lParams.setMargins(screenWidth*5/100,screenHeight*3/100,screenWidth*5/100,screenHeight*5/100);
        CardView childLearningAreaView = (CardView)findViewById(R.id.child_learning_area_cardview);
        ViewGroup.LayoutParams lParams =childLearningAreaView.getLayoutParams();
        if(screenHeight>800)
        lParams.height = screenHeight*65/100;
        else
        lParams.height = screenHeight*77/100;
        childLearningAreaView.setLayoutParams(lParams);
        childTopicTrainLayout = (LinearLayout)findViewById(R.id.child_topic_train_layout);
        childTopicTrainLayout.removeAllViews();
        childKnowledgeMapLayout = (LinearLayout)findViewById(R.id.child_knowledge_map_layout);
        for(int i=0;i<5;i++)
            childTopicTrainLayout.addView(getTrainBogiesImgView(unSelectedImgs[i]));
        updateBogiesLayout();
    }

    private ImageView getTrainBogiesImgView(int resId) {

        LinearLayout.LayoutParams lParams;
        final TextView learningAreaTextView = (TextView)findViewById(R.id.learning_topic_tv);
        ImageView bogieImg = new ImageView(this);
        bogieImg.setImageResource(resId);
        lParams = new LinearLayout.LayoutParams(screenWidth*16/100, screenHeight*18/100);
        switch (resId)
        {
            case R.drawable.all_about_me:  lParams = new LinearLayout.LayoutParams(screenWidth*25/100, screenHeight*20/100);
                lParams.setMargins(-(screenWidth * 1 / 100), 0, 0, 0);
                bogieImg.setId(Integer.parseInt(All_ABOUT_MYSELF_VIEW_ID));
                break;
            case R.drawable.beautiful_world : //lParams.bottomMargin = screenHeight*2/100;
                lParams.setMargins(-(screenWidth*3/100),(screenHeight*2/100),0,0);
                bogieImg.setId(Integer.parseInt(BEAUTIFUL_WORLD_VIEW_ID));
                break;
            case R.drawable.communication : lParams.bottomMargin = screenHeight*10/100;
                bogieImg.setId(Integer.parseInt(COMMUNICATION_VIEW_ID));
                break;
            case R.drawable.early_maths : lParams.setMargins(0,-(screenHeight*1/100),0,0);
                lParams = new LinearLayout.LayoutParams(screenWidth*16/100, screenHeight*17/100);
                bogieImg.setId(Integer.parseInt(EARLY_MATHS_VIEW_ID));
                break;
            case R.drawable.creativity :
                lParams = new LinearLayout.LayoutParams(screenWidth*18/100, screenHeight*18/100);
                bogieImg.setId(Integer.parseInt(CREATIVITY_VIEW_ID));
                break;
        }
        bogieImg.setLayoutParams(lParams);
        bogieImg.setScaleType(ImageView.ScaleType.FIT_XY);
        bogieImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedId = v.getId();
                if (clickedId == Integer.parseInt(All_ABOUT_MYSELF_VIEW_ID)) {
                    CURRENT_SELECTED_BOGIE = All_ABOUT_MYSELF_VIEW_ID;
                    learningAreaTextView.setText("ALL ABOUT ME");
                } else if (clickedId == Integer.parseInt(BEAUTIFUL_WORLD_VIEW_ID)) {
                    CURRENT_SELECTED_BOGIE = BEAUTIFUL_WORLD_VIEW_ID;
                    learningAreaTextView.setText("BEAUTIFUL WORLD");
                } else if (clickedId == Integer.parseInt(COMMUNICATION_VIEW_ID)) {
                    CURRENT_SELECTED_BOGIE = COMMUNICATION_VIEW_ID;
                    learningAreaTextView.setText("COMMUNICATION");
                } else if (clickedId == Integer.parseInt(EARLY_MATHS_VIEW_ID)) {
                    CURRENT_SELECTED_BOGIE = EARLY_MATHS_VIEW_ID;
                    learningAreaTextView.setText("EARLY MATHS");
                } else if (clickedId == Integer.parseInt(CREATIVITY_VIEW_ID)) {
                    CURRENT_SELECTED_BOGIE = CREATIVITY_VIEW_ID;
                    learningAreaTextView.setText("CREATIVITY");
                } else {
                    Toast.makeText(getApplicationContext(), "Oops !", Toast.LENGTH_SHORT).show();
                }
                updateBogiesLayout();
            }
        });
        return bogieImg;
    }

    private void updateBogiesLayout() {

        int selectdIndex = Integer.parseInt(CURRENT_SELECTED_BOGIE)-1;
        for(int i=0;i<5;i++)
        {
            if(i==selectdIndex)
            {
                ImageView iV=(ImageView)childTopicTrainLayout.getChildAt(selectdIndex);
                iV.setImageResource(selectorImgs[selectdIndex]);
            }
            else
            {
                ImageView iV=(ImageView)childTopicTrainLayout.getChildAt(i);
                iV.setImageResource(unSelectedImgs[i]);
            }


        }
        refreshKnowledgeMap();
    }

    private void refreshKnowledgeMap() {
        if(childKnowledgeMapLayout.getChildCount()!=0)
        childKnowledgeMapLayout.removeAllViews();
        int topicsCount=0;
        ArrayList<String> topicsList = new ArrayList<String>();
        switch (CURRENT_SELECTED_BOGIE)
        {
            case All_ABOUT_MYSELF_VIEW_ID:
                topicsList.addAll(getTopics(All_ABOUT_MYSELF_VIEW_ID));
                topicsCount = topicsList.size();
                break;
            case EARLY_MATHS_VIEW_ID:
                topicsList.addAll(getTopics(EARLY_MATHS_VIEW_ID));
                topicsCount = topicsList.size();
                break;
            case COMMUNICATION_VIEW_ID:
                topicsList.addAll(getTopics(COMMUNICATION_VIEW_ID));
                topicsCount = topicsList.size();
                break;
            case CREATIVITY_VIEW_ID : //topicsCount = creativityTopics.size();
                topicsList.addAll(getTopics(CREATIVITY_VIEW_ID));
                topicsCount = topicsList.size();
                break;
            case BEAUTIFUL_WORLD_VIEW_ID: //topicsCount = worldAroundMeTopics.size();
                topicsList.addAll(getTopics(BEAUTIFUL_WORLD_VIEW_ID));
                topicsCount = topicsList.size();
                break;
        }

        float viewWidth = screenWidth*21/100;
        float viewHeight = screenWidth*21/100;
        int maxNoHorizontal = (int)(screenWidth/viewWidth);
        int maxNoVertical = Math.round((float) topicsCount / maxNoHorizontal);

        if(topicsCount%maxNoHorizontal!=0)
            maxNoVertical+=1;
            for(int i=0;i<topicsCount;)
            {

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                for(int j=0;j<maxNoHorizontal;j++,i++)
                {
                    String topic;
                    if(i<topicsCount)
                    topic = topicsList.get(i);
                    else
                    break;
                    TextView subLearningView = getSubLearningView(topic,i,viewWidth,viewHeight);
                    layout.addView(subLearningView);
                }
                childKnowledgeMapLayout.addView(layout);
            }

    }

    private ArrayList<String> getTopics(String viewId) {

        ArrayList<String> topics = new ArrayList<String>();
        for(ChildVideo video:watchedVideos)
        {
            switch(viewId)
            {
                case All_ABOUT_MYSELF_VIEW_ID: if(video.allAboutMeTags!=null&&!video.allAboutMeTags.equals(" ")&&!topics.contains(video.allAboutMeTags))
                    topics.add(video.allAboutMeTags);
                    break;
                case BEAUTIFUL_WORLD_VIEW_ID: if(video.beautifulWorldTags!=null&&!video.beautifulWorldTags.equals(" ")&&!topics.contains(video.beautifulWorldTags))
                    topics.add(video.beautifulWorldTags);
                    break;
                case EARLY_MATHS_VIEW_ID: if(video.earlyMathsTags!=null&&!video.earlyMathsTags.equals(" ")&&!topics.contains(video.earlyMathsTags))
                    topics.add(video.earlyMathsTags);
                    break;
                case COMMUNICATION_VIEW_ID: if(video.communicationTags!=null&&!video.communicationTags.equals(" ")&&!topics.contains(video.communicationTags))
                    topics.add(video.communicationTags);
                    break;
                case CREATIVITY_VIEW_ID: if(video.creativityTags!=null&&!video.creativityTags.equals(" ")&&!topics.contains(video.creativityTags))
                    topics.add(video.creativityTags);
                    break;

            }
        }
        return topics;
    }

    private TextView getSubLearningView(String topic, int i,float viewWidth,float viewHt) {
        final TextView tView = new TextView(this);
        int[] textViewIds = new int[20];
        LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams((int)viewWidth,(int) viewHt);
        rParams.setMargins(screenWidth*2/100,0,screenWidth*2/100,0);
        tView.setPadding(screenWidth*1/100,screenWidth*1/100,screenWidth*1/100,screenWidth*1/100);
        tView.setId(Integer.parseInt(All_ABOUT_MYSELF_VIEW_ID)*10+i);
        textViewIds[i]=tView.getId();
        int random = new Random().nextInt(20);
        tView.setBackgroundResource(learningTopicBubbles[i]);
        tView.setText(topic);
        //tView.setTextColor(Color.parseColor("#ffffff"));
        tView.setTextSize(11f);
        tView.setMaxLines(2);
        tView.setTypeface(Typeface.SERIF, Typeface.BOLD);

        tView.setEllipsize(TextUtils.TruncateAt.END);
        tView.setGravity(Gravity.CENTER);
        tView.setLayoutParams(rParams);
        tView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)v;
                Intent intent = new Intent(ParentWatchPageActivity.this,ParentVideoHistoryActivity.class);
                intent.putExtra("ClickedDomain",CURRENT_SELECTED_BOGIE);
                startActivity(intent);
            }
        });
        return tView;
    }


}

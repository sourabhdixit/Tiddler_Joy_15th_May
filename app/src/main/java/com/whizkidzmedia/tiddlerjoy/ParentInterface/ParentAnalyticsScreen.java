package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.whizkidzmedia.tiddlerjoy.Adapters.NavDrawerAdapter;
import com.whizkidzmedia.tiddlerjoy.Adapters.ParentVideoHistoryListAdapter;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildHistoryVideo;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.DataModels.EarlyLearningDomainData;
import com.whizkidzmedia.tiddlerjoy.DataModels.LearningAreaData;
import com.whizkidzmedia.tiddlerjoy.Fragments.ListDomainPagerFragment;
import com.whizkidzmedia.tiddlerjoy.Networking.GetEldaListingsAsync;
import com.whizkidzmedia.tiddlerjoy.Networking.GetParentAnalyticsDataAsync;
import com.whizkidzmedia.tiddlerjoy.Networking.GetParentVideoHistoryAsync;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;
import com.whizkidzmedia.tiddlerjoy.YouTube.YouTubePlayerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ParentAnalyticsScreen extends AppCompatActivity implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener, View.OnClickListener {

    Toolbar toolBar;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout navDrawer;
    TabHost tabHost;
    MyPagerAdapter pageAdapter;
    ImageView domainImageView;
    ViewPager pager;
    ImageView plantImgView,plantImageView_1,plantImageView_2,plantImageView_3,plantImageView_4;
    NavDrawerAdapter navDrawerAdapter;
    ListView navDrawerList;
    public static final String All_ABOUT_MYSELF_VIEW_ID = "1";
    public static final String BEAUTIFUL_WORLD_VIEW_ID = "2";
    public static final String COMMUNICATION_VIEW_ID = "3";
    public static final String EARLY_MATHS_VIEW_ID = "4";
    public static final String CREATIVITY_VIEW_ID = "5";
    public static String CURRENT_SELECTED_BOGIE="1";
    ListView videoList;
    ParentVideoHistoryListAdapter listAdapter;
    ArrayList<ChildHistoryVideo> watchedVideos;
    int[] selectorImgs,unSelectedImgs,categoryImgViewIds;
    HashMap<String,ArrayList<EarlyLearningDomainData>> learningAreasData;
    public static final int MAX_ELDA_LISTING_SIZE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_parent_analytics_screen);
        toolBar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolBar);
        initUi();
        initNavDrawer();
        List<LearningAreaData> learningDomain = new Select().all().from(LearningAreaData.class).execute();
        if(learningDomain!=null&&learningDomain.size()>0)
            fetchDataFromServer();
        else
            initEldaListings();

       // fetchParentVideoHistory();
       // initAnalyticsData();
    }

    private void initEldaListings() {

        new GetEldaListingsAsync(){
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(aBoolean) {
                    Toast.makeText(ParentAnalyticsScreen.this, "YOUHUU", Toast.LENGTH_SHORT).show();
                    fetchDataFromServer();
                    //initAnalyticsData();
                }
                else
                    Toast.makeText(ParentAnalyticsScreen.this,"YOUHUU FLOP SHOW",Toast.LENGTH_SHORT).show();
            }
        }.execute();

    }

    private void fetchDataFromServer() {

        String childId = getChildId();
        new GetParentAnalyticsDataAsync(childId){
            @Override
            protected void onPostExecute(Boolean isSuccess) {
                if(isSuccess)
                {
                    initAnalyticsData();
                    //Toast.makeText (ParentAnalyticsScreen.this, "YOUHUUU", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ParentAnalyticsScreen.this, "FLOP SHOW", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void initAnalyticsData() {
        learningAreasData = new HashMap<String, ArrayList<EarlyLearningDomainData>>();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        List<LearningAreaData> learningDomain = new Select().all().from(LearningAreaData.class).execute();
        for(LearningAreaData data : learningDomain)
        {
            ArrayList<EarlyLearningDomainData> eldaData = (ArrayList)new Select().all().from(EarlyLearningDomainData.class).where(" LearningAreaName = ?",data.learningDomain).execute();
            learningAreasData.put(data.learningDomain,eldaData);

            if(data.learningDomain.equals("All About Me")) {
                fragmentList.add(new ListDomainPagerFragment().newInstance("1", 0,4 ));
                fragmentList.add(new ListDomainPagerFragment().newInstance("1", 4,8 ));
                fragmentList.add(new ListDomainPagerFragment().newInstance("1", 8,9 ));
             }
            if(data.learningDomain.equals("Communication")) {
                fragmentList.add(new ListDomainPagerFragment().newInstance("5", 0,4 ));
                fragmentList.add(new ListDomainPagerFragment().newInstance("5", 4,8 ));
                fragmentList.add(new ListDomainPagerFragment().newInstance("5", 8,10 ));
            }
            if(data.learningDomain.equals("Creativity")) {
                fragmentList.add(new ListDomainPagerFragment().newInstance("3", 0,4 ));
            }
            if(data.learningDomain.equals("Beautiful World")) {
                fragmentList.add(new ListDomainPagerFragment().newInstance("4", 0,4 ));
                fragmentList.add(new ListDomainPagerFragment().newInstance("4", 4,6 ));
            }
            if(data.learningDomain.equals("Early Maths")) {
                fragmentList.add(new ListDomainPagerFragment().newInstance("2", 0,3 ));
            }
        }
        pageAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
        pager.setOnPageChangeListener(this);

    }

    private void fetchParentVideoHistory()
    {
       new GetParentVideoHistoryAsync(getChildId(),CURRENT_SELECTED_BOGIE){

           @Override
           protected void onPreExecute() {

           }

           @Override
           protected void onPostExecute(Object o) {
               if(!o.equals("ERROR")) {
                   watchedVideos = (ArrayList<ChildHistoryVideo>) o;
                   //initVideoIds();
                   updateListView(CURRENT_SELECTED_BOGIE);
                   //progress.setVisibility(View.GONE);
               }
           }
       }.execute();
    }


    private void initPlantUi() {

        List<LearningAreaData> data = new Select().all().from(LearningAreaData.class).execute();

    }

    private String getChildId() {

        ChildProfile profile = new Select().all().from(ChildProfile.class).executeSingle();
        if(profile!=null)
            return profile.childID;
        else
            return "";

    }

    private void initNavDrawer() {
        navDrawerList = (ListView)findViewById(R.id.drawer);
        navDrawerAdapter = new NavDrawerAdapter(getApplicationContext(),
                null, " Sourabh ", "10");
        navDrawerList.setAdapter(navDrawerAdapter);

        navDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = null;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(ParentAnalyticsScreen.this, "Edit Parent Details" + position, Toast.LENGTH_SHORT).show();

                }
                if (position == 1) {
                    //intent = new Intent(ParentAnalyticsScreen.this,AddChildProfileActivity.class);
                    Toast.makeText(ParentAnalyticsScreen.this, "Edit Child Profile-" + position, Toast.LENGTH_SHORT).show();
                }
//                if (position == 2) {
//                    intent = new Intent(ParentAnalyticsScreen.this, SetTimerActivity.class);
//                    startActivity(intent);
//                    //Toast.makeText(ParentAnalyticsScreen.this,"Set-"+position,Toast.LENGTH_SHORT).show();
//                }
                if (position == 2) {
                    //intent = new Intent(ParentAnalyticsScreen.this,.class);
                    Toast.makeText(ParentAnalyticsScreen.this, "About Us", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initUi() {

        videoList = (ListView)findViewById(R.id.video_listview);
        findViewById(R.id.all_about_me).setOnClickListener(this);
        findViewById(R.id.beautiful_world).setOnClickListener(this);
        findViewById(R.id.communication).setOnClickListener(this);
        findViewById(R.id.early_maths).setOnClickListener(this);
        findViewById(R.id.creativity).setOnClickListener(this);
        selectorImgs = new int[]{R.drawable.highlighted_all_about_me,R.drawable.highlighted_early_maths,R.drawable.highlighted_creativity,R.drawable.highlighted_beautiful_world,
                R.drawable.highlighted_communication};
        unSelectedImgs = new int[]{R.drawable.aboutme_button,R.drawable.earlymaths_button,R.drawable.creativity_button,
                R.drawable.beautifulworld_button,R.drawable.communication_button};
        categoryImgViewIds = new int[]{R.id.all_about_me,R.id.early_maths,R.id.creativity,R.id.beautiful_world,R.id.communication};


        plantImgView = (ImageView)findViewById(R.id.myself_plant);
        plantImageView_1 = (ImageView)findViewById(R.id.communication_plant);
        plantImageView_2 = (ImageView)findViewById(R.id.beautiful_world_plant);
        plantImageView_3 = (ImageView)findViewById(R.id.creativity_plant);
        plantImageView_4 = (ImageView)findViewById(R.id.early_maths_plant);

//        List<Fragment> fragments = getFragments("1");

//        pageAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        pager = (ViewPager)findViewById(R.id.viewpager);
//        pager.setAdapter(pageAdapter);
//        pager.setOnPageChangeListener(this);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator(" ");
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator(" ");
        tabHost.addTab(spec);
        tabHost.getTabWidget().getChildAt(0)
                .setBackgroundResource(R.drawable.analytics_tab_sel);
        tabHost.getTabWidget().getChildAt(1)
                .setBackgroundResource(R.drawable.history_tab);
        tabHost.setOnTabChangedListener(this);


        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, navDrawer, toolBar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        navDrawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals("Tab One")) {
            tabHost.getTabWidget().getChildAt(0)
                    .setBackgroundResource(R.drawable.analytics_tab_sel);
            tabHost.getTabWidget().getChildAt(1)
                    .setBackgroundResource(R.drawable.history_tab);

        }

        if (tabId.equals("Tab Two")) {
            tabHost.getTabWidget().getChildAt(0)
                    .setBackgroundResource(R.drawable.analytics_tab);
            tabHost.getTabWidget().getChildAt(1)
                    .setBackgroundResource(R.drawable.history_tab_sel);

        }
    }

    private List<Fragment> getFragments(String selectedDomainIndex) {

        AppConstants.initTopics();
        List<Fragment> fList = new ArrayList<Fragment>();
        ArrayList<String> data = new ArrayList<String>();
            data.addAll(AppConstants.getMyselfTopics());
            data.addAll(AppConstants.getMathsTopics());
            data.addAll(AppConstants.getCreativityTopics());
            data.addAll(AppConstants.getWorldAroundMeTopics());
        int end=3;
        for(int i=0;i<data.size();i++,end++) {
            if(i%4==0)
                fList.add(ListDomainPagerFragment.newInstance(selectedDomainIndex, i / 4, i + 3));
        }
        return fList;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if(position<=2)
            plantImgView.setImageResource(R.drawable.plant_3_glow);
        else
            plantImgView.setImageResource(R.drawable.plant_3);
        if(position>2&&position<=5)
            plantImageView_1.setImageResource(R.drawable.plant_1_glow);
        else
            plantImageView_1.setImageResource(R.drawable.plant_1);
        if(position>5&&position<=7)
            plantImageView_2.setImageResource(R.drawable.plant_2_glow);
        else
            plantImageView_2.setImageResource(R.drawable.plant_2);
        if(position>7&&position<=8)
            plantImageView_3.setImageResource(R.drawable.plant_4_glow);
        else
            plantImageView_3.setImageResource(R.drawable.plant_4);


    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;
        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }
        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

    private void updateListView(String learningArea) {

        final ArrayList<ChildVideo> categoryVideos = new ArrayList<ChildVideo>();
        listAdapter = new ParentVideoHistoryListAdapter(ParentAnalyticsScreen.this,watchedVideos);
        videoList.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        videoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ParentAnalyticsScreen.this, YouTubePlayerActivity.class);
                intent.putExtra("VideoId",categoryVideos.get(position).videoUrl);
                startActivity(intent);
            }
        });
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
        fetchParentVideoHistory();
        //updateListView(categorySelected);
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

}


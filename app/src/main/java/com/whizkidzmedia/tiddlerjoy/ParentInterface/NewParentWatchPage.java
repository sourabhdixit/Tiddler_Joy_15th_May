package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.Fragments.LearningDomainFragment;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;

import java.util.List;

public class NewParentWatchPage extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolBar;
    int[] selectorImgs,unSelectedImgs,categoryImgViewIds;
    public static final String ABOUT_MYSELF_VIEW_ID = "1";
    public static final String BEAUTIFUL_WORLD_VIEW_ID = "4";
    public static final String COMMUNICATION_VIEW_ID = "5";
    public static final String EARLY_MATHS_VIEW_ID = "2";
    public static final String CREATIVITY_VIEW_ID = "3";
    public static String CURRENT_SELECTED_BOGIE="1";
    List<ChildProfile> profiles;
    TextView childName;
    Boolean isFragmentPresent=false;
    DrawerLayout navDrawer;
    String TITLES[] = {"About Me","Early Maths","Creativity","Beautiful World","Communication"};
    int ICONS[] = {R.drawable.aboutme_button,R.drawable.earlymaths_button,R.drawable.creativity_button,R.drawable.beautifulworld_button,R.drawable.communication_button};

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    ActionBarDrawerToggle mDrawerToggle;
    ImageView blueBlob,yellowBlob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_new_parent_watch_page);
        toolBar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolBar);
        toolBar.setTitle("Summary Map");
        setupWindowAnimations();
        initUI();
        initData();
    }

    @TargetApi(23)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(3000);
        getWindow().setExitTransition(slide);
    }

    private void initData() {

        if(profiles==null)
            profiles = new Select().from(ChildProfile.class).execute();
        childName.setText(profiles.get(0).childName);
        byte[]image = profiles.get(0).image;
        String gender = profiles.get(0).gender;
        ImageView childImage = (ImageView)findViewById(R.id.child_image);
        if(image!=null) {
            childImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length, new BitmapFactory.Options()));

        }
        else if(image==null&&gender.equals("1")){
            childImage.setImageResource(R.drawable.male);
        }
        else {
            childImage.setImageResource(R.drawable.female);
        }
        AppConstants.initTopics();
    }

    private void initUI() {

        childName = (TextView)findViewById(R.id.child_name);
        selectorImgs = new int[]{R.drawable.highlighted_all_about_me,R.drawable.highlighted_early_maths,R.drawable.highlighted_creativity,R.drawable.highlighted_beautiful_world,
                R.drawable.highlighted_communication};
        unSelectedImgs = new int[]{R.drawable.aboutme_button,R.drawable.earlymaths_button,R.drawable.creativity_button,
                R.drawable.beautifulworld_button,R.drawable.communication_button};
        categoryImgViewIds = new int[]{R.id.about_me_iv,R.id.early_maths_iv,R.id.creativity_iv,R.id.beautiful_world_iv,R.id.communication_iv};
        for(int id : categoryImgViewIds)
        {
            ImageView imgView = (ImageView) findViewById(id);
            imgView.setOnClickListener(this);
        }
        yellowBlob = (ImageView)findViewById(R.id.yellow_blob);
        blueBlob = (ImageView)findViewById(R.id.blue_blob);
        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,navDrawer,toolBar,R.string.openDrawer,R.string.closeDrawer){

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
        // Inflate the menu_main; this adds items to the action bar if it is present.
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.about_me_iv: CURRENT_SELECTED_BOGIE = ABOUT_MYSELF_VIEW_ID;
                break;
            case R.id.early_maths_iv : CURRENT_SELECTED_BOGIE = EARLY_MATHS_VIEW_ID;
                break;
            case R.id.creativity_iv : CURRENT_SELECTED_BOGIE = CREATIVITY_VIEW_ID;
                break;
            case R.id.beautiful_world_iv : CURRENT_SELECTED_BOGIE = BEAUTIFUL_WORLD_VIEW_ID;
                break;
            case R.id.communication_iv : CURRENT_SELECTED_BOGIE = COMMUNICATION_VIEW_ID;
                break;

        }
        updateImgViews();

        if(yellowBlob.getVisibility()==View.VISIBLE)
            yellowBlob.setVisibility(View.GONE);
        if(blueBlob.getVisibility()==View.VISIBLE)
            blueBlob.setVisibility(View.GONE);

    }

    private void loadFragmentLayout() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LearningDomainFragment hello = new LearningDomainFragment();
        Bundle args = new Bundle();
        args.putString("domain", CURRENT_SELECTED_BOGIE);
        hello.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container, hello, "HELLO");
        //fragmentTransaction.setCustomAnimations(R.anim.enter_anim,R.anim.exit_to_left);
        fragmentTransaction.commit();
        isFragmentPresent=true;
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
        loadFragmentLayout();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(!isFragmentPresent)
            super.onBackPressed();
        else
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> frag = fragmentManager.getFragments();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(frag.get(0));
            fragmentTransaction.commit();
            isFragmentPresent=false;
        }
    }
}

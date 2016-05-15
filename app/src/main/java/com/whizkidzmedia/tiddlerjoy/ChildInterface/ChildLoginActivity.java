package com.whizkidzmedia.tiddlerjoy.ChildInterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.makeramen.roundedimageview.RoundedImageView;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.DataModels.UserProfile;
import com.whizkidzmedia.tiddlerjoy.Networking.GetAllChildProfilesAsyncTask;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;
import com.whizkidzmedia.tiddlerjoy.Utilities.ConnectionDetector;
import com.whizkidzmedia.tiddlerjoy.Utilities.DialogBox;
import com.whizkidzmedia.tiddlerjoy.Utilities.SharedPrefs;


import java.util.ArrayList;
import java.util.List;

public class ChildLoginActivity extends AppCompatActivity {
    boolean isRunningf1=false,isRunningf2=false,greenLightAnimOn=false,redLightAnimOn=false;
    ProgressDialog asyncDialog;
    List<ChildProfile> profiles;
    int screenHeight,screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_child_login);
        initUI();
        initAnims();
        profiles = new Select().from(ChildProfile.class).execute();
        if(profiles.size()==0)
        {
            getChildProfiles();
        }
        else
        {
           // childListAdapter = new ChildListAdapter((ArrayList<ChildProfile>)profiles,ChildLoginActivity.this,ChildLoginActivity.this);
            setUpListView();
        }
    }

    private void setUpListView() {

        LinearLayout parentLinearLayout = (LinearLayout)findViewById(R.id.hsv_parent_linear);
        for(final ChildProfile profile:profiles)
        {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            linearLayout.setWeightSum(5.0f);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(LLParams);
            ImageView childImage = getChildImageView(profile.image,profile.gender);
            TextView childName = getChildTextView(profile.childName);
            linearLayout.addView(childImage);
            linearLayout.addView(childName);
            linearLayout.setId(Integer.parseInt(profile.childID));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"Clicked:"+v.getId(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChildLoginActivity.this,ChildWatchActivity.class);
                    startActivity(intent);
                }
            });
            parentLinearLayout.addView(linearLayout);
        }
    }

    private TextView getChildTextView(String childNameString) {
        TextView childName= new TextView(this);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, screenHeight*5/100);
        llParams.weight=.7f;
        llParams.gravity=Gravity.CENTER;
        childName.setLayoutParams(llParams);
        childName.setText(childNameString);
        childName.setTextColor(Color.parseColor("#ff0000"));
        childName.setTextSize(14f);
        return childName;
    }

    private ImageView getChildImageView(byte[] image, String gender) {

        RoundedImageView childImage = new RoundedImageView(this);
        childImage.setCornerRadius((float) 20);
        childImage.mutateBackground(true);
        //childImage.setOval(true);
        if(image!=null) {
            childImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length, new BitmapFactory.Options()));
            childImage.setPadding(screenWidth * 10 / 100, screenHeight * 3 / 100, screenWidth * 9 / 100, screenHeight * 5 / 100);

        }
        else if(image==null&& gender.equals("1")) {
            childImage.setImageResource(R.drawable.male);
            childImage.setPadding(screenWidth*10/100, screenHeight * 3 / 100, screenWidth * 9 / 100, screenHeight * 5 / 100);

        }
            else {
            childImage.setImageResource(R.drawable.female);
            childImage.setPadding(screenWidth*8/100, screenHeight * 3 / 100, screenWidth * 9 / 100, screenHeight * 5 / 100);

        }
        if(gender.equals("1"))
            childImage.setBackgroundResource(R.drawable.blue_flower);
        else
            childImage.setBackgroundResource(R.drawable.red_flower);


        childImage.setOval(true);

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(screenWidth*40/100,screenHeight * 40 / 100);
        lParams.weight=4.3f;
        childImage.setLayoutParams(lParams);

        childImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return childImage;
    }

    private void initUI() {
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

    }

    private void initAnims() {

        final ImageView lig = (ImageView)findViewById(R.id.green_lig);
        lig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (greenLightAnimOn) {
                    lig.setBackgroundResource(R.drawable.green_1);
                    greenLightAnimOn = false;
                } else {
                    lig.setBackgroundResource(R.drawable.green_anim);
                    AnimationDrawable amd = (AnimationDrawable) lig.getBackground();
                    amd.start();
                    greenLightAnimOn = true;
                }
            }
        });

        final ImageView rig = (ImageView)findViewById(R.id.red_lig);
        rig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(redLightAnimOn){
                    rig.setBackgroundResource(R.drawable.red_1);
                    redLightAnimOn=false;
                }
                else{
                    rig.setBackgroundResource(R.drawable.red_anim);
                    AnimationDrawable amd=(AnimationDrawable)rig.getBackground();
                    amd.start();
                    redLightAnimOn=true;
                }
            }
        });

        final ImageView fan1 = (ImageView)findViewById(R.id.fan_1);
        fan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunningf1){
                    fan1.setBackgroundResource(R.drawable.fan1);
                    isRunningf1=false;
                }
                else{
                    fan1.setBackgroundResource(R.drawable.fan_anim);
                    AnimationDrawable amd=(AnimationDrawable)fan1.getBackground();
                    amd.start();
                    isRunningf1=true;
                }
            }
        });
        final ImageView fan2 = (ImageView)findViewById(R.id.fan_2);
        fan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunningf2){
                    fan2.setBackgroundResource(R.drawable.fan1);
                    isRunningf2=false;
                }
                else{
                    fan2.setBackgroundResource(R.drawable.fan_anim);
                    AnimationDrawable amd=(AnimationDrawable)fan2.getBackground();
                    amd.start();
                    isRunningf2=true;
                }
            }
        });


    }

    private void getChildProfiles() {
        if(ConnectionDetector.isConnectedToInternet(ChildLoginActivity.this)) {
            new GetAllChildProfilesAsyncTask(getSharedPreferences(SharedPrefs.SHARED_PREFS_NAME, MODE_PRIVATE).getString(UserProfile.ID, "")) {
                @Override
                protected void onPreExecute() {
                    asyncDialog=new ProgressDialog(ChildLoginActivity.this);
                    asyncDialog.setMessage("Fetching Content..");
                    asyncDialog.setCancelable(false);
                    asyncDialog.setCanceledOnTouchOutside(false);
                    asyncDialog.show();
                }

                @Override
                protected void onPostExecute(ArrayList<ChildProfile> childProfiles) {
                    asyncDialog.dismiss();
                    if (childProfiles == null) {

                        new DialogBox(ChildLoginActivity.this,"No Child Profiles to show :( ");

                    } else {
                      //  childListAdapter = new ChildListAdapter(childProfiles, ChildLoginActivity.this, ChildLoginActivity.this);
                        setUpListView();
                        addToDatabase(childProfiles);
                        writeToSharedPreference(childProfiles.size());
                    }
                }
            }.execute();
        }
        else
            ConnectionDetector.displayNoConnectionDialog(ChildLoginActivity.this);

    }

    private void addToDatabase(ArrayList<ChildProfile> childProfiles) {
        new Delete().from(ChildProfile.class).execute();
        for(ChildProfile profiles:childProfiles)
        {
            profiles.save();
        }
    }

    private void writeToSharedPreference(int count) {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(SharedPrefs.SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ChildProfile.CHILD_COUNT, String.valueOf(count));
        editor.putBoolean("isFirstTimeLogin", false);
        editor.commit();
    }
}

package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tt.whorlviewlibrary.WhorlView;
import com.whizkidzmedia.tiddlerjoy.ChildInterface.ChildLoginActivity;
import com.whizkidzmedia.tiddlerjoy.DataModels.UserProfile;
import com.whizkidzmedia.tiddlerjoy.Networking.ParentSignupAsync;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.ConnectionDetector;
import com.whizkidzmedia.tiddlerjoy.Utilities.DialogBox;
import com.whizkidzmedia.tiddlerjoy.Utilities.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

public class ParentSignupActivity extends AppCompatActivity implements View.OnClickListener{

    TextView userName,userMobile,userEmail;
    Button regsiterBtn;
    UserProfile userProfile;
    public static final int INVALID_EMAIL=2;
    public static final int INVALID_NUMBER=3;
    public static final int INVALID_NAME=4;
    public static final int VALIDATION_SUCCESS=1;
    public static final String INVALID_EMAIL_MESSAGE = "You entered an invalid email id, please enter a valid email id";
    public static final String INVALID_MOBILE_NO_MESSAGE = "You entered an invalid mobile number, please enter a valid mobile number";
    public static final String BLANK_NAME_MESSAGE = "Name can not be left blank, please enter your name";

    WhorlView loadingView;
    String deviceID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
        setTitle("Parent Onboarding");
       // getSupportActionBar().hide();
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs=this.getSharedPreferences(SharedPrefs.SHARED_PREFS_NAME, 0);
        Boolean isLoggedIn = prefs.getBoolean(SharedPrefs.LOGGED_IN_STATUS, false);
        Boolean isChildAdded = prefs.getBoolean(UserProfile.IS_CHILD_ADDED, false);
        if(isLoggedIn&&isChildAdded) {
          //  getDeviceParams();
            startActivity(new Intent(ParentSignupActivity.this, ChildLoginActivity.class));
            finish();
        }
        else if(isLoggedIn&&!isChildAdded) {
            startActivity(new Intent(ParentSignupActivity.this, AddChildProfileActivity.class));
            finish();
        }
        else if(isLoggedIn)
        {

        }
        else {}

    }

    private void getDeviceParams() {

        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        deviceID = telephonyManager.getDeviceId();
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        Toast.makeText(getApplicationContext(),"Android Id:"+android_id+" Device Id:"+deviceID+"MAC Addr:"+address,Toast.LENGTH_LONG).show();

    }

    private void initUI() {
        loadingView = (WhorlView)findViewById(R.id.whorl2);
        userName = (TextView)findViewById(R.id.parent_registration_name);
        userEmail = (TextView)findViewById(R.id.parent_registration_email);
        userMobile = (TextView)findViewById(R.id.parent_registration_mobile);
        regsiterBtn = (Button)findViewById(R.id.parent_register_btn);
        regsiterBtn.setOnClickListener(this);
        findViewById(R.id.retrieve_profiles_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.parent_register_btn:  //validateInputs();
            checkResults(validateInputs());
                break;
            case R.id.retrieve_profiles_layout: showLoginDialog();
                break;
        }
    }

    private void showLoginDialog() {

        startActivity(new Intent(ParentSignupActivity.this,RetrieveChildProfilesActivity.class));
    }

    private void checkResults(int isValid) {

        if(isValid==VALIDATION_SUCCESS)
            registerUserProfile();
        else if(isValid==INVALID_NUMBER)
            new DialogBox(this,INVALID_MOBILE_NO_MESSAGE);
        else if(isValid==INVALID_EMAIL)
            new DialogBox(this,INVALID_EMAIL_MESSAGE);
        else if(isValid==INVALID_NAME)
            new DialogBox(this,BLANK_NAME_MESSAGE);
    }

    private void registerUserProfile() {

        String username = userName.getText().toString();
        String usermobile = userMobile.getText().toString();
        String useremail= userEmail.getText().toString();

        if(ConnectionDetector.isConnectedToInternet(ParentSignupActivity.this))
        new ParentSignupAsync(username,useremail,usermobile)
        {
            @Override
            protected void onPreExecute() {
                loadingView.setVisibility(View.VISIBLE);
                loadingView.start();
            }

            @Override
            protected void onPostExecute(String s) {

                loadingView.stop();
                loadingView.setVisibility(View.GONE);
                if(s.contains(UserProfile.EMAIL)&&s.contains(UserProfile.MOBILE_NUMBER)&&s.contains(UserProfile.FIRST_NAME)&&s.contains(UserProfile.ID))
                {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        userProfile = new UserProfile();
                        userProfile.email=jsonObject.getString(UserProfile.EMAIL);
                        userProfile.mobileNumber=jsonObject.getString(UserProfile.MOBILE_NUMBER);
                        userProfile.userId=jsonObject.getString(UserProfile.ID);
                        userProfile.username=jsonObject.getString(UserProfile.FIRST_NAME);
                        userProfile.save();
                        saveSharedPreferences();
                        startActivity(new Intent(ParentSignupActivity.this, AddChildProfileActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if(s.contains("error"))
                {
                    try {
                        JSONObject jbj = new JSONObject(s);
                        String errorString = jbj.getString("error");
                        new DialogBox(ParentSignupActivity.this,errorString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    new DialogBox(ParentSignupActivity.this,"Unable to register, try again !!!");
                }
            }
        }.execute();
        else
            ConnectionDetector.displayNoConnectionDialog(ParentSignupActivity.this);


    }

    private void saveSharedPreferences() {

        SharedPreferences prefs=this.getSharedPreferences(SharedPrefs.SHARED_PREFS_NAME,0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPrefs.USER_NAME,userProfile.username);
        editor.putString(SharedPrefs.USER_EMAIL,userProfile.email);
        editor.putString(SharedPrefs.USER_NUMBER,userProfile.mobileNumber);
        editor.putString(SharedPrefs.USER_ID,userProfile.userId);
        editor.putBoolean(SharedPrefs.LOGGED_IN_STATUS, true);
        editor.commit();
    }

    private int validateInputs() {
        String username = userName.getText().toString();
        String usermobile = userMobile.getText().toString();
        String useremail= userEmail.getText().toString();
        if(usermobile.length()!=10||usermobile.contains("[a-zA-Z]+")) {
            return INVALID_NUMBER;
        } else if(!useremail.contains("@"))
            return INVALID_EMAIL;
        else if(username.length()==0)
            return INVALID_NAME;
        else {
            return VALIDATION_SUCCESS;
        }
    }
}

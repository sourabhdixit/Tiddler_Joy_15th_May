package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import com.android.camera.CropImageIntentBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tt.whorlviewlibrary.WhorlView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.whizkidzmedia.tiddlerjoy.ChildInterface.ChildLoginActivity;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.DataModels.UserProfile;
import com.whizkidzmedia.tiddlerjoy.Networking.AddChildProfileAsyncTask;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.CameraHandler;
import com.whizkidzmedia.tiddlerjoy.Utilities.ConnectionDetector;
import com.whizkidzmedia.tiddlerjoy.Utilities.DialogBox;
import com.whizkidzmedia.tiddlerjoy.Utilities.SharedPrefs;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

public class AddChildProfileActivity extends Activity implements DatePickerDialog.OnDateSetListener,View.OnClickListener {

    ImageView calendarImgView;
    CameraHandler cameraHandler;
    private byte[] imageCaptured;
    private boolean backButtonDisabled=false;
    private static int REQUEST_PICTURE = 1;
    private static int REQUEST_CROP_PICTURE = 2;
    private static int REQUEST_CLICK_PICTURE=3;
    private static int REQUEST_CROP_PICTURE_FROM_CAMERA=4;
    public String childPicLocation;
    RoundedImageView childImageView;
    TextView dateOfBirthTextView;
    ImageView boyGenderIv,girlGenderIv;
    View boyGenderView,girlGenderView;
    EditText childNameEt;
    Button submitButton;
    public static final int GENDER_GIRL=2;
    public static final int GENDER_BOY = 1;
    public static int CURRENT_GENDER_SELECTION = 2;
    String childName,childDob;
    WhorlView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_profile);
        initUI();
        
    }

    private void initUI() {


        dateOfBirthTextView = (TextView)findViewById(R.id.age_input_tview);
        childNameEt = (EditText)findViewById(R.id.name_input_edittext);
        childImageView = (RoundedImageView)findViewById(R.id.child_image);
        cameraHandler=new CameraHandler(this, new CameraHandler.CameraHandlerDone() {
            @Override
            public void onCropped() {
                backButtonDisabled=false;
            }
        });
        calendarImgView = (ImageView)findViewById(R.id.dob_calendar);
        calendarImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddChildProfileActivity.this, "yo", Toast.LENGTH_SHORT).show();
                showCalendarDialog();
            }
        });
        submitButton = (Button)findViewById(R.id.submit_btn);
        boyGenderIv = (ImageView)findViewById(R.id.gender_boy);
        girlGenderIv = (ImageView)findViewById(R.id.gender_girl);
        boyGenderView = findViewById(R.id.gender_boy_select);
        girlGenderView = findViewById(R.id.gender_girl_select);
        boyGenderIv.setOnClickListener(this);
        girlGenderIv.setOnClickListener(this);
        boyGenderView.setOnClickListener(this);
        girlGenderView.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        findViewById(R.id.change_image_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonDisabled = false;
                dispatchTakePictureIntentNew();
            }
        });
        childName=childDob="";
        loadingView = (WhorlView)findViewById(R.id.whorl2);

    }

    private void showCalendarDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AddChildProfileActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void dispatchTakePictureIntentNew()
    {
        AlertDialog.Builder getImageFrom = new AlertDialog.Builder(this);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), REQUEST_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        File croppedImageFile = new File(getFilesDir(), "test.jpg");;//Environment.getExternalStorageDirectory().toString();
        childPicLocation = croppedImageFile.getAbsolutePath();


        if ((requestCode == REQUEST_PICTURE) && (resultCode == RESULT_OK)) {
            // When the user is done picking a picture, let's start the CropImage Activity,
            // setting the output image file and size to 144x144 pixels square.
            Uri croppedImage = Uri.fromFile(croppedImageFile);

            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(1,1,144,144,croppedImage);

            cropImage.setOutlineColor(0xFF03A9F4);
            cropImage.setSourceImage(data.getData());

            startActivityForResult(cropImage.getIntent(this), REQUEST_CROP_PICTURE);
           // Log.i(DEBUG_TAG,"Fired crop intent");
        }

        else if ((requestCode == REQUEST_CROP_PICTURE) && (resultCode == RESULT_OK)) {

            // When we are done cropping, display it in the ImageView.
            String path = croppedImageFile.getAbsolutePath();
            Bitmap imageBitmap=BitmapFactory.decodeFile(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            //Save this bytearray to our gloval var
            imageCaptured = stream.toByteArray();
            childImageView.setImageBitmap(imageBitmap);
            //Picasso.with(this).load(path).transform(new CircularTransform(100,5)).into(childImageView);
        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }

    private void showDate(int year, int month, int day) {

        dateOfBirthTextView.setText(" " + new StringBuilder().append(day).append(" / ")
                .append(month).append(" / ").append(year));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.gender_girl_select: switchStates(GENDER_GIRL);
                break;
            case R.id.gender_girl: switchStates(GENDER_GIRL);
                break;
            case R.id.gender_boy: switchStates(GENDER_BOY);
                break;
            case R.id.gender_boy_select: switchStates(GENDER_BOY);
                break;
            case R.id.submit_btn: addChildProfile();
                break;
        }
    }

    private void addChildProfile() {

        boolean isValid = false;
        isValid = validateInputs();
        SharedPreferences prefs=this.getSharedPreferences(SharedPrefs.SHARED_PREFS_NAME,0);
        String parentId = prefs.getString(SharedPrefs.USER_ID,"");
        if(isValid)
        {
            String[] dob = dateOfBirthTextView.getText().toString().split("/");
            childDob = dob[2].trim()+"-"+dob[1].trim()+"-"+dob[0].trim();
            if(ConnectionDetector.isConnectedToInternet(AddChildProfileActivity.this))
            new AddChildProfileAsyncTask(parentId,childName,CURRENT_GENDER_SELECTION,childDob.toString()){

                @Override
                protected void onPreExecute() {
                    loadingView.setVisibility(View.VISIBLE);
                    loadingView.start();
                }

                @Override
                protected void onPostExecute(ChildProfile childProfile) {
                    loadingView.stop();
                    loadingView.setVisibility(View.GONE);
                    if(childProfile.childID!=null){
                        addToDatabase(childProfile);
                    }
                    else
                    {
                        new DialogBox(AddChildProfileActivity.this,"Try Again !!!");
                    }
                }
            }.execute();
            else
                ConnectionDetector.displayNoConnectionDialog(AddChildProfileActivity.this);

        }
        else
        {

        }

    }

    private boolean validateInputs() {
        childName = childNameEt.getText().toString();
        if(childName.equals("")|| dateOfBirthTextView.getText().toString().equals(""))
            return false;
        else
            return true;
    }

    private synchronized void addToDatabase(ChildProfile childProfile) {

          if(imageCaptured!=null)
              childProfile.image=imageCaptured;
        childProfile.save();
//        childProfile.maxDailyTimeHours=Integer.parseInt(daily_time_hours.getText().toString());
//        String childMinutes = daily_time_mins.getText().toString();
//        if(childMinutes.contains("30"))
//        childProfile.maxDailyTimeMins= 30;
//        else
//        childProfile.maxDailyTimeMins=0;//Integer.parseInt(daily_time_mins.getText().toString());

        //int startHrs = Integer.valueOf(fromHours[timeFromHours.getSelectedItemPosition()]);
        //String amPmStartHrs = fromAmPm[timeFromAmPm.getSelectedItemPosition()];
        //int endHrs =  Integer.valueOf(toHours[timeToHours.getSelectedItemPosition()]);
        //String amPmEndHrs = toAmPm[timeToAmPm.getSelectedItemPosition()];
//        if(amPmStartHrs.equals("PM"))
//            startHrs+=12;
//        if(amPmEndHrs.equals("PM"))
//            endHrs+=12;
//        childProfile.childUsageStartHour = hoursFrom;
//        childProfile.childUsageEndHour = hoursTo;
//        childProfile.save();
        writeToSharedPreference(true);
        Intent intent = new Intent(AddChildProfileActivity.this, ChildLoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void writeToSharedPreference(boolean variable) {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(SharedPrefs.SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(UserProfile.IS_CHILD_ADDED, variable);
        String childCount = prefs.getString(UserProfile.CHILD_COUNT,"");
        int newCount =0;
        if(childCount.equals(""))
            newCount=1;
        else
            newCount+=Integer.parseInt(childCount);
        editor.putString(UserProfile.CHILD_COUNT,String.valueOf(newCount));
        editor.commit();
    }

    private void switchStates(int genderSelected) {

        if(genderSelected==GENDER_GIRL)
        {
            girlGenderIv.setImageResource(R.drawable.radio_box_selected);
            boyGenderIv.setImageResource(R.drawable.radio_box);
            CURRENT_GENDER_SELECTION=GENDER_GIRL;
            if(imageCaptured==null)
                childImageView.setImageResource(R.drawable.female);
        }
        else
        {
            girlGenderIv.setImageResource(R.drawable.radio_box);
            boyGenderIv.setImageResource(R.drawable.radio_box_selected);
            CURRENT_GENDER_SELECTION=GENDER_BOY;
            if(imageCaptured==null)
                childImageView.setImageResource(R.drawable.male);
        }


    }
}

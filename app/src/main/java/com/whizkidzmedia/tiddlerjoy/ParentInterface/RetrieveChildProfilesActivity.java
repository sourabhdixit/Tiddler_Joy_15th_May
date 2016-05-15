package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.DialogBox;

public class RetrieveChildProfilesActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener{

    View selectEmail,selectMobileNo;
    ImageView emailOptionIv,mobileNoIv;
    EditText emailIdEt,mobileNoEt;
    Button retrieveProfilesBtn;
    public static final int CASE_EMAIL=0;
    public static final int CASE_MOBILE_NO=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_child_profiles);
        initUI();
    }

    private void initUI() {

        selectEmail = findViewById(R.id.select_email_option);
        selectMobileNo = findViewById(R.id.contact_no_option);
        emailOptionIv = (ImageView)findViewById(R.id.imageview_email);
        mobileNoIv = (ImageView)findViewById(R.id.imageview_mobile_no);
        emailIdEt = (EditText)findViewById(R.id.email_id_input);
        mobileNoEt = (EditText)findViewById(R.id.mobile_no_input);
        retrieveProfilesBtn = (Button)findViewById(R.id.retrieve_button);
        emailIdEt.setOnFocusChangeListener(this);
        mobileNoEt.setOnFocusChangeListener(this);
        retrieveProfilesBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.select_email_option: switchStates(CASE_EMAIL);
                break;
            case R.id.contact_no_option: switchStates(CASE_MOBILE_NO);
                break;
            case R.id.retrieve_button : validateInputs();
                break;
        }
    }

    private boolean validateInputs() {
        if(emailIdEt.getText().toString().length()>0 && mobileNoEt.getText().toString().length()>0) {
            new DialogBox(this, "Use only one amongst email and mobile number.");
        return false;
            }
        else if(emailIdEt.getText().toString().length()==0 && mobileNoEt.getText().toString().length()==0)
        {new DialogBox(this,"Required fields are blank.");
        return false;}
        else
        {
            new DialogBox(this,"Success");
            return true;
        }

    }

    private void switchStates(int requestedCase) {

        if(requestedCase==CASE_EMAIL)
        {
            emailOptionIv.setImageResource(R.drawable.radio_box_selected);
            mobileNoIv.setImageResource(R.drawable.radio_box);
        }
        else
        {
            emailOptionIv.setImageResource(R.drawable.radio_box);
            mobileNoIv.setImageResource(R.drawable.radio_box_selected);

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId())
        {
            case R.id.mobile_no_input:
                if(hasFocus)
                switchStates(CASE_MOBILE_NO);
                else
                switchStates(CASE_EMAIL);
                break;
            case R.id.email_id_input:
                if (hasFocus)
                    switchStates(CASE_EMAIL);
                else
                switchStates(CASE_MOBILE_NO);
                break;
        }

    }
}

package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.whizkidzmedia.tiddlerjoy.Networking.SubmitUserQueryAsyncTask;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.DialogBox;

public class HelpActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    RadioGroup timeSlotRadioGroup,modeOfContactRadioGroup;
    EditText queryEditText,emailEditText,phoneEditText;
    Button submitBtn;
    int selectedRadioBtnId;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initUI();
    }

    private void initUI() {

        timeSlotRadioGroup = (RadioGroup)findViewById(R.id.time_of_contact_radio_grp);
        //timeSlotRadioGroup.setOnCheckedChangeListener(this);
        modeOfContactRadioGroup = (RadioGroup)findViewById(R.id.mode_of_contact_radio_grp);
        //modeOfContactRadioGroup.setOnCheckedChangeListener(this);
        queryEditText = (EditText)findViewById(R.id.query_input);
        emailEditText = (EditText)findViewById(R.id.email_id_input);
        phoneEditText = (EditText)findViewById(R.id.mobile_no_input);
        submitBtn = (Button)findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
            }
        });
    }

    private void validateInput() {
        String queryString,modeOfContactString,timeOfContactString,emailString,mobileNoString,timeOfContactStringValue,modeOfContactStringValue;
        RadioButton modeOfContactRadioBtn,timeOfContactRadioBtn;
        selectedRadioBtnId = modeOfContactRadioGroup.getCheckedRadioButtonId();
        modeOfContactRadioBtn = (RadioButton)findViewById(selectedRadioBtnId);
        modeOfContactString = String.valueOf(modeOfContactRadioGroup.indexOfChild(modeOfContactRadioBtn));
        selectedRadioBtnId = timeSlotRadioGroup.getCheckedRadioButtonId();
        timeOfContactRadioBtn = (RadioButton)findViewById(selectedRadioBtnId);
        timeOfContactString = String.valueOf(timeSlotRadioGroup.indexOfChild(timeOfContactRadioBtn));
        queryString = queryEditText.getText().toString();
        modeOfContactStringValue = modeOfContactRadioBtn.getText().toString();
        timeOfContactStringValue = timeOfContactRadioBtn.getText().toString();
        emailString = emailEditText.getText().toString();
        mobileNoString = phoneEditText.getText().toString();
        if(queryString.equals("")||modeOfContactStringValue.equals("")||timeOfContactStringValue.equals(""))
         new DialogBox(this," You left one of the mandatory fields blank ");
        else if(emailString.equals("")&&mobileNoString.equals(""))
            new DialogBox(this," Please fill your Email Id or Mobile No");
        else
            submitQuery(queryString,modeOfContactString,timeOfContactString,emailString,mobileNoString);

    }

    private void submitQuery(String queryString, String modeOfContactString, String timeOfContactStrng, String emailString, String mobileNoString) {

        new DialogBox(this,"Submitted successfully.Thanks for sending us your query");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        selectedRadioBtnId = checkedId;
    }
}

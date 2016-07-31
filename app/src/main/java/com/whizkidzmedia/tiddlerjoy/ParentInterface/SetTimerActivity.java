package com.whizkidzmedia.tiddlerjoy.ParentInterface;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.whizkidzmedia.tiddlerjoy.R;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    ImageView watchTimerImgView;
    TextView timerTextView;
    int currentWatchIndex=0;
    int[] watchTimerImgs;
    TextView startTimeEt,endTimeEt;
    public static final int START_TIME_PICKER=0,END_TIME_PICKER=1;
    public static int CURRENT_TIME_PICKER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);
        initUi();
    }

    private void initUi() {

        watchTimerImgView = (ImageView)findViewById(R.id.timer_iv);
        timerTextView = (TextView)findViewById(R.id.timer_tv);
        startTimeEt = (TextView)findViewById(R.id.starttime_picker);
        endTimeEt = (TextView)findViewById(R.id.endtime_picker);
        endTimeEt.setOnClickListener(this);
        startTimeEt.setOnClickListener(this);
        watchTimerImgView.setOnClickListener(this);
        watchTimerImgs = new int[]{R.drawable.timer_1,R.drawable.timer_2,R.drawable.timer_3,R.drawable.timer_4,R.drawable.timer_5,R.drawable.timer_6
        ,R.drawable.timer_7,R.drawable.timer_8};
        updateWatchTimer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.timer_iv: updateWatchTimer();
                break;
            case R.id.starttime_picker: CURRENT_TIME_PICKER = START_TIME_PICKER;
                openTimePickerDialog();
                break;
            case R.id.endtime_picker: CURRENT_TIME_PICKER = END_TIME_PICKER;
                openTimePickerDialog();
                break;
        }
    }

    private void openTimePickerDialog() {

        if(CURRENT_TIME_PICKER==START_TIME_PICKER)
        new TimePickerDialog(this,this,8,0,true).show();
        else
        new TimePickerDialog(this,this,20,0,true).show();
    }

    private void updateWatchTimer() {
        String timeString;

        if(currentWatchIndex==8)
            currentWatchIndex=0;
        watchTimerImgView.setImageResource(watchTimerImgs[currentWatchIndex]);
        currentWatchIndex++;
        if(currentWatchIndex==0)
            timeString=" 30 mins ";
        else if(currentWatchIndex%2==0)
            timeString = String.valueOf(currentWatchIndex/2)+ " hours ";
        else
            timeString = String.valueOf(currentWatchIndex/2)+" hours " + " 30 mins";
        timerTextView.setText(timeString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

     String hourOfDayString="",minuteString="";
        if(hourOfDay<10)
            hourOfDayString="0"+String.valueOf(hourOfDay);
        else
            hourOfDayString=String.valueOf(hourOfDay);
        if(minute<10)
            minuteString="0"+String.valueOf(minute);
        else
            minuteString=String.valueOf(minute);
        if(CURRENT_TIME_PICKER==START_TIME_PICKER)
        {
            startTimeEt.setText(" "+hourOfDayString+":"+minuteString);
        }
        if(CURRENT_TIME_PICKER==END_TIME_PICKER)
        {
            endTimeEt.setText(" "+hourOfDayString+":"+minuteString);
        }
    }
}

package com.whizkidzmedia.tiddlerjoy.Fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.whizkidzmedia.tiddlerjoy.DataModels.EarlyLearningDomainData;
import com.whizkidzmedia.tiddlerjoy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourabh Dixit on 06-05-2016.
 */
public class ListDomainPagerFragment extends Fragment {

    public static final String ABOUT_MYSELF_VIEW_ID = "1";
    public static final String BEAUTIFUL_WORLD_VIEW_ID = "4";
    public static final String COMMUNICATION_VIEW_ID = "5";
    public static final String EARLY_MATHS_VIEW_ID = "2";
    public static final String CREATIVITY_VIEW_ID = "3";
    public static String CURRENT_SELECTED_BOGIE="1";
    public static String domainString="";
    public int iteration, end;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static List<String> data;
    public TextView[] domainTextViews;
    public ProgressBar[] progressBars;
    public int[] progressBarColors;
    public ImageView leftScroller,rightScroller;
    TextView messageTextView;
    public static final ListDomainPagerFragment newInstance(String domainString,int start, int end)
    {
        String selectedDomainIndex=domainString;
        ListDomainPagerFragment f = new ListDomainPagerFragment();
        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_MESSAGE, selectedDomainIndex);
        bdl.putInt("Iteration", start);
        bdl.putInt("Iteration_1",end);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
        iteration = getArguments().getInt("Iteration");
        end = getArguments().getInt("Iteration_1");
        View v = inflater.inflate(R.layout.list_domain_pager_fragment, container, false);
        messageTextView = (TextView)v.findViewById(R.id.textView);
        leftScroller = (ImageView)v.findViewById(R.id.viewpager_left);
        rightScroller = (ImageView)v.findViewById(R.id.viewpager_right);
        initData(message,iteration,end);
        initDomainTextViews(v);
        initViewPagerScrollArrows();

        return v;
    }

    private void initData(String selectedDomainIndex,int start, int end) {

        data = new ArrayList<>();
        if(selectedDomainIndex.equals("1")) {
            ArrayList<EarlyLearningDomainData> eldaData = (ArrayList)new Select().all().from(EarlyLearningDomainData.class).where(" LearningAreaName = ?","All About Me").execute();
            for(int i=start;i<end;i++)
            {
                data.add(eldaData.get(i).eldaName);
            }
            domainString = "ABOUT MYSELF";
        }
        else if(selectedDomainIndex.equals("2")) {
            ArrayList<EarlyLearningDomainData> eldaData = (ArrayList)new Select().all().from(EarlyLearningDomainData.class).where(" LearningAreaName = ?","Early Maths").execute();
            for(int i=start;i<end;i++)
            {
                data.add(eldaData.get(i).eldaName);
            }
            domainString = "EARLY MATHS";
        }
        else if(selectedDomainIndex.equals("3")) {
            ArrayList<EarlyLearningDomainData> eldaData = (ArrayList)new Select().all().from(EarlyLearningDomainData.class).where(" LearningAreaName = ?","Creativity").execute();
            for(int i=start;i<end;i++)
            {
                data.add(eldaData.get(i).eldaName);
            }
            domainString = "CREATIVITY";
        }
        else if(selectedDomainIndex.equals("4")) {
            ArrayList<EarlyLearningDomainData> eldaData = (ArrayList)new Select().all().from(EarlyLearningDomainData.class).where(" LearningAreaName = ?","Beautiful World").execute();
            for(int i=start;i<end;i++)
            {
                data.add(eldaData.get(i).eldaName);
            }
            domainString = "BEAUTIFUL WORLD";
        }
        else {
            ArrayList<EarlyLearningDomainData> eldaData = (ArrayList)new Select().all().from(EarlyLearningDomainData.class).where(" LearningAreaName = ?","Communication").execute();
            for(int i=start;i<end;i++)
            {
                data.add(eldaData.get(i).eldaName);
            }
            domainString = "COMMUNICATION";
        }

    }

    private void initViewPagerScrollArrows() {

        switch (iteration)
        {
            case 0: if(data.size()>4) {
                leftScroller.setVisibility(View.GONE);
                rightScroller.setVisibility(View.VISIBLE);
            }
                else {
                rightScroller.setVisibility(View.GONE);
                leftScroller.setVisibility(View.GONE);
            }
                break;
            case 1: if(data.size()>8){
                leftScroller.setVisibility(View.VISIBLE);
                rightScroller.setVisibility(View.VISIBLE);
            }
                else
            rightScroller.setVisibility(View.GONE);
                break;
            case 2: if(data.size()>12){
                leftScroller.setVisibility(View.VISIBLE);
                rightScroller.setVisibility(View.VISIBLE);
            }
                else
                rightScroller.setVisibility(View.GONE);
                break;
            case 3: rightScroller.setVisibility(View.GONE);
                break;
        }
    }

    private void initDomainTextViews(View view) {
        domainTextViews = new TextView[]{(TextView) view.findViewById(R.id.domain_first),(TextView) view.findViewById(R.id.domain_second),(TextView) view.findViewById(R.id.domain_third)
        ,(TextView) view.findViewById(R.id.domain_fourth)};
        progressBars = new ProgressBar[]{(ProgressBar)view.findViewById(R.id.progress_first),(ProgressBar)view.findViewById(R.id.progress_second),(ProgressBar)view.findViewById(R.id.progress_third),
                (ProgressBar)view.findViewById(R.id.progress_fourth)};
        progressBarColors = new int[]{Color.GREEN,Color.RED,Color.YELLOW,Color.parseColor("#F185F3")};
        int beg= 0,textViewCount=0,end=this.end;
        for(textViewCount=0;textViewCount<4;beg++,textViewCount++) {
            if(beg<data.size()) {
                EarlyLearningDomainData eldaData = new Select().all().from(EarlyLearningDomainData.class).where(" Name = ?", data.get(beg)).executeSingle();
                end++;
                domainTextViews[textViewCount].setVisibility(View.VISIBLE);
                domainTextViews[textViewCount].setText(eldaData.eldaName);
                domainTextViews[textViewCount].setTextColor(Color.BLACK);
                domainTextViews[textViewCount].setEllipsize(TextUtils.TruncateAt.END);
                domainTextViews[textViewCount].setMaxLines(1);
                domainTextViews[textViewCount].setTextSize(12);
                initProgressBars(view, textViewCount, true, 0);
                progressBars[textViewCount].setProgress(eldaData.currentVideoCount);
                progressBars[textViewCount].setMax(eldaData.totalVideoCount);
                messageTextView.setText(domainString);
                //domainTextViews[textViewCount].setTextColor(progressBarColors[textViewCount]);
            }
            else
            {
                domainTextViews[textViewCount].setVisibility(View.GONE);
                initProgressBars(view,textViewCount, false,0);
            }
        }


    }

    private void initProgressBars(View view, int index, boolean isVisible, int progress) {

            if(isVisible) {
                progressBars[index].setVisibility(View.VISIBLE);
                progressBars[index].setProgress(index*10+20);
                if(index!=3)
                progressBars[index].getProgressDrawable().setColorFilter(progressBarColors[index], PorterDuff.Mode.MULTIPLY);
                else
                    progressBars[index].getProgressDrawable().setColorFilter(progressBarColors[index], PorterDuff.Mode.LIGHTEN);
            }
            else
                progressBars[index].setVisibility(View.GONE);


    }
}

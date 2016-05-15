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

import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;

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
    public int iteration;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static List<String> data;
    public TextView[] domainTextViews;
    public ProgressBar[] progressBars;
    public int[] progressBarColors;
    public ImageView leftScroller,rightScroller;
    public static final ListDomainPagerFragment newInstance(String selectedDomainIndex,int start, int end)
    {
        ListDomainPagerFragment f = new ListDomainPagerFragment();
        if(selectedDomainIndex.equals("1")) {
            data = AppConstants.getMyselfTopics();
            domainString = "ABOUT MYSELF";
        }
        else if(selectedDomainIndex.equals("2")) {
            data = AppConstants.getMathsTopics();
            domainString = "EARLY MATHS";
        }
        else if(selectedDomainIndex.equals("3")) {
            data = AppConstants.getCreativityTopics();
            domainString = "CREATIVITY";
        }
        else if(selectedDomainIndex.equals("4")) {
            data = AppConstants.getWorldAroundMeTopics();
            domainString = "BEAUTIFUL WORLD";
        }
        else {
            data = AppConstants.getCommunicationTopics();
            domainString = "COMMUNICATION";
        }

        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_MESSAGE, selectedDomainIndex);
        bdl.putInt("Iteration", start);
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
        View v = inflater.inflate(R.layout.list_domain_pager_fragment, container, false);
        TextView messageTextView = (TextView)v.findViewById(R.id.textView);
        messageTextView.setText(domainString);
        leftScroller = (ImageView)v.findViewById(R.id.viewpager_left);
        rightScroller = (ImageView)v.findViewById(R.id.viewpager_right);
        initDomainTextViews(v);
        initViewPagerScrollArrows();

        return v;
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
        int beg= iteration*4,textViewCount=0,end=0;
        for(textViewCount=0;textViewCount<4;beg++,textViewCount++) {
            if(beg<data.size()) {
                end++;
                domainTextViews[textViewCount].setVisibility(View.VISIBLE);
                domainTextViews[textViewCount].setText(data.get(beg));
                domainTextViews[textViewCount].setTextColor(Color.BLACK);
                domainTextViews[textViewCount].setEllipsize(TextUtils.TruncateAt.END);
                domainTextViews[textViewCount].setMaxLines(1);
                domainTextViews[textViewCount].setTextSize(12);
                //domainTextViews[textViewCount].setTextColor(progressBarColors[textViewCount]);
            }
            else
            {
                domainTextViews[textViewCount].setVisibility(View.GONE);
            }
        }
        initProgressBars(view,end);

    }

    private void initProgressBars(View view,int textViewCount) {

        for(int i = 0; i<progressBars.length;i++)
        {
            if(i<textViewCount) {
                progressBars[i].setVisibility(View.VISIBLE);
                progressBars[i].setProgress(i*10+20);
                if(i!=3)
                progressBars[i].getProgressDrawable().setColorFilter(progressBarColors[i], PorterDuff.Mode.MULTIPLY);
                else
                    progressBars[i].getProgressDrawable().setColorFilter(progressBarColors[i], PorterDuff.Mode.LIGHTEN);
            }
            else
                progressBars[i].setVisibility(View.GONE);
        }

    }
}

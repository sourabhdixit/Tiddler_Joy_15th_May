package com.whizkidzmedia.tiddlerjoy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildProfile;
import com.whizkidzmedia.tiddlerjoy.ParentInterface.ParentVideoHistoryActivity;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourabh Dixit on 05-05-2016.
 */
public class LearningDomainFragment extends Fragment implements ViewPager.OnPageChangeListener {

    String selectedDomainIndex;
    MyPagerAdapter pageAdapter;
    ImageView domainImageView;
    ViewPager pager;
    private TextView childNameTview;
    private List<ChildProfile> profiles;

    public LearningDomainFragment()
    {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        selectedDomainIndex = bundle.getString("domain","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.learning_domain_fragment_layout, container, false);
        List<Fragment> fragments = getFragments(selectedDomainIndex);
        pageAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        pager = (ViewPager)v.findViewById(R.id.viewpager);
        childNameTview = (TextView)v.findViewById(R.id.child_name);
        if(profiles==null)
            profiles = new Select().from(ChildProfile.class).execute();
        childNameTview.setText(profiles.get(0).childName);
        pager.setPageTransformer(true,new DepthPageTransformer());
        domainImageView = (ImageView)v.findViewById(R.id.category_iv);
        domainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ParentVideoHistoryActivity.class);
                intent.putExtra("ClickedDomain",selectedDomainIndex);
                startActivity(intent);
            }
        });
        initImgView();
        initViewPager();
        pager.setAdapter(pageAdapter);
        return v;
    }

    private void initViewPager() {

    }

    private void initImgView() {

        int imgId=0;
        switch (Integer.parseInt(selectedDomainIndex))
        {
            case 1: imgId = R.drawable.flower_all_about_me;
                break;
            case 2: imgId = R.drawable.flower_earlymaths;
                break;
            case 3: imgId = R.drawable.flower_creativity;
                break;
            case 4: imgId = R.drawable.flower_beautiful_world;
                break;
            case 5: imgId = R.drawable.flower_communication;
                break;
        }
        domainImageView.setImageResource(imgId);

    }


    private List<Fragment> getFragments(String selectedDomainIndex) {
        List<Fragment> fList = new ArrayList<Fragment>();
        ArrayList<String> data = new ArrayList<String>();

        if(selectedDomainIndex.equals("1")) {
            data = AppConstants.getMyselfTopics();
        }
        else if(selectedDomainIndex.equals("2")) {
            data = AppConstants.getMathsTopics();
        }
        else if(selectedDomainIndex.equals("3")) {
            data = AppConstants.getCreativityTopics();
        }
        else if(selectedDomainIndex.equals("4")) {
            data = AppConstants.getWorldAroundMeTopics();
        }
        else {
            data = AppConstants.getCommunicationTopics();
        }

        int end=3;
        for(int i=0;i<data.size();i++,end++) {
            if(i%4==0)
            fList.add(ListDomainPagerFragment.newInstance(selectedDomainIndex,i/4,i+3));
        }

            return fList;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

}

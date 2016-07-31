package com.whizkidzmedia.tiddlerjoy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.AppConstants;
import com.whizkidzmedia.tiddlerjoy.YouTube.YouTubePlayerActivity;

/**
 * Created by Sourabh Dixit on 16-05-2016.
 */
public class ChildWatchedVideoFragment extends Fragment {

    String videoTitle,videoId;

    public static final ChildWatchedVideoFragment newInstance(String videoId,String  title)
    {
        ChildWatchedVideoFragment f = new ChildWatchedVideoFragment();
        Bundle bdl = new Bundle();
        bdl.putString("video_id", videoId);
        bdl.putString("video_title", title);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        videoId = bundle.getString("video_id","");
        videoTitle = bundle.getString("video_title","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.grid_single_element, container, false);
        initLayout(v);
        return v;
    }

    private void initLayout(View v) {

        ImageView videoImgView = (ImageView)v.findViewById(R.id.grid_image);
        videoImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YouTubePlayerActivity.class);
                intent.putExtra("VideoId", videoId);
                startActivity(intent);
            }
        });
        TextView videoTitleTextView = (TextView)v.findViewById(R.id.grid_text);
        String imgUrl = "http://img.youtube.com/vi/" + videoId + "/0.jpg";
        Picasso.with(getActivity()).load(imgUrl).into(videoImgView);
        videoTitleTextView.setTypeface(Typeface.SERIF, Typeface.ITALIC);
        videoTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        videoTitleTextView.setMaxLines(2);
        videoTitleTextView.setText(videoTitle);
    }
}

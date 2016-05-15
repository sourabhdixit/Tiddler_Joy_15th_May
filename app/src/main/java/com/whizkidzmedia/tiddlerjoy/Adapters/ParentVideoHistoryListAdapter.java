package com.whizkidzmedia.tiddlerjoy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.R;

import java.util.ArrayList;

/**
 * Created by Sourabh Dixit on 21-04-2016.
 */
public class ParentVideoHistoryListAdapter extends BaseAdapter {
    private Context mContext;
    //    private final String[] web;
//    private final int[] Imageid;
    ArrayList<ChildVideo> videoData;
    int screenHeight,screenWidth;
    RelativeLayout.LayoutParams vidImgParams,imgParentParams ;


    public ParentVideoHistoryListAdapter(Context c,ArrayList<ChildVideo> data) {
        mContext = c;
        this.videoData = data;
        screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        vidImgParams = new RelativeLayout.LayoutParams(screenWidth*35/100,screenHeight*18/100);
        imgParentParams = vidImgParams;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return videoData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return videoData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout imgParent=null;
        String imgUrl = "http://img.youtube.com/vi/" + videoData.get(position).videoUrl + "/0.jpg";
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.video_history_list_element, null);
            viewHolder.vidTitle = (TextView) convertView.findViewById(R.id.video_title);
            viewHolder.vidTopic = (TextView) convertView.findViewById(R.id.video_topic);
            viewHolder.iv = (ImageView)convertView.findViewById(R.id.video_image);
            viewHolder.iv.setScaleType(ImageView.ScaleType.FIT_XY);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.iv.setLayoutParams(vidImgParams);
        imgParent = (RelativeLayout)convertView.findViewById(R.id.image_layout);
        imgParent.setLayoutParams(imgParentParams);
        Picasso.with(mContext).load(imgUrl).into(viewHolder.iv);
        viewHolder.vidTitle.setText(videoData.get(position).videoTitle);
        //viewHolder.vidTopic.setText(videoData.get(position).videoUiTag);
        return convertView;
    }

    public class ViewHolder{
        ImageView iv;
        TextView vidTopic,vidTitle;
    }
}


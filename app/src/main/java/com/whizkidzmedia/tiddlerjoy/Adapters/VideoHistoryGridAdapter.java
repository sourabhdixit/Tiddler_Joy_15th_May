package com.whizkidzmedia.tiddlerjoy.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whizkidzmedia.tiddlerjoy.DataModels.ChildVideo;
import com.whizkidzmedia.tiddlerjoy.R;

import java.util.ArrayList;

/**
 * Created by Sourabh Dixit on 21-04-2016.
 */
public class VideoHistoryGridAdapter extends BaseAdapter {
    private Context mContext;
//    private final String[] web;
//    private final int[] Imageid;
    ArrayList<ChildVideo> videoData;

    public VideoHistoryGridAdapter(Context c,ArrayList<ChildVideo> data) {
        mContext = c;
        this.videoData = data;
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
        String imgUrl = "http://img.youtube.com/vi/" + videoData.get(position).videoUrl + "/0.jpg";
        if (convertView == null) {
            //grid = new View(mContext);
            convertView = inflater.inflate(R.layout.grid_single_element, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.grid_text);
            viewHolder.iv = (ImageView)convertView.findViewById(R.id.grid_image);
            convertView.setTag(viewHolder);
           // textView.setText(videoData.get(position).videoUiTag);

           // Picasso.with(mContext).load(imgUrl).into(imageView);
        } else {
           viewHolder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(mContext).load(imgUrl).into(viewHolder.iv);
        viewHolder.tv.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        viewHolder.tv.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tv.setMaxLines(2);
        viewHolder.tv.setText(videoData.get(position).videoTitle);
        return convertView;
    }

    public class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}

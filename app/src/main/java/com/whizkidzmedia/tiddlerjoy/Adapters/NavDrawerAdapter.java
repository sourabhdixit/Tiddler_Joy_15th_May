package com.whizkidzmedia.tiddlerjoy.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.whizkidzmedia.tiddlerjoy.R;
import com.whizkidzmedia.tiddlerjoy.Utilities.NavDrawerData;

import java.util.ArrayList;

/**
 * Created by Sourabh Dixit on 20-06-2016.
 */
public class NavDrawerAdapter extends BaseAdapter {

    public ArrayList<NavDrawerData> navDrawerData;
    public Context context;
    public Bitmap userImg;
    public String usercredts;

    public NavDrawerAdapter(Context ctx, Bitmap img, String username,String credits)
    {

        navDrawerData = new ArrayList<NavDrawerData>();
        this.context = ctx;
        navDrawerData.add(new NavDrawerData(username, 0));
        userImg = img;
        usercredts = credits;
        navDrawerData.add(new NavDrawerData("Edit Child Profile", R.drawable.edit_child_profile));
        //navDrawerData.add(new NavDrawerData("Set Child Timers", R.drawable.set_child_timer));
        navDrawerData.add(new NavDrawerData("About Us", R.drawable.about_us));
       // navDrawerData.add(new NavDrawerData(" ", R.drawable.about_us));
//        navDrawerData.add(new NavDrawerData("Show Help", R.drawable.help_icon));
//        navDrawerData.add(new NavDrawerData("Logout", R.drawable.arrow_logout));
    }

    @Override
    public int getCount() {
        return navDrawerData.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerData.get(position
        );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        String title = (navDrawerData.get(position).getTitle());
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            holder = new ViewHolder();
        else
            holder = (ViewHolder) convertView.getTag();

        if (position == 0) {
            convertView = inflater.inflate(R.layout.navdrawer_item, null);
          //  holder.image = (ImageView) convertView.findViewById(R.id.user_img);
//            holder.title = (TextView) convertView.findViewById(R.id.title);
//            holder.title.setText(title);
 //           holder.image.setImageBitmap(userImg);
//            holder.userCredits = (TextView)convertView.findViewById(R.id.available_credits);
  //          holder.userCredits.setText(""+usercredts);
            convertView.setTag(holder);

        } else {

            convertView = inflater.inflate(R.layout.navdrawer_item_list, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.title.setText(title);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.image.setImageResource(navDrawerData.get(position).getIcon());
            convertView.setTag(holder);

        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView title,userCredits;
        public CheckBox checkBox;
        public ImageView image, tick;
    }
}

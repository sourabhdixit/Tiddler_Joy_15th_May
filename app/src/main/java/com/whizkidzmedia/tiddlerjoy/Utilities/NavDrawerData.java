package com.whizkidzmedia.tiddlerjoy.Utilities;

/**
 * Created by Sourabh Dixit on 20-06-2016.
 */
public class NavDrawerData {

    private String title;
    private int icon;

    public NavDrawerData() {
    }

    public NavDrawerData(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

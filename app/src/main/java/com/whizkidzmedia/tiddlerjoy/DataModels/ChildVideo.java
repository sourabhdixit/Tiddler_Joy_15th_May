package com.whizkidzmedia.tiddlerjoy.DataModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Sourabh Dixit on 20-04-2016.
 */
@Table(name = "ChildVideos")
public class ChildVideo extends Model {

    public static final String VIDEO_ACCEPTED="1";
    public static final String VIDEO_MIDWATCHED="2";
    public static final String VIDEO_REJECTED="3";

    @Column(name="ChildId")
    public String childId;

    @Column(name="VideoId")
    public String videoId;

    @Column(name="VideoUrl")
    public String videoUrl;

    @Column(name="VideoUiTag")
    public String videoUiTag;

    @Column(name="VideoWatched")
    public String videoWatchValue;

    @Column(name="AllAboutMe")
    public String allAboutMeTags;

    @Column(name="BeautifulWorld")
    public String beautifulWorldTags;

    @Column(name="Communication")
    public String communicationTags;

    @Column(name="Creativity")
    public String creativityTags;

    @Column(name="EarlyMaths")
    public String earlyMathsTags;

    @Column(name="VideoTitle")
    public String videoTitle;

    @Column(name="MinAge")
    public String minAge;

    @Column(name="MaxAge")
    public String maxAge;

    @Column(name="VideoDescription")
    public String videoDesc;

    @Column(name="VideoChannel")
    public String videoChannel;

    @Column(name="VideoLength")
    public String videoLength;

    @Column(name="VideoViews")
    public String videoViews;

    @Column(name="VideoRating")
    public float videoRating;

    @Column(name="VideoTheme")
    public String videoTheme;

    @Column(name="VideoSubTheme")
    public String videoSubTheme;

    @Column(name="VideoTimeSpent")
    public long watchedVideoTime;

    @Column(name="IsVideoWatched")
    public Boolean isVideoWatched;

    public ChildVideo()
    {
        super();
    }
    public ChildVideo(String url)
    {
        super();
        this.videoUrl=url;
    }

}


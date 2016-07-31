package com.whizkidzmedia.tiddlerjoy.DataModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Sourabh Dixit on 27-05-2016.
 */
@Table(name = "ChildHistoryVideos")
public class ChildHistoryVideo extends Model{

    @Column(name ="ChildId")
    public String childId;

    @Column(name ="VideoUrl")
    public String videoUrl;

    @Column(name ="VideoId")
    public String videoId;

    @Column(name = "YoutTubeId")
    public String youTubeId;

    @Column(name = "VideoWatchStatus")
    public String videoWatchStatus;

    @Column(name = "VideoUiTag")
    public String videoUiTag;

    @Column(name = "IsYoutubeVideo")
    public boolean isVideoYouTube;
}

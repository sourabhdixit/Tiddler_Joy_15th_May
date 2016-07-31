package com.whizkidzmedia.tiddlerjoy.DataModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Sourabh Dixit on 04-07-2016.
 */
@Table(name = "EarlyLearningDomainTable")
public class EarlyLearningDomainData extends Model {

    public static final String ELDA_NAME = "name";
    public static final String CURRENT_VIDEO_COUNT = "count";
    public static final String TOTAL_VIDEO_COUNT = "total_count";
    public static final String ELDA_ID = "id";
    public static final int MAX_VIDEO_COUNT_VALUE = 10;

    @Column(name = "Name")
    public String eldaName;

    @Column(name = "EldaId")
    public int eldaId;

    @Column(name = "CurrentVideoCount")
    public int currentVideoCount;

    @Column(name = "TotalVideoCount")
    public int totalVideoCount;

    @Column(name = "LearningAreaName")
    public String learningAreaName;

    @Column(name = "LearningAreaId")
    public int learningAreaId;



}

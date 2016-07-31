package com.whizkidzmedia.tiddlerjoy.DataModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Sourabh Dixit on 04-07-2016.
 */
@Table(name = "LearningAreaData")
public class LearningAreaData extends Model{

    public static final String ALL_ABOUT_ME = "All About Me";
    public static final String COMMUNICATION = "Communication";
    public static final String BEAUTIFUL_WORLD = "Beautiful World";
    public static final String CREATIVITY = "Creativity";
    public static final String EARLY_MATHS = "Early Maths";
    public static final String COUNT = "count";
    public static final String LEARNING_AREA_ID = "id";
    public static final String ELDA_SUB_AREAS = "elda_sub";
    public static final int TOTAL_VIDEO_COUNT = 10;


    @Column(name = "Name")
    public String learningDomain;

    @Column(name = "DomainId")
    public int domainId;

    @Column(name = "CurrentVideoCount")
    public int currentVideoCount;

    @Column(name = "TotalVideoCount")
    public int totalVideoCount;

    @Column(name = "TotalEldaCount")
    public int totalEldaCount;



}

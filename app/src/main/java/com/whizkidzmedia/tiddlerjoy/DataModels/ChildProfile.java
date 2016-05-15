package com.whizkidzmedia.tiddlerjoy.DataModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Sourabh Dixit on 25-12-2015.
 */
@Table(name = "ChildProfiles")
public class ChildProfile extends Model{

    public static final String CHILD_ID = "id";
    public static final String CHILD_NAME = "name";
    public static final String CHILD_GENDER = "gender";
    public static final String CHILD_DOB = "dob";
    public static final String CHILD_IMAGE = "image";
    public static final String CHILD_PARENT_ID = "parent";
    public static final String CHILD_COUNT = "count";
    public static final String CHILD_RESULTS = "results";

    @Column(name = "Name")
    public String childName;

//    @Column(name = "id")
//    public String childId;

    @Column(name = "Age")
    public String dob;

    @Column(name = "Sex")
    public String gender;

    @Column(name = "MaxDailyTimeHours")
    public int maxDailyTimeHours;

    @Column(name = "MaxDailyTimeMins")
    public int maxDailyTimeMins;

    @Column(name = "TimePerSessionHours")
    public int timePerSessionHours;

    @Column(name = "TimePerSessionMinutes")
    public int timePerSessionMins;

//    @Column(name = ChildConstants.REPORTING_SCHEDULE_DAILY)
//    public boolean reportingScheduleDaily;
//
//    @Column(name = ChildConstants.REPORTING_SCHEDULE_MONTHLY)
//    public boolean reportingScheduleMonthly;
//
//    @Column(name = ChildConstants.REPORTING_SCHEDULE_WEEKLY)
//    public boolean reportingScheduleWeekly;
//
//    @Column(name = ChildConstants.PREFERRED_METHOD_EMAIL)
//    public boolean preferredMethodEmail;
//
//    @Column(name = ChildConstants.PREFERRED_METHOD_SMS)
//    public boolean preferredMethodSMS;
//
//    @Column(name = ChildConstants.PREFERRED_METHOD_SOCIAL)
//    public boolean preferredMethodSocial;

    @Column(name = "ChildId")
    public String childID;

    @Column(name = "ChildParentId")
    public String childParentId;

    @Column(name = "uploaded")
    public boolean uploaded;

    @Column(name = "Image")
    public byte[] image;

    @Column(name = "actualChildId")
    public long actualChildId;

    @Column(name="ChildSessionUse")
    public long childSessionUsage;

    @Column(name="ChildUsageStartTiming")
    public long childUsageStartTiming;

    @Column(name="ChildUsageEndTiming")
    public long childUsageEndTiming;

    @Column(name="ChildUsageStartHour")
    public int childUsageStartHour;

    @Column(name="ChildUsageEndHour")
    public int childUsageEndHour;

    @Column(name="ChildDayNightStatusActive")
    public boolean isChildDayNightSettingActive;

    @Column(name="ChildSessionExceeded")
    public boolean isChildSessionTimeExceeded;

    @Column(name="ChildSessionExceedTimeStamp")
    public long childSessionExceedTimeStamp;

    @Column(name="NextChildSessionRefreshTimeStamp")
    public long nextChildSessionRefreshTimeStamp;

    @Column(name="CurrentChildSessionUsage")
    public long currentChildSessionUsage;

    public ChildProfile() {
        super();
    }

    public ChildProfile(String childName) {
        super();

        this.childName = childName;
        //this.personAge = personAge;
    }

}

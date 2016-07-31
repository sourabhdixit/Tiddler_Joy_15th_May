package com.whizkidzmedia.tiddlerjoy.Networking;

/**
 * Created by Sourabh Dixit on 20-05-2016.
 */
public class SubmitUserQueryAsyncTask {

    String queryText, contactTimeString, contactModeString, emailString, mobileNoString;
    public static final String USER_QUERY = "first_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_MOBILE = "mobile";
    public static final String USER_MODE_OF_CONTACT = "preferred_mode";
    public static final String USER_TIME_OF_CONTACT = "preferred_time";
    private enum PREFERRED_TIME {ANYTIME, SECONDSLOT, THIRDSLOT, FOURTHSLOT;}     //ANYTIME=1,SECONDSLOT=2 and so on
    public enum PREFERRED_CONTACT {PHONE,EMAIL;}

    public SubmitUserQueryAsyncTask(String query,String contactMode,String contactTime,String email,String mobileNo )
    {

    }

}

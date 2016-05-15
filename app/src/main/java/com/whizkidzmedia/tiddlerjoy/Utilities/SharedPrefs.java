package com.whizkidzmedia.tiddlerjoy.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sourabh Dixit on 17-03-2016.
 */
public class SharedPrefs {

    private Context context;
    private SharedPreferences prefs;
    public static final String SHARED_PREFS_NAME = "TiddlerJoy";
    public static final String USER_NAME="user_name";
    public static final String USER_NUMBER="user_mobile";
    public static final String USER_EMAIL="user_email";
    public static final String USER_ID="user_id";
    public static final String USER_TOKEN="user_token";
    public static final String LOGGED_IN_STATUS="logged_in_status";

    public void createSharedPreferences(Context ctx)
    {
        prefs= ctx.getSharedPreferences(SHARED_PREFS_NAME,0);

    }

    public SharedPreferences getSharedPrefs(Context ctx)
    {
        prefs= ctx.getSharedPreferences(SHARED_PREFS_NAME,0);
        return prefs;

    }

    public static String getLoggedInStatus() {
       return LOGGED_IN_STATUS;
    }

    public static String getSharedPrefsName() {
        return SHARED_PREFS_NAME;
    }

    public static String getUserId() {
        return USER_ID;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static String getUserNumber() {
        return USER_NUMBER;
    }

    public static String getUserToken() {
        return USER_TOKEN;
    }


}

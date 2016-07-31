package com.whizkidzmedia.tiddlerjoy.Utilities;

/**
 * Created by Sourabh Dixit on 15-03-2016.
 */
public class API {

    private static final String HOST_SERVER="http://54.254.252.2/api/";//"http://52.33.186.21/api/";
    private static final String APP_VERSION = "v2/";
    private static final String USER_API= "user/";
    private static final String CHILD_API = "child/";
    private static final String CONTENT_API = "content/";
    private static final String SERVER_URL_USER=HOST_SERVER+APP_VERSION+ USER_API;
    private static final String SERVER_URL_CHILD = HOST_SERVER+APP_VERSION+CHILD_API;
    private static final String SERVER_URL_CONTENT = HOST_SERVER+APP_VERSION+CONTENT_API;
    public static final String USER_REGISTER_API=SERVER_URL_USER+"register/";
    public static final String USER_RETRIEVE_API = SERVER_URL_USER+"retrieve/";
    public static final String CHILD_PROFILE_UPDATE_API = SERVER_URL_CHILD+"update/";
    public static final String CHILD_PROFILE_IMAGE_UPLOAD = SERVER_URL_CHILD+"profile/image/";
    public static final String CHILD_PROFILE_REGISTER= SERVER_URL_CHILD+"register/";
    public static final String CONTENT_CHILD_PROFILES_LISTINGS = SERVER_URL_CONTENT+"listing/";
    public static final String CONTENT_CHILD_VIDEO_HISTORY = SERVER_URL_CHILD+"history/";
    public static final String CONTENT_PARENT_ANALYTICS = SERVER_URL_CHILD+"fetchdata/";
    public static final String CONTENT_PARENT_VIDEO_HISTORY = SERVER_URL_CHILD+"fetchdata/";
    public static final String UPDATE_CHILD_PROFILE = SERVER_URL_CHILD+"update/";
    public static final String ELDA_LISTING_URL ="http://54.254.252.2/api/v2/get_elda_list/";
}

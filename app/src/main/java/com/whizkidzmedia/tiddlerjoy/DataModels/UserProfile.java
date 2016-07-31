package com.whizkidzmedia.tiddlerjoy.DataModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Sourabh Dixit on 16-03-2016.
 */
@Table(name="UserProfile")
public class UserProfile extends Model {

    public static final String FIRST_NAME="first_name";
    public static final String EMAIL="email";
    public static final String MOBILE_NUMBER="mobile_number";
    public static final String ID="id";
    public static final String TOKEN="token";
    public static final String IS_CHILD_ADDED="is_child_added";
    public static final String CHILD_COUNT="child_count";
    public static final String OTP_CODE = "ref_code";


    @Column(name="UserName")
    public String username;

    @Column(name="Email")
    public String email;

    @Column(name="MobileNumber")
    public String mobileNumber;

    @Column(name="UserId")
    public String userId;
    @Column(name="Otp")
    public String otp;
}

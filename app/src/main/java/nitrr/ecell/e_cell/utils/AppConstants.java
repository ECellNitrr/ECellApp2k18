package nitrr.ecell.e_cell.utils;

import nitrr.ecell.e_cell.R;

public class AppConstants {

    //URL's
    public static final String SIGN_UP_URL = "appregister/";
    public static final String ABOUT_US_URL = "team/list/";
    public static final String SPEAKER_URL = "speakers/list/";
    public static final String FB_SIGN_UP_URL = "oauth/login/facebook/";
    public static final String SEND_OTP_URL = "send_otp/";
    public static final String VERIFY_OTP_URL ="verify_otp/ ";
    public static final String MESSAGE_URL = "message/";
    public static final String SIGN_IN_URL = "applogin/";
    public static final String EVENTS_URL = "events/list/";
    public static final String SPONSOR_URL ="sponsors/list/";
    public static final String SPLASHSCREEN_URL ="is_update_available/";
    public static final String RETRY_OTP_URL = "retry_otp/";

    //Bquiz Urls
    public static final String BQUIZ_STATUS = "bquiz/is_active";
    public static final String BQUIZ_LEADERBOARD = "v2/5b66b49a3200007400ee11cb";
//    public static final String BQUIZ_QUESTION = "v2/5b682c6d3300005b0432db31";
    public static final String BQUIZ_QUESTION = "bquiz/get_question/";
    public static final String BQUIZ_SUBMIT_ANSWER = "bquiz/submit_answer/";

    public static final String FACEBOOK = "https://www.facebook.com/ecellnitrr";
    public static final String YOUTUBE = "https://www.youtube.com/channel/UCrlm4gpLnIaA3pKSXbB99Yw";
    public static final String TWITTER = "https://www.twitter.com/ecell_nitrr";
    public static final String LINKEDIN = "https://www.linkedin.com/company/entrepreneurship-cell-nit-raipur";
    public static final String INSTAGRAM = "https://www.instagram.com/ecell.nitraipur";

    //Authentication req.
    public static final String AUTH_ID = "auth_id";
    public static final String ACCESS_TOKEN = "access_token";

    //Field_Name
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CON_PASSWORD = "confirm_password";
    public static final String MOBILE_NO = "mobile_number";



    //Facebook_Sign_Up
    public static final String FIELDS = "fields";
    public static final String INPUT_FIELDS = "id, first_name, last_name, email";
    public static final String PUBLIC_PROFILE = "public_profile";
    public static final String AVATAR_URL = "avatar_url";
    public static final String ID = "id";


    //Home Contents
    public static final String[] HOME_TITLES = {
            "E-SUMMIT",
            "EVENTS",
            "B-QUIZ",
            "SPONSORS",
            "ABOUT US"
    };


    //Gradient locations
    public static final int[] GRADIENT_LOCATIONS = {
            R.drawable.gradient_red,
            R.drawable.gradient_purple,
            R.drawable.gradient_blue,
            R.drawable.gradient_yellow,
            R.drawable.gradient_green
    };


    //Home Center Image Locations
    public static final int[] IMAGE_LOCATIONS = {
            R.drawable.pawn_red,
            R.drawable.pawn_purple,
            R.drawable.pawn_blue,
            R.drawable.pawn_yellow,
            R.drawable.pawn_green
    };


    //BottomSheetNames
    public static final String ABOUT_US_SHEET = "about_us";
    public static final String ESUMMIT_SHEET = "es";
    public static final String BQUIZ_SHEET = "bq";
    public static final String SPONSORS_SHEET = "spons";
    public static final String EVENTS_SHEET = "ev";
    public static final String ESUMMIT_REGISTRATION = "";
}

package nitrr.ecell.e_cell.restapi;

import nitrr.ecell.e_cell.model.SplashScreenResponse;
import nitrr.ecell.e_cell.model.bquiz.Answer;
import nitrr.ecell.e_cell.model.bquiz.BQuizQuestionResponse;
import nitrr.ecell.e_cell.model.bquiz.BQuizStatusResponse;
import nitrr.ecell.e_cell.model.bquiz.BQuizLeaderboardResponse;
import nitrr.ecell.e_cell.model.otp.SendOtpResponse;
import nitrr.ecell.e_cell.model.events.EventsResponse;
import nitrr.ecell.e_cell.model.aboutus.AboutUsResponse;
import nitrr.ecell.e_cell.model.auth.AuthenticationResponse;
import nitrr.ecell.e_cell.model.auth.FacebookSignInUserDetails;
import nitrr.ecell.e_cell.model.auth.GenericResponse;
import nitrr.ecell.e_cell.model.aboutus.MessageDetails;
import nitrr.ecell.e_cell.model.esummit.SpeakerResponse;
import nitrr.ecell.e_cell.model.aboutus.UserDetails;
import nitrr.ecell.e_cell.model.auth.LoginDetails;
import nitrr.ecell.e_cell.model.otp.VerifyOtp;
import nitrr.ecell.e_cell.model.otp.otpSendNumber;
import nitrr.ecell.e_cell.model.otp.sendOtp;
import nitrr.ecell.e_cell.model.sponsors.SponsorsResponse;
import nitrr.ecell.e_cell.utils.AppConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @POST(AppConstants.SIGN_UP_URL)
    Call<AuthenticationResponse> sendRegisterDetails(@Body UserDetails userDetails);

    @POST(AppConstants.SEND_OTP_URL)
    Call<SendOtpResponse> sendMobileNo(@Body otpSendNumber otpSendNumber);

    @POST(AppConstants.VERIFY_OTP_URL)
    Call<VerifyOtp> sendOtpEntered(@Body sendOtp sendOtp);

    @POST(AppConstants.FB_SIGN_UP_URL)
    Call<AuthenticationResponse> sendFacebookRegistrationDetails(@Body FacebookSignInUserDetails details);

    @GET(AppConstants.ABOUT_US_URL)
    Call<AboutUsResponse> getAboutUsDetails();

    @GET(AppConstants.SPEAKER_URL)
    Call<SpeakerResponse> getSpeakerDetails();

    @POST(AppConstants.MESSAGE_URL)
    Call<GenericResponse> sendMessage(@Body MessageDetails details);

    @GET(AppConstants.BQUIZ_STATUS)
    Call<BQuizStatusResponse> getBquizStatus();

    @GET(AppConstants.BQUIZ_LEADERBOARD)
    Call<BQuizLeaderboardResponse> getBquizLeaderboard();

    @GET(AppConstants.EVENTS_URL)
    Call<EventsResponse> getEventsResponse();

    @POST(AppConstants.SIGN_IN_URL)
    Call<AuthenticationResponse>sendLoginDetails(@Body LoginDetails loginDetails);

    @GET(AppConstants.SPONSOR_URL)
    Call<SponsorsResponse> getSponsorsResponce();

    @GET(AppConstants.BQUIZ_QUESTION)
    Call<BQuizQuestionResponse> getQuestion(@Query("retryQuestion") Boolean retryQuestion);

    @GET(AppConstants.SPLASHSCREEN_URL)
    Call<SplashScreenResponse> getAppUpdate();

    @POST(AppConstants.BQUIZ_SUBMIT_ANSWER)
    Call<GenericResponse> submitAnswer(@Body Answer answer);
}

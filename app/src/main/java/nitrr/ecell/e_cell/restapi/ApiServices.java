package nitrr.ecell.e_cell.restapi;

import nitrr.ecell.e_cell.bquiz.model.Answer;
import nitrr.ecell.e_cell.bquiz.model.BQuizQuestionResponse;
import nitrr.ecell.e_cell.bquiz.model.BQuizStatusResponse;
import nitrr.ecell.e_cell.bquiz.model.BQuizLeaderboardResponse;
import nitrr.ecell.e_cell.otp.Model.AuthenticationVerifyOtpResponse;
import nitrr.ecell.e_cell.otp.Model.SendOtpResponse;
import nitrr.ecell.e_cell.events.Model.EventsResponse;
import nitrr.ecell.e_cell.model.AboutUsResponse;
import nitrr.ecell.e_cell.model.AuthenticationResponse;
import nitrr.ecell.e_cell.model.FacebookSignInUserDetails;
import nitrr.ecell.e_cell.model.GenericResponse;
import nitrr.ecell.e_cell.model.MessageDetails;
import nitrr.ecell.e_cell.model.SpeakerResponse;
import nitrr.ecell.e_cell.model.UserDetails;
import nitrr.ecell.e_cell.model.LoginDetails;
import nitrr.ecell.e_cell.sponsor.model.SponsorsResponse;
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
    Call<SendOtpResponse> sendMobileNo(@Body String mobileNoOtp);

    @POST(AppConstants.VERIFY_OTP_URL)
    Call<AuthenticationVerifyOtpResponse> sendOtpEntered(@Body String otpEntered);

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

    @POST(AppConstants.BQUIZ_SUBMIT_ANSWER)
    Call<GenericResponse> submitAnswer(@Body Answer answer);
}

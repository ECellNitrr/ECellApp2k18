package nitrr.ecell.e_cell.restapi;

import nitrr.ecell.e_cell.events.Model.EventsResponse;
import nitrr.ecell.e_cell.model.AboutUsResponse;
import nitrr.ecell.e_cell.model.AuthenticationResponse;
import nitrr.ecell.e_cell.model.FacebookSignInUserDetails;
import nitrr.ecell.e_cell.model.GenericResponse;
import nitrr.ecell.e_cell.model.MessageDetails;
import nitrr.ecell.e_cell.model.SpeakerResponse;
import nitrr.ecell.e_cell.model.UserDetails;
import nitrr.ecell.e_cell.signin.model.AuthenticationLoginResponse;
import nitrr.ecell.e_cell.signin.model.Logindetails;
import nitrr.ecell.e_cell.sponsor.model.SponsorsResponce;
import nitrr.ecell.e_cell.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {

    @POST(AppConstants.SIGN_UP_URL)
    Call<AuthenticationResponse> sendRegisterDetails(@Body UserDetails userDetails);

    @POST(AppConstants.FB_SIGN_UP_URL)
    Call<AuthenticationResponse> sendFacebookRegistrationDetails(@Body FacebookSignInUserDetails details);

    @GET(AppConstants.ABOUT_US_URL)
    Call<AboutUsResponse> getAboutUsDetails();

    @GET(AppConstants.SPEAKER_URL)
    Call<SpeakerResponse> getSpeakerDetails();

    @POST(AppConstants.MESSAGE_URL)
    Call<GenericResponse> sendMessage(@Body MessageDetails details);

    @GET(AppConstants.EVENTS_URL)
    Call<EventsResponse> getEventsResponse();

    @POST(AppConstants.SIGN_IN_URL)
    Call<AuthenticationLoginResponse>sendLoginDetails(@Body Logindetails logindetails);

    @GET(AppConstants.SPONSOR_URL)
    Call<SponsorsResponce> getSponsorsResponce();
}

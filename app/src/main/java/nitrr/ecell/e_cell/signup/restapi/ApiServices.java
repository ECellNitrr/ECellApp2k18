package nitrr.ecell.e_cell.signup.restapi;

import nitrr.ecell.e_cell.signup.model.AuthenticationResponse;
import nitrr.ecell.e_cell.signup.model.UserDetails;
import nitrr.ecell.e_cell.utils.AppConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {

    @POST(AppConstants.SIGN_UP_URL)
    Call<AuthenticationResponse> sendRegisterDetails(@Body UserDetails userDetails);

}

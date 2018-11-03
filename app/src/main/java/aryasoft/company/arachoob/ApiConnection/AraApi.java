package aryasoft.company.arachoob.ApiConnection;

import aryasoft.company.arachoob.Models.UserRegistration;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AraApi {

    @POST("api/AccountApi/Register")
    Call<Integer> registerUser(@Body UserRegistration userRegistration);
}

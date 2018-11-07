package aryasoft.company.arachoob.ApiConnection;

import aryasoft.company.arachoob.Models.ActivationAccount;
import aryasoft.company.arachoob.Models.RecoveryPasswordModel;
import aryasoft.company.arachoob.Models.UserInfoModel;
import aryasoft.company.arachoob.Models.UserRegistration;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AraApi {

    @Headers("User-Agent: <AraChoob>")
    @POST("api/AccountApi/Register")
    Call<Integer> registerUser(@Body UserRegistration userRegistration);/*done*/

    @Headers("User-Agent: <AraChoob>")
    @POST("api/AccountApi/ActiveUser")
    Call<Boolean> activeUserAccount(@Body ActivationAccount activationAccount);/*done*/

    @Headers("User-Agent: <AraChoob>")
    @GET("api/AccountApi/Login")
    Call<Integer> loginUser(@Query("mobileNumber") String mobileNumber, @Query("password") String password );/*done*/

    @Headers("User-Agent: <AraChoob>")
    @POST("api/AccountApi/RecoverPassword")
    Call<Integer> recoverPassword(@Body RecoveryPasswordModel recoveryPasswordModel);/*done*/

    @Headers("User-Agent: <AraChoob>")
    @GET("api/AccountApi/RenewActiveCode")
    Call<Integer> reSendActivationCode(@Query("mobileNumber") String mobileNumber);/*done*/

    @Headers("User-Agent: <AraChoob>")
    @GET("api/AccountApi/GetUserInfo")
    Call<UserInfoModel> getUserInfo(@Query("id") int userId);/*done*/

    @Headers("User-Agent: <AraChoob>")
    @POST("api/AccountApi/AddUserInfo")
    Call<Boolean> editUserProfile(@Query("userId") int userId, @Body UserInfoModel userInfoModel);/*done*/

    @Headers("User-Agent: <AraChoob>")
    @POST("api/AccountApi/Newpassword")
    Call<Boolean> changeUserPassword(@Query("mobileNumber") String mobileNumber, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);/*done*/

}
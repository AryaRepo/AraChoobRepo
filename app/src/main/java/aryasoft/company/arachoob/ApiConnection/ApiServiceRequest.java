package aryasoft.company.arachoob.ApiConnection;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.CollectionDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderDetail;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductCommentApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDetailInfoApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductGroupsApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.SliderApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.SubmitOrderApiModel;
import aryasoft.company.arachoob.Models.ActivationAccount;
import aryasoft.company.arachoob.Models.Message;
import aryasoft.company.arachoob.Models.RecoveryPasswordModel;
import aryasoft.company.arachoob.Models.Ticket;
import aryasoft.company.arachoob.Models.TicketChatsModel;
import aryasoft.company.arachoob.Models.TicketsModel;
import aryasoft.company.arachoob.Models.UserInfoModel;
import aryasoft.company.arachoob.Models.UserRegistration;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServiceRequest
{

    @Headers("User-Agent: <AraChoob>")
    @POST("api/AccountApi/Register")
    Call<Integer> registerUser(@Body UserRegistration userRegistration);/*done*/

    @Headers("User-Agent: <AraChoob>")
    @POST("api/AccountApi/ActiveUser")
    Call<Boolean> activeUserAccount(@Body ActivationAccount activationAccount);/*done*/

    @Headers("User-Agent: <AraChoob>")
    @GET("api/AccountApi/Login")
    Call<Integer> loginUser(@Query("mobileNumber") String mobileNumber, @Query("password") String password);/*done*/

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

    @Headers("User-Agent: <AraChoob>")
    @POST("api/MessageApi/CreateNewTicket")
    Call<Boolean> newTicket(@Body Ticket ticket);

    @Headers("User-Agent: <AraChoob>")
    @GET("api/MessageApi/GetTickets")
    Call<ArrayList<TicketsModel>> getAllTickets(@Query("userId") int userId, @Query("offsetNumber") int offsetNumber, @Query("takeNumber") int takeNumber);

    @Headers("User-Agent: <AraChoob>")
    @POST("api/MessageApi/CloseTicket")
    Call<Boolean> closeTicket(@Query("messageId") int messageId);

    @Headers("User-Agent: <AraChoob>")
    @GET("api/MessageApi/GetTicketChats")
    Call<ArrayList<TicketChatsModel>> showChats(@Query("messageId") int messageId, @Query("offsetNumber") int offsetNumber, @Query("takeNumber") int takeNumber);

    @Headers("User-Agent: <AraChoob>")
    @POST("api/MessageApi/SendChatToTicket")
    Call<Boolean> sendMessage(@Body Message message);


    //----------------------------------------------------------------------------------------------

    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("api/GeneralApi/getSliders/")
    Call<ArrayList<SliderApiModel>> GetSliders();

    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("api/ProductApi/Search/")
    Call<ArrayList<ProductDataModel>> Search(@Query("searchText") String searchText,@Query("skipItem") int skipItem,@Query("takeItem") int takeItem);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("api/ProductApi/GetProductInfoById/")
    Call<ProductDetailInfoApiModel> GetProductInfoById(@Query("productId") int productId);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("/api/ProductApi/GetProductGroups/")
    Call<ArrayList<ProductGroupsApiModel>> GetProductGroups(@Query("groupId") int groupId);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("/api/ProductApi/GetProductByGroupId/")
    Call<ArrayList<ProductDataModel>> GetProductByGroupId(@Query("groupId") int groupId, @Query("takeNumber") int takeNumber, @Query("offsetNumber") int offsetNumber);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("/api/OrderApi/GetUserOrder/")
    Call<ArrayList<GetUserOrderApiModel>> GetUserOrder(@Query("userId") int userId, @Query("skipItem") int skipItem, @Query("takeItem") int takeItem);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("/api/OrderApi/GetUserOrderDetail/")
    Call<ArrayList<GetUserOrderDetail>> GetUserOrderDetail(@Query("orderId") int orderId);

    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @POST("/api/OrderApi/SubmitOrder/")
    Call<Boolean> SubmitOrder(@Body SubmitOrderApiModel submitOrder);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @POST("/api/OrderApi/GetOrderBasketInfo/")
    Call<ArrayList<ProductDataModel>> GetOrderBasketInfo(@Body ArrayList<Integer> products);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @POST("api/ProductApi/SubmitComment/")
    Call<Boolean> SubmitComment(@Query("userId") int userId, @Query("commentTitle") String commentTitle, @Query("commentText") String commentText, @Query("pointValue") int pointValue, @Query("productId") int productId);



    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("api/ProductApi/GetProductComments/")
    Call<ArrayList<ProductCommentApiModel>> GetProductComments(@Query("productId") int productId,@Query("offsetNumber") int offsetNumber,@Query("takeNumber") int takeNumber);

    //----------------------------------------------------------------------

    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("api/ProductApi/GetTotalCollections/")
    Call<ArrayList<CollectionDataModel>> GetCollections();


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("api/ProductApi/GetTopSeals/")
    Call<ArrayList<ProductDataModel>> GetTopSeals(@Query("skipItem") int skipItem, @Query("takeItem") int takeItem);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("api/ProductApi/GetCollectionById/")
    Call<ArrayList<ProductDataModel>> GetCollectionById(@Query("collectionId") int collectionId, @Query("skipItem") int skipItem, @Query("takeItem") int takeItem);


    @Headers({"User-Agent: <AraChoob>", "Connection:close"})
    @GET("api/ProductApi/GetDiscountBasketById/")
    Call<ArrayList<ProductDataModel>> GetDiscountBasketById(@Query("collectionId") int collectionId, @Query("skipItem") int skipItem, @Query("takeItem") int takeItem);
}
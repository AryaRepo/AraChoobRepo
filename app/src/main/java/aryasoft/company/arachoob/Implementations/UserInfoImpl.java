package aryasoft.company.arachoob.Implementations;

import aryasoft.company.arachoob.Models.UserInfoModel;
import aryasoft.company.arachoob.Utils.UserPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoImpl implements Callback<UserInfoModel> {

    private OnUserInfoReceivedListener UserInfoReceivedListener;

    public UserInfoImpl(OnUserInfoReceivedListener userInfoReceivedListener) {
        UserInfoReceivedListener = userInfoReceivedListener;
    }

    @Override
    public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {
        UserInfoReceivedListener.onUserInfoReceived(response);
    }

    @Override
    public void onFailure(Call<UserInfoModel> call, Throwable t) {

    }

    public interface OnUserInfoReceivedListener
    {
        void onUserInfoReceived(Response<UserInfoModel> response);
    }

}

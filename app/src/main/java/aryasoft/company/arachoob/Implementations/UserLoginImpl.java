package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginImpl implements Callback<Integer> {

    private OnLoginListener LoginListener;

    public UserLoginImpl(OnLoginListener loginListener) {
        LoginListener = loginListener;
    }

    @Override
    public void onResponse(Call<Integer> call, Response<Integer> response) {
        LoginListener.onLoginStatusReceived(response);
    }

    @Override
    public void onFailure(Call<Integer> call, Throwable t) {

    }

    public interface OnLoginListener
    {
        void onLoginStatusReceived(Response<Integer> response);
    }

}

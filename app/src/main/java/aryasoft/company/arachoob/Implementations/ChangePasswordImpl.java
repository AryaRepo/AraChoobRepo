package aryasoft.company.arachoob.Implementations;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordImpl implements Callback<Boolean> {

    private OnPasswordChangedListener PasswordChangedListener;

    public ChangePasswordImpl(OnPasswordChangedListener passwordChangedListener) {
        PasswordChangedListener = passwordChangedListener;
    }

    @Override
    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
        PasswordChangedListener.onPasswordChanged(response);
    }

    @Override
    public void onFailure(Call<Boolean> call, Throwable t) {

    }

    public interface OnPasswordChangedListener
    {
        void onPasswordChanged(Response<Boolean> response);
    }

}

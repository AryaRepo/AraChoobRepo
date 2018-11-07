package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationImpl implements Callback<Integer> {

    private OnRegistrationStatusListener RegistrationListener;

    public UserRegistrationImpl(OnRegistrationStatusListener registrationListener) {
        RegistrationListener = registrationListener;
    }

    @Override
    public void onResponse(Call<Integer> call, Response<Integer> response) {
        RegistrationListener.onRegistrationStatusReceived(response);
    }

    @Override
    public void onFailure(Call<Integer> call, Throwable t) {

    }

    public interface OnRegistrationStatusListener
    {
        void onRegistrationStatusReceived(Response<Integer> response);
    }

}

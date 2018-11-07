package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivationImpl implements Callback<Boolean> {

    private OnUserActivationResultReceived OnResultReceived;

    public UserActivationImpl(OnUserActivationResultReceived onResultReceived) {
        OnResultReceived = onResultReceived;
    }

    @Override
    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
        OnResultReceived.onActivationResultReceived(response);
    }

    @Override
    public void onFailure(Call<Boolean> call, Throwable t) {

    }

    public interface OnUserActivationResultReceived
    {
        void onActivationResultReceived(Response<Boolean> response);
    }
}

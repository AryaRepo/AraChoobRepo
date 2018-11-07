package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoveryPasswordImpl implements Callback<Integer> {

    private OnRecoveryPasswordListener RecoveryListener;

    public RecoveryPasswordImpl(OnRecoveryPasswordListener recoveryListener) {
        RecoveryListener = recoveryListener;
    }

    @Override
    public void onResponse(Call<Integer> call, Response<Integer> response) {
        RecoveryListener.onRecoveryPassword(response);
    }

    @Override
    public void onFailure(Call<Integer> call, Throwable t) {

    }

    public interface OnRecoveryPasswordListener
    {
        void onRecoveryPassword(Response<Integer> response);
    }

}

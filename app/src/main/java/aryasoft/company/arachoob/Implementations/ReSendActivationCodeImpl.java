package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReSendActivationCodeImpl implements Callback<Integer> {

    private OnReSendListener ReSendListener;

    public ReSendActivationCodeImpl(OnReSendListener reSendListener) {
        ReSendListener = reSendListener;
    }

    @Override
    public void onResponse(Call<Integer> call, Response<Integer> response) {
        ReSendListener.onReSentCode(response);
    }

    @Override
    public void onFailure(Call<Integer> call, Throwable t) {

    }

    public interface OnReSendListener {
        void onReSentCode(Response<Integer> response);
    }

}

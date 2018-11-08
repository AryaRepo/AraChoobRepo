package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageImpl implements Callback<Boolean> {

    private OnMessageSentListener MessageSentListener;

    public SendMessageImpl(OnMessageSentListener messageSentListener) {
        MessageSentListener = messageSentListener;
    }

    @Override
    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
        MessageSentListener.onMessageSent(response);
    }

    @Override
    public void onFailure(Call<Boolean> call, Throwable t) {

    }

    public interface OnMessageSentListener
    {
        void onMessageSent(Response<Boolean> response);
    }

}

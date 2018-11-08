package aryasoft.company.arachoob.Implementations;

import java.util.ArrayList;

import aryasoft.company.arachoob.Models.TicketChatsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowChatsImpl implements Callback<ArrayList<TicketChatsModel>> {

    private OnChatsReceivedListener ChatsReceivedListener;

    public ShowChatsImpl(OnChatsReceivedListener chatsReceivedListener) {
        ChatsReceivedListener = chatsReceivedListener;
    }

    @Override
    public void onResponse(Call<ArrayList<TicketChatsModel>> call, Response<ArrayList<TicketChatsModel>> response) {
        ChatsReceivedListener.onChatsReceived(response);
    }

    @Override
    public void onFailure(Call<ArrayList<TicketChatsModel>> call, Throwable t) {

    }

    public interface OnChatsReceivedListener
    {
        void onChatsReceived(Response<ArrayList<TicketChatsModel>> response);
    }

}

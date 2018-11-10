package aryasoft.company.arachoob.Implementations;

import java.util.ArrayList;

import aryasoft.company.arachoob.Models.TicketChatsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadMoreChatsImpl implements Callback<ArrayList<TicketChatsModel>> {

    private OnMoreChatsReceivedListener ChatsReceivedListener;

    public LoadMoreChatsImpl(OnMoreChatsReceivedListener moreChatsReceivedListener) {
        ChatsReceivedListener = moreChatsReceivedListener;
    }

    @Override
    public void onResponse(Call<ArrayList<TicketChatsModel>> call, Response<ArrayList<TicketChatsModel>> response) {
        ChatsReceivedListener.onMoreChatsReceived(response);
    }

    @Override
    public void onFailure(Call<ArrayList<TicketChatsModel>> call, Throwable t) {

    }

    public interface OnMoreChatsReceivedListener
    {
        void onMoreChatsReceived(Response<ArrayList<TicketChatsModel>> response);
    }
}

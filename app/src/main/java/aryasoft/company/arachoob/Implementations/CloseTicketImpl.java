package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseTicketImpl implements Callback<Boolean> {

    private OnTicketClosedListener TicketClosedListener;

    public CloseTicketImpl(OnTicketClosedListener ticketClosedListener) {
        TicketClosedListener = ticketClosedListener;
    }

    @Override
    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
        TicketClosedListener.onTicketClosed(response);
    }

    @Override
    public void onFailure(Call<Boolean> call, Throwable t) {

    }

    public interface OnTicketClosedListener
    {
        void onTicketClosed(Response<Boolean> response);
    }

}

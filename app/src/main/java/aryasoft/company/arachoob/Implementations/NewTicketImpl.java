package aryasoft.company.arachoob.Implementations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTicketImpl implements Callback<Boolean> {

    private OnTicketCreatedListener TicketListener;

    public NewTicketImpl(OnTicketCreatedListener ticketListener) {
        TicketListener = ticketListener;
    }

    @Override
    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
        TicketListener.onTicketCreated(response);
    }

    @Override
    public void onFailure(Call<Boolean> call, Throwable t) {

    }

    public interface OnTicketCreatedListener
    {
        void onTicketCreated(Response<Boolean> response);
    }

}

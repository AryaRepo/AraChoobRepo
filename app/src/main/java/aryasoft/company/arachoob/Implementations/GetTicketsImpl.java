package aryasoft.company.arachoob.Implementations;
import java.util.ArrayList;
import aryasoft.company.arachoob.Models.TicketsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTicketsImpl implements Callback<ArrayList<TicketsModel>> {

    private OnAllTicketsReceivedListener AllTicketsReceived;

    public GetTicketsImpl(OnAllTicketsReceivedListener allTicketsReceived) {
        AllTicketsReceived = allTicketsReceived;
    }

    @Override
    public void onResponse(Call<ArrayList<TicketsModel>> call, Response<ArrayList<TicketsModel>> response) {
        AllTicketsReceived.onAllTicketReceived(response);
    }

    @Override
    public void onFailure(Call<ArrayList<TicketsModel>> call, Throwable t) {

    }

    public interface OnAllTicketsReceivedListener
    {
        void onAllTicketReceived(Response<ArrayList<TicketsModel>> response);
    }

}

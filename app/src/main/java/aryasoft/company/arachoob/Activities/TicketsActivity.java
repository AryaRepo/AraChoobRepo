package aryasoft.company.arachoob.Activities;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.TicketsAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.GetTicketsImpl;
import aryasoft.company.arachoob.Implementations.NewTicketImpl;
import aryasoft.company.arachoob.Models.Ticket;
import aryasoft.company.arachoob.Models.TicketsModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TicketsActivity extends AppCompatActivity implements NewTicketImpl.OnTicketCreatedListener,
        Networking.NetworkStatusListener, GetTicketsImpl.OnAllTicketsReceivedListener, TicketsAdapter.OnUserClosedTicketListener
{
    private RecyclerView recyclerTickets;
    private TicketsAdapter recyclerTicketsAdapter;
    private LinearLayoutManager recyclerTicketsLayoutManager;
    private FloatingActionButton fabAddNewTicket;
    private LinearLayout emptyMessagesBox;
    private Dialog MessagingDialogHolder;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;
    private EditText edtTicketTitle;
    private EditText edtTicketText;
    private int SkipNumber = 0;
    private int TakeNumber = 20;
    private boolean IsLoading = false;
    private boolean DataEnded = false;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        initViews();
        initEvents();
        Networking.checkNetwork(this, this, 10);
    }

    @Override
    public void onTicketCreated(Response<Boolean> response)
    {
        Loading.hide();
        if (response.body() != null)
        {
            if (response.body())
            {
                new CuteToast.Builder(this).setText(getString(R.string.ticketCreated)).setDuration(Toast.LENGTH_LONG).show();
                getAllTickets();
            }
            else if (!response.body())
            {
                MessageDialog.setContentText(getString(R.string.someProblemHappend)).show();
            }
        }
    }

    @Override
    public void onAllTicketReceived(Response<ArrayList<TicketsModel>> response)
    {
        Loading.hide();
        if (response.body() != null)
        {
            if (response.body().size() > 0)
            {
                updateTicketsList(response.body());
            }
            else
            {
                recyclerTickets.setVisibility(View.GONE);
                emptyMessagesBox.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNetworkConnected(int requestCode)
    {

        if (requestCode == 9)
        {
            createNewTicket();
        }
        else if (requestCode == 10)
        {
            getAllTickets();
        }
    }

    @Override
    public void onNetworkDisconnected()
    {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    @Override
    public void onUserClosedTicket()
    {
        getAllTickets();
    }

    private void initViews()
    {
        recyclerTickets = findViewById(R.id.recyclerTickets);
        fabAddNewTicket = findViewById(R.id.fabAddNewTicket);
        emptyMessagesBox = findViewById(R.id.emptyMessagesBox);
        setupRecyclerTickets();

        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(this);
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(this);

    }

    private void initEvents()
    {
        fabAddNewTicket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createNewTicketMessageDialog();
            }
        });
    }

    private void setupRecyclerTickets()
    {
        recyclerTicketsAdapter = new TicketsAdapter(this, this);
        recyclerTicketsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerTickets.setLayoutManager(recyclerTicketsLayoutManager);
        recyclerTickets.setAdapter(recyclerTicketsAdapter);

        recyclerTickets.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (recyclerTicketsAdapter.getItemCount() >= TakeNumber)
                {
                    if (!DataEnded)
                    {
                        int VisibleItemCount = recyclerTicketsLayoutManager.getChildCount();
                        int TotalItemCount = recyclerTicketsLayoutManager.getItemCount();
                        int PastVisibleItem = recyclerTicketsLayoutManager.findFirstVisibleItemPosition();
                        if (IsLoading)
                        {
                            return;
                        }
                        if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                        {
                            SkipNumber += TakeNumber;
                            IsLoading = true;
                            getAllTickets();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void createNewTicketMessageDialog()
    {
        android.support.v7.app.AlertDialog.Builder MessageDialogAlert = new android.support.v7.app.AlertDialog.Builder(TicketsActivity.this);
        View AlertView = View.inflate(this, R.layout.create_message_dialog_layout, null);
        edtTicketTitle = AlertView.findViewById(R.id.edtTicketTitle);
        edtTicketText = AlertView.findViewById(R.id.edtTicketText);
        final Button btnSendTicket = AlertView.findViewById(R.id.btnSendTicket);
        btnSendTicket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!(edtTicketTitle.getText().toString().isEmpty() || edtTicketText.getText().toString().isEmpty()))
                {
                    MessagingDialogHolder.dismiss();
                    Networking.checkNetwork(TicketsActivity.this, TicketsActivity.this, 9);
                }
                else
                {
                    new CuteToast.Builder(TicketsActivity.this).setText("لطفا موضوع پیام یا متن پیام را وارد کنید.").setDuration(Toast.LENGTH_LONG).show();
                }
            }
        });
        MessageDialogAlert.setView(AlertView);
        MessagingDialogHolder = MessageDialogAlert.show();
    }

    private void createNewTicket()
    {
        Loading.show();
        AraApi araApi = ApiServiceGenerator.getApiService();
        Call<Boolean> newTicketCall = araApi.newTicket(getTicketData());
        newTicketCall.enqueue(new NewTicketImpl(this));
    }

    private Ticket getTicketData()
    {
        Ticket ticket = new Ticket();
        ticket.setUserId(UserPreference.getUserId());
        ticket.setMessageTitle(edtTicketTitle.getText().toString());
        ticket.setMessageText(edtTicketText.getText().toString());

        return ticket;
    }

    private void getAllTickets()
    {
        Loading.show();
        AraApi araApi = ApiServiceGenerator.getApiService();
        int userId = UserPreference.getUserId();
        Call<ArrayList<TicketsModel>> getAllTicketsCall = araApi.getAllTickets(userId, SkipNumber, TakeNumber);
        getAllTicketsCall.enqueue(new GetTicketsImpl(this));
    }

    private void updateTicketsList(ArrayList<TicketsModel> tickets)
    {
        recyclerTicketsAdapter.updateTicketsList(tickets);
    }

}

package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.TicketChatsAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.ApiServiceRequest;
import aryasoft.company.arachoob.Implementations.LoadMoreChatsImpl;
import aryasoft.company.arachoob.Implementations.SendMessageImpl;
import aryasoft.company.arachoob.Implementations.ShowChatsImpl;
import aryasoft.company.arachoob.Models.Message;
import aryasoft.company.arachoob.Models.TicketChatsModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.RecyclerInstaller;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TicketChatActivity extends AppCompatActivity implements ShowChatsImpl.OnChatsReceivedListener,
        SendMessageImpl.OnMessageSentListener, Networking.NetworkStatusListener, LoadMoreChatsImpl.OnMoreChatsReceivedListener
{
    private RecyclerView recyclerChatTicket;
    private EditText edtMessageText;
    private ImageButton btnSendMessage;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;
    private TicketChatsAdapter ChatsAdapter;
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
        setContentView(R.layout.activity_ticket_chat);
        initViews();
        setupRecyclerView();
        Networking.checkNetwork(this, this, 12);
    }

    @Override
    public void onChatsReceived(Response<ArrayList<TicketChatsModel>> response)
    {
        Loading.hide();
        if (response.body() != null)
        {
            ChatsAdapter.addToTicketChatList(response.body());
            moveToEndOfList();
        }

    }

    @Override
    public void onMoreChatsReceived(Response<ArrayList<TicketChatsModel>> response)
    {
        Loading.hide();
        if (response.body() != null)
        {
            if (response.body().size() > 0)
            {
                ChatsAdapter.addToTicketChatList(response.body());
                IsLoading = false;
            }
            else
            {
                DataEnded = true;
                IsLoading = false;
            }
        }
    }

    @Override
    public void onMessageSent(Response<Boolean> response)
    {
        Loading.hide();
        if (response.body() != null)
        {
            if (response.body())
            {
                new CuteToast.Builder(this).setText(getString(R.string.MessageSentText)).setDuration(Toast.LENGTH_LONG).show();
                edtMessageText.setText("");
                getChats(getIntent().getIntExtra("messageId", 0));
            }
            else
            {
                new CuteToast.Builder(this).setText(getString(R.string.MessageDidNotSendText)).setDuration(Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNetworkConnected(int requestCode)
    {
        if (requestCode == 12)
        {
            getChats(getIntent().getIntExtra("messageId", 0));
        }
        else if (requestCode == 13)
        {
            sendMessage(getMessageData());
        }
    }

    @Override
    public void onNetworkDisconnected()
    {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    private void initViews()
    {
        recyclerChatTicket = findViewById(R.id.recyclerChatTicket);
        edtMessageText = findViewById(R.id.edtMessageText);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Networking.checkNetwork(TicketChatActivity.this, TicketChatActivity.this, 13);
            }
        });

        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(this);
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(this);
    }

    private void setupRecyclerView()
    {
        ChatsAdapter = new TicketChatsAdapter(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerInstaller recyclerInstaller = RecyclerInstaller.build();
        recyclerInstaller
                .setAdapter(ChatsAdapter)
                .setLayoutManager(linearLayoutManager)
                .setRecyclerView(recyclerChatTicket)
                .setup();

        recyclerChatTicket.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (ChatsAdapter.getItemCount() >= TakeNumber)
                {
                    if (!DataEnded)
                    {
                        int VisibleItemCount = linearLayoutManager.getChildCount();
                        int TotalItemCount = linearLayoutManager.getItemCount();
                        int PastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                        if (IsLoading)
                        {
                            return;
                        }
                        if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                        {
                            SkipNumber += TakeNumber;
                            IsLoading = true;
                            getMoreChats(getIntent().getIntExtra("messageId", 0));
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void getChats(int messageId)
    {
        Loading.show();
        ApiServiceRequest apiServiceRequest = ApiServiceGenerator.getApiService();
        SkipNumber = recyclerChatTicket.getAdapter().getItemCount();
        Call<ArrayList<TicketChatsModel>> showChatsCall = apiServiceRequest.showChats(messageId, SkipNumber, TakeNumber);
        showChatsCall.enqueue(new ShowChatsImpl(this));
    }

    private void getMoreChats(int messageId)
    {
        ApiServiceRequest apiServiceRequest = ApiServiceGenerator.getApiService();
        Call<ArrayList<TicketChatsModel>> showChatsCall = apiServiceRequest.showChats(messageId, SkipNumber, TakeNumber);
        showChatsCall.enqueue(new ShowChatsImpl(this));
    }

    private void sendMessage(Message message)
    {
        Loading.show();
        ApiServiceRequest apiServiceRequest = ApiServiceGenerator.getApiService();
        Call<Boolean> sendMessageCall = apiServiceRequest.sendMessage(message);
        sendMessageCall.enqueue(new SendMessageImpl(this));
    }

    private Message getMessageData()
    {
        Message message = new Message();
        message.setMessageId(getIntent().getIntExtra("messageId", 0));
        message.setUserId(UserPreference.getUserId());
        message.setMessageText(edtMessageText.getText().toString());

        return message;
    }

    private void moveToEndOfList()
    {
        recyclerChatTicket.scrollToPosition(recyclerChatTicket.getAdapter().getItemCount());
    }

}

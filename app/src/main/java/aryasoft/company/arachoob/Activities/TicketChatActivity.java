package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import aryasoft.company.arachoob.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TicketChatActivity extends AppCompatActivity
{
    private RecyclerView recyclerChatTicket;
    private EditText edtMessageText;
    private ImageButton btnSendMessage;

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
        initEvents();
    }


    private void initViews()
    {
        recyclerChatTicket = findViewById(R.id.recyclerChatTicket);
        edtMessageText = findViewById(R.id.edtMessageText);
        btnSendMessage = findViewById(R.id.btnSendMessage);
    }

    private void initEvents()
    {

    }
}

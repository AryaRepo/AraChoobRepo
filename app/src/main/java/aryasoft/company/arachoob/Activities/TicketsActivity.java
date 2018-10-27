package aryasoft.company.arachoob.Activities;

import android.app.Dialog;
import android.content.Context;
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
import aryasoft.company.arachoob.Adapters.TicketsAdapter;
import aryasoft.company.arachoob.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TicketsActivity extends AppCompatActivity
{
    private RecyclerView recyclerTickets;
    private TicketsAdapter recyclerTicketsAdapter;
    private LinearLayoutManager recyclerTicketsLayoutManager;
    private FloatingActionButton fabAddNewTicket;
    private LinearLayout emptyMessagesBox;
    private Dialog MessagingDialogHolder;

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
    }

    private void initViews()
    {
        recyclerTickets=findViewById(R.id.recyclerTickets);
        fabAddNewTicket=findViewById(R.id.fabAddNewTicket);
        emptyMessagesBox=findViewById(R.id.emptyMessagesBox);
        //---------
        recyclerTicketsAdapter=new TicketsAdapter(this);
        recyclerTicketsLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerTickets.setLayoutManager(recyclerTicketsLayoutManager);
        recyclerTickets.setAdapter(recyclerTicketsAdapter);

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
    private void createNewTicketMessageDialog()
    {
        android.support.v7.app.AlertDialog.Builder MessageDialogAlert = new android.support.v7.app.AlertDialog.Builder(TicketsActivity.this);
        View AlertView = View.inflate(this, R.layout.create_message_dialog_layout, null);
        final EditText edtTicketTitle = AlertView.findViewById(R.id.edtTicketTitle);
        final EditText edtTicketText = AlertView.findViewById(R.id.edtTicketText);
        final Button btnSendTicket = AlertView.findViewById(R.id.btnSendTicket);
        btnSendTicket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!(edtTicketTitle.getText().toString().isEmpty() || edtTicketText.getText().toString().isEmpty()))
                {

                    //-----
                    MessagingDialogHolder.dismiss();
                }
                else
                {
                    Toast.makeText(TicketsActivity.this, "لطفا موضوع پیام یا متن پیام را وارد کنید.", Toast.LENGTH_LONG).show();
                }
            }
        });
        MessageDialogAlert.setView(AlertView);
        MessagingDialogHolder = MessageDialogAlert.show();
    }



}

package aryasoft.company.arachoob.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aryasoft.company.arachoob.Activities.TicketChatActivity;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.CloseTicketImpl;
import aryasoft.company.arachoob.Models.TicketsModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>
    implements CloseTicketImpl.OnTicketClosedListener, Networking.NetworkStatusListener
{

    private Context context;
    private ArrayList<TicketsModel> ticketList;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;
    private int Position = 0;
    private OnUserClosedTicketListener UserClosedTicketListener;

    public TicketsAdapter(Context context, OnUserClosedTicketListener onUserClosedTicketListener)
    {
        this.context = context;
        this.ticketList = new ArrayList<>();
        UserClosedTicketListener = onUserClosedTicketListener;

        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(context.getString(R.string.waitingText))
                .build(context);
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(context);
    }

    @NonNull
    @Override
    public TicketsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new TicketsViewHolder(LayoutInflater.from(context).inflate(R.layout.tickets_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TicketsViewHolder holder, final int position)
    {
        if(ticketList.size()==0)
            return;
        holder.txtTicketTitle.setText(ticketList.get(position).MessageTitle);
        holder.txtTicketSentDate.setText(ticketList.get(position).SentDate);
        holder.txtTicketState.setText(ticketList.get(position).MessageStateTitle);
        holder.btnCloseTicket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setMessage("این تیکت بسته شود؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Position = position;
                                Networking.checkNetwork(context, TicketsAdapter.this, 11);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();

                alertDialog.show();
            }
        });
        holder.btnViewTicket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int messageId = ticketList.get(position).MessageId;
                showChatsActivity(messageId);
            }
        });


        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_anim));
    }

    @Override
    public int getItemCount()
    {
        return ticketList.size();
    }

    @Override
    public void onTicketClosed(Response<Boolean> response) {
        Loading.hide();
        if (response.body() != null)
        {
            if (response.body())
            {
                Toast.makeText(context, "تیکت بسته شد.", Toast.LENGTH_LONG).show();
                UserClosedTicketListener.onUserClosedTicket();
            }
            else
            {
                MessageDialog.setContentText(context.getString(R.string.someProblemHappend)).show();
            }
        }
    }

    @Override
    public void onNetworkConnected(int requestCode) {
        if (requestCode == 11)
            closeTicket(ticketList.get(Position).MessageId);
    }

    @Override
    public void onNetworkDisconnected() {
        MessageDialog.setContentText(context.getString(R.string.noInternetText)).show();
    }

    private void closeTicket(int messageId)
    {
        Loading.setContentText(context.getString(R.string.waitingText)).show();
        AraApi araApi = ApiServiceGenerator.getApiService();
        Call<Boolean> closeTicketCall = araApi.closeTicket(messageId);
        closeTicketCall.enqueue(new CloseTicketImpl(this));
    }

    public void updateTicketsList(ArrayList<TicketsModel> ticketList)
    {
        this.ticketList.clear();
        this.ticketList.addAll(ticketList);
        this.notifyDataSetChanged();
    }

    private void showChatsActivity(int messageId)
    {
        Intent chatsIntent = new Intent(context, TicketChatActivity.class);
        chatsIntent.putExtra("messageId", messageId);
        context.startActivity(chatsIntent);
    }


    class TicketsViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTicketTitle;
        TextView txtTicketSentDate;
        TextView txtTicketState;
        Button btnViewTicket;
        Button btnCloseTicket;

        TicketsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //-----
            txtTicketTitle = itemView.findViewById(R.id.txtTicketTitle);
            txtTicketSentDate = itemView.findViewById(R.id.txtTicketSentDate);
            txtTicketState = itemView.findViewById(R.id.txtTicketState);
            btnViewTicket = itemView.findViewById(R.id.btnViewTicket);
            btnCloseTicket = itemView.findViewById(R.id.btnCloseTicket);
        }
    }

    public interface  OnUserClosedTicketListener
    {
        void onUserClosedTicket();
    }
}

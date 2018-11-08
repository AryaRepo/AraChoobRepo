package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

import aryasoft.company.arachoob.Models.ChatModel;
import aryasoft.company.arachoob.Models.TicketChatsModel;
import aryasoft.company.arachoob.R;


public class TicketChatsAdapter extends RecyclerView.Adapter<TicketChatsAdapter.TicketChatsViewHolder>
{
    private int UserId = -1;
    private Context context;
    private ArrayList<TicketChatsModel> ticketChatList;

    public TicketChatsAdapter(Context context)
    {
        this.context = context;
        this.ticketChatList = new ArrayList<>();
        //UserId = SharedPreferencesHelper.ReadInt("UserId");
    }

    @NonNull
    @Override
    public TicketChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new TicketChatsViewHolder(LayoutInflater.from(context).inflate(R.layout.message_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TicketChatsViewHolder holder, int position)
    {
        if(ticketChatList.size()==0)
            return;
        //incoming message
        if (ticketChatList.get(position).UserIdSender != UserId)
        {
            holder.txtMessageText.setBackgroundResource(R.drawable.incoming_bubble);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.txtMessageText.setLayoutParams(layoutParams);
            holder.txtMessageText.setTextColor(Color.BLACK);
            holder.txtMessageText.setText(ticketChatList.get(position).MessageText);
            holder.txtMessageDate.setText( ticketChatList.get(position).SendDate);
        }
        //outgoing message
        else if (ticketChatList.get(position).UserIdSender == UserId)
        {
            holder.txtMessageText.setBackgroundResource(R.drawable.outgoing_bubble);
            //change view gravity
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.txtMessageText.setLayoutParams(layoutParams);
            holder.txtMessageText.setTextColor(Color.BLACK);
            holder.txtMessageText.setText(ticketChatList.get(position).MessageText);
            holder.txtMessageDate.setText( ticketChatList.get(position).SendDate);
        }
        //----

    }

    @Override
    public int getItemCount()
    {
        //return  ticketChatList.size();
        return ticketChatList.size();
    }

    public void addToTicketChatList(ArrayList<TicketChatsModel> ticketChatList)
    {
        this.ticketChatList.addAll(ticketChatList);
        this.notifyDataSetChanged();
    }

    class TicketChatsViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtMessageText;
        TextView txtMessageDate;

        TicketChatsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //-----
            txtMessageText = itemView.findViewById(R.id.txtMessageText);
            txtMessageDate = itemView.findViewById(R.id.txtMessageDate);
        }
    }
}

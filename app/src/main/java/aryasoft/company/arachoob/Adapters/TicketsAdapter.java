package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import aryasoft.company.arachoob.Models.TicketModel;
import aryasoft.company.arachoob.R;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>
{

    private Context context;
    private ArrayList<TicketModel> ticketList;

    public TicketsAdapter(Context context)
    {
        this.context = context;
        this.ticketList = new ArrayList<>();

    }

    @NonNull
    @Override
    public TicketsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new TicketsViewHolder(LayoutInflater.from(context).inflate(R.layout.tickets_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TicketsViewHolder holder, int position)
    {
        if(ticketList.size()==0)
            return;
        holder.txtTicketTitle.setText(ticketList.get(position).MessageTitle);
        holder.txtTicketSentDate.setText(ticketList.get(position).LastSendDate);
        holder.txtTicketState.setText(ticketList.get(position).MessageStateTitle);
        holder.btnCloseTicket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //close ticket api
            }
        });
        holder.btnViewTicket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //pass MessageId
            }
        });
        //----
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_anim));
    }

    @Override
    public int getItemCount()
    {
        //return  TicketList.size();
        return 10;
    }

    public void addToTicketList(ArrayList<TicketModel> ticketList)
    {
        this.ticketList.addAll(ticketList);
        this.notifyDataSetChanged();
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
}

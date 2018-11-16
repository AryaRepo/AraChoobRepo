package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import aryasoft.company.arachoob.Activities.OrderDetailActivity;
import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderApiModel;
import aryasoft.company.arachoob.Models.OrderHistoryModel;
import aryasoft.company.arachoob.R;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>
{

    private Context context;
    private ArrayList<GetUserOrderApiModel> orderHistoryList;

    public OrderHistoryAdapter(Context context)
    {
        this.context = context;
        this.orderHistoryList = new ArrayList<>();

    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new OrderHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.order_history_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final @NonNull OrderHistoryViewHolder holder, int position)
    {
        if (orderHistoryList.size() == 0)
        {
            return;
        }
        GetUserOrderApiModel order = orderHistoryList.get(position);
        //------------------------------------------------------------------
        if (order.PaymentStatus)
        {
            holder.txtPaymentType.setText("وضعیت پرداخت : پرداخت شده");
            holder.txtPaymentType.setTextColor(Color.parseColor("#4CAF50"));
        }
        else
        {
            holder.txtPaymentType.setText("وضعیت پرداخت : هنوز پرداخت نکردید");
            holder.txtPaymentType.setTextColor(Color.RED);
        }

        //-----------------------------------------
        switch (orderHistoryList.get(position).OrderStateId)

        {
            case 1:
                holder.txtPaymentStatus.setText("ثبت اولیه توسط مشتری");
                holder.imgPaymentStatus.setColorFilter(Color.parseColor("#2196F3"), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case 2:
                holder.txtPaymentStatus.setText("درحال بررسی توسط مدیر");
                holder.imgPaymentStatus.setColorFilter(Color.parseColor("#FFEE58"), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case 3:
                holder.txtPaymentStatus.setText("تایید سفارش توسط مدیر");
                holder.imgPaymentStatus.setColorFilter(Color.parseColor("#FF9800"), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case 4:
                holder.txtPaymentStatus.setText("تحویل پیک");
                holder.imgPaymentStatus.setColorFilter(Color.parseColor("#4CAF50"), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case 5:
                holder.txtPaymentStatus.setText("تحویل مشتری");
                holder.imgPaymentStatus.setColorFilter(Color.parseColor("#4CAF50"), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case 6:
                holder.txtPaymentStatus.setText("تایید نهایی");
                holder.imgPaymentStatus.setColorFilter(Color.parseColor("#4CAF50"), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
        }
        //-----------------------
        holder.txtInvoiceDate.setText(orderHistoryList.get(position).OrderDate);
        holder.txtInvoiceDeliveryDate.setText(orderHistoryList.get(position).DeliveryTime);
        holder.btnShowOrderCart.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                int orderId = orderHistoryList.get(holder.getAdapterPosition()).OrderId;
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("totalPrice", orderHistoryList.get(holder.getAdapterPosition()).TotalPrice);
                context.startActivity(intent);
            }
        });
        //--------------------------------------------------------
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_anim));
    }

    @Override
    public int getItemCount()
    {
        return  orderHistoryList.size();
    }

    public void addToOrderHistoryList(ArrayList<GetUserOrderApiModel> orderHistoryList)
    {
        this.orderHistoryList.addAll(orderHistoryList);
        this.notifyDataSetChanged();
    }

    class OrderHistoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtPaymentType;
        TextView txtInvoiceDate;
        TextView txtInvoiceDeliveryDate;
        TextView txtPaymentStatus;
        ImageView imgPaymentStatus;
        Button btnShowOrderCart;

        OrderHistoryViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //-----
            txtPaymentType = itemView.findViewById(R.id.txtPaymentType);
            txtInvoiceDate = itemView.findViewById(R.id.txtInvoiceDate);
            txtInvoiceDeliveryDate = itemView.findViewById(R.id.txtInvoiceDeliveryDate);
            txtPaymentStatus = itemView.findViewById(R.id.txtPaymentStatus);
            imgPaymentStatus = itemView.findViewById(R.id.imgPaymentStatus);
            btnShowOrderCart = itemView.findViewById(R.id.btnShowOrderCart);
        }
    }
}

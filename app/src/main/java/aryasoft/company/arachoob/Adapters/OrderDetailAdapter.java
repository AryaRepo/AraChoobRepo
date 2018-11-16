package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderDetail;
import aryasoft.company.arachoob.Models.OrderDetailModel;
import aryasoft.company.arachoob.R;


public class OrderDetailAdapter extends BaseAdapter
{
    private class OrderDetailViewHolder
    {
        TextView txtOrderProductNameItem;
        TextView txtOrderProductCountItem;
        TextView txtOrderProductPriceItem;
        TextView txtOrderProductDescription;
        TextView txtOrderProductDiscountPercent;
    }

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<GetUserOrderDetail> orderDetailList;
    private OrderDetailViewHolder holder;

    public OrderDetailAdapter(Context context)
    {
        this.context = context;
        this.orderDetailList = new ArrayList<>();
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount()
    {
        return orderDetailList.size();
    }

    public void addOrderDetailList(ArrayList<GetUserOrderDetail> orderDetailList)
    {
        this.orderDetailList.addAll(orderDetailList);
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position)
    {
        return orderDetailList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View MyView = convertView;
        if (convertView == null)
        {
            MyView = layoutInflater.inflate(R.layout.order_detail_list_item, null);
            holder = new OrderDetailViewHolder();
        }
        if (orderDetailList.size() != 0)
        {
            holder.txtOrderProductNameItem = MyView.findViewById(R.id.txtOrderProductNameItem);
            holder.txtOrderProductCountItem = MyView.findViewById(R.id.txtOrderProductCountItem);
            holder.txtOrderProductPriceItem = MyView.findViewById(R.id.txtOrderProductPriceItem);
            holder.txtOrderProductDescription = MyView.findViewById(R.id.txtOrderProductDescription);
            holder.txtOrderProductDiscountPercent = MyView.findViewById(R.id.txtOrderProductDiscountPercent);

            //--------------------
            holder.txtOrderProductNameItem.setText(orderDetailList.get(position).ProductTitle);
            holder.txtOrderProductCountItem.setText("تعداد کالا : "+orderDetailList.get(position).Number +" "+ "عدد");
            holder.txtOrderProductPriceItem.setText("قیمت : "+orderDetailList.get(position).Price + "");
            if(orderDetailList.get(position).DiscountPercent!=0)
            {
                holder.txtOrderProductDiscountPercent.setVisibility(View.VISIBLE);
                holder.txtOrderProductDiscountPercent.setText(" تخفیف کالا : " + orderDetailList.get(position).DiscountPercent + " " + "درصد");
            }
            if(orderDetailList.get(position).Description!=null)
            {
                holder.txtOrderProductDescription.setText("توضیحات ضمیمه شده سفارش : " + "\n" + orderDetailList.get(position).Description);
            }
            else
            {
                holder.txtOrderProductDescription.setText("توضیحاتی ثبت نشده");
            }


            //----------------
        }
        return MyView;
    }
}


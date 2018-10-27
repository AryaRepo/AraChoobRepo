package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import aryasoft.company.arachoob.Models.OrderDetailModel;
import aryasoft.company.arachoob.R;


public class OrderDetailAdapter extends BaseAdapter
{
    private class OrderDetailViewHolder
    {
        TextView txtOrderProductNameItem;
        TextView txtOrderProductCountItem;
        TextView txtOrderProductPriceItem;
    }

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<OrderDetailModel> orderDetailList;
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
        //return orderDetailList.size();
        return 10;
    }

    public void addOrderDetailList(ArrayList<OrderDetailModel> orderDetailList)
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
            //--------------------
            holder.txtOrderProductNameItem.setText(orderDetailList.get(position).ProductTitle);
            holder.txtOrderProductCountItem.setText(orderDetailList.get(position).Number + "");
            holder.txtOrderProductPriceItem.setText(orderDetailList.get(position).Price + "");
            //----------------
        }
        return MyView;
    }
}


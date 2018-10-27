package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import aryasoft.company.arachoob.Models.SimilarProductModel;
import aryasoft.company.arachoob.R;


public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.SimilarProductAdapterViewHolder>
{
    private Context context;
    private ArrayList<SimilarProductModel> similarProductList;

    public SimilarProductAdapter(Context context)
    {
        this.context = context;
        this.similarProductList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SimilarProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new SimilarProductAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.similar_product_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarProductAdapterViewHolder holder, int position)
    {
        if(similarProductList.size()==0)
            return;
        holder.txtPrimaryPrice.setText(similarProductList.get(position).PrimaryPrice);
        holder.txtDiscountedPrice.setText(similarProductList.get(position).DiscountedPrice);
        Glide.with(context).load(context.getString(R.string.BaseUrl)+context.getString(R.string.ProductImageFolder)).into(holder.imgSimilarImageName);
    }

    @Override
    public int getItemCount()
    {
        return 10;
      //  return similarProductList.size();
    }

    public void addSimilarProduct(ArrayList<SimilarProductModel> similarProductList)
    {
        this.similarProductList.addAll(similarProductList);
        this.notifyDataSetChanged();
    }

    class SimilarProductAdapterViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgSimilarImageName;
        private TextView txtPrimaryPrice;
        private TextView txtDiscountedPrice;

        SimilarProductAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //------------------
            imgSimilarImageName=itemView.findViewById(R.id.imgSimilarImageName);
            txtPrimaryPrice=itemView.findViewById(R.id.txtPrimaryPrice);
            txtDiscountedPrice=itemView.findViewById(R.id.txtDiscountedPrice);
        }
    }
}

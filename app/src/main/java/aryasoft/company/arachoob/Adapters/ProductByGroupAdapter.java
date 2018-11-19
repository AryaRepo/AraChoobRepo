package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import aryasoft.company.arachoob.Activities.DetailActivity;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.R;

public class ProductByGroupAdapter extends RecyclerView.Adapter<ProductByGroupAdapter.ProductByGroupViewHolder>
{

    private Context context;
    private ArrayList<ProductDataModel> productList;

    public ProductByGroupAdapter(Context context)
    {
        this.context = context;
        this.productList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductByGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ProductByGroupViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductByGroupViewHolder holder, int position)
    {
        final ProductDataModel currentProduct = productList.get(position);
        if(currentProduct.ImageName!=null)
        {
            Glide.with(context).load(context.getResources().getString(R.string.BaseUrl) + context.getResources().getString(R.string.ProductImageFolder) + currentProduct.ImageName).into(holder.ImgProductPhoto);
        }
        else
        {
            Glide.with(context).load(R.drawable.no_img).into(holder.ImgProductPhoto);
        }
        holder.TxtProductTitle.setText(currentProduct.ProductTitle);
        holder.TxtProductPrimaryPrice.setText(" قیمت پایه " + currentProduct.CoverPrice + " " + "تومان");
        if (currentProduct.DiscountPercent != 0)
        {
            holder.TxtProductPrimaryPrice.setPaintFlags(holder.TxtProductPrimaryPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.TxtProductDiscountedPrice.setText("باتخفیف " + currentProduct.DiscountPercent + " درصدی " + "\n" + calculateDiscount(currentProduct.CoverPrice, currentProduct.DiscountPercent) + " " + "تومان");
        }
        else
        {
            holder.TxtProductDiscountedPrice.setText(" " + "\n" + " ");
        }
        if (currentProduct.ProductCount == 0)
        {
            holder.txtProductCount.setText("ناموجود");
            holder.txtProductCount.setTextColor(Color.RED);
        }
        else
        {
            holder.txtProductCount.setText("موجود");
            holder.txtProductCount.setTextColor(Color.parseColor("#4CAF50"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ProductDataModel clickedProduct = productList.get(holder.getAdapterPosition());
                Intent detailsIntent = new Intent(context, DetailActivity.class);
                detailsIntent.putExtra("productId", clickedProduct.ProductId);
                detailsIntent.putExtra("productTitle", clickedProduct.ProductTitle);
                detailsIntent.putExtra("primaryPrice", clickedProduct.CoverPrice);
                detailsIntent.putExtra("productImage", clickedProduct.ImageName);
                detailsIntent.putExtra("productCount", clickedProduct.ProductCount);
                context.startActivity(detailsIntent);
            }
        });
        //----
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_from_up));
    }

    @Override
    public int getItemCount()
    {
        return productList.size();
    }

    private int calculateDiscount(int salesPrice, int discountPercent)
    {
        int finalPrice;
        finalPrice = (salesPrice * discountPercent) / 100;
        return salesPrice - finalPrice;
    }

    public void addToProductList(ArrayList<ProductDataModel> productList)
    {
        this.productList.addAll(productList);
        this.notifyDataSetChanged();
    }

    class ProductByGroupViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ImgProductPhoto;
        private TextView TxtProductTitle;
        private TextView TxtProductPrimaryPrice;
        private TextView TxtProductDiscountedPrice;
        private TextView txtProductCount;
        public ProductByGroupViewHolder(@NonNull View itemView)
        {
            super(itemView);
            initializeViews();
        }

        private void initializeViews()
        {
            ImgProductPhoto = itemView.findViewById(R.id.imgProductPhoto);
            TxtProductTitle = itemView.findViewById(R.id.txtProductTitle);
            TxtProductPrimaryPrice = itemView.findViewById(R.id.txtProductPrimaryPrice);
            TxtProductDiscountedPrice = itemView.findViewById(R.id.txtProductDiscountedPrice);
            txtProductCount = itemView.findViewById(R.id.txtProductCount);
        }
    }
}

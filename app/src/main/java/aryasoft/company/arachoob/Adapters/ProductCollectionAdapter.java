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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import aryasoft.company.arachoob.Activities.DetailActivity;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.R;

public class ProductCollectionAdapter extends RecyclerView.Adapter<ProductCollectionAdapter.ProductViewHolder>
{

    private ArrayList<ProductDataModel> products;
    private Context ContextInstance;

    public ProductCollectionAdapter(Context contextInstance)
    {
        products = new ArrayList<>();
        ContextInstance = contextInstance;
    }

    public ProductCollectionAdapter(ArrayList<ProductDataModel> products, Context contextInstance)
    {
        this.products = products;
        ContextInstance = contextInstance;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position)
    {
        return new ProductViewHolder(LayoutInflater.from(ContextInstance).inflate(R.layout.item_layout_product, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position)
    {
        bindViews(productViewHolder, position);
    }

    @Override
    public int getItemCount()
    {
        return products.size();
    }

    private void bindViews(final ProductViewHolder holder, final int position)
    {
        final ProductDataModel currentProduct = products.get(position);
        if (currentProduct.ImageName != null)
        {
            Glide.with(ContextInstance).load(ContextInstance.getResources().getString(R.string.BaseUrl) + ContextInstance.getResources().getString(R.string.ProductImageFolder) + currentProduct.ImageName).into(holder.ImgProductPhoto);
        }
        else
        {
            Glide.with(ContextInstance).load(R.drawable.no_img).into(holder.ImgProductPhoto);
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
                ProductDataModel clickedProduct = products.get(holder.getAdapterPosition());
                Intent detailsIntent = new Intent(ContextInstance, DetailActivity.class);
                detailsIntent.putExtra("productId", clickedProduct.ProductId);
                detailsIntent.putExtra("productTitle", clickedProduct.ProductTitle);
                detailsIntent.putExtra("primaryPrice", clickedProduct.CoverPrice);
                detailsIntent.putExtra("productImage", clickedProduct.ImageName);
                ContextInstance.startActivity(detailsIntent);
            }
        });

    }

    public void addToProductsList(ArrayList<ProductDataModel> products)
    {
        this.products.addAll(products);
        this.notifyDataSetChanged();
    }

    private int calculateDiscount(int salesPrice, int discountPercent)
    {
        int finalPrice;
        finalPrice = (salesPrice * discountPercent) / 100;
        return salesPrice - finalPrice;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ImgProductPhoto;
        private TextView TxtProductTitle;
        private TextView TxtProductPrimaryPrice;
        private TextView TxtProductDiscountedPrice;
        private TextView txtProductCount;


        ProductViewHolder(@NonNull View itemView)
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

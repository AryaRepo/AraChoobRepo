package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import aryasoft.company.arachoob.Models.Collection;
import aryasoft.company.arachoob.Models.Product;
import aryasoft.company.arachoob.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> Products;
    private Context ContextInstance;

    public ProductAdapter(ArrayList<Product> products, Context contextInstance) {
        Products = products;
        ContextInstance = contextInstance;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ProductViewHolder(
                LayoutInflater.from(ContextInstance)
                        .inflate(R.layout.item_layout_product, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        bindViews(productViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return Products.size();
    }

    private void bindViews(ProductViewHolder holder, final int position)
    {
        Product currentProduct = Products.get(position);
        Glide.with(ContextInstance).load(currentProduct.getProductPhoto()).into(holder.ImgProductPhoto);
        holder.TxtProductTitle.setText(currentProduct.getProductTitle());
        holder.TxtProductPrimaryPrice.setText(currentProduct.getProductPrimaryPrice());
        holder.TxtProductDiscountedPrice.setText(currentProduct.getProductDisCountedPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContextInstance, position+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ProductViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ImgProductPhoto;
        private TextView TxtProductTitle;
        private TextView TxtProductPrimaryPrice;
        private TextView TxtProductDiscountedPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeViews();
        }

        private void initializeViews()
        {
            ImgProductPhoto = itemView.findViewById(R.id.imgProductPhoto);
            TxtProductTitle = itemView.findViewById(R.id.txtProductTitle);
            TxtProductPrimaryPrice = itemView.findViewById(R.id.txtProductPrimaryPrice);
            TxtProductDiscountedPrice = itemView.findViewById(R.id.txtProductDiscountedPrice);
        }
    }
}

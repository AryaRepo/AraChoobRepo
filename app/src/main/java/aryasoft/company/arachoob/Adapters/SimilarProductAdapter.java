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
import aryasoft.company.arachoob.ApiConnection.ApiModels.SimilarProductApiModel;
import aryasoft.company.arachoob.R;


public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.SimilarProductAdapterViewHolder>
{
    private Context context;
    private ArrayList<SimilarProductApiModel> similarProductList;
    public SimilarProductAdapter(Context context)
    {
        this.context = context;
        this.similarProductList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SimilarProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new SimilarProductAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SimilarProductAdapterViewHolder holder, int position)
    {
        if (similarProductList.size() == 0)
        {
            return;
        }
        final SimilarProductApiModel currentProduct = similarProductList.get(position);
        if (currentProduct.ImageName != null)
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
                Intent similarProduct = new Intent(v.getContext(),DetailActivity.class);
                similarProduct.putExtra("productId", similarProductList.get(holder.getAdapterPosition()).ProductId);
                similarProduct.putExtra("productTitle", similarProductList.get(holder.getAdapterPosition()).ProductTitle);
                similarProduct.putExtra("productImage", similarProductList.get(holder.getAdapterPosition()).ImageName);
                similarProduct.putExtra("primaryPrice", similarProductList.get(holder.getAdapterPosition()).CoverPrice);
                similarProduct.putExtra("productCount", similarProductList.get(holder.getAdapterPosition()).ProductCount);
                context.startActivity(similarProduct);
                ((DetailActivity) context).finish();
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return similarProductList.size();
    }

    public void addSimilarProduct(ArrayList<SimilarProductApiModel> similarProductList)
    {
        this.similarProductList.addAll(similarProductList);
        this.notifyDataSetChanged();
    }

    private int calculateDiscount(int salesPrice, int discountPercent)
    {
        int finalPrice;
        finalPrice = (salesPrice * discountPercent) / 100;
        return salesPrice - finalPrice;
    }

    class SimilarProductAdapterViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ImgProductPhoto;
        private TextView TxtProductTitle;
        private TextView TxtProductPrimaryPrice;
        private TextView TxtProductDiscountedPrice;
        private TextView txtProductCount;




     /*   private ImageView imgSimilarImageName;
        private TextView txtSimilarProductTitle;
        private TextView txtPrimaryPrice;
        private TextView txtDiscountedPrice;*/

        SimilarProductAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            //------------------
           /* txtSimilarProductTitle = itemView.findViewById(R.id.txtSimilarProductTitle);
            imgSimilarImageName = itemView.findViewById(R.id.imgSimilarProductImageName);
            txtPrimaryPrice = itemView.findViewById(R.id.txtSimilarProductPrimaryPrice);
            txtDiscountedPrice = itemView.findViewById(R.id.txtSimilarProductDiscountedPrice);*/

            ImgProductPhoto = itemView.findViewById(R.id.imgProductPhoto);
            TxtProductTitle = itemView.findViewById(R.id.txtProductTitle);
            TxtProductPrimaryPrice = itemView.findViewById(R.id.txtProductPrimaryPrice);
            TxtProductDiscountedPrice = itemView.findViewById(R.id.txtProductDiscountedPrice);
            txtProductCount = itemView.findViewById(R.id.txtProductCount);
        }
    }
}

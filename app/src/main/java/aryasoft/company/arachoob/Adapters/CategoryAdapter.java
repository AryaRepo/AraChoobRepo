package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import aryasoft.company.arachoob.Activities.ProductActivity;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductGroupsApiModel;
import aryasoft.company.arachoob.Models.Category;
import aryasoft.company.arachoob.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>
{

    private Context ContextInstance;
    private ArrayList<ProductGroupsApiModel> categoriesList;

    public CategoryAdapter(Context contextInstance)
    {
        ContextInstance = contextInstance;
        categoriesList = new ArrayList<>();
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position)
    {
        return new CategoryViewHolder(LayoutInflater.from(ContextInstance).inflate(R.layout.item_layout_category, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position)
    {
        ProductGroupsApiModel category = categoriesList.get(position);
        Glide.with(ContextInstance).load(ContextInstance.getResources().getString(R.string.BaseUrl) + ContextInstance.getResources().getString(R.string.ProductGroupImageFolder) + category.ImageName).into(holder.ImgCategoryIcon);
        holder.TxtCategoryTitle.setText(category.ProductGroupTitle);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContextInstance
                        .startActivity
                                (new Intent(ContextInstance, ProductActivity.class)
                                        .putExtra("productGroupTitle", categoriesList.get(holder.getAdapterPosition()).ProductGroupTitle)
                                        .putExtra("productGroupId", categoriesList.get(holder.getAdapterPosition()).ProductGroupID)
                                );
            }
        });
    }

    public void addCategoriesList(ArrayList<ProductGroupsApiModel> categoriesList)
    {
        this.categoriesList.addAll(categoriesList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return categoriesList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ImgCategoryIcon;
        private TextView TxtCategoryTitle;

        public CategoryViewHolder(@NonNull View itemView)
        {
            super(itemView);
            initializeViews(itemView);
        }

        private void initializeViews(View view)
        {
            ImgCategoryIcon = view.findViewById(R.id.imgCategoryIcon);
            TxtCategoryTitle = view.findViewById(R.id.txtCategoryTitle);
        }
    }
}

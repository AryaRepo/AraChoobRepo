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
import java.util.List;

import aryasoft.company.arachoob.Models.Category;
import aryasoft.company.arachoob.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context ContextInstance;
    private ArrayList<Category> Categories;

    public CategoryAdapter(Context contextInstance, ArrayList<Category> categories) {
        ContextInstance = contextInstance;
        Categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new CategoryViewHolder(LayoutInflater.from(ContextInstance).inflate(R.layout.item_layout_category, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = Categories.get(position);
        String imageUrl = "";
        Glide.with(ContextInstance).load("").into(holder.ImgCategoryIcon);
        holder.TxtCategoryTitle.setText(category.getCategoryTitle());
    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }
    
    class CategoryViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ImgCategoryIcon;
        private TextView TxtCategoryTitle;

        public CategoryViewHolder(@NonNull View itemView) {
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

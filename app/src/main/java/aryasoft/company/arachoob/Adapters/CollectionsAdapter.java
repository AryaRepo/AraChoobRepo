package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import aryasoft.company.arachoob.Activities.AllCollectionProductActivity;
import aryasoft.company.arachoob.Models.Collection;
import aryasoft.company.arachoob.R;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionViewHolder> {

    private ArrayList<Collection> Collections;
    private Context ContextInstance;

    public CollectionsAdapter(ArrayList<Collection> collections, Context contextInstance) {
        Collections = collections;
        ContextInstance = contextInstance;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new CollectionViewHolder(
                LayoutInflater.from(ContextInstance)
                              .inflate(R.layout.item_layout_colletion, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder collectionViewHolder, int position) {
        bindViews(collectionViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return Collections.size();
    }

    private void bindViews(CollectionViewHolder holder, int position)
    {
        final Collection currentCollection = Collections.get(position);
        holder.TxtCollectionName.setText(Collections.get(position).getTitle());
        holder.TxtShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showMoreIntent = new Intent(ContextInstance, AllCollectionProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("productsList", currentCollection.getProductsList());
                bundle.putString("collectionTitle", currentCollection.getTitle());
                showMoreIntent.putExtras(bundle);
                ContextInstance.startActivity(showMoreIntent);
            }
        });
        ProductAdapter productAdapter = new ProductAdapter(currentCollection.getProductsList(), ContextInstance);
        LinearLayoutManager horizontalLinearLayoutManager =
                new LinearLayoutManager(ContextInstance, LinearLayoutManager.HORIZONTAL, true);
        holder.RecyclerProducts.setLayoutManager(horizontalLinearLayoutManager);
        holder.RecyclerProducts.setAdapter(productAdapter);
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder
    {
        private TextView TxtCollectionName;
        private TextView TxtShowMore;
        private RecyclerView RecyclerProducts;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            initializeViews();
        }

        private void initializeViews()
        {
            TxtCollectionName = itemView.findViewById(R.id.txt_collection_name);
            TxtShowMore = itemView.findViewById(R.id.txt_show_more);
            RecyclerProducts = itemView.findViewById(R.id.recyclerProducts);
        }
    }
}

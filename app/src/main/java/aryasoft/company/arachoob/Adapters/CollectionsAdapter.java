package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import aryasoft.company.arachoob.Activities.AllCollectionProductActivity;
import aryasoft.company.arachoob.ApiConnection.ApiModels.CollectionDataModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.VectorDrawablePreLollipopHelper;
import aryasoft.company.arachoob.Utils.VectorView;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.CollectionViewHolder>
{

    private ArrayList<CollectionDataModel> Collections;
    private Context ContextInstance;

    public CollectionsAdapter(Context contextInstance)
    {
        this.Collections = new ArrayList<>();
        ContextInstance = contextInstance;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position)
    {
        return new CollectionViewHolder(LayoutInflater.from(ContextInstance).inflate(R.layout.item_layout_colletion, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder collectionViewHolder, int position)
    {
        bindViews(collectionViewHolder, position);
    }

    @Override
    public int getItemCount()
    {
        ArrayList<CollectionDataModel> TempData = new ArrayList<>();
        for (int i = 0; i < Collections.size(); ++i)
        {
            if (Collections.get(i).Products.size() != 0)
            {
                TempData.add(Collections.get(i));
            }
        }
        Collections.clear();
        Collections.addAll(TempData);
        return Collections.size();
    }

    private void bindViews(final CollectionViewHolder holder, int position)
    {
        final CollectionDataModel currentCollection = Collections.get(position);
        holder.TxtCollectionName.setText(currentCollection.CollectionTitle);
        holder.TxtShowMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContextInstance.startActivity
                        (
                                new Intent(ContextInstance, AllCollectionProductActivity.class)
                                        .putExtra("sectionId", currentCollection.SectionId)
                                        .putExtra("collectionId", currentCollection.CollectionId)
                                        .putExtra("collectionTitle", currentCollection.CollectionTitle)
                        );
            }
        });
        ProductAdapter productAdapter = new ProductAdapter(currentCollection.Products, ContextInstance);
        LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(ContextInstance, LinearLayoutManager.HORIZONTAL, true);
        holder.RecyclerProducts.setLayoutManager(horizontalLinearLayoutManager);
        holder.RecyclerProducts.setAdapter(productAdapter);
    }


    public void addCollections(ArrayList<CollectionDataModel> collections)
    {
        Collections.addAll(collections);
        this.notifyDataSetChanged();
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder
    {
        private TextView TxtCollectionName;
        private TextView TxtShowMore;
        private RecyclerView RecyclerProducts;

        CollectionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            initializeViews();
        }

        private void initializeViews()
        {
            TxtCollectionName = itemView.findViewById(R.id.txt_collection_name);
            TxtShowMore = itemView.findViewById(R.id.txt_show_more);
            RecyclerProducts = itemView.findViewById(R.id.recyclerProducts);
            VectorDrawablePreLollipopHelper.SetVectors(itemView.getResources(), new VectorView(R.drawable.chevron_left, TxtShowMore, VectorDrawablePreLollipopHelper.MyDirType.start));
        }
    }
}

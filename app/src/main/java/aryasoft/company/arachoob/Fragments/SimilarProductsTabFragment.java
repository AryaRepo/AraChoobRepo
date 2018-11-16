package aryasoft.company.arachoob.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.SimilarProductAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiModels.SimilarProductApiModel;
import aryasoft.company.arachoob.R;

public class SimilarProductsTabFragment extends Fragment
{
    private Context fragmentContext;
    private RecyclerView recyclerSimilarProduct;
    private SimilarProductAdapter recyclerSimilarProductAdapter;
    private ArrayList<SimilarProductApiModel> similarProduct;
    private RelativeLayout relEmptySimilarProduct;

    public SimilarProductsTabFragment()
    {

    }

    public SimilarProductsTabFragment(ArrayList<SimilarProductApiModel> similarProduct)
    {
        this.similarProduct = similarProduct;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_similar_products_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fragmentContext = view.getContext();
        initViews(view);
    }

    private void initViews(View view)
    {
        relEmptySimilarProduct = view.findViewById(R.id.relEmptySimilarProduct);
        recyclerSimilarProduct = view.findViewById(R.id.recyclerSimilarProduct);
        if (similarProduct.size() == 0)
        {
            recyclerSimilarProduct.setVisibility(View.GONE);
            relEmptySimilarProduct.setVisibility(View.VISIBLE);
            return;
        }
        recyclerSimilarProductAdapter = new SimilarProductAdapter(fragmentContext);
        recyclerSimilarProduct.setLayoutManager(new LinearLayoutManager(fragmentContext, LinearLayoutManager.HORIZONTAL, true));
        recyclerSimilarProduct.setAdapter(recyclerSimilarProductAdapter);
        recyclerSimilarProductAdapter.addSimilarProduct(similarProduct);
    }
}

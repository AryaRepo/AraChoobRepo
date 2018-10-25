package aryasoft.company.arachoob.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.ProductAdapter;
import aryasoft.company.arachoob.Models.Product;
import aryasoft.company.arachoob.R;

public class SearchFragment extends Fragment {

    private RecyclerView RecyclerSearchResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeViews(View view)
    {
        RecyclerSearchResult = view.findViewById(R.id.recyclerSearch);
    }

    private void setupSearchResultRecyclerView()
    {
        ArrayList<Product> products = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        RecyclerSearchResult.setLayoutManager(gridLayoutManager);
        ProductAdapter productAdapter = new ProductAdapter(products, getContext());
        RecyclerSearchResult.setAdapter(productAdapter);
    }
}

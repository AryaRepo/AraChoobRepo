package aryasoft.company.arachoob.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.CategoryAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetProductGroupsListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductGroupsApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.ProductModule;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.SweetLoading;

public class CategoryFragment extends Fragment implements OnDataReceiveStateListener
{

    private int productGroupId = -1;
    private RecyclerView RecyclerCategory;
    private SweetLoading Loading;
    private ProductModule productModule;
    private CategoryAdapter categoryAdapter;

    @Override
    public void OnDataReceiveState(Throwable ex)
    {
        Loading.hide();
        new CuteToast.Builder(getActivity()).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Loading = new SweetLoading.Builder().build(view.getContext());
        productModule = new ProductModule();
        productModule.setOnDataReceiveStateListener(this);
        initializeViews(view);
        setupCategoriesRecycler();
    }

    private void initializeViews(View view)
    {
        RecyclerCategory = view.findViewById(R.id.recyclerCategories);
        setupCategoriesRecycler();
        getCategories();
    }


    private void setupCategoriesRecycler()
    {
        categoryAdapter = new CategoryAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerCategory.setLayoutManager(linearLayoutManager);
        RecyclerCategory.setAdapter(categoryAdapter);
    }

    private void getCategories()
    {
        Loading.show();
        productModule.setOnGetProductGroupsListener(new OnGetProductGroupsListener()
        {
            @Override
            public void OnGetProductGroups(ArrayList<ProductGroupsApiModel> productGroups)
            {
                Loading.hide();
                categoryAdapter.addCategoriesList(productGroups);
            }
        });
        productModule.getProductGroups(productGroupId);
    }


}

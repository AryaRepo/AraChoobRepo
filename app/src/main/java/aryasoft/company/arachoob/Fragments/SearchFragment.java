package aryasoft.company.arachoob.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.ProductAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnSearchProductListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.ProductModule;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.ItemDecorationRecycler;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.SweetLoading;

public class SearchFragment extends Fragment implements OnDataReceiveStateListener
{
    private RecyclerView recyclerSearchResult;
    private GridLayoutManager recyclerGridLayoutManager;
    private ProductAdapter productAdapter;
    private ProductModule productModule;
    private EditText edtSearch;
    private ImageButton btnSearch;
    private ImageButton btnCleanSearchBox;
    private Context context;
    private SweetLoading Loading;
    private boolean IsLoading = false;
    private boolean isLoadMoreEnd = false;

    @Override
    public void OnDataReceiveState(Throwable ex)
    {
        IsLoading = false;
        Loading.hide();
        new CuteToast.Builder(getActivity()).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        initializeViews(view);
        initializeEvents();
    }


    private void initializeViews(View view)
    {
        productModule = new ProductModule();
        productModule.setOnDataReceiveStateListener(this);
        Loading = new SweetLoading.Builder().build(view.getContext());
        edtSearch = view.findViewById(R.id.edtSearch);
        btnCleanSearchBox = view.findViewById(R.id.btnCleanSearchBox);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerSearchResult = view.findViewById(R.id.recyclerSearch);
        setupSearchResultRecyclerView();

    }

    private void setupSearchResultRecyclerView()
    {
        recyclerGridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerSearchResult.addItemDecoration(new ItemDecorationRecycler(2, 8, true));
        recyclerSearchResult.setLayoutManager(recyclerGridLayoutManager);
        productAdapter = new ProductAdapter(context);
        recyclerSearchResult.setAdapter(productAdapter);
    }

    private void initializeEvents()
    {
        btnCleanSearchBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edtSearch.getText().clear();
                productAdapter.clearProductsList();
                IsLoading = false;
                isLoadMoreEnd = false;
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (edtSearch.getText().toString().length() <= 0)
                {
                    new CuteToast.Builder(getActivity()).setText("لطفا عبارت مورد جستجو را وارد کنید...").setDuration(Toast.LENGTH_LONG).show();
                }
                else
                {
                    searchProducts(edtSearch.getText().toString());
                }
            }
        });
        loadMoreSearchResult();
        edtSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() <= 0)
                {
                    IsLoading = false;
                    isLoadMoreEnd = false;
                }
            }
        });
    }

    private void searchProducts(String searchText)
    {
        IsLoading = true;
        Loading.show();
        productModule.setOnSearchProductListener(new OnSearchProductListener()
        {
            @Override
            public void OnSearchProduct(ArrayList<ProductDataModel> searchData)
            {
                if (searchData.size() == 0)
                {
                    isLoadMoreEnd = true;
                }
                else
                {
                    productAdapter.addToProductsList(searchData);
                    IsLoading = false;
                }
                Loading.hide();
            }
        });
        productModule.searchProducts(searchText, productAdapter.getItemCount(), 20);
    }

    private void loadMoreSearchResult()
    {
        recyclerSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (isLoadMoreEnd)
                {
                    return;
                }
                if (productAdapter.getItemCount() >= 10)
                {
                    if (IsLoading)
                    {
                        return;
                    }
                    int VisibleItemCount = recyclerGridLayoutManager.getChildCount();
                    int TotalItemCount = recyclerGridLayoutManager.getItemCount();
                    int PastVisibleItem = recyclerGridLayoutManager.findFirstVisibleItemPosition();
                    if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                    {
                        searchProducts(edtSearch.getText().toString());
                    }
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

}

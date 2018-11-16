package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.ProductByGroupAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetProductByGroupIdListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.ProductModule;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.ItemDecorationRecycler;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.SweetLoading;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductActivity extends AppCompatActivity implements OnDataReceiveStateListener
{

    private TextView txtProductGroupTitle;
    private RecyclerView recyclerProducts;
    private ProductByGroupAdapter productByGroupAdapter;
    private GridLayoutManager productByGroupLayoutManager;
    private int productGroupId;
    private ProductModule productModule;
    private SweetLoading Loading;
    private boolean isLoading=false;

    @Override
    public void OnDataReceiveState(Throwable ex)
    {
        isLoading=false;
        Loading.hide();
        new CuteToast.Builder(this).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();

    }
    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Loading = new SweetLoading.Builder().build(this);
        productModule = new ProductModule();
        productModule.setOnDataReceiveStateListener(this);
        initializeViews();
        initializeEvents();
    }

    private void initializeViews()
    {
        txtProductGroupTitle = findViewById(R.id.txtProductGroupTitle);
        recyclerProducts = findViewById(R.id.recyclerProducts);
        txtProductGroupTitle.setText(getIntent().getStringExtra("productGroupTitle"));
        productGroupId = getIntent().getIntExtra("productGroupId", 0);
        productByGroupAdapter = new ProductByGroupAdapter(this);
        productByGroupLayoutManager = new GridLayoutManager(this, 2);
        recyclerProducts.addItemDecoration(new ItemDecorationRecycler(2, 8, true));
        recyclerProducts.setLayoutManager(productByGroupLayoutManager);
        recyclerProducts.setAdapter(productByGroupAdapter);
    }

    private void initializeEvents()
    {
        getProductByGroupId(productGroupId,productByGroupAdapter.getItemCount());
        recyclerProducts.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (productByGroupAdapter.getItemCount() >= 10)
                {
                    if (isLoading)
                    {
                        return;
                    }
                    int VisibleItemCount = productByGroupLayoutManager.getChildCount();
                    int TotalItemCount = productByGroupLayoutManager.getItemCount();
                    int PastVisibleItem = productByGroupLayoutManager.findFirstVisibleItemPosition();
                    if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                    {
                        isLoading = true;
                        getProductByGroupId(productGroupId,productByGroupAdapter.getItemCount()+10);
                    }
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }


    private void getProductByGroupId(int productGroupId,int offsetNumber)
    {
        Loading.show();
        productModule.setOnGetProductByGroupIdListener(new OnGetProductByGroupIdListener()
        {
            @Override
            public void OnGetProductByGroupId(ArrayList<ProductDataModel> productList)
            {
                Loading.hide();
                productByGroupAdapter.addToProductList(productList);
                isLoading=false;
            }
        });
        productModule.getProductByGroupId(productGroupId, offsetNumber, 10);
    }


}

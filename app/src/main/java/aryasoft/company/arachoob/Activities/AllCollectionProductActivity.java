package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.ProductCollectionAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetSectionedCollectionDataListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.CollectionModule;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.ItemDecorationRecycler;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.RecyclerInstaller;
import aryasoft.company.arachoob.Utils.SweetLoading;
import aryasoft.company.arachoob.Utils.VectorDrawablePreLollipopHelper;
import aryasoft.company.arachoob.Utils.VectorView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AllCollectionProductActivity extends AppCompatActivity implements OnDataReceiveStateListener
{

    private RecyclerView RecyclerMore;
    private int sectionId;
    private int collectionId;
    private String collectionTitle;
    private CollectionModule collectionModule;
    private SweetLoading Loading;
    private GridLayoutManager recyclerGridLayoutManager;
    private ProductCollectionAdapter recyclerProductCollectionAdapter;
    private boolean IsLoading = false;

    @Override
    public void OnDataReceiveState(Throwable ex)
    {
        IsLoading = false;
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
        setContentView(R.layout.activity_all_collection_product);
        initVariables();
        initializeViews();
        setupToolbar();
        initializeEvents();
        getProductBySection(sectionId, collectionId, recyclerProductCollectionAdapter.getItemCount(), 20);
    }


    private void initVariables()
    {
        sectionId = getIntent().getIntExtra("sectionId", 0);
        collectionId = getIntent().getIntExtra("collectionId", 0);
        collectionTitle = getIntent().getStringExtra("collectionTitle");
        collectionModule = new CollectionModule();
        collectionModule.setOnDataReceiveStateListener(this);
    }

    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbarMore);
        TextView txtToolbarTitle =toolbar.findViewById(R.id.txtToolbarTitleAllCollectionProduct);
        VectorDrawablePreLollipopHelper.SetVectors(getResources(), new VectorView(R.drawable.ic_more, txtToolbarTitle, VectorDrawablePreLollipopHelper.MyDirType.end));
        setSupportActionBar(toolbar);
        txtToolbarTitle.setText(collectionTitle);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initializeViews()
    {
        Loading = new SweetLoading.Builder().build(this);
        RecyclerMore = findViewById(R.id.recyclerMore);
        recyclerGridLayoutManager = new GridLayoutManager(this, 2);
        recyclerProductCollectionAdapter = new ProductCollectionAdapter(this);
        RecyclerMore.setLayoutManager(recyclerGridLayoutManager);
        RecyclerMore.addItemDecoration(new ItemDecorationRecycler(2, 8, true));
        RecyclerMore.setAdapter(recyclerProductCollectionAdapter);
    }


    private void initializeEvents()
    {
        collectionModule.setOnGetSectionedCollectionDataListener(new OnGetSectionedCollectionDataListener()
        {
            @Override
            public void OnGetSectionedCollectionData(ArrayList<ProductDataModel> sectionedCollectionList)
            {
                recyclerProductCollectionAdapter.addToProductsList(sectionedCollectionList);
                IsLoading = false;
                Loading.hide();
            }
        });

        RecyclerMore.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (recyclerProductCollectionAdapter.getItemCount() >= 10)
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
                        IsLoading = true;
                        getProductBySection(sectionId, collectionId, recyclerProductCollectionAdapter.getItemCount(), 20);
                    }
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    private void getProductBySection(int sectionId, int collectionId, int offsetItem, int takeItem)
    {
        Loading.show();
        if (sectionId == 1)
        {
            //load discounts
            collectionModule.getDiscountBasketById(collectionId, offsetItem, takeItem);

        }
        else if (sectionId == 2)
        {
            //load topseals
            collectionModule.getTopSeals(offsetItem, takeItem);
        }
        else if (sectionId == 3)
        {
            //load collections
            collectionModule.getCollectionById(collectionId, offsetItem, takeItem);
        }
    }

}

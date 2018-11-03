package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.ProductAdapter;
import aryasoft.company.arachoob.Models.Product;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.RecyclerInstaller;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AllCollectionProductActivity extends AppCompatActivity {

    private RecyclerView RecyclerMore;
    private Bundle ProductBundle;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_collection_product);
        initializeViews();
        setupToolbar();
        setupRecyclerView();
    }

    private void initializeViews()
    {
        RecyclerMore = findViewById(R.id.recyclerMore);
        ProductBundle = getIntent().getExtras();
    }

    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbarMore);
        toolbar.setTitle(ProductBundle.getString("collectionTitle"));
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView()
    {
        ArrayList<Product> products = (ArrayList<Product>) ProductBundle.getSerializable("productsList");
        ProductAdapter productAdapter = new ProductAdapter(products, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //RecyclerMore.setLayoutManager(gridLayoutManager);
        //RecyclerMore.setAdapter(productAdapter);

        RecyclerInstaller recyclerInstaller = RecyclerInstaller.build();
        recyclerInstaller
                .setAdapter(productAdapter)
                .setLayoutManager(gridLayoutManager)
                .setRecyclerView(RecyclerMore)
                .setup();
    }

}

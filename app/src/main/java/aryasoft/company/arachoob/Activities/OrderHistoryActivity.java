package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import aryasoft.company.arachoob.Adapters.OrderHistoryAdapter;
import aryasoft.company.arachoob.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderHistoryActivity extends AppCompatActivity
{
    private RecyclerView recyclerOrderHistory;
    private OrderHistoryAdapter recyclerOrderHistoryAdapter;
    private LinearLayoutManager recyclerOrderLayoutManager;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        initViews();
        initEvents();
    }


    private void initViews()
    {
        recyclerOrderHistory = findViewById(R.id.recyclerOrderHistory);
        //--
        recyclerOrderHistoryAdapter = new OrderHistoryAdapter(this);
        recyclerOrderLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerOrderHistory.setLayoutManager(recyclerOrderLayoutManager);
        recyclerOrderHistory.setAdapter(recyclerOrderHistoryAdapter);
    }

    private void initEvents()
    {

    }

}

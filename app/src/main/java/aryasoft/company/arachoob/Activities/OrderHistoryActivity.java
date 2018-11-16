package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.OrderHistoryAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetUserOrderListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.OrderModule;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.SweetLoading;
import aryasoft.company.arachoob.Utils.UserPreference;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderHistoryActivity extends AppCompatActivity
{
    private RecyclerView recyclerOrderHistory;
    private OrderHistoryAdapter recyclerOrderHistoryAdapter;
    private LinearLayoutManager recyclerOrderLayoutManager;
    private SweetLoading Loading;
    private OrderModule orderModule;
    private boolean IsLoading = false;

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
        Loading = new SweetLoading.Builder().build(this);
        orderModule = new OrderModule();
        recyclerOrderHistory = findViewById(R.id.recyclerOrderHistory);
        recyclerOrderHistoryAdapter = new OrderHistoryAdapter(this);
        recyclerOrderLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerOrderHistory.setLayoutManager(recyclerOrderLayoutManager);
        recyclerOrderHistory.setAdapter(recyclerOrderHistoryAdapter);
    }

    private void initEvents()
    {
        orderModule.setOnDataReceiveStateListener(new OnDataReceiveStateListener()
        {
            @Override
            public void OnDataReceiveState(Throwable ex)
            {
                Loading.hide();
                new CuteToast.Builder(OrderHistoryActivity.this).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();
                IsLoading=false;
            }
        });
        getUserOrders(recyclerOrderHistoryAdapter.getItemCount());
        loadMoreOrders();



    }

    private void getUserOrders(int offsetItem)
    {
        Loading.show();
        orderModule.setOnGetUserOrderListener(new OnGetUserOrderListener()
        {
            @Override
            public void OnGetUserOrder(ArrayList<GetUserOrderApiModel> userOrdersList)
            {
                recyclerOrderHistoryAdapter.addToOrderHistoryList(userOrdersList);
                Loading.hide();
                IsLoading=false;
            }
        });
        orderModule.getUserOrder(UserPreference.getUserId(),offsetItem, 10);
    }


    private void loadMoreOrders()
    {
        recyclerOrderHistory.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                if (recyclerOrderHistoryAdapter.getItemCount() >= 10)
                {
                    if (IsLoading)
                    {
                        return;
                    }
                    int VisibleItemCount = recyclerOrderLayoutManager.getChildCount();
                    int TotalItemCount = recyclerOrderLayoutManager.getItemCount();
                    int PastVisibleItem = recyclerOrderLayoutManager.findFirstVisibleItemPosition();
                    if ((VisibleItemCount + PastVisibleItem) >= TotalItemCount)
                    {
                        IsLoading = true;
                        getUserOrders(recyclerOrderHistoryAdapter.getItemCount());
                    }
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

}

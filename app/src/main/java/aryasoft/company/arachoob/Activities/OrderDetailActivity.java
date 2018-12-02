package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.OrderDetailAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetUserDetailListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.GetUserOrderDetail;
import aryasoft.company.arachoob.ApiConnection.ApiModules.OrderModule;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.SweetLoading;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderDetailActivity extends AppCompatActivity
{
    private TextView txtOrderDetailTotalPrice;
    private ListView listOderDetail;
    private OrderDetailAdapter listOrderDetailAdapter;
    private OrderModule orderModule;
    private SweetLoading Loading;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initViews();
    }

    private void initViews()
    {
        listOderDetail = findViewById(R.id.listOderDetail);
        txtOrderDetailTotalPrice = findViewById(R.id.txtOrderDetailTotalPrice);
        listOrderDetailAdapter = new OrderDetailAdapter(this);
        listOderDetail.setAdapter(listOrderDetailAdapter);

        Loading = new SweetLoading.Builder().build(this);
        int orderId = getIntent().getIntExtra("orderId", 0);
        int totalPrice = getIntent().getIntExtra("totalPrice", 0);
        orderModule = new OrderModule();
        orderModule.setOnDataReceiveStateListener(new OnDataReceiveStateListener()
        {
            @Override
            public void OnDataReceiveState(Throwable ex)
            {
                Loading.hide();
                new CuteToast.Builder(OrderDetailActivity.this).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();
            }
        });
        getUserOrderDetail(orderId,totalPrice);
    }

    private void getUserOrderDetail(int orderId,final int totalPrice)
    {
        Loading.show();
        orderModule.setOnGetUserDetailListener(new OnGetUserDetailListener()
        {
            @Override
            public void OnGetUserDetail(ArrayList<GetUserOrderDetail> userOrderDetailList)
            {

                listOrderDetailAdapter.addOrderDetailList(userOrderDetailList);
                txtOrderDetailTotalPrice.setText("جمع سفارشات : "+totalPrice+" تومان ");
                Loading.hide();
            }
        });
        orderModule.getUserOrderDetail(orderId);
    }
}

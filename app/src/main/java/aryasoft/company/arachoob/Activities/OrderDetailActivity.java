package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import aryasoft.company.arachoob.Adapters.OrderDetailAdapter;
import aryasoft.company.arachoob.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderDetailActivity extends AppCompatActivity
{

    private ListView listOderDetail;
    private OrderDetailAdapter listOrderDetailAdapter;

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
        listOderDetail=findViewById(R.id.listOderDetail);
        listOrderDetailAdapter=new OrderDetailAdapter(this);
        listOderDetail.setAdapter(listOrderDetailAdapter);
    }
}

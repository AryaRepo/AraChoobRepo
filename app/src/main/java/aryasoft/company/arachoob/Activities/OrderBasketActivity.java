package aryasoft.company.arachoob.Activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import aryasoft.company.arachoob.Adapters.OrderBasketAdapter;
import aryasoft.company.arachoob.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderBasketActivity extends AppCompatActivity
{
    //------bottomSheet
    private ImageButton btnCloseInvoicePanel;
    private TextView txtProductCountInvoicePanel;
    private TextView txtProductPriceInvoicePanel;
    //--
    private RecyclerView recyclerOrderBasket;
    private OrderBasketAdapter recyclerOrderBasketAdapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton btnOpenInvoice;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        recyclerOrderBasketAdapter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        recyclerOrderBasketAdapter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //----------------
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
        {
            closeInvoicePanel();
        }
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_basket);
        initViews();
        initEvents();
    }

    private void initViews()
    {
        btnOpenInvoice = findViewById(R.id.btnOpenInvoice);
        btnCloseInvoicePanel = findViewById(R.id.btnCloseInvoicePanel);
        txtProductCountInvoicePanel = findViewById(R.id.txtProductCountInvoicePanel);
        txtProductPriceInvoicePanel = findViewById(R.id.txtProductPriceInvoicePanel);
        recyclerOrderBasket = findViewById(R.id.recyclerOrderBasket);
        final LinearLayout bottomSheet = findViewById(R.id.bottom_sheet2);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        recyclerOrderBasketAdapter = new OrderBasketAdapter(this);
        recyclerOrderBasket.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerOrderBasket.setAdapter(recyclerOrderBasketAdapter);

    }

    private void initEvents()
    {
        btnOpenInvoice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                {
                    return;
                }
                openInvoicePanel();
            }
        });
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState)
            {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                {
                    btnOpenInvoice.show();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset)
            {
            }
        });
        recyclerOrderBasket.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(final @NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING)
                {
                    btnOpenInvoice.hide();
                }
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE)
                                {
                                    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                                    {
                                        return;
                                    }
                                    btnOpenInvoice.show();
                                }
                            }
                        });

                    }
                }, 500);

            }
        });
        btnCloseInvoicePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                closeInvoicePanel();
            }
        });
    }

    private void openInvoicePanel()
    {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        btnOpenInvoice.hide();
    }

    private void closeInvoicePanel()
    {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        btnOpenInvoice.show();
    }
}

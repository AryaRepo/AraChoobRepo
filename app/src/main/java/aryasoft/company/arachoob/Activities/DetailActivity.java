package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import aryasoft.company.arachoob.Adapters.DetailSliderAdapter;
import aryasoft.company.arachoob.Adapters.DetailTabViewPagerAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnFetchProductDetailInfoListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDetailInfoApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.ProductDetailInfoModule;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.SharedPreferencesHelper;
import aryasoft.company.arachoob.Utils.ShoppingCartManger;
import aryasoft.company.arachoob.Utils.SweetLoading;
import aryasoft.company.arachoob.Utils.VectorDrawablePreLollipopHelper;
import aryasoft.company.arachoob.Utils.VectorView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends AppCompatActivity implements OnDataReceiveStateListener
{
    private static int currentPage = 0;
    //------------
    private int productId;
    private String productTitle;
    private String productImage;
    private int primaryPrice;
    private int discountPercent;
    private int productCount;
    //-----------------
    private TextView txtProductTitle;
    private TextView txtPrimaryPrice;
    private TextView  txtProductGroupTitle;
    private TextView txtSecondaryPrice;
    private TextView txtUnitTitle;
    private Button btnAddToOrderBasket;
    private ViewPager detailGallerySlider;
    private TabLayout detailTabLayout;
    private ViewPager detailTabViewPager;
    private ProductDetailInfoModule detailInfoModule;
    private DetailSliderAdapter detailSliderAdapter;
    private SweetLoading Loading;

    @Override
    public void OnDataReceiveState(Throwable ex)
    {
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
        setContentView(R.layout.activity_detail);
        initVariables();
        initViews();
        initEvents();
    }

    private void initVariables()
    {
        detailInfoModule = new ProductDetailInfoModule();
        detailInfoModule.setOnDataReceiveStateListener(this);
        productId = getIntent().getIntExtra("productId", 0);
        productTitle = getIntent().getStringExtra("productTitle");
        productImage = getIntent().getStringExtra("productImage");
        primaryPrice = getIntent().getIntExtra("primaryPrice", 0);
        productCount = getIntent().getIntExtra("productCount", 0);
        Loading = new SweetLoading.Builder().build(this);
    }

    private void initViews()
    {
        txtProductTitle = findViewById(R.id.txtProductTitle);
        txtPrimaryPrice = findViewById(R.id.txtPrimaryPrice);
        txtSecondaryPrice = findViewById(R.id.txtSecondaryPrice);
        txtProductGroupTitle = findViewById(R.id.txtProductGroupTitle);
        txtUnitTitle = findViewById(R.id.txtUnitTitle);
        btnAddToOrderBasket = findViewById(R.id.btnAddToOrderBasket);
        VectorDrawablePreLollipopHelper.SetVectors(getResources(), new VectorView(R.drawable.ic_myshopping_basket, btnAddToOrderBasket, VectorDrawablePreLollipopHelper.MyDirType.end));
        detailGallerySlider = findViewById(R.id.detailGallerySlider);
        detailTabLayout = findViewById(R.id.detailTabLayout);
        detailTabViewPager = findViewById(R.id.detailTabViewPager);
        detailSliderAdapter = new DetailSliderAdapter(this);
        detailGallerySlider.setAdapter(detailSliderAdapter);
        txtProductTitle.setText(productTitle);
        txtPrimaryPrice.setText(" قیمت پایه " + primaryPrice + " " + "تومان");

    }

    private void initEvents()
    {
        getDetailInfo();
        btnAddToOrderBasket.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(productCount<=0)
                {
                    new CuteToast.Builder(DetailActivity.this).setText("کالا موجود نیست !").setDuration(Toast.LENGTH_LONG).show();
                    return;
                }
                if (!ShoppingCartManger.AddProductToCart(productId))
                {
                    new CuteToast.Builder(DetailActivity.this).setText("محصول به سبد سفارش اضافه گردید.").setDuration(Toast.LENGTH_LONG).show();
                }
                else
                {
                    new CuteToast.Builder(DetailActivity.this).setText("این محصول قبلا به سبداضافه شده!").setDuration(Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setupSliderSlides(final int sliderSize)
    {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable()
        {
            public void run()
            {
                if (currentPage == sliderSize)
                {
                    currentPage = 0;
                }
                detailGallerySlider.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(Update);
            }
        }, 3000, 3000);
    }


    private void getDetailInfo()
    {
        detailInfoModule.setOnFetchProductDetailInfoListener(new OnFetchProductDetailInfoListener()
        {
            @Override
            public void OnFetchProductDetailInfo(ProductDetailInfoApiModel detailInfo)
            {
                txtProductGroupTitle.setText(" دسته بندی : "+detailInfo.ProductGroup);
                discountPercent = detailInfo.DiscountPercent;
                if (discountPercent == 0)
                {
                    txtSecondaryPrice.setVisibility(View.GONE);
                }
                else
                {
                    txtSecondaryPrice.setText("قیمت با تخفیف" + calculateDiscount(primaryPrice, discountPercent) + " تومان ");
                }
                txtUnitTitle.setText("واحد کالا " + detailInfo.UnitTitle + " " + "می باشد");
                detailTabViewPager.setAdapter(new DetailTabViewPagerAdapter(DetailActivity.this, getSupportFragmentManager(), detailInfo));
                detailTabLayout.setupWithViewPager(detailTabViewPager);
                detailTabViewPager.setCurrentItem(2);
                changeTabsFont();
                detailSliderAdapter.addSliderImage(productImage, detailInfo.ImageList);
                setupSliderSlides(detailSliderAdapter.getCount());
                Loading.hide();
            }
        });
        detailInfoModule.getProductInfoById(productId);
        Loading.show();
    }

    private int calculateDiscount(int salesPrice, int discountPercent)
    {
        int finalPrice;
        finalPrice = (salesPrice * discountPercent) / 100;
        return salesPrice - finalPrice;
    }

    private void changeTabsFont()
    {
        ViewGroup vg = (ViewGroup) detailTabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++)
        {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildesCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildesCount; i++)
            {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView)
                {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/iran_yekan_mobile_regular.ttf"), Typeface.NORMAL);
                }
            }
        }
    }
}

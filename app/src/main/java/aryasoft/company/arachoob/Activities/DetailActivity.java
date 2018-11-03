package aryasoft.company.arachoob.Activities;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import java.util.Timer;
import java.util.TimerTask;

import aryasoft.company.arachoob.Adapters.DetailSliderAdapter;
import aryasoft.company.arachoob.Adapters.DetailTabViewPagerAdapter;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.VectorDrawablePreLollipopHelper;
import aryasoft.company.arachoob.Utils.VectorView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends AppCompatActivity
{
    private Button btnAddToOrderBasket;
    private ViewPager detailGallerySlider;
    private TabLayout detailTabLayout;
    private ViewPager detailTabViewPager;
    private static int currentPage = 0;

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
        initViews();
    }

    private void initViews()
    {
        btnAddToOrderBasket=findViewById(R.id.btnAddToOrderBasket);
        VectorDrawablePreLollipopHelper.SetVectors(getResources(), new VectorView(R.drawable.ic_myshopping_basket, btnAddToOrderBasket, VectorDrawablePreLollipopHelper.MyDirType.end));
        detailGallerySlider = findViewById(R.id.detailGallerySlider);
        detailTabLayout= findViewById(R.id.detailTabLayout);
        detailTabViewPager= findViewById(R.id.detailTabViewPager);
        detailTabViewPager.setAdapter(new DetailTabViewPagerAdapter(this,getSupportFragmentManager()));
        detailTabLayout.setupWithViewPager(detailTabViewPager);
        detailTabViewPager.setCurrentItem(2);
        //-------------------------------------
        final int[] slides = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3};
        detailGallerySlider.setAdapter(new DetailSliderAdapter(this, slides));
        final Handler handler = new Handler();
        final Runnable Update = new Runnable()
        {
            public void run()
            {
                if (currentPage == slides.length)
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
}

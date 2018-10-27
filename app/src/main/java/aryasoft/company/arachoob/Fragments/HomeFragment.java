package aryasoft.company.arachoob.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import aryasoft.company.arachoob.Adapters.CollectionsAdapter;
import aryasoft.company.arachoob.Adapters.SliderPagerAdapter;
import aryasoft.company.arachoob.Models.Collection;
import aryasoft.company.arachoob.Models.Product;
import aryasoft.company.arachoob.Models.Slide;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Transformers.SimpleCardsPagerTransformer;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private Handler SlideHandler;
    private ViewPager PagerSlider;
    private int CurrentPage = 0;
    private CircleIndicator SliderIndicator;
    private RecyclerView RecyclerProducts;
    private ImageView ImgHomeBg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initializeViews(view);
        setupSlider();
        setupProductsRecycler();
    }


    private void initializeViews(View view)
    {
        PagerSlider = view.findViewById(R.id.viewPagerSlider);
        SliderIndicator = view.findViewById(R.id.sliderIndicatorLand);
        RecyclerProducts = view.findViewById(R.id.recyclerProducts);
        ImgHomeBg = view.findViewById(R.id.imgHomeBg);
        Glide.with(getContext()).load(R.drawable.simple_pattern).into(ImgHomeBg);
    }

    private void setupSlider()
    {
        ArrayList<Slide> slides = new ArrayList<>();
        slides.add(new Slide(R.drawable.img1));
        slides.add(new Slide(R.drawable.img2));
        slides.add(new Slide(R.drawable.img3));

        SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter(getChildFragmentManager(), slides);

        PagerSlider.setClipToPadding(false);
        PagerSlider.setAdapter(sliderPagerAdapter);
        PagerSlider.setPageTransformer(false, new SimpleCardsPagerTransformer());
        SliderIndicator.setViewPager(PagerSlider);

        SlideHandler = new Handler();
        SlideHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                changeSliderPage();
            }
        });
    }

    private void changeSliderPage()
    {
        if (CurrentPage == PagerSlider.getAdapter().getCount())
        {
            CurrentPage = 0;
        }
        PagerSlider.setCurrentItem(CurrentPage++, true);
        SlideHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                changeSliderPage();
            }
        }, 4000);
    }

    private void setupProductsRecycler()
    {
        CollectionsAdapter collectionsAdapter  = new CollectionsAdapter(createFakeData(), getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerProducts.setLayoutManager(linearLayoutManager);
        RecyclerProducts.setAdapter(collectionsAdapter);
    }

    private ArrayList<Collection> createFakeData() {
        ArrayList<Product> products1 = new ArrayList<>();
        products1.add(new Product(R.drawable.img1, "A10a", "1000000 تومان", "با تخفیف 950000 تومان",""));
        products1.add(new Product(R.drawable.img2, "A10b", "2000000 تومان", "با تخفیف 1950000 تومان",""));
        products1.add(new Product(R.drawable.img3, "A10c", "1500000 تومان", "با تخفیف 9590000 تومان",""));
        products1.add(new Product(R.drawable.img1, "A10d", "1100000 تومان", "با تخفیف 800000 تومان",""));
        products1.add(new Product(R.drawable.img2, "A10e", "1050000 تومان", "با تخفیف 750000 تومان",""));

        ArrayList<Product> products2 = new ArrayList<>();
        products2.add(new Product(R.drawable.img3, "B10a", "1000000 تومان", "با تخفیف 950000 تومان",""));
        products2.add(new Product(R.drawable.img1, "B10b", "2000000 تومان", "با تخفیف 1950000 تومان",""));
        products2.add(new Product(R.drawable.img2, "B10c", "1500000 تومان", "با تخفیف 9590000 تومان",""));

        ArrayList<Product> products3 = new ArrayList<>();
        products3.add(new Product(R.drawable.img1, "C10a", "1000000 تومان", "با تخفیف 950000 تومان",""));
        products3.add(new Product(R.drawable.img2, "C10b", "2000000 تومان", "با تخفیف 1950000 تومان",""));
        products3.add(new Product(R.drawable.img3, "C10c", "1500000 تومان", "با تخفیف 9590000 تومان",""));
        products3.add(new Product(R.drawable.img1, "C10d", "1100000 تومان", "با تخفیف 800000 تومان",""));
        products3.add(new Product(R.drawable.img2, "C10e", "1050000 تومان", "با تخفیف 750000 تومان",""));
        products3.add(new Product(R.drawable.img3, "C10f", "1000000 تومان", "با تخفیف 950000 تومان",""));
        products3.add(new Product(R.drawable.img2, "C10g", "2000000 تومان", "با تخفیف 1950000 تومان",""));
        products3.add(new Product(R.drawable.img1, "C10h", "1500000 تومان", "با تخفیف 9590000 تومان",""));

        ArrayList<Product> products4 = new ArrayList<>();
        products4.add(new Product(R.drawable.img3, "D10a", "1080000 تومان", "با تخفیف 950000 تومان",""));
        products4.add(new Product(R.drawable.img2, "D10b", "1600000 تومان", "با تخفیف 1590000 تومان",""));

        ArrayList<Product> products5 = new ArrayList<>();
        products5.add(new Product(R.drawable.img2, "E10a", "2600000 تومان", "با تخفیف 2390000 تومان",""));

        ArrayList<Collection> collections = new ArrayList<>();
        collections.add(new Collection("پر فروش ترین ها", products1));
        collections.add(new Collection("تخفیف خرده ها", products2));
        collections.add(new Collection("محبوب ترین ها", products3));
        collections.add(new Collection("تزیینی", products4));
        collections.add(new Collection("لاکچری", products5));

        return collections;

    }

}

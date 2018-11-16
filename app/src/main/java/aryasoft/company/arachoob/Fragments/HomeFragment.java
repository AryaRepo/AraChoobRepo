package aryasoft.company.arachoob.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.CollectionsAdapter;
import aryasoft.company.arachoob.Adapters.SliderPagerAdapter;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnFetchCollectionListener;
import aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners.OnGetSliderListener;
import aryasoft.company.arachoob.ApiConnection.ApiModels.CollectionDataModel;
import aryasoft.company.arachoob.ApiConnection.ApiModels.SliderApiModel;
import aryasoft.company.arachoob.ApiConnection.ApiModules.ProductModule;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Transformers.SimpleCardsPagerTransformer;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Listeners.OnDataReceiveStateListener;
import aryasoft.company.arachoob.Utils.SweetLoading;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements OnDataReceiveStateListener
{

    private Handler SlideHandler;
    private ViewPager PagerSlider;
    private int CurrentPage = 0;
    private CircleIndicator SliderIndicator;
    private RecyclerView RecyclerProducts;
    private ImageView ImgHomeBg;
    private SweetLoading Loading;
    private ProductModule productModule;
    private CollectionsAdapter collectionsAdapter;
    private Context context;
    //------------------------------------------------------

    @Override
    public void OnDataReceiveState(Throwable ex)
    {
        Loading.hide();
        new CuteToast.Builder(getActivity()).setText(getString(R.string.noInternetText)).setDuration(Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        context = view.getContext();
        productModule = new ProductModule();
        productModule.setOnDataReceiveStateListener(this);
        initializeViews(view);
        setupSlider();
    }


    private void initializeViews(View view)
    {
        Loading = new SweetLoading.Builder().build(view.getContext());
        PagerSlider = view.findViewById(R.id.viewPagerSlider);
        SliderIndicator = view.findViewById(R.id.sliderIndicatorLand);
        RecyclerProducts = view.findViewById(R.id.recyclerProducts);
        ImgHomeBg = view.findViewById(R.id.imgHomeBg);
        Glide.with(view.getContext()).load(R.drawable.simple_pattern).into(ImgHomeBg);
    }

    private void setupSlider()
    {
        Loading.show();
        final SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter(context);
        PagerSlider.setClipToPadding(false);
        PagerSlider.setAdapter(sliderPagerAdapter);
        PagerSlider.setPageTransformer(false, new SimpleCardsPagerTransformer());
        SliderIndicator.setViewPager(PagerSlider);
        productModule.setOnGetSliderListener(new OnGetSliderListener()
        {
            @Override
            public void OnGetSlider(ArrayList<SliderApiModel> sliderList)
            {
                sliderPagerAdapter.addSlidesList(sliderList);
                getProductsCollections();
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
        });
        productModule.getSliders();
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

    private void getProductsCollections()
    {
        collectionsAdapter = new CollectionsAdapter(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerProducts.setLayoutManager(linearLayoutManager);
        RecyclerProducts.setAdapter(collectionsAdapter);
        //---------------------------------------------------------
        productModule.setOnFetchCollectionListener(new OnFetchCollectionListener()
        {
            @Override
            public void OnFetchCollection(ArrayList<CollectionDataModel> totalCollectionDataModels)
            {
                collectionsAdapter.addCollections(totalCollectionDataModels);
                Loading.hide();
            }
        });
        productModule.getCollections();
    }


}

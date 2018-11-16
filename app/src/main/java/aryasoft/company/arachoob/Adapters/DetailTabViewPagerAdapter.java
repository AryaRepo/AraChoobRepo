package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import aryasoft.company.arachoob.ApiConnection.ApiModels.ProductDetailInfoApiModel;
import aryasoft.company.arachoob.Fragments.CommentsTabFragment;
import aryasoft.company.arachoob.Fragments.DescriptionTabFragment;
import aryasoft.company.arachoob.Fragments.SimilarProductsTabFragment;

public class DetailTabViewPagerAdapter extends FragmentPagerAdapter
{
    private Context context;
    private final String[] tabTitle = new String[]{"نظرات", "توضیحات", "محصولات مشابه"};
    private ProductDetailInfoApiModel detailInfo;

    public DetailTabViewPagerAdapter(Context context, FragmentManager fragmentManager, ProductDetailInfoApiModel detailInfo)
    {
        super(fragmentManager);
        this.context = context;
        this.detailInfo = detailInfo;
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment targetFragment = new Fragment();
        switch (position)
        {
            case 0:
                targetFragment = new CommentsTabFragment(detailInfo.ProductId, detailInfo.ProductComments);
                break;
            case 1:
                targetFragment = new DescriptionTabFragment(detailInfo.SummeryDescription + "\n\n" + detailInfo.Description);
                break;
            case 2:
                targetFragment = new SimilarProductsTabFragment(detailInfo.SimilarProduct);
                break;
        }
        return targetFragment;
    }

    @Override
    public int getCount()
    {
        return tabTitle.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitle[position];
    }
}


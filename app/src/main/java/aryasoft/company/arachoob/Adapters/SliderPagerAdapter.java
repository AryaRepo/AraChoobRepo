package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.SliderApiModel;
import aryasoft.company.arachoob.R;

public class SliderPagerAdapter extends PagerAdapter
{
    private ArrayList<SliderApiModel> slidesList;
    private Context context;
    private LayoutInflater inflater;

    public SliderPagerAdapter(Context context)
    {
        this.context = context;
        slidesList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void addSlidesList(ArrayList<SliderApiModel> slidesList)
    {
        this.slidesList.addAll(slidesList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return slidesList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View imageLayout = inflater.inflate(R.layout.slider_item_layout, container, false);
        ImageView imgSlider = imageLayout.findViewById(R.id.imgSlider);
        if (slidesList.size() != 0)
        {
            if (slidesList.get(position).ImageName!=null)
            {
                Glide.with(context).load(context.getString(R.string.BaseUrl) + context.getString(R.string.SliderImageFolder) + slidesList.get(position).ImageName).into(imgSlider);
            }
            else
            {
                Glide.with(context).load(R.drawable.no_img).into(imgSlider);
            }

            container.addView(imageLayout);
        }
        return imageLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o)
    {
        return view == o;
    }

}

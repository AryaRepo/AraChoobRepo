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

import aryasoft.company.arachoob.R;

public class DetailSliderAdapter extends PagerAdapter
{
    private LayoutInflater inflater;
    private ArrayList<String> sliderImages;
    private Context context;

    public DetailSliderAdapter(Context context)
    {
        this.context = context;
        this.sliderImages = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void addSliderImage(String primaryImage, ArrayList<String> sliderImages)
    {
        this.sliderImages.add(primaryImage);
        this.sliderImages.addAll(sliderImages);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return sliderImages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View imageLayout = inflater.inflate(R.layout.slider_item_layout, container, false);
        ImageView imgSlider = imageLayout.findViewById(R.id.imgSlider);
        if (sliderImages.size() != 0)
        {
            if (position == 0)
            {
                if (sliderImages.get(position)!=null)
                {
                    Glide.with(context).load(context.getString(R.string.BaseUrl) + context.getString(R.string.ProductImageFolder) + sliderImages.get(position)).into(imgSlider);
                }
                else
                {
                    Glide.with(context).load(R.drawable.no_img).into(imgSlider);
                }
            }

            else
            {
                Glide.with(context).load(context.getString(R.string.BaseUrl) + context.getString(R.string.GalleryImageFolder) + sliderImages.get(position)).into(imgSlider);
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

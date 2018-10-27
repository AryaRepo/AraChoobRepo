package aryasoft.company.arachoob.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import aryasoft.company.arachoob.R;

public class DetailSliderAdapter extends PagerAdapter
{
    private LayoutInflater inflater;
  //  private String[] sliderImages;
    private int[] sliderImages;
    private Context context;

    public DetailSliderAdapter(Context context, int[] sliderImages)
    {
        this.context = context;
        this.sliderImages = sliderImages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return sliderImages.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View imageLayout = inflater.inflate(R.layout.detail_gallery_item_layout, container, false);
        ImageView imgSlider = imageLayout.findViewById(R.id.imgDetailGalleryImage);
        //String imageUrl = context.getString(R.string.BaseUrl) + context.getString(R.string.DetailImageFolder) + sliderImages[position];
        Glide.with(context).load(sliderImages[position]).into(imgSlider);
        container.addView(imageLayout);
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

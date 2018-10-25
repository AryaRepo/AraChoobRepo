package aryasoft.company.arachoob.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import aryasoft.company.arachoob.Fragments.SlideFragment;
import aryasoft.company.arachoob.Models.Slide;

/**
 * Created by MohamadAmin on 2/8/2018.
 */

public class SliderPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Slide> SlidesList;

    public SliderPagerAdapter(FragmentManager fm, ArrayList<Slide> slidesList) {
        super(fm);
        SlidesList = slidesList;
    }

    @Override
    public Fragment getItem(int position) {
        return new SlideFragment().newInstance(SlidesList.get(position).getSlidePhotoName());
    }

    @Override
    public int getCount() {
        return SlidesList.size();
    }

}

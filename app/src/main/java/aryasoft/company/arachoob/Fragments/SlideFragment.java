package aryasoft.company.arachoob.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import aryasoft.company.arachoob.R;

/**
 * Created by MohamadAmin on 2/8/2018.
 */

public class SlideFragment extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_slide, container, false);
        ImageView imgSlide = view.findViewById(R.id.imgSlide);
        Glide.with(view.getContext()).load(getArguments().getInt("imageId")).into(imgSlide);
        return view;
    }

    public static SlideFragment newInstance(int imageId)
    {
        SlideFragment slideFragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putInt("imageId", imageId);
        slideFragment.setArguments(args);
        return slideFragment;
    }

    public static SlideFragment newInstance(String imageId)
    {
        SlideFragment slideFragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putString("imageId", imageId);
        slideFragment.setArguments(args);
        return slideFragment;
    }

}

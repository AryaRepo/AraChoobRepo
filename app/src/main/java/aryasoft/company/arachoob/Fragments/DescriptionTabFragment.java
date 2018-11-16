package aryasoft.company.arachoob.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import aryasoft.company.arachoob.R;


public class DescriptionTabFragment extends Fragment
{
    private TextView txtDetailDescription;
    private String description;

    public DescriptionTabFragment()
    {

    }

    public DescriptionTabFragment(String description)
    {
        this.description = description;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_description_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        txtDetailDescription = view.findViewById(R.id.txtDetailDescription);
        txtDetailDescription.setText(description);

    }
}

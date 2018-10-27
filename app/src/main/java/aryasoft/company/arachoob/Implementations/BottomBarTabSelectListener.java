package aryasoft.company.arachoob.Implementations;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import aryasoft.company.arachoob.Activities.LandActivity;
import aryasoft.company.arachoob.Fragments.CategoryFragment;
import aryasoft.company.arachoob.Fragments.HomeFragment;
import aryasoft.company.arachoob.Fragments.SearchFragment;
import aryasoft.company.arachoob.R;


public class BottomBarTabSelectListener implements AHBottomNavigation.OnTabSelectedListener {

    private LandActivity LandActivityInstance;
    public static final int TAB_CATEGORY = 0;
    public static final int TAB_SEARCH = 2;
    public static final int TAB_HOME = 1;

    public BottomBarTabSelectListener(LandActivity landActivityInstance) {
        LandActivityInstance = landActivityInstance;
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (!wasSelected)
            showTabContent(position);
        return true;
    }

    private void showTabContent(int tabId) {
        if (tabId == TAB_CATEGORY) {
            replaceFragment(new CategoryFragment());
            showActionBar(true);
        } else if (tabId == TAB_SEARCH) {
            replaceFragment(new SearchFragment());
            showActionBar(false);
        } else if (tabId == TAB_HOME) {
            replaceFragment(new HomeFragment());
            showActionBar(true);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                LandActivityInstance.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.zoom_enter, R.anim.zoom_exit);
        fragmentTransaction.replace(R.id.main_placeholder, fragment);
        fragmentTransaction.commit();
    }

    private void showActionBar(boolean show)
    {
        try
        {
            if (show)
            {
                if (!LandActivityInstance.getSupportActionBar().isShowing())
                {
                    LandActivityInstance.getSupportActionBar().show();
                }
            }
            else
            {
                LandActivityInstance.getSupportActionBar().hide();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

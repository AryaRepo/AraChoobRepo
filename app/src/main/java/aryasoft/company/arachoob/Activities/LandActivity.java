package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;

import Implementations.BottomBarTabSelectListener;
import aryasoft.company.arachoob.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LandActivity extends AppCompatActivity implements View.OnClickListener {

    private AHBottomNavigation  BottomNavigation;
    private DrawerLayout Drawer;
    private TableRow RowLoginRegister;
    private TableRow RowManageProfile;
    private TableRow RowShareApp;
    private TableRow RowOrders;
    private TableRow RowContactUs;
    private TableRow RowMessages;
    private TableRow RowAboutUs;
    private TextView txtNavMenuTitle;
    private ImageView ImgDrawerBG;
    private ImageView ImgProfilePhoto;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId() )
        {
            case R.id.row_manage_profile:
                Intent profileManagementIntent = new Intent(LandActivity.this, ProfileActivity.class);
                startActivity(profileManagementIntent);
                break;
        }
        Drawer.closeDrawer(Gravity.END);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
        setupToolbar();
        initializeViews();
        setupMainBottomBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_nav_menu)
        {
            Drawer.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if (Drawer.isDrawerOpen(GravityCompat.END))
        {
            Drawer.closeDrawer(GravityCompat.END);
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.include_land_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void initializeViews()
    {
        BottomNavigation = findViewById(R.id.bottomNavLand);
        RowLoginRegister = findViewById(R.id.row_loginRegister);
        RowManageProfile = findViewById(R.id.row_manage_profile);
        RowShareApp = findViewById(R.id.row_share_app);
        RowOrders = findViewById(R.id.row_orders);
        RowMessages= findViewById(R.id.row_messages);
        RowContactUs = findViewById(R.id.row_contact_us);
        RowAboutUs = findViewById(R.id.row_about_us);

        RowLoginRegister.setOnClickListener(this);
        RowManageProfile.setOnClickListener(this);
        RowShareApp .setOnClickListener(this);
        RowOrders .setOnClickListener(this);
        RowMessages.setOnClickListener(this);
        RowContactUs.setOnClickListener(this);
        RowAboutUs.setOnClickListener(this);

        txtNavMenuTitle = findViewById(R.id.nav_menu_title_loginRegister);
        Drawer = findViewById(R.id.drawer_layout_land);
        ImgDrawerBG =findViewById(R.id.img_drawer_bg);
        ImgProfilePhoto =findViewById(R.id.imgProfileNavHeader);
        Glide.with(this).load(R.drawable.simple_pattern).into(ImgDrawerBG);
        Glide.with(this).load(R.drawable.user_profile).into(ImgProfilePhoto);
    }

    private void setupMainBottomBar()
    {
        AHBottomNavigationItem categoryTabItem = new AHBottomNavigationItem(R.string.tabTitleCategory, R.drawable.menu, R.color.bottomBarIconColor);
        AHBottomNavigationItem homeTabItem = new AHBottomNavigationItem(R.string.tabTitleHome, R.drawable.home, R.color.bottomBarIconColor);
        AHBottomNavigationItem searchTabItem = new AHBottomNavigationItem(R.string.tabTitleSearch, R.drawable.magnifier, R.color.bottomBarIconColor);
        BottomNavigation.addItem(categoryTabItem);
        BottomNavigation.addItem(homeTabItem);
        BottomNavigation.addItem(searchTabItem);
        BottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));
        BottomNavigation.setAccentColor(getResources().getColor(R.color.colorAccent));
        BottomNavigation.setInactiveColor(Color.parseColor("#BDBDBD"));
        BottomNavigation.setBehaviorTranslationEnabled(true);
        BottomNavigation.setForceTint(true);
        BottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        BottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        BottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        BottomNavigation.setOnTabSelectedListener(new BottomBarTabSelectListener(this));
        BottomNavigation.setCurrentItem(BottomBarTabSelectListener.TAB_HOME);
    }

}

package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import aryasoft.company.arachoob.BuildConfig;
import aryasoft.company.arachoob.Implementations.BottomBarTabSelectListener;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.SharedPreferencesHelper;
import aryasoft.company.arachoob.Utils.UserPreference;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LandActivity extends AppCompatActivity implements View.OnClickListener
{

    public static int CurrentPage = BottomBarTabSelectListener.TAB_HOME;
    private AHBottomNavigation BottomNavigation;
    private DrawerLayout Drawer;
    private TableRow RowLoginRegister;
    private TableRow RowManageProfile;
    private TableRow RowShareApp;
    private TableRow RowOrders;
    private TableRow RowContactUs;
    private TableRow RowMessages;
    private TableRow RowAboutUs;
    private TextView TxtWellcomeUser;
    private TextView TxtSignInOut;
    private ImageView ImgDrawerBG;
    private ImageView ImgProfilePhoto;
    private ImageButton BtnShowShoppingCart;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.row_manage_profile:
                showProfileManagement();
                break;
            case R.id.row_loginRegister:
                showSignInOrUp();
                break;
            case R.id.row_orders:
                showOrdersHistory();
                break;
            case R.id.row_messages:
                showTickets();
                break;
            case R.id.btn_show_shopping_cart:
                showOrders();
                break;
            case R.id.row_share_app:
                ShareApplication(this);
                break;
        }
        Drawer.closeDrawer(Gravity.END);
    }

    private void showProfileManagement()
    {
        Intent profileManagementIntent = new Intent(LandActivity.this, ProfileActivity.class);
        startActivity(profileManagementIntent);
    }

    private void showSignInOrUp()
    {
        if (UserPreference.isUserLogin())
        {
            signOut();
        }
        else
        {
            Intent signInOrUPIntent = new Intent(LandActivity.this, SignUpSignInActivity.class);
            startActivity(signInOrUPIntent);
        }
    }

    private void showOrdersHistory()
    {
        Intent ordersHistoryIntent = new Intent(LandActivity.this, OrderHistoryActivity.class);
        startActivity(ordersHistoryIntent);
    }

    private void showTickets()
    {
        Intent ticketsIntent = new Intent(LandActivity.this, TicketsActivity.class);
        startActivity(ticketsIntent);
    }

    private void showOrders()
    {
        Intent orderBasketIntent = new Intent(LandActivity.this, OrderBasketActivity.class);
        startActivity(orderBasketIntent);
        //finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
        setupToolbar();
        initializeViews();
        setupMainBottomBar();
        checkUserLoginStatus();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        checkUserLoginStatus();
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
        else if (CurrentPage == BottomBarTabSelectListener.TAB_CATEGORY || CurrentPage == BottomBarTabSelectListener.TAB_SEARCH)
        {
            BottomNavigation.setCurrentItem(BottomBarTabSelectListener.TAB_HOME);
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
        RowMessages = findViewById(R.id.row_messages);
        RowContactUs = findViewById(R.id.row_contact_us);
        RowAboutUs = findViewById(R.id.row_about_us);
        BtnShowShoppingCart = findViewById(R.id.btn_show_shopping_cart);
        RowLoginRegister.setOnClickListener(this);
        RowManageProfile.setOnClickListener(this);
        RowShareApp.setOnClickListener(this);
        RowOrders.setOnClickListener(this);
        RowMessages.setOnClickListener(this);
        RowContactUs.setOnClickListener(this);
        RowAboutUs.setOnClickListener(this);
        BtnShowShoppingCart.setOnClickListener(this);

        TxtWellcomeUser = findViewById(R.id.txt_greetings_land);
        TxtSignInOut = findViewById(R.id.nav_menu_title_loginRegister);
        Drawer = findViewById(R.id.drawer_layout_land);
        ImgDrawerBG = findViewById(R.id.img_drawer_bg);
        ImgProfilePhoto = findViewById(R.id.imgProfileNavHeader);
        Glide.with(this).load(R.drawable.simple_pattern).into(ImgDrawerBG);
        Glide.with(this).load(R.drawable.ic_man).into(ImgProfilePhoto);
    }

    private void setupMainBottomBar()
    {
        AHBottomNavigationItem categoryTabItem = new AHBottomNavigationItem(R.string.tabTitleCategory, R.drawable.ic_big_and_small_dots, R.color.bottomBarIconColor);
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

    private void checkUserLoginStatus()
    {
        String fullName = UserPreference.getUserFullName() + " ";
        TxtWellcomeUser.setText(String.format(" %s%s", fullName, getString(R.string.welcomeUserText)));

        if (UserPreference.isUserLogin())
        {
            TxtSignInOut.setText(getString(R.string.logoutUser));
            RowManageProfile.setVisibility(View.VISIBLE);
            RowOrders.setVisibility(View.VISIBLE);
            RowMessages.setVisibility(View.VISIBLE);
        }
        else if (!UserPreference.isUserLogin())
        {
            TxtSignInOut.setText(getString(R.string.nav_loginRegister));
            RowManageProfile.setVisibility(View.GONE);
            RowOrders.setVisibility(View.GONE);
            RowMessages.setVisibility(View.GONE);
        }
    }

    private void signOut()
    {
        UserPreference.isUserLogin(false);
        UserPreference.setUserFullName("کاربر مهمان");
        checkUserLoginStatus();
        new CuteToast.Builder(this).setText(getString(R.string.signoutText)).setDuration(Toast.LENGTH_LONG).show();
    }

    public static void ShareApplication(Context AppContext)
    {
        ApplicationInfo app = AppContext.getApplicationInfo();
        String filePath = app.sourceDir;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        File originalApk = new File(filePath);
        try
        {
            File tempFile = new File(AppContext.getExternalCacheDir() + "/ExtractedApk");
            if (!tempFile.isDirectory())
            {
                if (!tempFile.mkdirs())
                {
                    return;
                }
            }
            tempFile = new File(tempFile.getPath() + "/" + AppContext.getString(app.labelRes).replace(" ", "").toLowerCase() + ".apk");
            if (!tempFile.exists())
            {
                if (!tempFile.createNewFile())
                {
                    return;
                }
            }
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(AppContext, BuildConfig.APPLICATION_ID + ".provider", tempFile));
            AppContext.startActivity(Intent.createChooser(intent, "اشتراک گذاری برنامه با"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

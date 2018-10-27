package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import aryasoft.company.arachoob.Fragments.SignInFragment;
import aryasoft.company.arachoob.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpSignInActivity extends AppCompatActivity
{

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_signin);
        showLoginFragment(new SignInFragment());
    }

    private void showLoginFragment(Fragment switchToFragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_up , R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, switchToFragment);
        fragmentTransaction.commit();
    }
}

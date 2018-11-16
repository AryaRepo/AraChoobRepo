package aryasoft.company.arachoob.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import aryasoft.company.arachoob.Activities.LandActivity;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.UserInfoImpl;
import aryasoft.company.arachoob.Implementations.UserLoginImpl;
import aryasoft.company.arachoob.Models.UserInfoModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class SignInFragment extends Fragment implements UserLoginImpl.OnLoginListener,
        View.OnClickListener, Networking.NetworkStatusListener, UserInfoImpl.OnUserInfoReceivedListener
{
    private Context fragmentContext;
    private EditText edtUsernameSignIn;
    private EditText edtPasswordSignIn;
    private Button btnLoginSignIn;
    private Button btnCreateAccountSignIn;
    private Button btnForgetPassword;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;

    public SignInFragment()
    {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fragmentContext = view.getContext();
        initViews(view);
        initEvents();
    }

    @Override
    public void onLoginStatusReceived(Response<Integer> response)
    {
        Integer status = response.body();
        int NOT_REGISTERED = -2;
        int REGISTERED_AND_NOT_ACTIVE = -1;
        int REGISTERED_AND_ACTIVE = 0;
        if (status == REGISTERED_AND_NOT_ACTIVE)
        {
            Loading.hide();
            MessageDialog.setContentText(getString(R.string.alreadyRegisteredAndNotActiveText)).show();
            UserPreference.setUserMobileNumber(edtUsernameSignIn.getText().toString());
            UserPreference.setUserPassword(edtPasswordSignIn.getText().toString());
            showUserActivationFragment(status);
        }
        else if (status == NOT_REGISTERED)
        {
            Loading.hide();
            MessageDialog.setContentText(getString(R.string.notRegisteredYetText)).show();
        }
        else if (status > REGISTERED_AND_ACTIVE) //this means that the user is registered and is active and the result will be the user identity code.
        {
            UserPreference.setUserId(status);
            UserPreference.isUserLogin(true);
            getUserInfo();
        }
    }

    @Override
    public void onUserInfoReceived(Response<UserInfoModel> response)
    {
        Loading.hide();
        assert response.body() != null;
        String fullName = response.body().FirstName + " " + response.body().LastName;
        if (fullName.isEmpty())
        {
            UserPreference.setUserFullName("کاربر عزیز");
        }
        else
        {
            UserPreference.setUserFullName(fullName);
        }
        getActivity().finish();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.btnLoginSignIn)
        {
            Networking.checkNetwork(getContext(), this, 4);
        }
        else if (view.getId() == R.id.btnCreateAccountSignIn)
        {
            showSignUpFragment();
        }
        else if (view.getId() == R.id.btnForgetPassword)
        {
            showRecoveryPasswordFragment();
        }
    }

    @Override
    public void onNetworkConnected(int requestCode)
    {
        signIn();
    }

    @Override
    public void onNetworkDisconnected()
    {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    private void initViews(View view)
    {
        edtUsernameSignIn = view.findViewById(R.id.edtUsernameSignIn);
        edtPasswordSignIn = view.findViewById(R.id.edtPasswordSignIn);
        btnLoginSignIn = view.findViewById(R.id.btnLoginSignIn);
        btnCreateAccountSignIn = view.findViewById(R.id.btnCreateAccountSignIn);
        btnForgetPassword = view.findViewById(R.id.btnForgetPassword);
        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(getContext());
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(getContext());
    }

    private void initEvents()
    {
        btnLoginSignIn.setOnClickListener(this);
        btnCreateAccountSignIn.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);
    }

    private void signIn()
    {
        Loading.show();
        String mobileNumber = edtUsernameSignIn.getText().toString();
        String password = edtPasswordSignIn.getText().toString();
        AraApi araApi = ApiServiceGenerator.getApiService();
        Call<Integer> loginCall = araApi.loginUser(mobileNumber, password);
        loginCall.enqueue(new UserLoginImpl(this));
    }

    private void getUserInfo()
    {
        AraApi araApi = ApiServiceGenerator.getApiService();
        int userId = UserPreference.getUserId();
        Call<UserInfoModel> userInfoCall = araApi.getUserInfo(userId);
        userInfoCall.enqueue(new UserInfoImpl(this));
    }

    private void showSignUpFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_up, R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, new SignUpFragment());
        fragmentTransaction.commit();
    }

    private void showRecoveryPasswordFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_up, R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, new RecoveryPasswordFragment());
        fragmentTransaction.commit();
    }

    private void showUserActivationFragment(int status)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserActivationAccountFragment userActivationAccountFragment = new UserActivationAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        userActivationAccountFragment.setArguments(bundle);
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_up, R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, userActivationAccountFragment);
        fragmentTransaction.commit();
    }

}

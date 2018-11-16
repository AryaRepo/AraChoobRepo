package aryasoft.company.arachoob.Fragments;


import android.content.Context;
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

import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.UserRegistrationImpl;
import aryasoft.company.arachoob.Models.UserRegistration;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class SignUpFragment extends Fragment implements UserRegistrationImpl.OnRegistrationStatusListener,
        View.OnClickListener, Networking.NetworkStatusListener
{
    private Context fragmentContext;
    private EditText edtUsernameSignUp;
    private EditText edtUserEmailSignUp;
    private EditText edtPasswordSignUp;
    private EditText edtRepeatPasswordSignUp;
    private Button btnSignUp;
    private Button btnLoginAccountSignUp;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;

    public SignUpFragment()
    {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
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
    public void onRegistrationStatusReceived(Response<Integer> response)
    {
        Loading.hide();
        int ALREADY_REGISTERED_AND_ACTIVE = 0;
        int ALREADY_REGISTERED_AND_NOT_ACTIVE = 1;
        int PROBLEM_HAPPENED = -1;
        try
        {
            Integer status = response.body();
            if (status == ALREADY_REGISTERED_AND_NOT_ACTIVE)
            {
                MessageDialog.setContentText(getString(R.string.alreadyRegisteredAndNotActiveText)).show();
                UserPreference.setUserMobileNumber(edtUsernameSignUp.getText().toString());
                UserPreference.setUserPassword(edtPasswordSignUp.getText().toString());
                showUserActivationFragment(status);
            }
            else if (status == ALREADY_REGISTERED_AND_ACTIVE)
            {
                MessageDialog.setContentText(getString(R.string.alreadyRegisteredAndActiveText)).show();
            }
            else if (status == PROBLEM_HAPPENED)
            {
                MessageDialog.setContentText(getString(R.string.someProblemHappend)).show();
            }
            else if (status > 1)
            {
                MessageDialog.setContentText(getString(R.string.successfullyRegistered)).show();
                saveUserInfo();
                Toast.makeText(getContext(), status + "", Toast.LENGTH_LONG).show();
                showUserActivationFragment(status);
            }
        } catch (Exception ex)
        {
            MessageDialog.setContentText(getString(R.string.someProblemHappend)).show();
        }
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.btnSignUp)
        {
            Networking.checkNetwork(getContext(), this, 3);
        }
        else if (view.getId() == R.id.btnLoginAccountSignUp)
        {
            showSignInFragment();
        }
    }

    @Override
    public void onNetworkConnected(int requestCode)
    {
        signUp();
    }

    @Override
    public void onNetworkDisconnected()
    {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }


    private void initViews(View view)
    {
        edtUsernameSignUp = view.findViewById(R.id.edtUsernameSignUp);
        edtUserEmailSignUp = view.findViewById(R.id.edtUserEmailSignUp);
        edtPasswordSignUp = view.findViewById(R.id.edtPasswordSignUp);
        edtRepeatPasswordSignUp = view.findViewById(R.id.edtRepeatPasswordSignUp);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnLoginAccountSignUp = view.findViewById(R.id.btnLoginAccountSignUp);
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
        btnSignUp.setOnClickListener(this);
        btnLoginAccountSignUp.setOnClickListener(this);
    }

    private void showSignInFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_up, R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, new SignInFragment());
        fragmentTransaction.commit();
    }

    private void showUserActivationFragment(int status)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserActivationAccountFragment userActivationAccountFragment = new UserActivationAccountFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mobileNumber", edtUsernameSignUp.getText().toString());
        bundle.putInt("status", status);
        userActivationAccountFragment.setArguments(bundle);
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_up, R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, userActivationAccountFragment);
        fragmentTransaction.commit();
    }

    private void signUp()
    {
        if (getUserRegistrationModel() != null)
        {
            Loading.show();
            AraApi araApi = ApiServiceGenerator.getApiService();
            Call<Integer> registerCall = araApi.registerUser(getUserRegistrationModel());
            registerCall.enqueue(new UserRegistrationImpl(this));
        }
    }

    private UserRegistration getUserRegistrationModel()
    {
        String errorMessage = "";
        String mobileNumber = edtUsernameSignUp.getText().toString();
        if (mobileNumber.isEmpty())
        {
            errorMessage += getString(R.string.enterMobileNumber) + "\n";
        }
        else if (mobileNumber.length() > 11 || mobileNumber.length() < 11)
        {
            errorMessage += getString(R.string.mobileNumberLengthText) + "\n";
        }

        String emailAddress = edtUserEmailSignUp.getText().toString();
        if (!emailAddress.isEmpty())
        {
            if (!validateEmail(emailAddress))
            {
                errorMessage += getString(R.string.emailValidationText) + "\n";
            }
            else
            {
                emailAddress = "";
            }
        }
        String password = edtPasswordSignUp.getText().toString();
        if (password.length() < 6)
        {
            errorMessage += getString(R.string.passwordValidLengthText) + "\n";
        }

        if (!password.equals(edtRepeatPasswordSignUp.getText().toString()))
        {
            errorMessage += getString(R.string.repeatPassword) + "\n";
        }

        if (errorMessage.isEmpty())
        {
            UserRegistration userRegistration = new UserRegistration();
            userRegistration.setMobileNumber(mobileNumber);
            userRegistration.setUserEmail(emailAddress);
            userRegistration.setPassword(password);
            return userRegistration;
        }
        else
        {
            MessageDialog.setContentText(errorMessage).show();
            return null;
        }


    }

    private void saveUserInfo()
    {
        UserPreference.setUserMobileNumber(edtUsernameSignUp.getText().toString());
        UserPreference.setUserPassword(edtPasswordSignUp.getText().toString());
    }

    private boolean validateEmail(String emailAddress)
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";   //REGEX PATTERN FOR EMAIL
        return emailAddress.matches(emailPattern);
    }

}

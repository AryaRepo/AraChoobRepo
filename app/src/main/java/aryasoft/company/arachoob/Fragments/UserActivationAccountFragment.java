package aryasoft.company.arachoob.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import aryasoft.company.arachoob.Activities.LandActivity;
import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.ReSendActivationCodeImpl;
import aryasoft.company.arachoob.Implementations.UserActivationImpl;
import aryasoft.company.arachoob.Implementations.UserLoginImpl;
import aryasoft.company.arachoob.Implementations.UserRegistrationImpl;
import aryasoft.company.arachoob.Models.ActivationAccount;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class UserActivationAccountFragment extends Fragment
        implements UserActivationImpl.OnUserActivationResultReceived, View.OnClickListener,
                   ReSendActivationCodeImpl.OnReSendListener, UserLoginImpl.OnLoginListener,
        Networking.NetworkStatusListener {

    private EditText EdtActivationCode;
    private String ActivationCode;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_activation_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
    }

    @Override
    public void onActivationResultReceived(Response<Boolean> response)
    {
        Loading.hide();
        if (response.body() != null)
        if (response.body())
        {
            new CuteToast.Builder(getActivity()).setText(getString(R.string.validCodeText)).setDuration(Toast.LENGTH_LONG).show();
            signIn();
        }
        else if (!response.body())
        {
            new CuteToast.Builder(getActivity()).setText(getString(R.string.someProblemHappend)).setDuration(Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onReSentCode(Response<Integer> response) {
        Loading.hide();
        if (response.body() != null)
        ActivationCode = response.body().toString();
    }

    @Override
    public void onLoginStatusReceived(Response<Integer> response) {
        Loading.hide();
        if (response.body() != null)
        {
            Integer status = response.body();
            int NOT_REGISTERED = -2;
            int REGISTERED_AND_NOT_ACTIVE = -1;
            int REGISTERED_AND_ACTIVE = 0;
            if (status == REGISTERED_AND_NOT_ACTIVE)
            {
                MessageDialog.setContentText(getString(R.string.alreadyRegisteredAndNotActiveText)).show();
            }
            else if (status == NOT_REGISTERED)
            {
                MessageDialog.setContentText(getString(R.string.notRegisteredYetText)).show();
            }
            else if (status > REGISTERED_AND_ACTIVE) //this means that the user is registered and is active and the result will be the user identity code.
            {
                UserPreference.setUserId(status);
                UserPreference.isUserLogin(true);
                UserPreference.setUserFullName("عزیز");
                startActivity(new Intent(getActivity(), LandActivity.class));
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmitCode)
            Networking.checkNetwork(getContext(), this, 1);
        else if (view.getId() == R.id.btnResendCode)
            Networking.checkNetwork(getContext(), this, 2);
    }

    @Override
    public void onNetworkConnected(int requestCode) {
        if (requestCode == 1)
            activeAccount(getActivationAccountModel());
        else if (requestCode == 2)
            resendActivationCode();
    }

    @Override
    public void onNetworkDisconnected() {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    private void initializeView(View view)
    {
        Button btnSubmitCode = view.findViewById(R.id.btnSubmitCode);
        Button btnReSendCode = view.findViewById(R.id.btnResendCode);
        btnSubmitCode.setOnClickListener(this);
        btnReSendCode.setOnClickListener(this);
        EdtActivationCode = view.findViewById(R.id.edtActivationCode);
        Bundle bundle = getArguments();
        assert bundle != null;
        ActivationCode = bundle.getString("activationCode");
        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(getContext());
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(getContext());
    }

    private void activeAccount(ActivationAccount account)
    {
        if (account.getActiveCode().equals(EdtActivationCode.getText().toString()))
        {
            Loading.setContentText(getString(R.string.waitingText)).show();
            AraApi araApi = ApiServiceGenerator.getApiService();
            Call<Boolean> userActivationCall = araApi.activeUserAccount(getActivationAccountModel());
            userActivationCall.enqueue(new UserActivationImpl(this));
        }
        else
        {
            new CuteToast.Builder(getActivity()).setText(getString(R.string.notValidCodeText)).setDuration(Toast.LENGTH_LONG).show();
        }
    }

    private ActivationAccount getActivationAccountModel()
     {
        ActivationAccount activationAccount = new ActivationAccount();
        String mobileNumber = UserPreference.getUserMobileNumber();
        activationAccount.setMobileNumber(mobileNumber);
        activationAccount.setActiveCode(ActivationCode);
        return activationAccount;
    }

    private void resendActivationCode()
    {
        Loading.setContentText(getString(R.string.waitingText)).show();
        AraApi araApi = ApiServiceGenerator.getApiService();
        String mobileNumber = UserPreference.getUserMobileNumber();
        Call<Integer> resendCall = araApi.reSendActivationCode(mobileNumber);
        resendCall.enqueue(new ReSendActivationCodeImpl(this));
    }

    private void signIn()
    {
        Loading.setContentText(getString(R.string.waitingText)).show();
        String mobileNumber = UserPreference.getUserMobileNumber();
        String password = UserPreference.getUserPassword();
        AraApi araApi = ApiServiceGenerator.getApiService();
        Call<Integer> loginCall = araApi.loginUser(mobileNumber, password);
        loginCall.enqueue(new UserLoginImpl(this));
    }


}

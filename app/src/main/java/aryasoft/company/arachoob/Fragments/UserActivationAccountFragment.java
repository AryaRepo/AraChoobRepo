package aryasoft.company.arachoob.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.ReSendActivationCodeImpl;
import aryasoft.company.arachoob.Implementations.UserActivationImpl;
import aryasoft.company.arachoob.Implementations.UserLoginImpl;
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
        Networking.NetworkStatusListener
{

    private EditText EdtActivationCode;
    private String ActivationCode;
    private Button BtnSubmitCode;
    private Button BtnReSendCode;
    private TextView TxtTimeReminder;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;
    private int Seconds = 0;
    private Timer SchedulerTask;

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (SchedulerTask != null)
        {
            SchedulerTask.cancel();
            SchedulerTask = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_user_activation_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    @Override
    public void onActivationResultReceived(Response<Boolean> response)
    {
        Loading.hide();
        if (response.body() != null)
        {
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
    }

    @Override
    public void onReSentCode(Response<Integer> response)
    {
        Loading.hide();
        if (response.body() != null)
        {
            ActivationCode = response.body().toString();
            new CuteToast.Builder(getActivity()).setText(ActivationCode + "کدفعال سازی : ").setDuration(Toast.LENGTH_LONG).show();
           // Toast.makeText(getContext(), ActivationCode + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoginStatusReceived(Response<Integer> response)
    {
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
                UserPreference.setUserFullName("کاربر عزیز");
                SchedulerTask.cancel();
                SchedulerTask = null;
                getActivity().finish();
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.btnSubmitCode)
        {
            Networking.checkNetwork(getContext(), this, 1);
        }
        else if (view.getId() == R.id.btnResendCode)
        {
            Networking.checkNetwork(getContext(), this, 2);
        }
    }

    @Override
    public void onNetworkConnected(int requestCode)
    {
        if (requestCode == 1)
        {
            activeAccount(getActivationAccountModel());
        }
        else if (requestCode == 2)
        {
            Seconds = 10;
            startCounting();
            resendActivationCode();
        }

    }

    @Override
    public void onNetworkDisconnected()
    {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    private void initializeViews(View view)
    {
        BtnSubmitCode = view.findViewById(R.id.btnSubmitCode);
        BtnReSendCode = view.findViewById(R.id.btnResendCode);
        BtnSubmitCode.setOnClickListener(this);
        BtnReSendCode.setOnClickListener(this);
        EdtActivationCode = view.findViewById(R.id.edtActivationCode);
        TxtTimeReminder = view.findViewById(R.id.txtTimeReminder);
        setupDialogs();
        checkRegistrationStatus();
    }

    private void setupDialogs()
    {
        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(getContext());
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(getContext());
    }

    private void checkRegistrationStatus()
    {
        Bundle bundle = getArguments();
        assert bundle != null;
        int status = bundle.getInt("status");
        if (status == 1 || status == -1)
        {
            resendActivationCode();
        }
        else if (status > 1)
        {
            ActivationCode = status + "";
        }
        Seconds = 10;
        startCounting();
    }

    private void activeAccount(ActivationAccount account)
    {
        if (account.getActiveCode().equals(EdtActivationCode.getText().toString()))
        {
            Loading.show();
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
        Loading.show();
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

    private void startCounting()
    {
        BtnReSendCode.setEnabled(false);
        TxtTimeReminder.setVisibility(View.VISIBLE);
        if (SchedulerTask != null)
        {
            SchedulerTask.cancel();
            SchedulerTask = null;
        }
        SchedulerTask = new Timer();
        SchedulerTask.schedule(new TimerTask()
        {
            @Override
            public void run()
            {

                if (Seconds > 0)
                {
                    final int minuets = Seconds / 60;
                    final int seconds = Seconds % 60;
                    Seconds = Seconds - 1;

                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            TxtTimeReminder.setText("شما میتوانید تا " + minuets + " دقیقه و " + seconds + " ثانیه دیگر، درخواست برای دریافت کد فعال سازی مجدد کنید.");
                        }
                    });
                }
                else
                {
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            BtnReSendCode.setEnabled(true);
                            TxtTimeReminder.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        }, 0, 1000);

    }


}

package aryasoft.company.arachoob.Fragments;

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
import aryasoft.company.arachoob.Implementations.RecoveryPasswordImpl;
import aryasoft.company.arachoob.Implementations.UserRegistrationImpl;
import aryasoft.company.arachoob.Models.RecoveryPasswordModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class RecoveryPasswordFragment extends Fragment implements RecoveryPasswordImpl.OnRecoveryPasswordListener,
        View.OnClickListener, Networking.NetworkStatusListener {

    private EditText EdtMobileNumber;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recovery_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnRecoveryPassword)
            Networking.checkNetwork(getContext(), this, 5);
    }

    @Override
    public void onRecoveryPassword(Response<Integer> response) {
        Loading.hide();
        if (response.body() != null) {
            Integer status = response.body();
            if (status == -1) {
                MessageDialog.setContentText(getString(R.string.alreadyRegisteredAndNotActiveText)).show();
            } else if (status == 2) {
                MessageDialog.setContentText(getString(R.string.alreadyRegisteredAndNotActiveText)).show();
            } else if (status == 3) {
                MessageDialog.setContentText(getString(R.string.notRegisteredYetText)).show();
            } else {
                UserPreference.setUserPassword(status.toString());
                Toast.makeText(getContext(), status+"", Toast.LENGTH_LONG).show();
                UserPreference.setUserPassword(status.toString());
                MessageDialog.setContentText(getString(R.string.successRecoveryPasswordText)).show();
                showSignInFragment();
            }
        }
    }

    @Override
    public void onNetworkConnected(int requestCode) {
        recoveryPassword();
    }

    @Override
    public void onNetworkDisconnected() {
        Loading.setContentText(getString(R.string.noInternetText));
    }

    private void initializeViews(View view) {
        EdtMobileNumber = view.findViewById(R.id.edtUsernameRecovery);
        Button btnRecoveryPassword = view.findViewById(R.id.btnRecoveryPassword);
        btnRecoveryPassword.setOnClickListener(this);

        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(getContext());
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(getContext());
    }

    private void recoveryPassword() {
        Loading.show();
        AraApi araApi = ApiServiceGenerator.getApiService();
        String mobileNumber = EdtMobileNumber.getText().toString();
        RecoveryPasswordModel model = new RecoveryPasswordModel(mobileNumber);
        Call<Integer> recoveryCall = araApi.recoverPassword(model);
        recoveryCall.enqueue(new RecoveryPasswordImpl(this));
    }

    private void showSignInFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_up, R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, new SignInFragment());
        fragmentTransaction.commit();
    }

}

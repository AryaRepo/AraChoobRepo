package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.ApiServiceRequest;
import aryasoft.company.arachoob.Implementations.ChangePasswordImpl;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangeUserPasswordActivity extends AppCompatActivity implements ChangePasswordImpl.OnPasswordChangedListener,
        View.OnClickListener, Networking.NetworkStatusListener {

    private EditText EdtOldPassWord;
    private EditText EdtNewPassWord;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);
        initializeViews();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnChangePassword)
            Networking.checkNetwork(this, this, 7);
    }

    @Override
    public void onPasswordChanged(Response<Boolean> response) {
        Loading.hide();
        if (response.body() != null)
        {
            if (response.body())
            {
                new CuteToast.Builder(this).setText(getString(R.string.successChangePasswordText)).setDuration(Toast.LENGTH_LONG).show();
                UserPreference.setUserPassword(EdtNewPassWord.getText().toString());
                finish();
            }
            else
            {
                MessageDialog.setContentText(getString(R.string.someProblemHappend)).show();
            }
        }

    }

    @Override
    public void onNetworkConnected(int requestCode) {
        changePassword();
    }

    @Override
    public void onNetworkDisconnected() {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    private void initializeViews()
    {
        EdtOldPassWord = findViewById(R.id.edtOldPassword);
        EdtNewPassWord = findViewById(R.id.edtNewPassword);
        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(this);

        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(this);
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(this);
    }

    private void changePassword()
    {
        String mobileNumber = UserPreference.getUserMobileNumber();
        String oldPassword = UserPreference.getUserPassword();
        String oldPasswordUserInput = EdtOldPassWord.getText().toString();
        String newPassword = EdtNewPassWord.getText().toString();
        if (!oldPassword.equals(oldPasswordUserInput))
        {
            MessageDialog.setContentText(getString(R.string.incorrectPasswordText)).show();
        }
        else if ( EdtNewPassWord.getText().toString().length() < 6)
        {
            MessageDialog.setContentText(getString(R.string.passwordValidLengthText)).show();
        }
        else
        {
            Loading.show();
            ApiServiceRequest apiServiceRequest = ApiServiceGenerator.getApiService();
            Call<Boolean> changePasswordCall = apiServiceRequest.changeUserPassword(mobileNumber, oldPasswordUserInput, newPassword);
            changePasswordCall.enqueue(new ChangePasswordImpl(this));
        }
    }

}

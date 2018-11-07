package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.EditProfileImpl;
import aryasoft.company.arachoob.Models.UserInfoModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.MiladiToShamsi;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener,
        EditProfileImpl.OnProfileEditedStatusListener, Networking.NetworkStatusListener {
    private CircleImageView ImgEditProfile;
    private TextView TxtBirthday;
    private EditText EdtUserNameEdit;
    private EditText EdtUserLastNameEdit;
    private EditText EdtUserEmail;
    private EditText EdtPhoneEdit;
    private EditText EdtAddressEdit;
    private Button BtnSaveChanges;
    private Button BtnChooseBirthday;
    private AppCompatSpinner SpStatesList;
    private AppCompatSpinner SpCitiesList;
    private String BirthDay = "";
    private SweetDialog Loading;
    private SweetDialog MessageDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_choose_birthday)
            chooseBirthDate();
        else if (view.getId() == R.id.btn_save_changes)
            Networking.checkNetwork(this, this, 8);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initViews();
        getUserInfo();
    }

    @Override
    public void onProfileEdited(Response<Boolean> response) {
        Loading.hide();
        if (response.body() != null)
        {
            if (response.body())
            {
                new CuteToast.Builder(this).setText(getString(R.string.successProfileUpdateText)).setDuration(Toast.LENGTH_LONG).show();
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
        editProfile();
    }

    @Override
    public void onNetworkDisconnected() {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    private void getUserInfo() {
        Bundle bundle = getIntent().getExtras();
        UserInfoModel userInfo = (UserInfoModel) bundle.getSerializable("userInfo");
        EdtUserNameEdit.setText(userInfo.FirstName);
        EdtUserLastNameEdit.setText(userInfo.LastName);
        EdtPhoneEdit.setText(userInfo.PhoneNumber);
        EdtUserEmail.setText(userInfo.UserEmail);
        TxtBirthday.setText(userInfo.BirthDate);
        EdtAddressEdit.setText(userInfo.UserAddress);
    }

    private void initViews() {
        ImgEditProfile = findViewById(R.id.img_edit_profile);
        EdtUserEmail = findViewById(R.id.edt_user_email);
        BtnChooseBirthday = findViewById(R.id.btn_choose_birthday);
        EdtUserNameEdit = findViewById(R.id.edt_user_name_edit);
        TxtBirthday = findViewById(R.id.txt_birthday);
        EdtUserLastNameEdit = findViewById(R.id.edt_user_last_name_edit);
        EdtPhoneEdit = findViewById(R.id.edt_phone_edit);
        EdtAddressEdit = findViewById(R.id.edt_address_edit);
        BtnSaveChanges = findViewById(R.id.btn_save_changes);
        SpStatesList = findViewById(R.id.sp_state_lists);
        SpCitiesList = findViewById(R.id.sp_city_lists);
        ImgEditProfile.requestFocus();
        BtnChooseBirthday.setOnClickListener(this);
        BtnSaveChanges.setOnClickListener(this);
        Glide.with(this).load(R.drawable.ic_man).into(ImgEditProfile);

        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(this);
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(this);
    }

    private void chooseBirthDate() {
        PersianDatePickerDialog picker = new PersianDatePickerDialog(EditProfileActivity.this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setMinYear(1320)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        BirthDay = dateFormat.format(persianCalendar.getTime()) + "";
                        try {
                            if (!BirthDay.isEmpty()) {
                                Date UserBirth = dateFormat.parse(BirthDay);
                                TxtBirthday.setText(new MiladiToShamsi().getPersianDate(UserBirth) + "");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDismissed() {
                        BirthDay = "";
                    }
                });

        picker.show();
    }

    private void editProfile()
    {
        Loading.show();
        AraApi araApi = ApiServiceGenerator.getApiService();
        int userId = UserPreference.getUserId();
        Call<Boolean> editProfileCall = araApi.editUserProfile(userId, getUpdatedUserInfoModel());
        editProfileCall.enqueue(new EditProfileImpl(this));
    }

    private UserInfoModel getUpdatedUserInfoModel()
    {
        UserInfoModel updatedUserInfo = new UserInfoModel();
        updatedUserInfo.FirstName = EdtUserNameEdit.getText().toString();
        updatedUserInfo.LastName = EdtUserLastNameEdit.getText().toString();
        updatedUserInfo.PhoneNumber = EdtPhoneEdit.getText().toString();
        updatedUserInfo.UserEmail = EdtUserEmail.getText().toString();
        updatedUserInfo.BirthDate = TxtBirthday.getText().toString();
        updatedUserInfo.MobileNumber = UserPreference.getUserMobileNumber();
        updatedUserInfo.UserAddress = EdtAddressEdit.getText().toString();
        updatedUserInfo.StateCode = 27;
        updatedUserInfo.CityCode = 377;

        return updatedUserInfo;
    }

}

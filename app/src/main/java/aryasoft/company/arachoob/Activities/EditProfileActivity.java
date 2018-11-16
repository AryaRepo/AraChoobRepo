package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.EditProfileImpl;
import aryasoft.company.arachoob.Models.StateCityModel;
import aryasoft.company.arachoob.Models.UserInfoModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import aryasoft.company.arachoob.Utils.MiladiToShamsi;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.StateCityHelper;
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
        EditProfileImpl.OnProfileEditedStatusListener, Networking.NetworkStatusListener
{
    private CircleImageView ImgEditProfile;
    private TextView TxtBirthday;
    private EditText EdtUserNameEdit;
    private EditText EdtUserLastNameEdit;
    private EditText EdtUserEmail;
    private EditText EdtPhoneEdit;
    private EditText EdtAddressEdit;
    private AppCompatSpinner SpStatesList;
    private AppCompatSpinner SpCitiesList;
    private String BirthDay = "";
    private SweetDialog Loading;
    private SweetDialog MessageDialog;
    //----------------------
    private ArrayList<String> cityList;
    private ArrayList<StateCityModel> stateCityList;
    private ArrayAdapter<String> spinnerStateAdapter;
    private ArrayAdapter<String> spinnerCityAdapter;
    private int stateCode=0;
    private int cityCode=0;
    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.btn_choose_birthday)
        {
            chooseBirthDate();
        }
        else if (view.getId() == R.id.btn_save_changes)
        {
            Networking.checkNetwork(this, this, 8);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initViews();
        getUserInfo();
    }

    @Override
    public void onProfileEdited(Response<Boolean> response)
    {
        Loading.hide();
        if (response.body() != null)
        {
            if (response.body())
            {
                String userFullName = EdtUserNameEdit.getText() + " " + EdtUserLastNameEdit.getText();
                UserPreference.setUserFullName(userFullName);
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
    public void onNetworkConnected(int requestCode)
    {
        editProfile();
    }

    @Override
    public void onNetworkDisconnected()
    {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    private void getUserInfo()
    {
        Bundle bundle = getIntent().getExtras();
        UserInfoModel userInfo = (UserInfoModel) bundle.getSerializable("userInfo");
        EdtUserNameEdit.setText(userInfo.FirstName);
        EdtUserLastNameEdit.setText(userInfo.LastName);
        EdtPhoneEdit.setText(userInfo.PhoneNumber);
        EdtUserEmail.setText(userInfo.Mail);
        TxtBirthday.setText(userInfo.BirthDate);
        EdtAddressEdit.setText(userInfo.UserAddress);
        for (int i = 0; i < stateCityList.size(); ++i)
        {
            if (stateCityList.get(i).StateCode == userInfo.StateCode)
            {
                SpStatesList.setSelection(i);
                stateCode = userInfo.StateCode;
                //---------------------------------
                for (int j = 0; j < stateCityList.get(i).CityCollection.size(); ++j)
                {
                    if (stateCityList.get(i).CityCollection.get(j).CityCode == userInfo.CityCode)
                    {
                        cityCode=userInfo.CityCode;
                        final int cityPosition=j;
                        SpCitiesList.postDelayed(new Runnable()
                        {
                            public void run()
                            {
                                spinnerCityAdapter.notifyDataSetChanged();
                                SpCitiesList.setSelection(cityPosition);

                            }
                        }, 1000);
                        break;
                    }

                }
                //--------------------------------

            }

        }
    }

    private void initViews()
    {
        ImgEditProfile = findViewById(R.id.img_edit_profile);
        EdtUserEmail = findViewById(R.id.edt_user_email);
        Button btnChooseBirthday = findViewById(R.id.btn_choose_birthday);
        EdtUserNameEdit = findViewById(R.id.edt_user_name_edit);
        TxtBirthday = findViewById(R.id.txt_birthday);
        EdtUserLastNameEdit = findViewById(R.id.edt_user_last_name_edit);
        EdtPhoneEdit = findViewById(R.id.edt_phone_edit);
        EdtAddressEdit = findViewById(R.id.edt_address_edit);
        Button btnSaveChanges = findViewById(R.id.btn_save_changes);
        SpStatesList = findViewById(R.id.sp_state_lists);
        SpCitiesList = findViewById(R.id.sp_city_lists);
        ImgEditProfile.requestFocus();
        btnChooseBirthday.setOnClickListener(this);
        btnSaveChanges.setOnClickListener(this);
        Glide.with(this).load(R.drawable.ic_man).into(ImgEditProfile);

        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(this);
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(this);
        initCities();
    }

    private void initCities()
    {
        cityList=new ArrayList<>();
        stateCityList=new ArrayList<>();
        //-----------------------
        stateCityList = StateCityHelper.GetStateCityList(this);
        final String[] stateNames = new String[stateCityList.size()];
        for (int i = 0; i < stateCityList.size(); ++i)
        {
            stateNames[i] = stateCityList.get(i).StateName;
        }
        spinnerStateAdapter = new ArrayAdapter<>(EditProfileActivity.this, R.layout.spinner_item_layout, stateNames);
        spinnerCityAdapter = new ArrayAdapter<>(EditProfileActivity.this, R.layout.spinner_item_layout);
        SpStatesList.setAdapter(spinnerStateAdapter);
        SpCitiesList.setAdapter(spinnerCityAdapter);
        //-----------------------------
        SpStatesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                stateCode = stateCityList.get(position).StateCode;
                cityList.clear();
                spinnerCityAdapter.clear();
                for (int p = 0; p < stateCityList.get(position).CityCollection.size(); ++p)
                {
                    cityList.add(stateCityList.get(position).CityCollection.get(p).CityName);
                }
                spinnerCityAdapter.addAll(cityList);
                spinnerCityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        SpCitiesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                cityCode = stateCityList.get(SpStatesList.getSelectedItemPosition()).CityCollection.get(position).CityCode;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }

    private void chooseBirthDate()
    {
        PersianDatePickerDialog picker = new PersianDatePickerDialog(EditProfileActivity.this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setMinYear(1320)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener()
                {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar)
                    {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        BirthDay = dateFormat.format(persianCalendar.getTime()) + "";
                        try
                        {
                            if (!BirthDay.isEmpty())
                            {
                                Date UserBirth = dateFormat.parse(BirthDay);
                                TxtBirthday.setText(new MiladiToShamsi().getPersianDate(UserBirth) + "");
                            }

                        } catch (ParseException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onDismissed()
                    {
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

        if (EdtUserNameEdit.getText().toString().isEmpty() ||
                EdtUserLastNameEdit.getText().toString().isEmpty() ||
                EdtPhoneEdit.getText().toString().isEmpty() ||
                EdtUserEmail.getText().toString().isEmpty() ||
                TxtBirthday.getText().toString().isEmpty() ||
                EdtAddressEdit.getText().toString().isEmpty())
        {
            MessageDialog.setContentText("لطفا تمام مشخصات خود را وارد نمایید.").show();
        }
        else
        {
            updatedUserInfo.FirstName = EdtUserNameEdit.getText().toString();
            updatedUserInfo.LastName = EdtUserLastNameEdit.getText().toString();
            updatedUserInfo.PhoneNumber = EdtPhoneEdit.getText().toString();
            updatedUserInfo.Mail = EdtUserEmail.getText().toString();
            updatedUserInfo.BirthDate = TxtBirthday.getText().toString();
            updatedUserInfo.MobileNumber = UserPreference.getUserMobileNumber();
            updatedUserInfo.UserAddress = EdtAddressEdit.getText().toString();
            updatedUserInfo.StateCode = stateCode;
            updatedUserInfo.CityCode = cityCode;
        }

        return updatedUserInfo;
    }


}

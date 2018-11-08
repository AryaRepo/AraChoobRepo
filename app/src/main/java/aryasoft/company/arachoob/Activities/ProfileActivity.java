package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import aryasoft.company.arachoob.ApiConnection.ApiServiceGenerator;
import aryasoft.company.arachoob.ApiConnection.AraApi;
import aryasoft.company.arachoob.Implementations.UserInfoImpl;
import aryasoft.company.arachoob.Models.UserInfoModel;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.Networking;
import aryasoft.company.arachoob.Utils.SweetDialog;
import aryasoft.company.arachoob.Utils.UserPreference;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,
        UserInfoImpl.OnUserInfoReceivedListener, Networking.NetworkStatusListener {

    private CircleImageView profilePhoto;
    private ImageView ImgProfileBG;
    private TextView txtUserProfileName;
    private TextView txtUserProfileTxtEmail;
    private TextView txtUserProfileAddress;
    private UserInfoModel UserInfo;
    private SweetDialog Loading;
    private SweetDialog MessageDialog;


    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_edit_profile)
            showEditProfile();
        else if (view.getId() == R.id.btn_edit_password)
            showChangePassword();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        Networking.checkNetwork(this, this, 6);
    }

    @Override
    public void onUserInfoReceived(Response<UserInfoModel> response) {
        Loading.hide();
        if (response.body() != null)
        {
            UserInfo = response.body();
            txtUserProfileName.setText(String.format("%s %s", UserInfo.FirstName, UserInfo.LastName));
            txtUserProfileTxtEmail.setText(UserInfo.UserEmail);
            txtUserProfileAddress.setText(UserInfo.UserAddress);
        }
    }

    @Override
    public void onNetworkConnected(int requestCode) {
        getUserInfo();
    }

    @Override
    public void onNetworkDisconnected() {
        MessageDialog.setContentText(getString(R.string.noInternetText)).show();
    }

    private void initViews()
    {
        LinearLayout btnEditProfile = findViewById(R.id.btn_edit_profile);
        LinearLayout btnEditPassword = findViewById(R.id.btn_edit_password);
        profilePhoto = findViewById(R.id.profile_person_image);
        ImgProfileBG = findViewById(R.id.img_bg_pro);
        txtUserProfileName = findViewById(R.id.user_profile_name);
        txtUserProfileTxtEmail = findViewById(R.id.txt_UserProfile_txtEmail);
        txtUserProfileAddress = findViewById(R.id.txt_UserProfile_address);
        btnEditProfile.setOnClickListener(this);
        btnEditPassword.setOnClickListener(this);
        Glide.with(this).load(R.drawable.ic_man).into(profilePhoto);
        Glide.with(this).load(R.drawable.pic1)
                .apply(bitmapTransform(new BlurTransformation(15, 3))).into(ImgProfileBG);

        Loading = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.waitingText))
                .build(this);
        MessageDialog = new SweetDialog.Builder()
                .setDialogType(SweetAlertDialog.WARNING_TYPE)
                .setTitleText("کاربر گرامی")
                .build(this);
    }

    private void showEditProfile() {
        Intent editProfileIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        Bundle bundle =  new Bundle();
        bundle.putSerializable("userInfo", UserInfo);
        editProfileIntent.putExtras(bundle);
        startActivity(editProfileIntent);
    }

    private void getUserInfo()
    {
        Loading.setContentText(getString(R.string.waitingText)).show();
        AraApi araApi = ApiServiceGenerator.getApiService();
        int userId = UserPreference.getUserId();
        Call<UserInfoModel> userInfoCall = araApi.getUserInfo(userId);
        userInfoCall.enqueue(new UserInfoImpl(this));
    }

    private void showChangePassword()
    {
        Intent changePasswordIntent = new Intent(ProfileActivity.this, ChangeUserPasswordActivity.class);;
        startActivity(changePasswordIntent);
    }

}

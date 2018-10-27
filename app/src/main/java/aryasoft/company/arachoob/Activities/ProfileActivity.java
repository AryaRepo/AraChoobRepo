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

import aryasoft.company.arachoob.R;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView profilePhoto;
    private ImageView ImgProfileBG;
    private TextView userProfileName;
    private TextView txtUserProfileTxtEmail;
    private TextView txtUserProfileAddress;


    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_edit_profile)
            editProfile();
        else if (view.getId() == R.id.btn_edit_password)
            return;
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
    }

    private void initViews()
    {
        LinearLayout btnEditProfile = findViewById(R.id.btn_edit_profile);
        LinearLayout btnEditPassword = findViewById(R.id.btn_edit_password);
        profilePhoto = findViewById(R.id.profile_person_image);
        ImgProfileBG = findViewById(R.id.img_bg_pro);
        userProfileName = findViewById(R.id.user_profile_name);
        txtUserProfileTxtEmail = findViewById(R.id.txt_UserProfile_txtEmail);
        txtUserProfileAddress = findViewById(R.id.txt_UserProfile_address);
        btnEditProfile.setOnClickListener(this);
        btnEditPassword.setOnClickListener(this);
        Glide.with(this).load(R.drawable.ic_man).into(profilePhoto);
        Glide.with(this).load(R.drawable.pic1)
                .apply(bitmapTransform(new BlurTransformation(15, 3))).into(ImgProfileBG);
    }

    private void editProfile() {
        Intent editProfileIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(editProfileIntent);
    }

}

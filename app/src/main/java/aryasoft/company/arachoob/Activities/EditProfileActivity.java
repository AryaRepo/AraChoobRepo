package aryasoft.company.arachoob.Activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.MiladiToShamsi;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView ImgEditProfile;
    private TextView TxtBirthday;
    private EditText EdtUserNameEdit;
    private EditText EdtUserLastNameEdit;
    private EditText EdtUserEmail;
    private EditText EdtPhoneEdit;
    private EditText EdtAddressEdit;
    private Button BtnSaveChanges;
    private Button BtnChooseBirthday;
    private ir.hamsaa.RtlMaterialSpinner SpZoneLists;
    private String BirthDay = "";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_choose_birthday)
            chooseBirthDate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initViews();
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
        SpZoneLists = findViewById(R.id.sp_zone_lists);
        ImgEditProfile.requestFocus();
        BtnChooseBirthday.setOnClickListener(this);
        BtnSaveChanges.setOnClickListener(this);
        Glide.with(this).load(R.drawable.ic_man).into(ImgEditProfile);
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

}

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
import aryasoft.company.arachoob.Implementations.RegistrationUserImpl;
import aryasoft.company.arachoob.Models.UserRegistration;
import aryasoft.company.arachoob.R;
import aryasoft.company.arachoob.Utils.CuteToast;
import retrofit2.Call;
import retrofit2.Response;

public class SignUpFragment extends Fragment implements RegistrationUserImpl.OnRegistrationStatusListener
{
    private Context  fragmentContext;
    private EditText edtUsernameSignUp;
    private EditText edtPasswordSignUp;
    private EditText edtRepeatPasswordSignUp;
    private Button   btnSignUp;
    private Button btnLoginAccountSignUp;
    private final int ALREADY_REGISTERED_AND_ACTIVE = 0;
    private final int ALREADY_REGISTERED_AND_NOT_ACTIVE = 1;

    public SignUpFragment()
    {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fragmentContext = view.getContext();
        initViews(view);
        initEvents();
        signUp();
    }

    @Override
    public void onRegistrationStatusReceived(Response<Integer> response) {

       Integer status = response.body();
       if (status == ALREADY_REGISTERED_AND_NOT_ACTIVE)
       {
           new CuteToast.Builder(getActivity()).setText(getString(R.string.alreadyRegisteredAndNotActiveText)).setDuration(Toast.LENGTH_LONG).show();
       }
        else if (status == ALREADY_REGISTERED_AND_ACTIVE)
        {
            new CuteToast.Builder(getActivity()).setText(getString(R.string.alreadyRegisteredAndActiveText)).setDuration(Toast.LENGTH_LONG).show();
        }
        else if (status > 1)
       {
           new CuteToast.Builder(getActivity()).setText(getString(R.string.successfullyRegistered)).setDuration(Toast.LENGTH_LONG).show();
       }
    }



    private void initViews(View view)
    {
        edtUsernameSignUp= view.findViewById(R.id.edtUsernameSignUp);
        edtPasswordSignUp= view.findViewById(R.id.edtPasswordSignUp);
        edtRepeatPasswordSignUp= view.findViewById(R.id.edtRepeatPasswordSignUp);
        btnSignUp= view.findViewById(R.id.btnSignUp);
        btnLoginAccountSignUp = view.findViewById(R.id.btnLoginAccountSignUp);
    }

    private void initEvents()
    {
        btnLoginAccountSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_up, R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, new SignInFragment());
                fragmentTransaction.commit();
            }
        });
    }

    private void signUp()
    {
        AraApi araApi = ApiServiceGenerator.getApiService();
        Call<Integer> registerCall = araApi.registerUser(getUserRegistrationModel());
        registerCall.enqueue(new RegistrationUserImpl(this));
    }

    private UserRegistration getUserRegistrationModel()
    {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setMobileNumber(edtUsernameSignUp.getText().toString());
        userRegistration.setPassword(edtPasswordSignUp.getText().toString());
        userRegistration.setRePassword(edtRepeatPasswordSignUp.getText().toString());

        return userRegistration;
    }

}

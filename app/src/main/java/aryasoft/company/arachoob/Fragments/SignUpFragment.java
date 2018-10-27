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

import aryasoft.company.arachoob.R;

public class SignUpFragment extends Fragment
{
    private Context  fragmentContext;
    private EditText edtUsernameSignUp;
    private EditText edtPasswordSignUp;
    private EditText edtRepeatPasswordSignUp;
    private Button   btnSignUp;
    private Button btnLoginAccountSignUp;

    public SignUpFragment()
    {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fragmentContext = view.getContext();
        initViews(view);
        initEvents();
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
}

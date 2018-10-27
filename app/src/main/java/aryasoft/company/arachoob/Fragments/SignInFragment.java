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

public class SignInFragment extends Fragment
{
    private Context fragmentContext;
    private EditText edtUsernameSignIn;
    private EditText edtPasswordSignIn;
    private Button btnLoginSignIn;
    private Button btnCreateAccountSignIn;

    public SignInFragment()
    {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
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
        edtUsernameSignIn = view.findViewById(R.id.edtUsernameSignIn);
        edtPasswordSignIn = view.findViewById(R.id.edtPasswordSignIn);
        btnLoginSignIn = view.findViewById(R.id.btnLoginSignIn);
        btnCreateAccountSignIn = view.findViewById(R.id.btnCreateAccountSignIn);
    }

    private void initEvents()
    {

        btnCreateAccountSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_up , R.anim.slide_from_down).replace(R.id.registrationPlaceHolder, new SignUpFragment());
                fragmentTransaction.commit();
            }
        });

    }
}

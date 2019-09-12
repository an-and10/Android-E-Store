package com.example.e_store;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

    private Button dontHaveAnAccount;
    private FrameLayout parentframeLayout;
    private EditText email, password;
    private  Button signIn;
    private ImageView sign_in_close_btn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar signInProgress;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private TextView forget_password;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.sign_in_signup_btn);
        parentframeLayout = getActivity().findViewById(R.id.register_framelayout);
        email = view.findViewById(R.id.sign_in_email);
        password  = view.findViewById(R.id.sign_in_password);
        signIn = view.findViewById(R.id.sign_in_btn);
        sign_in_close_btn =view.findViewById(R.id.sign_in_close_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        signInProgress = view.findViewById(R.id.sign_in_progressbar);
        forget_password = view.findViewById(R.id.forget_password);







        return  view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }


        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ResetPasswordFragment());
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        sign_in_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });


    }

    private void checkEmailAndPassword() {

        Drawable customeErrorIcons = getResources().getDrawable(R.mipmap.error_icons);
        customeErrorIcons.setBounds(0,0,customeErrorIcons.getIntrinsicWidth(),customeErrorIcons.getIntrinsicHeight());
        if(email.getText().toString().matches(emailPattern))
        {
            if(password.getText().toString().length()>=6)
            {
                signIn.setEnabled(false);
                signIn.setTextColor(getResources().getColor(R.color.colorFaint));

               // signInProgress.setVisibility(View.VISIBLE);
              firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                mainIntent();
                            }else

                            {   signIn.setEnabled(true);
                                signIn.setTextColor(getResources().getColor(R.color.colorAccent));

                                signInProgress.setVisibility(View.INVISIBLE);

                                String  messg = task.getException().getMessage();
                                Toast.makeText(getActivity(), "Error: In Firebase :" + messg, Toast.LENGTH_SHORT).show();
                            }
                          }
                      });

            }else
            {
                password.setError("Invalid Password");
                signInProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "InCorrect Password Length", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            email.setError("Invalid Email");
            signInProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "InCorrect  Email", Toast.LENGTH_SHORT).show();

        }
    }


    private void mainIntent() {
        Intent mainIntent = new Intent(getActivity(),MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText()) )
        {
            if(!TextUtils.isEmpty(password.getText()))
            {
                signInProgress.setVisibility(View.VISIBLE);
                signIn.setEnabled(true);
                signIn.setTextColor(getResources().getColor(R.color.colorAccent));

            }else{
                signIn.setEnabled(false);
                signIn.setTextColor(getResources().getColor(R.color.colorFaint));

            }

        }else{
            signIn.setEnabled(false);
            signIn.setTextColor(getResources().getColor(R.color.colorFaint));

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);

        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}

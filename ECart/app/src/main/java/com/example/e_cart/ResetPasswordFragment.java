package com.example.e_cart;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    private TextView go_back;
     private Button forget_submit_btn;
     private EditText forget_reg_email;
     private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
     private FrameLayout parentFrameLayout;
     private FirebaseAuth firebaseAuth;
     private  TextView email_text;
     private ImageView email_img;
     private ProgressBar forget_bar;




    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        go_back = view.findViewById(R.id.forget_pasword_go_back_btn);
        forget_reg_email = view.findViewById(R.id.forget_password_email);
        forget_submit_btn = view.findViewById(R.id.forget_password_reset_password_btn);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        email_img  = view.findViewById(R.id.email_img);
        email_text = view.findViewById(R.id.email_text);
        forget_bar  =view.findViewById(R.id.forget_progress_bar);




        firebaseAuth = FirebaseAuth.getInstance();



        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forget_reg_email.addTextChangedListener(new TextWatcher() {
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
        forget_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            forget_bar.setVisibility(View.VISIBLE);
            forget_submit_btn.setEnabled(false);
            forget_submit_btn.setTextColor(getResources().getColor(R.color.colorFaint));
              firebaseAuth.sendPasswordResetEmail(forget_reg_email.getText().toString())
                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if(task.isSuccessful())
                              {

                                    email_img.setVisibility(View.VISIBLE);
                                    email_text.setVisibility(View.VISIBLE);
                                  Toast.makeText(getActivity(), "Mail Send Successfully!!", Toast.LENGTH_SHORT).show();
                              }else
                              {

                                  String msg = task.getException().getMessage();
                                  Toast.makeText(getActivity(), "Error in Mail Sending:"+msg, Toast.LENGTH_LONG).show();
                              }
                              forget_bar.setVisibility(View.INVISIBLE);
                              forget_submit_btn.setEnabled(true);
                              forget_submit_btn.setTextColor(getResources().getColor(R.color.colorAccent));


                          }
                      });
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);

        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }


    private void checkInputs() {
        if(TextUtils.isEmpty(forget_reg_email.getText().toString()))
        {
            Drawable customeErrorIcons = getResources().getDrawable(R.mipmap.error_icons);
            customeErrorIcons.setBounds(0,0,customeErrorIcons.getIntrinsicWidth(),customeErrorIcons.getIntrinsicHeight());

            forget_submit_btn.setEnabled(false);
            forget_submit_btn.setTextColor(getResources().getColor(R.color.colorFaint));
            forget_reg_email.setError("Invalid Email",customeErrorIcons);

        }else
        {
            forget_submit_btn.setEnabled(true);
            forget_submit_btn.setTextColor(getResources().getColor(R.color.colorAccent));

        }

    }
}

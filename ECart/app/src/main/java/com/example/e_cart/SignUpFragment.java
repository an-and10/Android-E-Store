package com.example.e_cart;


import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }
    private Button alreadyaccount; 
    private FrameLayout parentFrameLayout;
    private EditText email, password,name,confirmPassword;
    private Button signup_btn;
    private ImageView close_btn;
    private ProgressBar signUpProgress;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    public  static  boolean disabledBtn = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyaccount = view.findViewById(R.id.sign_up_signin_btn);
        parentFrameLayout = getActivity().findViewById(R.id.register_framelayout);
        name = view.findViewById(R.id.sign_up_name);
        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        confirmPassword = view.findViewById(R.id.sign_up_c_password);
        signup_btn = view.findViewById(R.id.sign_in_btn);
        close_btn =view.findViewById(R.id.sign_in_close_btn);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        signUpProgress =view.findViewById(R.id.signup_progressbar);
        if(disabledBtn)
            close_btn.setVisibility(View.INVISIBLE);
        else
            close_btn.setVisibility(View.VISIBLE);
        


        
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        alreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());


            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
                checkEmailandPassword();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
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
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });
    }

    private void mainIntent() {
        if(disabledBtn ==true)
        {
            disabledBtn = false;
        }else
        {
            Intent mainIntent = new Intent(getActivity(),MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();
    }

    private void checkEmailandPassword() {

        Drawable customeErrorIcons = getResources().getDrawable(R.mipmap.error_icons);
        customeErrorIcons.setBounds(0,0,customeErrorIcons.getIntrinsicWidth(),customeErrorIcons.getIntrinsicHeight());
        if(email.getText().toString().matches(emailPattern))
        {
            if((password.getText().toString().equals(confirmPassword.getText().toString())))
            {
                signUpProgress.setVisibility(View.VISIBLE);
                signup_btn.setEnabled(false);
                signup_btn.setTextColor(Color.rgb(50,255,255));
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("Name",name.getText().toString());
                        userData.put("Email",email.getText().toString());
                        userData.put("Password",password.getText().toString());

                        firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                .set(userData)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            CollectionReference userDataRefernce =   firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

                                            Map<String,Object> wishListMap = new HashMap<>();
                                            wishListMap.put("list_size",(long)0);

                                            Map<String,Object> ratingMap = new HashMap<>();
                                            ratingMap.put("list_size",(long)0);

                                            Map<String,Object> cartMap = new HashMap<>();
                                            cartMap.put("list_size",(long)0);

                                            Map<String,Object> addressMap = new HashMap<>();
                                            addressMap.put("list_size",(long)0);


                                            final List<String> documentNames = new ArrayList<>();
                                            documentNames.add("MY_WISHLIST");
                                            documentNames.add("MY_RATINGS");
                                            documentNames.add("MY_CART");
                                            documentNames.add("MY_ADDRESS");

                                            List<Map<String,Object>> documentField = new ArrayList<>();
                                            documentField.add(wishListMap);
                                            documentField.add(ratingMap);
                                            documentField.add(cartMap);
                                            documentField.add(addressMap);

                                            for(int i=0;i<documentNames.size();i++)
                                            {
                                                final int finalI = i;
                                                userDataRefernce.document(documentNames.get(i))
                                                        .set(documentField.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            if(finalI == documentNames.size()-1)
                                                            mainIntent();
                                                        }else
                                                        {
                                                            signUpProgress.setVisibility(View.INVISIBLE);
                                                            signup_btn.setEnabled(true);
                                                            signup_btn.setTextColor(getResources().getColor(R.color.colorAccent));
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(getContext(), "Error: "+error, Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                                            }

                                        }else
                                        {
                                            signUpProgress.setVisibility(View.INVISIBLE);
                                            signup_btn.setEnabled(true);
                                            signup_btn.setTextColor(getResources().getColor(R.color.colorAccent));
                                            Toast.makeText(getActivity(), "Error in Firebase", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else
                    {
                        signUpProgress.setVisibility(View.INVISIBLE);
                        signup_btn.setEnabled(true);
                        signup_btn.setTextColor(getResources().getColor(R.color.colorAccent));
                        Toast.makeText(getActivity(), "Error:", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            }else
            {
                confirmPassword.setError("Password Doesn't Match",customeErrorIcons);
            }
        }else
        {
            email.setError("Invalid Email",customeErrorIcons);
        }

    }

    private void checkInput() {

        if(!TextUtils.isEmpty(email.getText()))
        {
            if(!TextUtils.isEmpty(password.getText()) && password.length()>=5)
            {
                if(!TextUtils.isEmpty(name.getText()))
                {
                    if(!TextUtils.isEmpty(confirmPassword.getText()))
                    {
                        signup_btn.setEnabled(true);
                        signup_btn.setTextColor(getResources().getColor(R.color.colorAccent));


                    }else
                    {
                        signup_btn.setEnabled(false);
                        signup_btn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                    }
                }else
                {
                    signup_btn.setEnabled(false);
                    signup_btn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                }
            }else
            {
                signup_btn.setEnabled(false);
                signup_btn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            }

        }else
        {
            signup_btn.setEnabled(false);
            signup_btn.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);

        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    


}

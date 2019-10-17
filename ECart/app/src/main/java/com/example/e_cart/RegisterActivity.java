package com.example.e_cart;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class RegisterActivity extends AppCompatActivity {
    public static  Boolean setSignUpFragment =false;
    private FrameLayout frameLayout;
    public static  boolean isForgetPasswordFragment = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);
        frameLayout = findViewById(R.id.register_framelayout);
        if(setSignUpFragment)
        {   setSignUpFragment=false;
            setDefaultFragment(new SignUpFragment());
        }else
        {
            setDefaultFragment(new SignInFragment());

        }

    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


      if(keyCode == KeyEvent.KEYCODE_BACK)
      {
          SignUpFragment.disabledBtn = false;
          SignInFragment.disabledBtn =true;
          if(isForgetPasswordFragment)
          {
                isForgetPasswordFragment = false;
                setFragment(new SignUpFragment());
                return false;
          }

      }else
      {

      }
        return super.onKeyDown(keyCode, event);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }
}

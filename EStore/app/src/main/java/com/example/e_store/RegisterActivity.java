package com.example.e_store;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class RegisterActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    public static  boolean isForgetPasswordFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);
        frameLayout = findViewById(R.id.register_framelayout);
        setDefaultFragment(new SignInFragment());

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

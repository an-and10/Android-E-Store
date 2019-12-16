package com.example.e_cart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.e_cart.RegisterActivity.setSignUpFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private FrameLayout frameLayout;
        private static  final int HOMEFRAGMENT =0;
        private static final  int CARTFRAGMENT =1;
        private  int CURRENTFRAGMENT = -1;
        private  static final  int ORDERFRAGMENT =2;
        private static final int MYWISHLISTFRAGMENT=3;
        private static final int MYREWARDSFRAGEMENT = 4;
        private static final int MYACCOUNTFRAGMENT = 5;
        public  static  Boolean showCart = false;
        public static  boolean resetMainActivity = false;
        private ImageView internet;
        private   Dialog SignInDialog;
        public static   DrawerLayout drawer;
         private NavigationView navigationView;
         private FirebaseUser currentUser;
         private TextView badgeCount;
         private Window window;
         private int scrollFlags;
    AppBarLayout.LayoutParams params;

    public static Activity mainActivity = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Flipkart");
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        scrollFlags  = params.getScrollFlags();



        frameLayout =  findViewById(R.id.frame_layout);
        internet = findViewById(R.id.no_internet_connections);

        drawer= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        setFragment(new HomeFragment(), HOMEFRAGMENT);

        if (showCart == true) {
            mainActivity = this;

            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            goToFragment("MY CART", new CartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOMEFRAGMENT);

        }
        Toast.makeText(this, "Current User Name:"+ currentUser, Toast.LENGTH_SHORT).show();



        SignInDialog = new Dialog(MainActivity.this);
        SignInDialog.setContentView(R.layout.signin_dialog);
        SignInDialog.setCancelable(true);
        SignInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogsignIn = SignInDialog.findViewById(R.id.signInBtn);
        Button dialogsignUp = SignInDialog.findViewById(R.id.signUpBtn);
        final Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);

        dialogsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disabledBtn = true;
                SignInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);


            }
        });
        dialogsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disabledBtn = true;
                SignInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null)
        {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }else
        {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);
        }
        if(resetMainActivity)
        {
            resetMainActivity =false;
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Flipkart");
            setFragment(new HomeFragment(), 0);
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(CURRENTFRAGMENT == HOMEFRAGMENT)
            {
                CURRENTFRAGMENT =-1;
                super.onBackPressed();
            }else
            {
                if(showCart)
                {
                    mainActivity = null;

                    showCart=false;
                    finish();

                }else {
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setTitle("Flipkart");
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), 0);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(CURRENTFRAGMENT == HOMEFRAGMENT){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
            MenuItem cartItem = menu.findItem(R.id.main_cart_icons);
               cartItem.setActionView(R.layout.badge_layout);
                badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
                ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
                badgeIcon.setImageResource(R.mipmap.shopping_cart);
                if(currentUser!=null)
                {
                    Toast.makeText(this, "cartList Size:"+DBQueries.cartList.size(), Toast.LENGTH_SHORT).show();
                    if(DBQueries.cartList.size() ==0)
                    {
                        DBQueries.loadCart(MainActivity.this,new Dialog(MainActivity.this), false, badgeCount, new TextView(MainActivity.this));
                    }else
                    {
                        badgeCount.setVisibility(View.VISIBLE);
                        if(DBQueries.cartList.size()<99)
                        {
                            badgeCount.setText(String.valueOf(DBQueries.cartList.size()));
                        }else
                        {
                            badgeCount.setText("99");
                        }
                    }

                }
              cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(currentUser==null)
                          SignInDialog.show();
                      else
                          goToFragment("My Cart", new CartFragment(), CARTFRAGMENT);

                  }
              });



        }

        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search_icons) {
            return true;
        } else if (id == R.id.main_notification_icons) {
            return true;
        } else if (id == R.id.main_cart_icons) {
            if(currentUser==null)
                SignInDialog.show();
            else
                goToFragment("My Cart", new CartFragment(), CARTFRAGMENT);
            return true;
        }else if(id ==android.R.id.home)
        {
                if(showCart )
                {
                    mainActivity =null;

                    showCart=false;
                    finish();
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToFragment(String title, Fragment fragment,int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if(fragmentNo == CARTFRAGMENT  || showCart) {
            params.setScrollFlags(0);
            navigationView.getMenu().getItem(3).setChecked(true);
        }else
        {
            params.setScrollFlags(scrollFlags);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    MenuItem menuItem;
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        menuItem  =  item;


        if(currentUser != null) {
            drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    int id = menuItem .getItemId();
                    if (id == R.id.nav_my_flipkart) {
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                        getSupportActionBar().setTitle("Flipkart");
                        invalidateOptionsMenu();
                        setFragment(new HomeFragment(), 0);
                    } else if (id == R.id.nav_my_orders) {
                        goToFragment("My Orders", new MyOrderFragment(), ORDERFRAGMENT);
                    } else if (id == R.id.nav_my_rewards) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                        goToFragment("My Rewards", new MyRewardsFragment(), MYREWARDSFRAGEMENT);
                    } else if (id == R.id.nav_my_cart) {

                        goToFragment("My Cart", new CartFragment(), CARTFRAGMENT);


                    } else if (id == R.id.nav_my_wishlists) {
                        goToFragment("My WishList", new MywishListFragment(), MYWISHLISTFRAGMENT);

                    } else if (id == R.id.nav_my_account) {
                        goToFragment("My Account", new MyAccountFragment(), MYACCOUNTFRAGMENT);


                    } else if (id == R.id.nav_my_signout) {

                        FirebaseAuth.getInstance().signOut();
                        DBQueries.clearData();
                        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);

                        startActivity(registerIntent);
                        finish();

                    }
                }
            });



            return true;
        }else
        {
            SignInDialog.show();

            return false;
        }


    }
        private  void setFragment(Fragment fragment, int fragmentNo){

            CURRENTFRAGMENT = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();

    }
}

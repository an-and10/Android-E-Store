package com.example.e_store;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private FrameLayout frameLayout;
        private static  final int HOMEFRAGMENT =0;
        private static final  int CARTFRAGMENT =1;
        private static  int CURRENTFRAGMENT;
        private  static final  int ORDERFRAGMENT =2;
        private static final int MYWISHLISTFRAGMENT=3;
        private static final int MYREWARDSFRAGEMENT = 4;
    private static final int MYACCOUNTFRAGMENT = 5;
        private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Flipkart");


        frameLayout =  findViewById(R.id.frame_layout);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        setFragment(new HomeFragment(), HOMEFRAGMENT);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(CURRENTFRAGMENT == HOMEFRAGMENT)
            {
                super.onBackPressed();
            }else
            {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle("Flipkart");
                invalidateOptionsMenu();
                setFragment(new HomeFragment(),0);
                navigationView.getMenu().getItem(0).setChecked(true);

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(CURRENTFRAGMENT == HOMEFRAGMENT){
            getMenuInflater().inflate(R.menu.main, menu);
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
            goToFragment("My Cart", new CartFragment(), CARTFRAGMENT);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void goToFragment(String title, Fragment fragment,int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if(fragmentNo == CARTFRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_my_flipkart)
        {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Flipkart");
            invalidateOptionsMenu();
            setFragment(new HomeFragment(),0);
        }
        else if (id == R.id.nav_my_orders) {
                goToFragment("My Orders", new MyOrderFragment(), ORDERFRAGMENT);
        } else if (id == R.id.nav_my_rewards) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

          goToFragment("My Rewards", new MyRewardsFragment(),MYREWARDSFRAGEMENT );
        } else if (id == R.id.nav_my_cart) {
            goToFragment("My Cart", new CartFragment(), CARTFRAGMENT);


        } else if (id == R.id.nav_my_wishlists) {
            goToFragment("My WishList", new MywishListFragment(),MYWISHLISTFRAGMENT);

        } else if (id == R.id.nav_my_account) {
            goToFragment("My Account", new MyAccountFragment(),MYACCOUNTFRAGMENT);


        }else if(id == R.id.nav_my_signout)
        {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
        private  void setFragment(Fragment fragment, int fragmentNo){

            CURRENTFRAGMENT = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();

    }
}

package com.example.e_store;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;
    private FloatingActionButton add_to_wishList_btn;
    private static Boolean ALREADYADDED_TO_WISHLIST =false;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private Button buyNowBtn;



    // Rating Layout//
    private LinearLayout rateNowContainer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_image_viewpager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        add_to_wishList_btn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_view_pager);
        productDetailsTabLayout  =findViewById(R.id.product_details_tab_layout);
        buyNowBtn = findViewById(R.id.buy_now_btn);

        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.phone1);
        productImages.add(R.drawable.phone2);
        productImages.add(R.drawable.phone1);
        productImages.add(R.drawable.phone3);

        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
        productImagesViewPager.setAdapter(productImageAdapter);

        viewPagerIndicator.setupWithViewPager(productImagesViewPager,true);
        add_to_wishList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(ALREADYADDED_TO_WISHLIST)
                {
                    ALREADYADDED_TO_WISHLIST = false;
                add_to_wishList_btn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#918989")));
                }else
            {
                ALREADYADDED_TO_WISHLIST = true;
                add_to_wishList_btn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

            }

            }
        });

        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               productDetailsViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Rating Now Layout//
        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int i =0;i<rateNowContainer.getChildCount();i++)
        {
            final int star_position = i;
            rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(star_position);

                }
            });

        }
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });
    }





    private void setRating(int star_position)
    {
        for(int x =0;x <rateNowContainer.getChildCount();x++)
        {
            ImageView starbtn = (ImageView) rateNowContainer.getChildAt(x);
            starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x<=star_position)
            {
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            }

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.product_details_search) {
            return true;
        } else if (id == R.id.product_details_cart) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }




}

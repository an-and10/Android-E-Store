 package com.example.e_store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

 public class CategoryActivity extends AppCompatActivity {

     private RecyclerView categoryRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        String category_name = getIntent().getStringExtra("categoryName");
        getSupportActionBar().setTitle(category_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        categoryRecycleView = findViewById(R.id.category_recycler_view);


        List<sliderModel> sliderModelList = new ArrayList<sliderModel>();
        sliderModelList.add(new sliderModel(R.mipmap.ic_launcher,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.error_icons,"#0774AE4"));

        sliderModelList.add(new sliderModel(R.mipmap.red_email,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.my_orders,"#acdacd"));
        sliderModelList.add(new sliderModel(R.mipmap.flipkart_logo,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.cross_btn,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.ic_launcher,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.error_icons,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.default_profile,"#0774AE4"));


        sliderModelList.add(new sliderModel(R.mipmap.red_email,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.my_orders,"#acdacd"));



        /* Code end for banner Slider  */
        /* Code for strip Banner */


        List<HorizontalScrollProductModel>horizontalScrollProductModelList = new ArrayList<>();
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone1,"Redmi 5A","Intel Core Processor","Rs. 5999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone2,"Redmi 5A","Intel Core Processor","Rs. 5999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone3,"Redmi 5A","Intel Core Processor","Rs. 5999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone1,"Redmi 5A","Intel Core Processor","Rs. 5999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone2,"Redmi 5A","Intel Core Processor","Rs. 5999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone3,"Redmi 5A","Intel Core Processor","Rs. 5999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone1,"Redmi 5A","Intel Core Processor","Rs. 5999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone2,"Redmi 5A","Intel Core Processor","Rs. 5999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.phone3,"Redmi 5A","Intel Core Processor","Rs. 5999"));



        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycleView.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(sliderModelList,0));
        homePageModelList.add(new HomePageModel(1,R.drawable.gtravel, "#dddddd"));
        homePageModelList.add(new HomePageModel(2,"Deals of the Day", horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(3,"Trending Products", horizontalScrollProductModelList));
       // homePageModelList.add(new HomePageModel(3,"Trending Products", horizontalScrollProductModelList));

        HomePageAdapter homePageAdapter = new HomePageAdapter(homePageModelList);
        categoryRecycleView.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();








    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_search_menu,menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();
        if(itemid ==R.id.category_search_icon)
        {
            return  true;
        }else if(itemid ==android.R.id.home)
        {
            finish();
            return true;
        }


         return super.onOptionsItemSelected(item);
     }
 }

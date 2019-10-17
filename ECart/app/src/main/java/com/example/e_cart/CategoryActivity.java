 package com.example.e_cart;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.e_cart.DBQueries.lists;
import static com.example.e_cart.DBQueries.loadFragmentFirebase;
import static com.example.e_cart.DBQueries.loadedCategoriesName;

 public class CategoryActivity extends AppCompatActivity {

     private RecyclerView categoryRecycleView;
     private      HomePageAdapter homePageAdapter;
     private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

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
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycleView.setLayoutManager(testingLayoutManager);
        homePageAdapter = new HomePageAdapter(homePageModelFakeList);


        List<sliderModel> sliderModelFakeList =  new ArrayList<>();
        sliderModelFakeList.add(new sliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new sliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new sliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new sliderModel("null", "#FFFFFF"));
        sliderModelFakeList.add(new sliderModel("null", "#FFFFFF"));

        List<HorizontalScrollProductModel> horizontalScrollProductModelFakeList = new ArrayList<>();
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));

        homePageModelFakeList.add(new HomePageModel(sliderModelFakeList,0));
        homePageModelFakeList.add(new HomePageModel(1,"", "#aaccdd"));
        homePageModelFakeList.add(new HomePageModel(2,"","#ffffff",horizontalScrollProductModelFakeList, new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3,"","#ffffff", horizontalScrollProductModelFakeList ));

            int listPosition =0;
        for(int x =0;x< loadedCategoriesName.size(); x++)
        {
            if(loadedCategoriesName.get(x).equals(category_name.toUpperCase()))
            {
                listPosition =x;

            }

        }
        if(listPosition ==0)
        {
            loadedCategoriesName.add(category_name.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());

            loadFragmentFirebase(categoryRecycleView,this,loadedCategoriesName.size()-1, category_name);
        }else
        {
            homePageAdapter = new HomePageAdapter(lists.get(listPosition));

        }

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

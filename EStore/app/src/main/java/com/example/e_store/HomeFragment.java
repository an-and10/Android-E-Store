package com.example.e_store;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView testing;


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private  CategoryAdapter categoryAdapter;


    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        categoryRecyclerView.setLayoutManager(linearLayoutManager);

       final  List<CategoryModel> CategoryModelList = new ArrayList<CategoryModel>();
        CategoryModelList.add(new CategoryModel("Link", "Home"));
        CategoryModelList.add(new CategoryModel("Link", "Shop"));
        CategoryModelList.add(new CategoryModel("Link", "Home2"));
        CategoryModelList.add(new CategoryModel("Link", "Home3"));
        CategoryModelList.add(new CategoryModel("Link", "Home4"));
        CategoryModelList.add(new CategoryModel("Link", "Home5"));
        CategoryModelList.add(new CategoryModel("Link", "Home"));
        CategoryModelList.add(new CategoryModel("Link", "Shop"));
        CategoryModelList.add(new CategoryModel("Link", "Home2"));
        CategoryModelList.add(new CategoryModel("Link", "Home3"));
        CategoryModelList.add(new CategoryModel("Link", "Home4"));
        CategoryModelList.add(new CategoryModel("Link", "Home5"));

        categoryAdapter = new CategoryAdapter(CategoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


        List<sliderModel>sliderModelList = new ArrayList<sliderModel>();
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


          testing = view.findViewById(R.id.home_fragment_recycle_view);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(sliderModelList,0));
        homePageModelList.add(new HomePageModel(1,R.drawable.gtravel, "#dddddd"));
        homePageModelList.add(new HomePageModel(2,"Delas of the Day", horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(3,"Trending ", horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(3,"Trending", horizontalScrollProductModelList));

        HomePageAdapter homePageAdapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();





        return view;
    }

}

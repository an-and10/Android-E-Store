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
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    // Code for Banner Slider//
    private ViewPager viewPager;
    private  List<sliderModel>sliderModelList;
    private  int current_page =2;
    private Timer timer;
    final private  long Delay_Time = 3000;
    final private  long Period_Time =3000;

    //

    private RecyclerView categoryRecyclerView;
    private  CategoryAdapter categoryAdapter;

    // Code for strip add Baner//
    private ImageView stripAdImage;
    private ConstraintLayout stripAdContainer;


    // code for strip ends here//


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
        List<CategoryModel> CategoryModelList = new ArrayList<CategoryModel>();
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


        /* Code for Banner Slider */
        viewPager = view.findViewById(R.id.view_pager_banner_slider);
        sliderModelList = new ArrayList<sliderModel>();
        sliderModelList.add(new sliderModel(R.mipmap.ic_launcher,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.error_icons,"#0774AE4"));

        sliderModelList.add(new sliderModel(R.mipmap.red_email,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.notifications,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.flipkart_logo,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.cross_btn,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.ic_launcher,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.error_icons,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.default_profile,"#0774AE4"));


        sliderModelList.add(new sliderModel(R.mipmap.red_email,"#0774AE4"));
        sliderModelList.add(new sliderModel(R.mipmap.notifications,"#0774AE4"));


        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(20);
        viewPager.setCurrentItem(current_page);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                current_page = i;

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == ViewPager.SCREEN_STATE_OFF) {
                    Looper();
                }

            }

        };
        viewPager.addOnPageChangeListener(onPageChangeListener);

        startBannerSliderShow();
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Looper();
                stopBannerSlideShow();
                if (event.getAction() == MotionEvent.ACTION_UP)
                    startBannerSliderShow();

                return false;
            }
        });


        /* Code end for banner Slider  */
        /* Code for strip Banner */

        stripAdContainer = view.findViewById(R.id.strip_ad_container);
        stripAdImage = view.findViewById(R.id.strip_ad_image);

        stripAdImage.setImageResource(R.drawable.mum1);
        stripAdContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
        /* code for Strip Add image */





        /* Code ends for strip add */

        return view;
    }

    // Code for Banner  //

    private  void Looper()
    {
        if(current_page == sliderModelList.size()-2){
            current_page =2;
            viewPager.setCurrentItem(current_page,false);
        }
        if(current_page == 1){
            current_page =sliderModelList.size()-1;
            viewPager.setCurrentItem(current_page,false);
        }
    }
    private  void startBannerSliderShow()
    {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(current_page >= sliderModelList.size())
                {
                    current_page =1;
                }
                viewPager.setCurrentItem(current_page++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);

            }
        }, Delay_Time, Period_Time);
    }
    private  void stopBannerSlideShow()
    {
        timer.cancel();
    }
}

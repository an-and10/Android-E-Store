package com.example.e_cart;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    public SliderAdapter(List<sliderModel> slideModelList) {
        this.slideModelList = slideModelList;
    }

    private List<sliderModel> slideModelList;


    @Override
    public int getCount() {

        return slideModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view  = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
        ConstraintLayout bannerContainer = view.findViewById(R.id.banner_container);
        bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(slideModelList.get(position).getBackgroundColor())));

        ImageView banner_slide= view.findViewById(R.id.slide_banner);
        Glide.with(container.getContext()).load(slideModelList.get(position).getBanner()).apply(new RequestOptions().placeholder(R.mipmap.ph)).into(banner_slide);

        container.addView(view, 0);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);

    }
}

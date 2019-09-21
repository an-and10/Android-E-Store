package com.example.e_store;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
       // bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(slideModelList.get(position).getBackgroundColor())));

        ImageView banner_slide= view.findViewById(R.id.slide_banner);
        banner_slide.setImageResource(slideModelList.get(position).getBanner());
        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);

    }
}

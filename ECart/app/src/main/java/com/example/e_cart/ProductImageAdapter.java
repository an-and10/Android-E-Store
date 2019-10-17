package com.example.e_cart;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProductImageAdapter extends PagerAdapter {
    private List<String> productImageList;

    public ProductImageAdapter(List<String> productImageList) {
        this.productImageList = productImageList;
    }



    @Override
    public int getCount() {
        return productImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return  view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImage = new ImageView(container.getContext());
        Glide.with(container.getContext()).load(productImageList.get(position)).apply(new RequestOptions()).placeholder(R.mipmap.ph).into(productImage);

        container.addView(productImage,0);
        return productImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ImageView) object);
    }
}

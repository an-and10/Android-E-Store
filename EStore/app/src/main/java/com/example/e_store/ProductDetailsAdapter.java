package com.example.e_store;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    private int totaltabs;

    public ProductDetailsAdapter(FragmentManager fm, int totaltabs) {
        super(fm);
        this.totaltabs = totaltabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0 :
            ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment();
            return  productDescriptionFragment;

            case 1:
                ProductSpecificationsFragment productSpecificationsFragment = new ProductSpecificationsFragment();
                return productSpecificationsFragment;

            case 2:
            ProductDescriptionFragment productDescriptionOthersFragment = new ProductDescriptionFragment();
            return productDescriptionOthersFragment;
            default :
                return null;

        }
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}

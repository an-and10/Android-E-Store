package com.example.e_cart;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    private int totaltabs;
    private String productDescription;
    private String productOtherDetails;
    private List<ProductSpecificationModel> productSpecificationModelList;


    public ProductDetailsAdapter(FragmentManager fm, int totaltabs, String productDescription, String productOtherDetails, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.productDescription = productDescription;
        this.productOtherDetails = productOtherDetails;
        this.productSpecificationModelList = productSpecificationModelList;
        this.totaltabs = totaltabs;
    }


    public ProductDetailsAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0 :


            ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
            productDescriptionFragment1.body = productDescription;

            return  productDescriptionFragment1;

            case 1:
                ProductSpecificationsFragment productSpecificationsFragment = new ProductSpecificationsFragment();
               productSpecificationsFragment.productSpecificationModelList = productSpecificationModelList;

                return productSpecificationsFragment;

            case 2:
            ProductDescriptionFragment productDescriptionOthersFragment = new ProductDescriptionFragment();
            productDescriptionOthersFragment.body = productOtherDetails;
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

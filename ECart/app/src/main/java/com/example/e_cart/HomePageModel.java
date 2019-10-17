package com.example.e_cart;

import java.util.ArrayList;
import java.util.List;

public class HomePageModel  {
    public  static final int BANNER_SLIDER=0;
    public static final int STRIP_AD_BANNER = 1;
    public  static  final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_LAYOUT = 3;
    private String setBackground;

    public HomePageModel(List<WishlistModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    public List<WishlistModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishlistModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    private List<WishlistModel> viewAllProductList = new ArrayList<>();




    private List<sliderModel> sliderModelList;

    public String getResource() {
        return resource;
    }

    public void setResource(String  resource) {
        this.resource = resource;
    }

    public String getSetBackground() {
        return setBackground;
    }

    public void setSetBackground(String setBackground) {
        this.setBackground = setBackground;
    }
    //Code for Strip Banner //

    int type;
    String  resource;



    public List<sliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public HomePageModel(List<sliderModel> sliderModelList, int type) {
        this.sliderModelList = sliderModelList;
        this.type = type;
    }

    public HomePageModel(int type, String resource, String setBackground) {
        this.type = type;
        this.resource = resource;
        this.setBackground = setBackground;
    }
    // Code ends Strip banner//

    // Code for Banner Slider //

    public void setSliderModelList(List<sliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // Code ends for Banner Slider //
    // Code for Horizontal product  && Grid Layout//
    private  String title;
    private  List<HorizontalScrollProductModel>horizontalScrollProductModelList = new ArrayList<>();
    public HomePageModel(int type, String title,String setBackground,  List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.type = type;
        this.title = title;
        this.setBackground = setBackground;
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    public HomePageModel(int type, String title,String setBackground,  List<HorizontalScrollProductModel> horizontalScrollProductModelList,List<WishlistModel>viewAllProductList) {
        this.type = type;
        this.title = title;
        this.setBackground = setBackground;
        this.viewAllProductList = viewAllProductList;
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalScrollProductModel> getHorizontalScrollProductModelList() {
        return horizontalScrollProductModelList;
    }

    public void setHorizontalScrollProductModelList(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }
    // Horizontal Grid Layout
}


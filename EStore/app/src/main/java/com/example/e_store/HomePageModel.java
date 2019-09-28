package com.example.e_store;

import java.util.ArrayList;
import java.util.List;

public class HomePageModel  {
    public  static final int BANNER_SLIDER=0;
    public static final int STRIP_AD_BANNER = 1;
    public  static  final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_LAYOUT = 3;





    private List<sliderModel> sliderModelList;

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
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
    int resource;
    String setBackground;



    public HomePageModel(List<sliderModel> sliderModelList, int type) {
        this.sliderModelList = sliderModelList;
        this.type = type;
    }

    public HomePageModel(int type, int resource, String setBackground) {
        this.type = type;
        this.resource = resource;
        this.setBackground = setBackground;
    }
    // Code ends Strip banner//

    // Code for Banner Slider //
    public List<sliderModel> getSliderModelList() {
        return sliderModelList;
    }

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
    public HomePageModel(int type, String title, List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.type = type;
        this.title = title;
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


package com.example.e_store;

public class sliderModel {
    private int banner;


    public int getBanner() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public sliderModel(int banner, String backgroundColor) {
        this.banner = banner;
        this.backgroundColor = backgroundColor;
    }

    private  String backgroundColor;

}

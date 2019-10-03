package com.example.e_store;

public class RewardModel {
    private String title;

    public RewardModel(String title, String expirydate, String coupon_body) {
        this.title = title;
        this.expirydate = expirydate;
        this.coupon_body = coupon_body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getCoupon_body() {
        return coupon_body;
    }

    public void setCoupon_body(String coupon_body) {
        this.coupon_body = coupon_body;
    }

    private String expirydate;
    private String coupon_body;

}

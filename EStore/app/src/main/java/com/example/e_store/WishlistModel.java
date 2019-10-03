package com.example.e_store;

public class WishlistModel {
    private int productImg;
    private int  freeCoupomns;
    private String rating;
    private  String producttitle;
    private int total_rating;
    private String productPrice;
    private String productCuttedPrice;
    private String paymentMethod;

    public String getProducttitle() {
        return producttitle;
    }

    public void setProducttitle(String producttitle) {
        this.producttitle = producttitle;
    }

    public WishlistModel(int productImg, String producttitle, int freeCoupomns, String rating, int total_rating, String productPrice, String productCuttedPrice, String paymentMethod) {
        this.productImg = productImg;
        this.freeCoupomns = freeCoupomns;
        this.rating = rating;
        this.total_rating = total_rating;
        this.productPrice = productPrice;
        this.productCuttedPrice = productCuttedPrice;
        this.paymentMethod = paymentMethod;
        this.producttitle = producttitle;
    }

    public int getProductImg() {
        return productImg;
    }

    public void setProductImg(int productImg) {
        this.productImg = productImg;
    }

    public int getFreeCoupomns() {
        return freeCoupomns;
    }

    public void setFreeCoupomns(int freeCoupomns) {
        this.freeCoupomns = freeCoupomns;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(int total_rating) {
        this.total_rating = total_rating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCuttedPrice() {
        return productCuttedPrice;
    }

    public void setProductCuttedPrice(String productCuttedPrice) {
        this.productCuttedPrice = productCuttedPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}

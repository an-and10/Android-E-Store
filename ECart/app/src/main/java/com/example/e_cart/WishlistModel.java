package com.example.e_cart;

public class WishlistModel {
    private String  productImg;
    private String productId;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private long    freeCoupons;
    private String rating;
    private  String producttitle;
    private long total_rating;
    private String productPrice;
    private String productCuttedPrice;
    private boolean  COD;
    private boolean inStock;

    public WishlistModel(String productId, String productImg, long freeCoupons, String rating, String producttitle, long total_rating, String productPrice, String productCuttedPrice, boolean COD, boolean inStock) {
        this.productImg = productImg;
        this.freeCoupons = freeCoupons;
        this.rating = rating;
        this.producttitle = producttitle;
        this.total_rating = total_rating;
        this.productPrice = productPrice;
        this.productCuttedPrice = productCuttedPrice;
        this.COD = COD;
        this.inStock = inStock;
        this.productId = productId;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public long getFreeCoupons() {
        return freeCoupons;
    }

    public void setFreeCoupons(long freeCoupons) {
        this.freeCoupons = freeCoupons;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProducttitle() {
        return producttitle;
    }

    public void setProducttitle(String producttitle) {
        this.producttitle = producttitle;
    }

    public long getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(long total_rating) {
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

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }
}

package com.example.e_store;

public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;



    // Cart item variables//


    private String product_title;
    private String product_price;
    private String cutted_product_price;
    private int product_images;
    private int free_coupons;
    private int quantity;
    private int offer_applied;
    private int coupons_applied;
////    // Cart item variable ends//

    public CartItemModel(int type, String product_title, String product_price, String cutted_product_price, int product_images, int free_coupons, int quantity, int offer_applied, int coupons_applied) {
        this.type = type;
        this.product_title = product_title;
        this.product_price = product_price;
        this.cutted_product_price = cutted_product_price;
        this.product_images = product_images;
        this.free_coupons = free_coupons;
        this.quantity = quantity;
        this.offer_applied = offer_applied;
        this.coupons_applied = coupons_applied;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getCutted_product_price() {
        return cutted_product_price;
    }

    public void setCutted_product_price(String cutted_product_price) {
        this.cutted_product_price = cutted_product_price;
    }

    public int getProduct_images() {
        return product_images;
    }

    public void setProduct_images(int product_images) {
        this.product_images = product_images;
    }

    public int getFree_coupons() {
        return free_coupons;
    }

    public void setFree_coupons(int free_coupons) {
        this.free_coupons = free_coupons;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOffer_applied() {
        return offer_applied;
    }

    public void setOffer_applied(int offer_applied) {
        this.offer_applied = offer_applied;
    }

    public int getCoupons_applied() {
        return coupons_applied;
    }

    public void setCoupons_applied(int coupons_applied) {
        this.coupons_applied = coupons_applied;
    }
        // Cart ends

    // Cart Total Amount//

    private String totalItems;
    private String totalItemPrice;
    private String  deliveryPrice;
    private String savedAmount;
    private String totalAmount;

    public CartItemModel(int type, String totalItems, String totalItemPrice, String deliveryPrice, String totalAmount,String savedAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPrice = deliveryPrice;
        this.totalAmount = totalAmount;
        this.savedAmount = savedAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotatItems() {
        return totalItems;
    }

    public void setTotatItems(String totatItems) {
        this.totalItems = totatItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }





}
package com.example.e_cart;

public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;

    // Cart item variables//


    private String product_title;
    private String product_price;
    private String cutted_product_price;
    private String product_images;
    private long free_coupons;
    private long quantity;
    private String productId;
    private long offer_applied;
    private long coupons_applied;
    private boolean inStock;
////    // Cart item variable ends//

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public CartItemModel(int type, String productId, String product_title, String product_price, String cutted_product_price, String product_images, long free_coupons, long quantity, long offer_applied, long coupons_applied, boolean inStock) {
        this.type = type;
        this.productId = productId;
        this.product_title = product_title;
        this.product_price = product_price;
        this.cutted_product_price = cutted_product_price;
        this.product_images = product_images;
        this.free_coupons = free_coupons;
        this.quantity = quantity;
        this.offer_applied = offer_applied;
        this.coupons_applied = coupons_applied;
        this.inStock = inStock;
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

    public String getProduct_images() {
        return product_images;
    }

    public void setProduct_images(String product_images) {
        this.product_images = product_images;
    }

    public long getFree_coupons() {
        return free_coupons;
    }

    public void setFree_coupons(int free_coupons) {
        this.free_coupons = free_coupons;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOffer_applied() {
        return offer_applied;
    }

    public void setOffer_applied(int offer_applied) {
        this.offer_applied = offer_applied;
    }

    public long getCoupons_applied() {
        return coupons_applied;
    }

    public void setCoupons_applied(int coupons_applied) {
        this.coupons_applied = coupons_applied;
    }
        // Cart ends

    // Cart Total Amount//


    public CartItemModel(int type) {
        this.type = type;
    }
}
package com.example.e_cart;

public class HorizontalScrollProductModel {
    private String productID;
    private  String  productImage;
    private  String product_name;
    private  String product_desc;
    private  String product_price;

    public String getProductImage() {

        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public HorizontalScrollProductModel(String  productID, String productImage, String product_name, String product_desc, String product_price) {
        this.productImage = productImage;
        this.product_name = product_name;
        this.product_desc = product_desc;
        this.product_price = product_price;
        this.productID = productID;
    }
}

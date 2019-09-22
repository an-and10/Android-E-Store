package com.example.e_store;

public class HorizontalScrollProductModel {
    private  int productImage;
    private  String product_name;
    private  String product_desc;
    private  String product_price;

    public int getProductImage() {

        return productImage;
    }

    public void setProductImage(int productImage) {
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

    public HorizontalScrollProductModel(int productImage, String product_name, String product_desc, String product_price) {
        this.productImage = productImage;
        this.product_name = product_name;
        this.product_desc = product_desc;
        this.product_price = product_price;
    }
}

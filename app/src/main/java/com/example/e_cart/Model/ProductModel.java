package com.example.e_cart.Model;

public class ProductModel {
    public ProductModel(String productid, String productTitle, String productimage,
                        String productDescription, String productCategory, String productprice, String productQuantity, String timestemp, String uid) {
        this.productid = productid;
        this.productTitle = productTitle;
        this.productimage = productimage;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productprice = productprice;
        this.productQuantity = productQuantity;
        this.timestemp = timestemp;
        this.uid = uid;
    }

    public ProductModel() {

    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(String timestemp) {
        this.timestemp = timestemp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String productid, productTitle, productimage, productDescription,
  productCategory, productprice,
    productQuantity, timestemp,  uid;

}
package com.example.e_cart.Model;

public class ModelOrder {
    String Id;
    String Title;
    String Timestamp;
    String Product_id;
    String PriceEach;
    String shopid;
    String Total_price;
    String Quantity;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getProduct_id() {
        return Product_id;
    }

    public void setProduct_id(String product_id) {
        Product_id = product_id;
    }

    public String getPriceEach() {
        return PriceEach;
    }

    public void setPriceEach(String priceEach) {
        PriceEach = priceEach;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getTotal_price() {
        return Total_price;
    }

    public void setTotal_price(String total_price) {
        Total_price = total_price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public ModelOrder(String id, String title, String timestamp, String product_id, String priceEach, String shopid, String total_price, String quantity) {
        Id = id;
        Title = title;
        Timestamp = timestamp;
        Product_id = product_id;
        PriceEach = priceEach;
        this.shopid = shopid;
        Total_price = total_price;
        Quantity = quantity;
    }

    public ModelOrder() {
    }
}

package com.example.e_cart.Model;

public class ModelOrderShop {
    public ModelOrderShop() {
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public String getOrder_Time() {
        return Order_Time;
    }

    public void setOrder_Time(String order_Time) {
        Order_Time = order_Time;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public String getShop_name() {
        return Shop_name;
    }

    public void setShop_name(String shop_name) {
        Shop_name = shop_name;
    }

    public String getOrder_Status() {
        return Order_Status;
    }

    public void setOrder_Status(String order_Status) {
        Order_Status = order_Status;
    }

    public String getOrder_Total() {
        return Order_Total;
    }

    public void setOrder_Total(String order_Total) {
        Order_Total = order_Total;
    }

    public String getOrder_BY() {
        return Order_BY;
    }

    public void setOrder_BY(String order_BY) {
        Order_BY = order_BY;
    }

    public String getOrder_To() {
        return Order_To;
    }

    public void setOrder_To(String order_To) {
        Order_To = order_To;
    }

    public ModelOrderShop(String order_ID, String order_Time, String order_Date, String shop_name, String order_Status, String order_Total, String order_BY, String order_To) {
        Order_ID = order_ID;
        Order_Time = order_Time;
        Order_Date = order_Date;
        Shop_name = shop_name;
        Order_Status = order_Status;
        Order_Total = order_Total;
        Order_BY = order_BY;
        Order_To = order_To;
    }

    String Order_ID;
    String Order_Time;
    String Order_Date;
    String Shop_name;
    String Order_Status;
    String Order_Total;
    String Order_BY;
    String Order_To;
}

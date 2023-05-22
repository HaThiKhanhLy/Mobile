package com.example.e_cart.Model;

public class CatagoryModel {
    String name;

    public CatagoryModel(String name, Integer image) {
        this.name = name;
        Image = image;
    }

    Integer Image;


    public CatagoryModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }
}

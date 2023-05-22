package com.example.e_cart.Model;

public class Modelshop {
    String uid;
    String name;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getBlocked() {
        return Blocked;
    }

    public void setBlocked(String blocked) {
        Blocked = blocked;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getTimestemp() {
        return Timestemp;
    }

    public void setTimestemp(String timestemp) {
        Timestemp = timestemp;
    }

    public String getShopopen() {
        return shopopen;
    }

    public void setShopopen(String shopopen) {
        this.shopopen = shopopen;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getDeliveryprice() {
        return deliveryprice;
    }

    public void setDeliveryprice(String deliveryprice) {
        this.deliveryprice = deliveryprice;
    }

    public Modelshop(String uid, String name, String email, String phone, String state, String city, String country, String address, String online, String blocked, String accounttype, String timestemp, String shopopen, String profileimage, String deliveryprice) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.city = city;
        this.country = country;
        this.address = address;
        this.online = online;
        Blocked = blocked;
        this.accounttype = accounttype;
        Timestemp = timestemp;
        this.shopopen = shopopen;
        this.profileimage = profileimage;
        this.deliveryprice = deliveryprice;
    }

    String email;
    String phone;
    String state;
    String city;
    String country;
    String address;
    String online;
    String Blocked;
    String accounttype;
    String Timestemp;
    String shopopen;
    String profileimage;
    String deliveryprice;

    public Modelshop() {

    }
}

package com.example.e_cart.Model;

public class UserModel {
    String idUser;
    String Fullname;
    String email;
    String phone;
    String Address;
    String status;

    public UserModel(String idUser, String fullname, String status, String email, String phone, String address) {
        this.idUser = idUser;
        this.Fullname = fullname;
        this.email = email;
        this.phone = phone;
        Address = address;
        this.status = status;

    }

    public UserModel() {

    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

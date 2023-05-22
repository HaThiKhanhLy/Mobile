package com.example.e_cart.Model;

public class ModelChat {
    String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecevier() {
        return recevier;
    }

    public void setRecevier(String recevier) {
        this.recevier = recevier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String recevier;
    String message;

    public ModelChat(String sender, String recevier, String message) {
        this.sender = sender;
        this.recevier = recevier;
        this.message = message;
    }

    public ModelChat() {
    }
}

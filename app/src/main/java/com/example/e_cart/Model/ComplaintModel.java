package com.example.e_cart.Model;

public class ComplaintModel {
    String Uid;

    public ComplaintModel() {
    }

    String Ticket;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    String Subject;

    public ComplaintModel(String uid, String ticket, String subject, String comment) {
        Uid = uid;
        Ticket = ticket;
        Subject = subject;
        Comment = comment;
    }

    String Comment;
}

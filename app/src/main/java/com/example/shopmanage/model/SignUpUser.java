package com.example.shopmanage.model;

public class SignUpUser {
    int id;
    String fullname;
    String phone;
    String email;
    String user;
    String pass;
    byte [] image;

    public SignUpUser(int id, String fullname, String phone, String email, String user, String pass, byte[] image) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.user = user;
        this.pass = pass;
        this.image = image;
    }

    public SignUpUser(String fullname, String phone, String email, String user, String pass, byte[] image) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.user = user;
        this.pass = pass;
        this.image = image;
    }

    public SignUpUser(String fullname, String phone, String email) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;

    }

    public SignUpUser(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public SignUpUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

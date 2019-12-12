package com.example.shopmanage.model;

public class SignUpUser {
    int id;
    String fullname;

    String email;
    String user;
    String pass;


    public SignUpUser(int id, String fullname, String email, String user, String pass ) {
        this.id = id;
        this.fullname = fullname;

        this.email = email;
        this.user = user;
        this.pass = pass;

    }

    public SignUpUser(String fullname,  String email, String user, String pass ) {
        this.fullname = fullname;

        this.email = email;
        this.user = user;
        this.pass = pass;

    }

    public SignUpUser(String fullname,  String email) {
        this.fullname = fullname;

        this.email = email;

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
    }



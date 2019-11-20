package com.example.shopmanage.model;

public class Loai {
    String id;
    String name;
    String moTa;
    byte [] hinhAnh;

    public Loai() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }



    public String sp(){
        return id +" | "+ name;
    }
}

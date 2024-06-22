package com.example.projectbasisdata.model;

public class Produk {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Produk(int id,String name,double price,int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}

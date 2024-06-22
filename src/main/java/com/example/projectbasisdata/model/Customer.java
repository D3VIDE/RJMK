package com.example.projectbasisdata.model;

public class Customer {
    private int id;
    private String cust_name;
    private int point;

    public Customer(int id,String nama){
        this.id = id;
        this.cust_name = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}

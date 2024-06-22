package com.example.projectbasisdata.model;

public class DetailMenu {
    private int detail_id; //punya detailmenu id
    private int menu_id; //punya menu id
    private double price;

    public DetailMenu(int detail_id,int menu_id,double price){
        this.detail_id = detail_id;
        this.menu_id = menu_id;
        this.price = price;
    }

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

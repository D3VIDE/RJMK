package com.example.projectbasisdata.model;

import java.util.Date;

public class Temp_order {
    private int temp_number;
    private Date order_date;
    private int quantity;
    private int detailmenu_id;

    public Temp_order(int temp_number, Date order_date, int quantity, int detailmenu_id) {
        this.temp_number = temp_number;
        this.order_date = order_date;
        this.quantity = quantity;
        this.detailmenu_id = detailmenu_id;
    }

    public int getTemp_number() {
        return temp_number;
    }

    public void setTemp_number(int temp_number) {
        this.temp_number = temp_number;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDetailmenu_id() {
        return detailmenu_id;
    }

    public void setDetailmenu_id(int detailmenu_id) {
        this.detailmenu_id = detailmenu_id;
    }
}

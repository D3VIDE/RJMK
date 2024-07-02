package com.example.projectbasisdata.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
public class Order {
    private int order_number;
    private Date order_date;
    private int quantity;
    private int detailmenu_id;

    public Order(int order_number, Date order_date, int quantity, int detailmenu_id) {
        this.order_number = order_number;
        this.order_date = order_date;
        this.quantity = quantity;
        this.detailmenu_id = detailmenu_id;
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
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

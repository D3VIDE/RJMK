package com.example.projectbasisdata.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public class Order {
    private int id;
    private LocalDateTime orderDateTime;
    private int customerId;
    private List<OrderDetail> orderDetails;


    public Order(int id, LocalDateTime orderDateTime, int customerId, List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderDateTime = orderDateTime;
        this.customerId = customerId;
        this.orderDetails = orderDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

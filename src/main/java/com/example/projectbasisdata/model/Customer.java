package com.example.projectbasisdata.model;

public class Customer {
    private int customer_id;
    private String customer_name;
    private String jenis_customer;
    private int member_total_point;

    public Customer(int customer_id,String customer_name, String jenis_customer, int member_total_point){
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.jenis_customer = jenis_customer;
        this.member_total_point = member_total_point;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getJenis_customer() {
        return jenis_customer;
    }

    public void setJenis_customer(String jenis_customer) {
        this.jenis_customer = jenis_customer;
    }

    public int getMember_total_point() {
        return member_total_point;
    }

    public void setMember_total_point(int member_total_point) {
        this.member_total_point = member_total_point;
    }
}

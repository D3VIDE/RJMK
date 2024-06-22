package com.example.projectbasisdata.model;

public class PaymentMethod {
    private int method_id;
    private String method_name;

    public PaymentMethod(int method_id,String method_name){
        this.method_id = method_id;
        this.method_name = method_name;
    }

    public int getMethod_id() {
        return method_id;
    }

    public void setMethod_id(int method_id) {
        this.method_id = method_id;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }
}

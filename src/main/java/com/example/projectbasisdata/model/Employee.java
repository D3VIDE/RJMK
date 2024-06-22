package com.example.projectbasisdata.model;

public class Employee {
    private int emp_id;
    private String emp_name;

    public Employee(int emp_id,String emp_name){
        this.emp_id = emp_id;
        this.emp_name = emp_name;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }
}

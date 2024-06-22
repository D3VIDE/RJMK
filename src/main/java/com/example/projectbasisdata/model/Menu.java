package com.example.projectbasisdata.model;

public class Menu {
    private int id;
    private String name;

    public Menu(int id, String name, double price, int stock){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

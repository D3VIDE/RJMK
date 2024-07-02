package com.example.projectbasisdata.model;

public class Menu {
    private int menu_id;
    private String menu_name;
    private int kategori_id;


    public Menu(int id, String menu_name,int kategori){
        this.menu_id = id;
        this.menu_name= menu_name;
        this.kategori_id = kategori;
    }


    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }
}

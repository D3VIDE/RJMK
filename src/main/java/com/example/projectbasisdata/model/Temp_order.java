package com.example.projectbasisdata.model;

import java.util.Date;

public class Temp_order {
    private String menu_name;
    private int quantity;
    private int harga_nominal;
    private String size_name;
    private String kategori_name;
    // Constructor
    public Temp_order(String menu_name, int quantity, int harga, String size_name) {
        this.menu_name = menu_name;
        this.quantity = quantity;
        this.harga_nominal = harga;
        this.size_name = size_name;
    }

    // Getters
    public String getMenu_name() {
        return menu_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getHarga_nominal() {
        return harga_nominal;
    }

    public String getSize_name() {
        return size_name;
    }
    public int getTotal_price() {
        return harga_nominal * quantity;
    }

    public String getKategori_name() {
        return kategori_name;
    }
}

package com.example.projectbasisdata.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public class Order {
    private int order_number;
    private LocalDateTime order_date;
    private int total_pembayaran;
    private int point;
    private int kembalian;
    private int customerId;
    private int method_id;
    private int employee_id ;
    private List<OrderDetail> orderDetails;

    String menu_name;
    int jumlah;
    int harga_nominal;
    String size;
//    public Order(String namaProduk, int jumlah, int harga, String size) {
//        this.namaProduk = namaProduk;
//        this.jumlah = jumlah;
//        this.harga = harga;
//        this.size = size;
//    }


    public Order(String menuName, int jumlah, int hargaNominal, String sizeName) {
        this.menu_name = menuName;
        this.jumlah = jumlah;
        this.harga_nominal = hargaNominal;
        this.size = sizeName;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga_nominal() {
        return harga_nominal;
    }

    public void setHarga_nominal(int harga_nominal) {
        this.harga_nominal = harga_nominal;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}

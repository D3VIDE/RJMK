package com.example.projectbasisdata.model;

public class DetailMenu {
    private int detailmenu_id;
    private String kategori_name;
    private String menu_name;
    private String size_name;
    private int harga_nominal;  // Should remain an integer

    public DetailMenu(int detailMenuId, String kategoriName, String menuName, String sizeName, int hargaNominal) {
        this.detailmenu_id = detailMenuId;
        this.kategori_name = kategoriName;
        this.menu_name = menuName;
        this.size_name = sizeName;
        this.harga_nominal = hargaNominal;
    }

    // Getters and setters...
    public int getDetailmenu_id() {
        return detailmenu_id;
    }

    public void setDetailmenu_id(int detailmenu_id) {
        this.detailmenu_id = detailmenu_id;
    }

    public String getKategori_name() {
        return kategori_name;
    }

    public void setKategori_name(String kategori_name) {
        this.kategori_name = kategori_name;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    public int getHarga_nominal() {
        return harga_nominal;
    }

    public void setHarga_nominal(int harga_nominal) {
        this.harga_nominal = harga_nominal;
    }
}
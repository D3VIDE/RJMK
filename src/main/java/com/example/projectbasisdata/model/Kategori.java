package com.example.projectbasisdata.model;

public class Kategori {
    private int kategori_id;
    private String kategori_name;

    public Kategori(int kategoriId,String kategoriName){
        this.kategori_id = kategoriId;
        this.kategori_name = kategoriName;
    }

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategoriId) {
        this.kategori_id = kategoriId;
    }

    public String getKategori_name() {
        return kategori_name;
    }

    public void setKategori_name(String kategoriName) {
        this.kategori_name = kategoriName;
    }
}

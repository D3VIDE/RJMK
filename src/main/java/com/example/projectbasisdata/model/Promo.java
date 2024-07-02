package com.example.projectbasisdata.model;


import java.time.LocalDate;
import java.util.Date;

public class Promo {
    private int promo_id;
    private String promo_name;
    private double promo_nominal;
    private Date date_start;
    private Date date_end;
    private String kategori_name;
    private String menu_name;
    private String method_name;


    public Promo(String promo_name, double promo_nominal, Date date_start, Date date_end, String kategori_name, String menu_name, String method_name){

        this.promo_name = promo_name;
        this.promo_nominal = promo_nominal;
        this.date_start = date_start;
        this.date_end = date_end;
        this.kategori_name = kategori_name;
        this.menu_name = menu_name;
        this.method_name = method_name;

    }  public Promo(int promo_id ,String promo_name, double promo_nominal, Date date_start, Date date_end, String kategori_name, String menu_name, String method_name){
        this.promo_id = promo_id;
        this.promo_name = promo_name;
        this.promo_nominal = promo_nominal;
        this.date_start = date_start;
        this.date_end = date_end;
        this.kategori_name = kategori_name;
        this.menu_name = menu_name;
        this.method_name = method_name;
    }
    public Promo(String promo_name, double promo_nominal, Date date_start, Date date_end, String kategori_name) {
        this.promo_name = promo_name;
        this.promo_nominal = promo_nominal;
        this.date_start = date_start;
        this.date_end = date_end;
        this.kategori_name = kategori_name;
    }

    public Promo(double promoNominal, String promoName, java.sql.Date dateStart, java.sql.Date dateEnd, String kategoriName, String menuName) {
        this.promo_nominal = promoNominal;
        this.promo_name = promoName;
        this.date_start = dateStart;
        this.date_end = dateEnd;
        this.kategori_name = kategoriName;
    }


    public String getPromo_name() {
        return promo_name;
    }

    public void setPromo_name(String promo_name) {
        this.promo_name = promo_name;
    }

    public double getPromo_nominal() {
        return promo_nominal;
    }

    public void setPromo_nominal(double promo_nominal) {
        this.promo_nominal = promo_nominal;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
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

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public int getPromo_id() {
        return promo_id;
    }

    public void setPromo_id(int promo_id) {
        this.promo_id = promo_id;
    }

    public String getMenuName() {
        return menu_name;
    }

    public String getKategoriName() {
        return kategori_name;
    }

    public double getPromoNominal() {
        return promo_nominal;
    }

    public String getPromoName() {
        return promo_name;
    }
}

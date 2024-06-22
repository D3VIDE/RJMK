package com.example.projectbasisdata.model;


import java.time.LocalDate;

public class Promo {
    private int promo_id;
    private double promo_nominal;
    private LocalDate start_date; //LocalDate
    private LocalDate end_date;

    public Promo(int promo_id,double promo_nominal,LocalDate start_date,LocalDate end_date){
        this.promo_id = promo_id;
        this.promo_nominal = promo_nominal;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getPromo_id() {
        return promo_id;
    }

    public void setPromo_id(int promo_id) {
        this.promo_id = promo_id;
    }

    public double getPromo_nominal() {
        return promo_nominal;
    }

    public void setPromo_nominal(double promo_nominal) {
        this.promo_nominal = promo_nominal;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
}

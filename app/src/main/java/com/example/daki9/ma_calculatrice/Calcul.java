package com.example.daki9.ma_calculatrice;

import java.util.Date;

public class Calcul {
    private int ID;
    private String formule;
    private String valeur;
    private String date;

    public Calcul(int ID, String formule, String valeur, String date) {
        this.setID( ID);
        this.setFormule(formule) ;
        this.setValeur (valeur);
        this.setDate(date );
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFormule() {
        return formule;
    }

    public void setFormule(String formule) {
        this.formule = formule;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "["+ID +"] :   "+formule + "=" +valeur +"\n"+"Le" + date ;
    }
}
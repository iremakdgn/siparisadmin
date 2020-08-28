package com.corba.corbam;

public class masalar {
    private int id;
    private String masano,kapasite,durum;

    public masalar() {
        this.id = 0;
        this.masano = null;
        this.kapasite = null;
        this.durum = null;
    }

    public masalar(int id, String masano, String kapasite, String durum) {
        this.id = id;
        this.masano = masano;
        this.kapasite = kapasite;
        this.durum = durum;
    }
    public masalar(String masano, String kapasite, String durum,String btn) {

        this.masano = masano;
        this.kapasite = kapasite;
        this.durum = durum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMasano() {
        return masano;
    }

    public void setMasano(String masano) {
        this.masano = masano;
    }

    public String getKapasite() {
        return kapasite;
    }

    public void setKapasite(String kapasite) {
        this.kapasite = kapasite;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}

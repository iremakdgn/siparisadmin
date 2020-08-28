package com.corba.corbam;

import java.util.ArrayList;

public class siparisliste {

    private int siparisid;
    private String masano;
    private String tarih;
    private String saat;
    private String fiyat;
    private String ad;
    private String sdetaylst;

    public siparisliste(int siparisid,String masano, String fiyat, String tarih,String saat,String sdetaylst  ) {
       this.siparisid=siparisid;
        this.masano = masano;
        this.tarih = tarih;
        this.saat = saat;
        this.fiyat = fiyat;
        this.sdetaylst = sdetaylst;
    }
    public siparisliste(String masano, String tarih, String saat, String sdetaylst) {
        this.masano = masano;
        this.tarih = tarih;
        this.saat = saat;
        this.sdetaylst = sdetaylst;
    }
    public siparisliste() {
        this.siparisid = 0;
        this.masano = null;
        this.tarih = null;
        this.saat = null;
        this.fiyat = null;
        this.ad = null;
        this.sdetaylst=null;
    }

    public siparisliste(int siparisid, String masano, String tarih, String saat) {
        this.siparisid = siparisid;
        this.masano = masano;
        this.tarih = tarih;
        this.saat = saat;
    }

    public String getSdetaylst() {
        return sdetaylst;
    }

    public void setSdetaylst(String sdetaylst) {
        this.sdetaylst = sdetaylst;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }



    public String getMasano() {
        return masano;
    }

    public void setMasano(String masano) {
        this.masano = masano;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public int getSiparisid() {
        return siparisid;
    }

    public void setSiparisid(int siparisid) {
        this.siparisid = siparisid;
    }

}

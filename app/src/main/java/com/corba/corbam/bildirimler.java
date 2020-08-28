package com.corba.corbam;

public class bildirimler {
    int id;
    String bilmetin;
    String biltarih;
    String bilsaat;
    public bildirimler() {
        this.id = 0;
        this.bilmetin = null;
        this.biltarih = null;
        this.bilsaat = null;
    }

    public bildirimler(int id,String bilmetin) {
        this.bilmetin = bilmetin;
        this.id = id;
    }

    public bildirimler(int id, String bilmetin, String biltarih, String bilsaat) {
        this.id = id;
        this.bilmetin = bilmetin;
        this.biltarih = biltarih;
        this.bilsaat = bilsaat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBilmetin() {
        return bilmetin;
    }

    public void setBilmetin(String bilmetin) {
        this.bilmetin = bilmetin;
    }

    public String getBiltarih() {
        return biltarih;
    }

    public void setBiltarih(String biltarih) {
        this.biltarih = biltarih;
    }

    public String getBilsaat() {
        return bilsaat;
    }

    public void setBilsaat(String bilsaat) {
        this.bilsaat = bilsaat;
    }
}

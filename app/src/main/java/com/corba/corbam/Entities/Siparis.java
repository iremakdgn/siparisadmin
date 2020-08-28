package com.corba.corbam.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Siparis {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("masano")
    @Expose
    private Integer masano;
    @SerializedName("tarih")
    @Expose
    private String tarih;
    @SerializedName("saat")
    @Expose
    private String saat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasano() {
        return masano;
    }

    public void setMasano(Integer masano) {
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
}

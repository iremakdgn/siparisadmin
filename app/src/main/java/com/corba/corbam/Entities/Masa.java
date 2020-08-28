package com.corba.corbam.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Masa {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kapasite")
    @Expose
    private String kapasite;
    @SerializedName("masano")
    @Expose
    private String masano;
    @SerializedName("durum")
    @Expose
    private String durum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKapasite() {
        return kapasite;
    }

    public void setKapasite(String kapasite) {
        this.kapasite = kapasite;
    }

    public String getMasano() {
        return masano;
    }

    public void setMasano(String masano) {
        this.masano = masano;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    @Override
    public String toString() {
        return "Masa{" +
                "id=" + id +
                ", kapasite='" + kapasite + '\'' +
                ", masano='" + masano + '\'' +
                ", durum='" + durum + '\'' +
                '}';
    }
}

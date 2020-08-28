package com.corba.corbam.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kullanici {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kullaniciadi")
    @Expose
    private String kullaniciadi;
    @SerializedName("sifre")
    @Expose
    private String sifre;
    @SerializedName("rol")
    @Expose
    private String rol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKullaniciadi() {
        return kullaniciadi;
    }

    public void setKullaniciadi(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Kullanici{" +
                "id=" + id +
                ", kullaniciadi='" + kullaniciadi + '\'' +
                ", sifre='" + sifre + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}

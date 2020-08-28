package com.corba.corbam.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SiparisListesi {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("spid")
    @Expose
    private Integer spid;
    @SerializedName("menuid")
    @Expose
    private Integer menuid;
    @SerializedName("urunad")
    @Expose
    private String urunad;
    @SerializedName("urunfiyat")
    @Expose
    private String urunfiyat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpid() {
        return spid;
    }

    public void setSpid(Integer spid) {
        this.spid = spid;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public String getUrunad() {
        return urunad;
    }

    public void setUrunad(String urunad) {
        this.urunad = urunad;
    }

    public String getUrunfiyat() {
        return urunfiyat;
    }

    public void setUrunfiyat(String urunfiyat) {
        this.urunfiyat = urunfiyat;
    }
}

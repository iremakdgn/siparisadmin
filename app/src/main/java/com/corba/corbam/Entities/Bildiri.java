package com.corba.corbam.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bildiri {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("bilmetin")
    @Expose
    private String bilmetin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBilmetin() {
        return bilmetin;
    }

    public void setBilmetin(String bilmetin) {
        this.bilmetin = bilmetin;
    }

}

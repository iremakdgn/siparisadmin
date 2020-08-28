package com.corba.corbam.Services;

import com.corba.corbam.Entities.Siparis;
import com.corba.corbam.Entities.SiparisListesi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class SiparisListesiService {
    static RestInterface restInterface;
    public List<SiparisListesi> GetSiparisListesi() {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<SiparisListesi>> call = restInterface.getSiparisListesi();
            List<SiparisListesi> siparisListesi = call.execute().body();
            return siparisListesi;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public SiparisListesi PostSiparisListesi(String spid, int menuid, String urunad, String urunfiyat) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<SiparisListesi> call = restInterface.postSiparisListesi(spid, menuid, urunad, urunfiyat);
            SiparisListesi siparisListesi = call.execute().body();
            return siparisListesi;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<SiparisListesi> GetSiparisListesiBySpId(int spid) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<SiparisListesi>> call = restInterface.getSiparisListesiBySpId(spid);
            List<SiparisListesi> siparisListesi = call.execute().body();
            return siparisListesi;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public SiparisListesi DeleteSiparisListesiBySpId(int spid) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<SiparisListesi> call = restInterface.deleteSiparisListesiBySpId(spid);
            SiparisListesi siparisListesi = call.execute().body();
            return siparisListesi;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

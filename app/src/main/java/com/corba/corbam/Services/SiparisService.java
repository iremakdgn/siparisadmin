package com.corba.corbam.Services;

import com.corba.corbam.Entities.Siparis;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

public class SiparisService {
    static RestInterface restInterface;

    public List<Siparis> GetSiparisler() {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<Siparis>> call = restInterface.getSiparisler();
            List<Siparis> siparisler = call.execute().body();
            return siparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Siparis GetSiparisByMasano(int masano, Date tarih, Time saat) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Siparis> call = restInterface.getSiparisByMasano(masano, tarih, saat);
            Siparis siparisler = call.execute().body();
            return siparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Siparis> GetSiparislerByTarihSaat(int gun, int ay, int yil, int saat) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<Siparis>> call = restInterface.getSiparislerByTarihSaat(gun,ay,yil, saat);
            List<Siparis> siparisler = call.execute().body();
            return siparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Siparis PostSiparis(String masano, DateFormat tarih, DateFormat saat) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Siparis> call = restInterface.postSiparis(masano, tarih, saat);
            Siparis siparisler = call.execute().body();
            return siparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Siparis> GetSiparislerByTarih(int gun, int ay, int yil) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<Siparis>> call = restInterface.getSiparislerByTarih(gun,ay,yil);
            List<Siparis> siparisler = call.execute().body();
            return siparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Siparis> GetSiparislerByIkiTarih(int basGun, int basAy, int basYil, int bitGun,int bitAy, int bitYil) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<Siparis>> call = restInterface.getSiparislerByIkiTarih(basGun,basAy,basYil,bitGun,bitAy,bitYil);
            List<Siparis> siparisler = call.execute().body();
            return siparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Siparis DeleteSiparislerById(int id) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Siparis> call = restInterface.deleteSiparislerById(id);
            Siparis siparisler = call.execute().body();
            return siparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.corba.corbam.Services;

import com.corba.corbam.Entities.Kullanici;
import com.corba.corbam.Entities.Menu;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class KullaniciService {
    static RestInterface restInterface;

    public Kullanici Login(String kullaniciAdi, String sifre, String rol) {
        try{
        restInterface = APIClient.getClient().create(RestInterface.class);
        Call<Kullanici> call = restInterface.login(kullaniciAdi, sifre, rol);

            Kullanici kullanici = call.execute().body();
            return kullanici;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Kullanici> GetKullanici() {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<Kullanici>> call = restInterface.getKullanici();
            List<Kullanici> kullanici = call.execute().body();
            return kullanici;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void UpdateKullanici(String kullaniciadi, String sifre, String rol) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Void> call = restInterface.updateKullanici(kullaniciadi,sifre,rol);
            call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

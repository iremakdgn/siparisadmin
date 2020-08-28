package com.corba.corbam.Services;

import com.corba.corbam.Entities.YdkSiparisler;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class YdkSiparislerService {
    static RestInterface restInterface;

    public List<YdkSiparisler> GetYdkSiparisler(String masano) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<YdkSiparisler>> call = restInterface.getYdkSiparisler(masano);
            List<YdkSiparisler> ydkSiparisler = call.execute().body();
            return ydkSiparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public YdkSiparisler DeleteYdkSiparislerByMasaNo(String masano) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<YdkSiparisler> call = restInterface.deleteYdkSiparislerByMasaNo(masano);
            YdkSiparisler ydkSiparisler = call.execute().body();
            return ydkSiparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public YdkSiparisler DeleteYdkSiparisler(int id) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<YdkSiparisler> call = restInterface.deleteYdkSiparisler(id);
            YdkSiparisler ydkSiparisler = call.execute().body();
            return ydkSiparisler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void PostYdkSiparisler(YdkSiparisler ydkSiparis) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Void> call = restInterface.postYdkSiparisler(ydkSiparis);
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

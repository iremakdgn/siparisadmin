package com.corba.corbam.Services;

import com.corba.corbam.Entities.Bildiri;
import com.corba.corbam.Entities.Kullanici;
import com.corba.corbam.Entities.Masa;
import com.corba.corbam.Entities.YdkSiparisler;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class BildiriService {
    static RestInterface restInterface;

    public List<Bildiri> GetBildiriler() {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<Bildiri>> call = restInterface.getBildiriler();
            List<Bildiri> bildiriler = call.execute().body();
            return bildiriler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Bildiri DeleteBildirilerById(int id) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Bildiri> call = restInterface.deleteBildirilerById(id);
            Bildiri bildiriler = call.execute().body();
            return bildiriler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bildiri PostBildiriler(Bildiri bildiri) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Bildiri> call = restInterface.postBildiriler(bildiri);
            Bildiri bildiriler = call.execute().body();
            return bildiriler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

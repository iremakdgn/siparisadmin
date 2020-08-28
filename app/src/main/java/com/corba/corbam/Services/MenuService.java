package com.corba.corbam.Services;

import com.corba.corbam.Entities.Menu;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class MenuService {
    static RestInterface restInterface;

    public List<Menu> GetMenu() {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<Menu>> call = restInterface.getMenu();
            List<Menu> menuler = call.execute().body();
            return menuler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Menu GetMenuById(int urunid) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Menu> call = restInterface.getMenuById(urunid);
            Menu menuler = call.execute().body();
            return menuler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Menu> GetMenuDesc() {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<List<Menu>> call = restInterface.getMenuDesc();
            List<Menu> menuler = call.execute().body();
            return menuler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Menu DeleteMenuById(int id) {
        try {
            restInterface = APIClient.getClient().create(RestInterface.class);
            Call<Menu> call = restInterface.deleteMenuById(id);
            Menu menuler = call.execute().body();
            return menuler;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

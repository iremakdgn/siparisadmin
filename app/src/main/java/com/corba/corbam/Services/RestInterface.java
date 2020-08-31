package com.corba.corbam.Services;

import com.corba.corbam.Entities.Bildiri;
import com.corba.corbam.Entities.Kullanici;
import com.corba.corbam.Entities.Masa;
import com.corba.corbam.Entities.Menu;
import com.corba.corbam.Entities.Siparis;
import com.corba.corbam.Entities.SiparisListesi;
import com.corba.corbam.Entities.YdkSiparisler;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RestInterface {
    @GET("Masalar/GetMasalar")
    Call<List<Masa>> getMasa();

    @GET("Masalar/GetMasalarDesc")
    Call<List<Masa>> getMasalarDesc();

    @GET("Masalar/GetMasalarByMasaNo")
    Call<Masa> getMasaByMasaNo(@Query("masano") String masano);

    @PUT("Masalar")
    Call<Masa> UpdateMasalar(@Query("masano") String masano);

    @GET("Bildiriler")
    Call<List<Bildiri>> getBildiri();

    @GET("Kullanici/Login")
    Call<Kullanici> login(@Query("kullaniciadi") String kullaniciadi, @Query("sifre") String sifre, @Query("rol") String rol);

    @GET("Siparisler/GetSiparisler")
    Call<List<Siparis>> getSiparisler();

    @POST("Siparisler")
    Call<Siparis> postSiparis(@Query("masano") String masano, @Query("tarih") DateFormat tarih, @Query("saat") DateFormat saat);

    @GET("Siparisler/getSiparisByMasano")
    Call<Siparis> getSiparisByMasano(@Query("masano") int masano, @Query("tarih") Date tarih, @Query("saat") Time saat);

    @GET("YdkSiparisler")
    Call<List<YdkSiparisler>> getYdkSiparisler(@Query("masano") String masano);

    @DELETE("YdkSiparisler")
    Call<YdkSiparisler> deleteYdkSiparislerByMasaNo(@Query("masano") String masano);

    @DELETE("YdkSiparisler")
    Call<YdkSiparisler> deleteYdkSiparisler(@Query("id") int id);

    @GET("Menu/GetMenu")
    Call<List<Menu>> getMenu();

    @GET("Menu/GetMenuDesc")
    Call<List<Menu>> getMenuDesc();

    @GET("Menu/GetMenuById")
    Call<Menu> getMenuById(@Query("urunid") int urunid);

    @GET("SiparisListesi/GetSiparisListesi")
    Call<List<SiparisListesi>> getSiparisListesi();

    @POST("SiparisListesi")
    Call<SiparisListesi> postSiparisListesi(@Query("spid") String spid, @Query("menuid") int menuid, @Query("urunad") String urunad, @Query("urunfiyat") String urunfiyat);

    @PUT("Masalar")
    Call<Void> updateMasaDurumByMasaNo(@Query("masano") String glnmasano, @Query("durum") String durum);

    @POST("YdkSiparisler")
    Call<Void> postYdkSiparisler(@Body YdkSiparisler ydkSiparis);

    @GET("Bildiriler")
    Call<List<Bildiri>> getBildiriler();

    @DELETE("Bildiriler")
    Call<Bildiri> deleteBildirilerById(@Query("id") int id);

    @GET("Siparisler/GetSiparislerByTarih")
    Call<List<Siparis>> getSiparislerByTarih(@Query("gun") int gun,@Query("ay") int ay,@Query("yil") int yil);

    @GET("SiparisListesi/GetSiparisListesiBySpId")
    Call<List<SiparisListesi>> getSiparisListesiBySpId(@Query("spid") int spid);

    @POST("Bildiriler")
    Call<Bildiri> postBildiriler(@Body Bildiri bildiri);

    @DELETE("Siparisler")
    Call<Siparis> deleteSiparislerById(@Query("id") int id);

    @DELETE("SiparisListesi")
    Call<SiparisListesi> deleteSiparisListesiBySpId(@Query("spid") int spid);

    @DELETE("Menu")
    Call<Menu> deleteMenuById(@Query("id") int id);

    @GET("Kullanici")
    Call<List<Kullanici>> getKullanici();

    @PUT("Kullanici")
    Call<Void> updateKullanici(@Query("kullaniciadi") String kullaniciadi, @Query("sifre") String sifre, @Query("rol") String rol);

    @DELETE("Masalar")
    Call<Masa> deleteMasalarById(@Query("id") int id);

    @POST("Masalar")
    Call<Void> postMasalar(@Body Masa masa);

    @GET("Siparisler/GetSiparislerByTarihSaat")
    Call<List<Siparis>> getSiparislerByTarihSaat(@Query("gun") int gun,@Query("ay") int ay, @Query("yil") int yil, @Query("saat") int saat);

    @GET("Siparisler/GetSiparislerByIkiTarih")
    Call<List<Siparis>> getSiparislerByIkiTarih(@Query("basGun") int basGun,@Query("basAy") int basAy,@Query("basYil")int basYil,@Query("bitGun") int bitGun,@Query("bitAy") int bitAy,@Query("bitYil") int bitYil);
}

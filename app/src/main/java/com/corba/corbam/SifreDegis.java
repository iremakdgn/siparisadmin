package com.corba.corbam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.corba.corbam.Entities.Bildiri;
import com.corba.corbam.Entities.Kullanici;
import com.corba.corbam.Services.BildiriService;
import com.corba.corbam.Services.KullaniciService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class SifreDegis extends AppCompatActivity {

    private TextView geskikul;//veritabanındaki adminin kullanıcı adı
    private TextView geskisif;//veritabanındaki adminin şifresi
    private EditText akultext;//güncellenecek adminin kullanıcı adı
    private EditText asifretext;//güncellenecek adminin şifresi
    private TextView eskikul; //veritabanındaki garsonun kullanıcıadı
    private TextView eskisif;//veritabanındaki garsonun şifresi
    private EditText gkultext; //gğncellenecek garson kullanıcı adı
    private EditText gsifretext;//güncellenecek garson şifresi
    ConnectionClass connectionClass;
    private Button button; //adminin butonu
    private Button button2; //garsonun butonu
    private  Button cksbtn;
    int da=0;
    int dg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_degis);
        geskikul=(TextView)findViewById(R.id.geskikul);
        geskisif=(TextView)findViewById(R.id.geskisif);
        akultext=(EditText)findViewById(R.id.akultext);
        asifretext=(EditText)findViewById(R.id.asifretext);
        eskikul=(TextView)findViewById(R.id.eskikul);
        eskisif=(TextView)findViewById(R.id.eskisif);
        gkultext=(EditText)findViewById(R.id.gkultext);
        gsifretext=(EditText)findViewById(R.id.gsifretext);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        cksbtn=(Button)findViewById(R.id.cksbtn);

        /*verigetir doregister = new verigetir();
        System.out.println("1");
        doregister.execute("");*/
        new GetKullanici().execute();

        cksbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(SifreDegis.this, girisAdminActivity.class);
                startActivity(intentbtn1);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(SifreDegis.this).create();

                builder.setMessage("Yönetici bilgisi değişikliğinden sonra giriş ekranına yönlendiriliceksiniz .Onaylıyor musunuz?");
                builder.setButton(AlertDialog.BUTTON_POSITIVE,"EVET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*adminguncelle doregister1 = new adminguncelle();
                        System.out.println("1");
                        doregister1.execute("");*/
                        new UpdateAdmin().execute();

                    }
                });
                builder.setButton(AlertDialog.BUTTON_NEGATIVE,"VAZGEÇ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.show();
                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.aktif));
                builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.pasif));

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(SifreDegis.this).create();

                builder.setMessage("Garson bilgileri değiştirilsin mi?");
                builder.setButton(AlertDialog.BUTTON_POSITIVE,"EVET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*garsonguncelle doregister1 = new garsonguncelle();
                        System.out.println("1");
                        doregister1.execute("");*/

                        new UpdateGarson().execute();

                    }
                });
                builder.setButton(AlertDialog.BUTTON_NEGATIVE,"VAZGEÇ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.show();
                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.aktif));
                builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.pasif));

            }
        });
    }


    public class GetKullanici extends android.os.AsyncTask<Void, Void, List<Kullanici>> {
        @Override
        protected List<Kullanici> doInBackground(Void... voids) {
            KullaniciService k = new KullaniciService();
            List<Kullanici> list = k.GetKullanici();
            return list;
        }

        @Override
        protected void onPostExecute(List<Kullanici> kullanicis) {
            for (Kullanici k : kullanicis) {
                if (kullanicis != null) {

                    if(k.getRol().equals("admin")){

                        geskikul.setText("Kullanıcı adı : "+k.getKullaniciadi());
                        geskisif.setText("Şifre : "+k.getSifre());
                        akultext.setText(k.getKullaniciadi());
                        asifretext.setText(k.getSifre());

                    }
                    else if(k.getRol().equals("garson")){
                        eskikul.setText("Kullanıcı adı : "+k.getKullaniciadi());
                        eskisif.setText("Şifre : "+k.getSifre());
                        gkultext.setText(k.getKullaniciadi());
                        gsifretext.setText(k.getSifre());

                    }
                }
            }
        }
    }

    /*public  class verigetir extends AsyncTask<String, String, String> {

        String z = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con =connectionClass.connectDatabase();
                if (con == null) {
                    z = "Lütfen internet bağlantınızı kontrol edin";
                } else {

                    Statement st=con.createStatement();
                    String query = "select * from kullanici;";
                    Statement stmt = con.createStatement();
                    ResultSet rs=stmt.executeQuery(query);
                    while(rs.next())
                    {System.out.println("adminnnnnn1");
                        if(rs.getString("rol").equals("admin")){

                            geskikul.setText("Kullanıcı adı : "+rs.getString("kullaniciadi"));
                            geskisif.setText("Şifre : "+rs.getString("sifre"));
                            akultext.setText(rs.getString("kullaniciadi"));
                            asifretext.setText(rs.getString("sifre"));

                        }
                        else if(rs.getString("rol").equals("garson")){
                            eskikul.setText("Kullanıcı adı : "+rs.getString("kullaniciadi"));
                            eskisif.setText("Şifre : "+rs.getString("sifre"));
                            gkultext.setText(rs.getString("kullaniciadi"));
                            gsifretext.setText(rs.getString("sifre"));

                        }
                    }
                    z = "Ekleme İşlemi Başarılı Bir Şekilde Oldu";

                }


            } catch (Exception ex) {


                System.out.println(z+ex);
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s) {


        }


    }*/


    public class UpdateAdmin extends android.os.AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            KullaniciService kullaniciService = new KullaniciService();
            kullaniciService.UpdateKullanici(akultext.getText().toString(),asifretext.getText().toString(),"admin");
            da=1;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Toast.makeText(getBaseContext(), "Yönetici Bilgileri Başarıyla Güncellendi." , Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SifreDegis.this, GirisActivity.class);
            startActivity(intent);

            super.onPostExecute(aVoid);
        }
    }
    /*public  class adminguncelle extends AsyncTask<String, String, String> {

        String z = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con =connectionClass.connectDatabase();
                if (con == null) {
                    z = "Lütfen internet bağlantınızı kontrol edin";
                } else {
                    Statement st=con.createStatement();
                    String query = "UPDATE kullanici SET kullaniciadi='"+String.valueOf(akultext.getText())+
                            "',sifre='"+String.valueOf(asifretext.getText())+"' where rol='admin';";
                    Statement stmt = con.createStatement();
                    stmt.execute(query);
                    da=1;
                    z = "Yönetici bilgileri başarıyla güncellendi.";

                }


            } catch (Exception ex) {


                System.out.println(z+ex);
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SifreDegis.this, GirisActivity.class);

                    startActivity(intent);

        }


    }*/


    public class UpdateGarson extends android.os.AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            KullaniciService kullaniciService = new KullaniciService();
            kullaniciService.UpdateKullanici(gkultext.getText().toString(),gsifretext.getText().toString(),"garson");
            dg=1;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Toast.makeText(getBaseContext(), "Garson Bilgileri Başarıyla Güncellendi." , Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SifreDegis.this, girisAdminActivity.class);
            startActivity(intent);

            super.onPostExecute(aVoid);
        }
    }
    /*public  class garsonguncelle extends AsyncTask<String, String, String> {

        String z = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con =connectionClass.connectDatabase();
                if (con == null) {
                    z = "Lütfen internet bağlantınızı kontrol edin";
                } else {
                    Statement st=con.createStatement();
                    String query = "UPDATE kullanici SET kullaniciadi='"+String.valueOf(gkultext.getText())+"',sifre='"+String.valueOf(gsifretext.getText())+"' where rol='garson';";
                    Statement stmt = con.createStatement();
                    stmt.execute(query);
                    z = "Garson bilgileri başarıyla güncellendi.";

                    dg=1;

                }


            } catch (Exception ex) {


                System.out.println(z+ex);
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(SifreDegis.this, girisAdminActivity.class);

            startActivity(intent);
        }


    }*/

}

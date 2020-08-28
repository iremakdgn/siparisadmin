package com.corba.corbam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.sql.Connection;
import java.sql.Statement;

public class menuGuncelle  extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText gncmenuad,gncmenufiyat;
    Button btngeri,btnguncel;
    Spinner spinner1;
    menu dsrdnglenmenu;
    public  static menu gln;
    String tursec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_guncelle);
        gncmenuad=(EditText)findViewById(R.id.eklemenuad);
        gncmenufiyat=(EditText)findViewById(R.id.eklemenufiyat);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        btngeri=(Button)findViewById(R.id.cksbtn);
        btnguncel=(Button)findViewById(R.id.btngncl);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tursec= spinner1.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        btngeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbtn11= new Intent(menuGuncelle.this, menuIslem.class);
                startActivity(intentbtn11);
            }
        });
        menuIslem stmunu=new menuIslem();
        menu aa=stmunu.menugonder;
        gln=aa;
        gncmenuad.setText(aa.getAd());
        gncmenufiyat.setText(aa.getFiyat());
        if(aa.getTur().equals("Yiyecek")){
            spinner1.setSelection(0);
        }
        else if(aa.getTur().equals("İçecek")){
            spinner1.setSelection(1);
        }

        btnguncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog builder = new AlertDialog.Builder(menuGuncelle.this).create();

                builder.setMessage("Ürün güncellensin mi?");
                builder.setButton(AlertDialog.BUTTON_POSITIVE,"EVET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        menugncl doregister = new menugncl();
                        doregister.degeral(String.valueOf(gncmenuad.getText()),String.valueOf(gncmenufiyat.getText()),tursec);
                        System.out.println("1");
                        doregister.execute("");
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
    public  class menugncl extends AsyncTask<String, String, String> {

        String z = "";
        String ad;
        String fiyat;
        String tur;
        public void degeral(String mad,String mfiyat,String mtur){
            ad=mad;
            fiyat=mfiyat;
            tur=mtur;
        }
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
                    z="Lütfen sayısal Değer Giriniz";
                    double aa=Double.parseDouble(fiyat);
                    String query = "UPDATE menu SET ad='"+ad+"',fiyat='"+aa+"',tur='"+tur+"' WHERE id="+gln.getId()+";";
                    Statement stmt = con.createStatement();
                    stmt.execute(query);
                    z="Güncelleme İşlemi Başarılı Bir Şekilde Oldu";
                }
            } catch (Exception ex) {

                z = "Exceptions " + ex;
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(menuGuncelle.this, menuIslem.class);

            startActivity(intent);

        }


    }
}

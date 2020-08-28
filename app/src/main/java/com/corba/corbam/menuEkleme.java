package com.corba.corbam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.Statement;

public class menuEkleme extends AppCompatActivity {
     String tur2="Yiyecek";
    ConnectionClass connectionClass;
    EditText eklemenuad,eklemenufiyat;
    Spinner spinner;
    Button btngeri,btnekle;
    String tursec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuekleme_activity);
        eklemenuad=(EditText)findViewById(R.id.eklemenuad);
        eklemenufiyat=(EditText)findViewById(R.id.eklemenufiyat);
        btngeri=(Button)findViewById(R.id.cksbtn);
        btnekle=(Button)findViewById(R.id.btngncl);
        spinner=(Spinner)findViewById(R.id.spinner1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               tursec= spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        btngeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentbtn11= new Intent(menuEkleme.this, menuIslem.class);
                startActivity(intentbtn11);
            }
        });
        btnekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(eklemenuad.getText()+"addddd");
                System.out.println(eklemenufiyat.getText()+"fiyatttt");

                menuekle doregister = new menuekle();
                doregister.degeral(String.valueOf(eklemenuad.getText()),String.valueOf(eklemenufiyat.getText()),String.valueOf(tursec));
                System.out.println("1");
                doregister.execute("");
            }
        });

    }

    public  class menuekle extends AsyncTask<String, String, String> {

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
                    Statement st=con.createStatement();
                            String query = "INSERT INTO menu(ad,fiyat,tur) VALUES ('" + ad + "','" + fiyat + "','"+tur+"');";
                            Statement stmt = con.createStatement();
                            stmt.execute(query);
                            z = "Ekleme İşlemi Başarılı Bir Şekilde Oldu";

                    }


            } catch (Exception ex) {


                System.out.println(z+ex);
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(menuEkleme.this, menuIslem.class);

            startActivity(intent);

        }


    }



}
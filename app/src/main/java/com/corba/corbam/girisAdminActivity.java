package com.corba.corbam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class girisAdminActivity extends AppCompatActivity {

    Button nav_sip,nav_btrh,nav_ikitrh,nav_bldr,nav_masais,nav_menuis,nav_sfrdgs,nav_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_admin);
        nav_sip=(Button)findViewById(R.id.nav_sip);
        nav_btrh=(Button)findViewById(R.id.nav_btrh);
        nav_ikitrh=(Button)findViewById(R.id.nav_ikitrh);
        nav_bldr=(Button)findViewById(R.id.nav_bldr);
        nav_masais=(Button)findViewById(R.id.nav_masais);
        nav_menuis=(Button)findViewById(R.id.nav_menuis);
        nav_sfrdgs=(Button)findViewById(R.id.nav_sfrdgs);
        nav_out=(Button)findViewById(R.id.nav_out);


        nav_sip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intentbtn1= new Intent(girisAdminActivity.this, adminSiparis.class);
                    startActivity(intentbtn1);
            }
        });
        nav_btrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(girisAdminActivity.this, belliTarih.class);
                startActivity(intentbtn1);
            }
        });
        nav_ikitrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(girisAdminActivity.this,IkiTarih.class);
                startActivity(intentbtn1);
            }
        });
        nav_bldr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(girisAdminActivity.this, adminBildiri.class);
                startActivity(intentbtn1);
            }
        });
        nav_masais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(girisAdminActivity.this, masaIslem.class);
                startActivity(intentbtn1);
            }
        });
        nav_menuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(girisAdminActivity.this, menuIslem.class);
                startActivity(intentbtn1);
            }
        });
        nav_sfrdgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(girisAdminActivity.this, SifreDegis.class);
                startActivity(intentbtn1);
            }
        });
        nav_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(girisAdminActivity.this, GirisActivity.class);
                startActivity(intentbtn1);
            }
        });
    }
}

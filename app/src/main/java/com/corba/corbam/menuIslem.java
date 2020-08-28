package com.corba.corbam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corba.corbam.Entities.Bildiri;
import com.corba.corbam.Entities.Menu;
import com.corba.corbam.Entities.Siparis;
import com.corba.corbam.Entities.SiparisListesi;
import com.corba.corbam.Services.BildiriService;
import com.corba.corbam.Services.MenuService;
import com.corba.corbam.Services.SiparisListesiService;
import com.corba.corbam.Services.SiparisService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class menuIslem extends AppCompatActivity {

    ArrayList<menu> menulist = new ArrayList<menu>();
    private ListView listmenu;
    ConnectionClass connectionClass;
    Button cksbtn,btnekle;

    public static menu menugonder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_islem);
        cksbtn=(Button)findViewById(R.id.cksbtn);
        btnekle=(Button)findViewById(R.id.btngncl);
        listmenu=(ListView)findViewById(R.id.listmenu);

        /*listeleme doregister2 = new listeleme();
        System.out.println("1");
        doregister2.execute("");*/
        new GetMenuDesc().execute();
        System.out.println(menulist.size());
        RefreshAdapter();

        cksbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1= new Intent(menuIslem.this, girisAdminActivity.class);
                startActivity(intentbtn1);
            }
        });
        btnekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbtn1= new Intent(menuIslem.this, menuEkleme.class);
                startActivity(intentbtn1);
            }
        });

    }
    private void RefreshAdapter() {
        myAdaptor adaptor=new myAdaptor(menuIslem.this,1,menulist);
        listmenu.setAdapter(adaptor);
    }


    public class GetMenuDesc extends android.os.AsyncTask<Void, Void, List<Menu>> {
        @Override
        protected List<Menu> doInBackground(Void... voids) {
            menulist.clear();
            MenuService m = new MenuService();
            List<Menu> list = m.GetMenuDesc();
            return list;
        }

        @Override
        protected void onPostExecute(List<Menu> menus) {
            //menulist.clear();
            for (Menu m : menus) {

                if(menus !=null) {
                    menulist.add(new menu(m.getId(), m.getAd(), m.getFiyat(), m.getTur()));
                }

                }


            RefreshAdapter();
        }
    }
    /*public  class listeleme extends AsyncTask<String, String, String> {

        String z = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            menulist.clear();
            System.out.println("1");
            try {
                Connection con =connectionClass.connectDatabase();
                if (con == null) {
                    z = "Lütfen internet bağlantınızı kontrol edin";
                } else {
                    System.out.println("2");
                    String query = "select * from menu order by id desc;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        menulist.add(new menu(rs.getInt("id"),rs.getString("ad"),rs.getString("fiyat"),rs.getString("tur")));
                    }
                }
            } catch (Exception ex) {

                z = "Exceptions " + ex;
                System.out.println(z);
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s) {


        }


    }*/


    public class myAdaptor extends ArrayAdapter<menu> {
        ArrayList<menu> arr;
        private TextView text;
        LayoutInflater layoutInflater=getLayoutInflater();

        public myAdaptor(Context context, int textViewResourceId, ArrayList<menu> mList){
            super(context,textViewResourceId,mList);
            this.arr=mList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View satirView;
            satirView = layoutInflater.inflate(R.layout.menu_dizayn, null);

            TextView mnid = (TextView) satirView.findViewById(R.id.mnid);
            TextView lblmnad = (TextView) satirView.findViewById(R.id.lblmnad);
            TextView lblmnfiyat = (TextView) satirView.findViewById(R.id.lblmnfiyat);
            TextView lblmntur = (TextView) satirView.findViewById(R.id.lblmntur);
            ImageButton btnsil=(ImageButton)satirView.findViewById(R.id.btnsil);
            ImageButton btngncl=(ImageButton)satirView.findViewById(R.id.btngncl);


            final menu sp=arr.get(position);
            lblmnad.setText(sp.getAd());
            lblmnfiyat.setText(sp.getFiyat());
            lblmntur.setText(sp.getTur());
            mnid.setText(String.valueOf(sp.getId()));
            mnid.setVisibility(TextView.VISIBLE);

            btnsil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog builder = new AlertDialog.Builder(menuIslem.this).create();

                    builder.setMessage("Ürün menüden çıkarılsın mı?");
                    builder.setButton(AlertDialog.BUTTON_POSITIVE,"EVET", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            /*silbildiri doregister = new silbildiri();
                            System.out.println("1");
                            doregister.silincekid(sp.getId());
                            doregister.execute("");*/
                            new AsyncSil(sp.getId()).execute();
                            arr.remove(position);
                            RefreshAdapter();
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

            btngncl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menugonder=sp;
                    Intent intentbtn1= new Intent(menuIslem.this, menuGuncelle.class);
                    startActivity(intentbtn1);
                      }
            });

            return satirView;
        }



        public class AsyncSil extends android.os.AsyncTask<Void, Void, Void> {
            int id;

            public AsyncSil(int id) {
                this.id = id;
            }

            @Override
            protected Void doInBackground(Void... params) {

                MenuService menuService = new MenuService();
                menuService.DeleteMenuById(id);

                return null;
            }
        }
    }
        /*public  class silbildiri extends AsyncTask<String, String, String> {

            int glnid;
            public void silincekid(int id){
                System.out.println("ggsgfsggs"+id);
                glnid=id;
            }

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
                        String query1 = "DELETE FROM menu WHERE id="+glnid+";";
                        Statement stmt1 = con.createStatement();
                        stmt1.execute(query1);

                        z="SİLİNDİ";

                    }
                    listeleme doregister = new listeleme();
                    System.out.println("1");
                    doregister.execute("");
                    System.out.println(menulist.size());
                } catch (Exception ex) {

                    z = "Exceptions " + ex;
                    System.out.println(z);
                }

                return z;
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();

            }


        }*/

    }


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

import com.corba.corbam.Entities.Siparis;
import com.corba.corbam.Entities.SiparisListesi;
import com.corba.corbam.Services.BildiriService;
import com.corba.corbam.Services.SiparisListesiService;
import com.corba.corbam.Services.SiparisService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class adminSiparis extends AppCompatActivity {

    ArrayList<siparisliste> menulist = new ArrayList<siparisliste>();
    private ListView listsViewsiparisler;

    Button cksbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_siparis);
        listsViewsiparisler = (ListView) findViewById(R.id.listsViewsiparisler);
        cksbtn = (Button) findViewById(R.id.cksbtn);

        /*listeleme doregister = new listeleme();
        System.out.println("1");
        doregister.execute("");*/

        new AsyncListele().execute();
        System.out.println(menulist.size());
        RefreshAdapter();

        cksbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1 = new Intent(adminSiparis.this, girisAdminActivity.class);
                startActivity(intentbtn1);
            }
        });

    }

    private void RefreshAdapter() {
        myAdaptor adaptor = new myAdaptor(adminSiparis.this, 1, menulist);
        listsViewsiparisler.setAdapter(adaptor);
    }

    public class AsyncListele extends android.os.AsyncTask<Void, Void, Void> {
        //pardon dogru yanlıs ama mecburmussun boyle olmasına :P normalde list leri alırsın post execute de işlem yaparsın

        @Override
        protected Void doInBackground(Void... params) {
            menulist.clear();
            SiparisService siparisService = new SiparisService();
            SiparisListesiService siparisListesiService = new SiparisListesiService();
            List<Siparis> siparisList = siparisService.GetSiparisler();

            for (Siparis siparis : siparisList) {

                siparisliste spal = new siparisliste();
                String menuad = "";
                Double top = 0.0;
                List<SiparisListesi> siparisListesis = siparisListesiService.GetSiparisListesiBySpId(siparis.getId());
                for (SiparisListesi s : siparisListesis) {
                    menuad = menuad + s.getUrunad();
                    top = top + Double.parseDouble(s.getUrunfiyat());
                }

                spal.setSiparisid(siparis.getId());
                spal.setSdetaylst(menuad);
                spal.setTarih(Helper.formatDate(siparis.getTarih()));
                System.out.println("5");
                spal.setSaat(siparis.getSaat());
                spal.setMasano(siparis.getMasano().toString());
                spal.setFiyat(String.valueOf(top));
                menulist.add(new siparisliste(spal.getSiparisid(), spal.getMasano(), spal.getFiyat(), spal.getTarih(), spal.getSaat(), spal.getSdetaylst()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RefreshAdapter();
            super.onPostExecute(aVoid);
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
                    String query = "select * from siparisler order by id desc;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        System.out.println("3");
                        siparisliste spal=new siparisliste();
                        String menuad = "";
                        Double top=0.0;
                        String query1 = " select * from siparislistesi where spid="+rs.getInt("id");
                        Statement stmt1 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        while (rs1.next()){
                            System.out.println("4");

                                menuad = menuad+rs1.getString("urunad")+" , ";
                            System.out.println("1");
                                top=top+Double.parseDouble(rs1.getString("urunfiyat"));
                            System.out.println("1");


                        }
                        SimpleDateFormat tarih = new SimpleDateFormat("dd-MM-yyyy");
                        spal.setSiparisid(rs.getInt("id"));
                        spal.setSdetaylst(menuad);
                        spal.setTarih((tarih.format(rs.getDate("tarih"))).toString());
                        System.out.println("5");
                        spal.setSaat(rs.getString("saat").toString());
                        spal.setMasano(rs.getString("masano"));
                        spal.setFiyat(String.valueOf(top));
                        menulist.add(new siparisliste(spal.getSiparisid(),spal.getMasano(),spal.getFiyat(),spal.getTarih(),spal.getSaat(),spal.getSdetaylst()));
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


    public class myAdaptor extends ArrayAdapter<siparisliste> {
        ArrayList<siparisliste> arr;
        private TextView text;
        LayoutInflater layoutInflater = getLayoutInflater();

        public myAdaptor(Context context, int textViewResourceId, ArrayList<siparisliste> mList) {
            super(context, textViewResourceId, mList);
            this.arr = mList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View satirView;
            satirView = layoutInflater.inflate(R.layout.siparis_row_layout, null);

            final TextView spid = (TextView) satirView.findViewById(R.id.spid);
            TextView listmenu = (TextView) satirView.findViewById(R.id.listmenu);
            TextView lbltarih = (TextView) satirView.findViewById(R.id.lbltarih);
            TextView lblsaat = (TextView) satirView.findViewById(R.id.lblsaat);
            TextView lblmasano = (TextView) satirView.findViewById(R.id.lblmasano);
            TextView lbltplmfiyat = (TextView) satirView.findViewById(R.id.lbltplmfiyat);
            ImageButton btndlt = (ImageButton) satirView.findViewById(R.id.btndlt);


            final siparisliste sp = arr.get(position);
            listmenu.setText(sp.getSdetaylst());
            lblmasano.setText("Masa NO : " + sp.getMasano());
            lbltarih.setText("Tarih : " + sp.getTarih());
            lblsaat.setText("Saat : " + sp.getSaat());
            lbltplmfiyat.setText("Toplam : " + sp.getFiyat() + " TL");
            spid.setText(String.valueOf(sp.getSiparisid()));
            spid.setVisibility(TextView.VISIBLE);

            btndlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog builder = new AlertDialog.Builder(adminSiparis.this).create();

                    builder.setMessage("Bu siparişi silerseniz bununla alakalı bilgilerde silinecektir .Devam Etmek ister misiniz ?");
                    builder.setButton(AlertDialog.BUTTON_POSITIVE, "EVET", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            /*silsiparis doregister = new silsiparis();
                            System.out.println("1");
                            doregister.silincekid(sp.getSiparisid());
                            doregister.execute("");*/
                            new AsyncSiparisSil(sp.getSiparisid()).execute();
                            arr.remove(position);
                            RefreshAdapter();
                        }
                    });
                    builder.setButton(AlertDialog.BUTTON_NEGATIVE, "VAZGEÇ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    builder.show();
                    builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.aktif));
                    builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.pasif));
                }
            });

            return satirView;
        }


        public class AsyncSiparisSil extends android.os.AsyncTask<Void, Void, Void> {
            int spid;

            public AsyncSiparisSil(int spid) {
                this.spid = spid;
            }

            @Override
            protected Void doInBackground(Void... params) {
                SiparisService siparisService = new SiparisService();
                siparisService.DeleteSiparislerById(spid);
                SiparisListesiService siparisListesiService = new SiparisListesiService();
                siparisListesiService.DeleteSiparisListesiBySpId(spid);
                return null;
            }
        }
    }

       /* public  class silsiparis extends AsyncTask<String, String, String> {

            int glnid;
            public void silincekid(int id){
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
                        String query1 = " delete from siparisler where id="+glnid+";";
                        Statement stmt1 = con.createStatement();
                        stmt1.execute(query1);


                        System.out.println("2");
                        String query = "delete from siparislistesi where  spid="+glnid+";";
                        Statement stmt = con.createStatement();
                        stmt.execute(query);

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




package com.corba.corbam;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class siparislerActivity extends AppCompatActivity {

    ArrayList<siparisliste> menulist = new ArrayList<siparisliste>();
    private ListView listsViewsiparisler;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparislerlist);
        listsViewsiparisler=(ListView)findViewById(R.id.listsViewsiparisler);

        listeleme doregister = new listeleme();
        System.out.println("1");
        doregister.execute("");

        myAdaptor adaptor=new myAdaptor(siparislerActivity.this,1,menulist);
        listsViewsiparisler.setAdapter(adaptor);


    }
    public  class listeleme extends AsyncTask<String, String, String> {

        String z = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            menulist.clear();
            try {
                Connection con =connectionClass.connectDatabase();
                if (con == null) {
                    z = "Lütfen internet bağlantınızı kontrol edin";
                } else {
                    String query = "select * from siparisler order by id desc;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        siparisliste spal=new siparisliste();
                        String menuad = null;
                        Double top=0.0;
                        String query1 = " select * from siparislistesi where spid="+rs.getInt("id");
                        Statement stmt1 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query);
                        while (rs1.next()){
                            String query2 = " select * from menu where id="+rs1.getInt("menuid");
                            Statement stmt2 = con.createStatement();
                            ResultSet rs2 = stmt2.executeQuery(query);
                            if (rs2.next()){
                                menuad = menuad.toString()+rs2.getString("ad")+"\n";
                                top=top+Double.parseDouble(rs2.getString("fiyat"));
                            }

                        }
                        SimpleDateFormat tarih = new SimpleDateFormat("dd-MM-yyyy");
                        spal.setAd(menuad);
                        spal.setTarih((tarih.format(rs.getDate("tarih"))).toString());
                        spal.setSaat(rs.getString("saat").toString());
                        spal.setMasano(rs.getString("masano"));
                        spal.setFiyat(String.valueOf(top));

                    }
                }
            } catch (Exception ex) {

                z = "Exceptions " + ex;
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s) {


        }


    }


    public class myAdaptor extends ArrayAdapter<siparisliste> {
        ArrayList<siparisliste> arr;
        private TextView text;
        LayoutInflater layoutInflater=getLayoutInflater();

        public myAdaptor(Context context, int textViewResourceId, ArrayList<siparisliste> mList){
            super(context,textViewResourceId,mList);
            this.arr=mList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View satirView;
            satirView = layoutInflater.inflate(R.layout.siparis_row_layout, null);

            TextView listmenu = (TextView) satirView.findViewById(R.id.listmenu);
            TextView lbltarih = (TextView) satirView.findViewById(R.id.lbltarih);
            TextView lblsaat = (TextView) satirView.findViewById(R.id.lblsaat);
            TextView lblmasano = (TextView) satirView.findViewById(R.id.lblmasano);
            TextView lbltplmfiyat = (TextView) satirView.findViewById(R.id.lbltplmfiyat);

            SimpleDateFormat tarih = new SimpleDateFormat("dd-MM-yyyy");
            final siparisliste sp=arr.get(position);
            listmenu.setText(sp.getAd());
            lblmasano.setText("Masa NO : "+sp.getMasano());
            lbltarih.setText("Tarih : "+(tarih.format(sp.getTarih())));
            lblsaat.setText("Saat : "+(sp.getSaat()));
            lbltplmfiyat.setText("Toplam : "+sp.getFiyat()+" TL");


            return satirView;
        }

    }


}


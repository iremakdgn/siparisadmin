package com.corba.corbam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SyncStatusObserver;
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
import com.corba.corbam.Entities.Kullanici;
import com.corba.corbam.Entities.Masa;
import com.corba.corbam.Entities.Menu;
import com.corba.corbam.Entities.Siparis;
import com.corba.corbam.Entities.SiparisListesi;
import com.corba.corbam.Entities.YdkSiparisler;
import com.corba.corbam.Services.BildiriService;
import com.corba.corbam.Services.KullaniciService;
import com.corba.corbam.Services.MasaService;
import com.corba.corbam.Services.MenuService;
import com.corba.corbam.Services.SiparisListesiService;
import com.corba.corbam.Services.SiparisService;
import com.corba.corbam.Services.YdkSiparislerService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class adminBildiri extends AppCompatActivity {
    ArrayList<bildirimler> menulist = new ArrayList<bildirimler>();
    private ListView listbil;
    ConnectionClass connectionClass;
    Button cksbtn, btnbil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bildiri);

        listbil = (ListView) findViewById(R.id.listbil);
        cksbtn = (Button) findViewById(R.id.cksbtn);
        btnbil = (Button) findViewById(R.id.btnbil);
        /*listeleme doregister = new listeleme();
        System.out.println("1");
        doregister.execute("");*/
        new GetBildiriler().execute();
        System.out.println(menulist.size());
        RefreshAdapter();


        cksbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1 = new Intent(adminBildiri.this, girisAdminActivity.class);
                startActivity(intentbtn1);
            }
        });
        btnbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*bildriekle doregister1 = new bildriekle();
                System.out.println("1");
                doregister1.execute("");*/
                new AsyncBildiriEkle().execute();
                AlertDialog builder = new AlertDialog.Builder(adminBildiri.this).create();
                builder.setMessage("Bildirim eklenmiştir?");
                builder.setButton(AlertDialog.BUTTON_POSITIVE, "EVET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        /*listeleme doregister2 = new listeleme();
                        System.out.println("1");
                        doregister2.execute("");*/
                        new GetBildiriler().execute();
                        System.out.println(menulist.size());
                        RefreshAdapter();
                    }
                });
                builder.show();
                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.aktif));


            }
        });
    }

    private void RefreshAdapter() {
        myAdaptor adaptor = new myAdaptor(adminBildiri.this, 1, menulist);
        listbil.setAdapter(adaptor);
    }

    public class GetBildiriler extends android.os.AsyncTask<Void, Void, List<Bildiri>> {
        @Override
        protected List<Bildiri> doInBackground(Void... voids) {
            BildiriService b = new BildiriService();
            List<Bildiri> list = b.GetBildiriler();
            return list;
        }

        @Override
        protected void onPostExecute(List<Bildiri> bildiris) {
            menulist.clear();
            for (Bildiri b : bildiris) {
                menulist.add(new bildirimler(b.getId(), b.getBilmetin()));

            }
            RefreshAdapter();
        }
    }

   /* public  class listeleme extends AsyncTask<String, String, String> {

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
                    String query = "select * from bildiriler order by id desc;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {

                        menulist.add(new bildirimler(rs.getInt("id"),rs.getString("bilmetin")));
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


    public class myAdaptor extends ArrayAdapter<bildirimler> {
        ArrayList<bildirimler> arr;
        private TextView text;
        LayoutInflater layoutInflater = getLayoutInflater();

        public myAdaptor(Context context, int textViewResourceId, ArrayList<bildirimler> mList) {
            super(context, textViewResourceId, mList);
            this.arr = mList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View satirView;
            satirView = layoutInflater.inflate(R.layout.bildiri_dizayn, null);

            final TextView bldrid = (TextView) satirView.findViewById(R.id.bldrid);
            TextView metin = (TextView) satirView.findViewById(R.id.lblbldrmetin);
            ImageButton btnsil = (ImageButton) satirView.findViewById(R.id.btnsil);


            final bildirimler sp = arr.get(position);
            metin.setText(sp.getBilmetin());
            bldrid.setText(String.valueOf(sp.getId()));
            bldrid.setVisibility(TextView.VISIBLE);

            btnsil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog builder = new AlertDialog.Builder(adminBildiri.this).create();

                    builder.setMessage("Günlük gelir bilgileriniz eklenmiştir?");
                    builder.setButton(AlertDialog.BUTTON_POSITIVE, "EVET", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            /*silbildiri doregister = new silbildiri();
                            System.out.println("1");
                            doregister.silincekid(sp.getId());
                            doregister.execute("");*/
                            new AsyncBildiriSil(sp.getId()).execute();
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
    }

    public class AsyncBildiriSil extends android.os.AsyncTask<Void, Void, Void> {
        int id;

        public AsyncBildiriSil(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            BildiriService bildiriService = new BildiriService();
            bildiriService.DeleteBildirilerById(id);

            bildiriService.GetBildiriler();

            return null;
        }
    }

       /* public  class silbildiri extends AsyncTask<String, String, String> {

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
                        String query1 = "DELETE FROM bildiriler WHERE id="+glnid+";";
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


        }

    }*/


    public class AsyncBildiriEkle extends android.os.AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            Date tarih = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(tarih);
            int yil = cal.get(Calendar.YEAR);
            int ay = cal.get(Calendar.MONTH);
            int gun = cal.get(Calendar.DAY_OF_MONTH);
            Double top = 0.0;
            //SimpleDateFormat bicim = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat bicim2 = new SimpleDateFormat("dd-MM-yyyy");

            SiparisService siparisService = new SiparisService();
            SiparisListesiService siparisListesiService = new SiparisListesiService();
            List<Siparis> siparisList = siparisService.GetSiparislerByTarih(gun, ay, yil);

            for (Siparis siparisid : siparisList) {
                List<SiparisListesi> siparisListesi = siparisListesiService.GetSiparisListesiBySpId(siparisid.getId());
                for (SiparisListesi i : siparisListesi) {
                    Double d = Double.parseDouble(i.getUrunfiyat());
                    top = top + d;
                }
            }
            String trhh = bicim2.format(tarih);
            String son = "" + trhh + " tarihindeki toplam geliriniz " + top + " TL dir.";
            BildiriService bildiriService = new BildiriService();
            bildiriService.PostBildirilerByBilMetin(son);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            RefreshAdapter();
            super.onPostExecute(aVoid);
        }
    }
}

    /*public  class bildriekle extends AsyncTask<String, String, String> {
        Date tarih=new Date();
        Double top=0.0;
        String z = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            try {
                SimpleDateFormat bicim=new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat bicim2=new SimpleDateFormat("dd-MM-yyyy");
                Connection con =connectionClass.connectDatabase();
                if (con == null) {
                    z = "Lütfen internet bağlantınızı kontrol edin";
                } else {
                    System.out.println("else içinde 1");
                    String query = "select id from siparisler where tarih='"+bicim.format(tarih)+"';";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        System.out.println("else içinde 4");
                        String query1 = " select * from siparislistesi where spid='"+rs.getInt("id")+"';";
                        Statement stmt1 = con.createStatement();
                        System.out.println(rs.getInt("id")+"sipariş id listedeki");
                        ResultSet rs1 = stmt1.executeQuery(query1);System.out.println("else içinde 6");
                        while (rs1.next()){
                            Double d=Double.parseDouble(rs1.getString("urunfiyat"));
                            top=top+d;
                            System.out.println("else içinde 8");
                            System.out.println(top+"topppppp");
                            System.out.println("başarılı");
                    }
                }
                    String trhh=bicim2.format(tarih);
                    System.out.println(trhh);
                    String son=""+trhh+" tarihindeki toplam geliriniz "+top+" TL dir.";
                    System.out.println(bicim2.format(tarih)+" tarihindeki toplam geliriniz "+top+"TL dir.");
                    String querybildiri="insert into bildiriler(bilmetin) values ('"+son+"');";
                    Statement stbildiri=con.createStatement();
                    stbildiri.execute(querybildiri);
                    ///z="Günlük gelir bilgileriniz eklenmiştir.";


            }
            } catch (Exception ex) {

                z = "Exceptions " + ex;
                System.out.println(z);
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" +z, Toast.LENGTH_LONG).show();


        }


    }*/


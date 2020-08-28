package com.corba.corbam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.corba.corbam.Entities.Siparis;
import com.corba.corbam.Entities.SiparisListesi;
import com.corba.corbam.Services.SiparisListesiService;
import com.corba.corbam.Services.SiparisService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IkiTarih extends AppCompatActivity {

    ArrayList<siparisliste> menulist = new ArrayList<siparisliste>();
    Button cksbtn, listelebtn;
    EditText texttrhal1;
    EditText texttrhal2;
    private BarChart mChart;
    TextView txtgelir;
    ListView listsp;
    String blnntrh1 = null, blnntrh2 = null;
    double guntop, geneltop;
    double saattop;
    int tarihfrk = 0;
    Date islemdate;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iki_tarih);


        cksbtn = (Button) findViewById(R.id.cksbtn);
        listelebtn = (Button) findViewById(R.id.listelebtn);
        texttrhal1 = (EditText) findViewById(R.id.texttrhal1);
        texttrhal2 = (EditText) findViewById(R.id.texttrhal2);
        txtgelir = (TextView) findViewById(R.id.txtgelir);
        listsp = (ListView) findViewById((R.id.listsp));


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        texttrhal1.setInputType(InputType.TYPE_NULL);
        texttrhal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        IkiTarih.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "-" + month + "-" + year;
                        texttrhal1.setText(date);
                        blnntrh1 = year + "-" + month + "-" + dayOfMonth;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        texttrhal2.setInputType(InputType.TYPE_NULL);
        texttrhal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        IkiTarih.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "-" + month + "-" + year;
                        texttrhal2.setText(date);
                        blnntrh2 = year + "-" + month + "-" + dayOfMonth;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        cksbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1 = new Intent(IkiTarih.this, girisAdminActivity.class);
                startActivity(intentbtn1);
            }
        });

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);
        listelebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (texttrhal1.length() == 0 || texttrhal2.length() == 0) {

                    Toast.makeText(getBaseContext(), "" + "Lütfen Tarih Seçiniz .", Toast.LENGTH_LONG).show();

                } else {
                    DateFormat forma = new SimpleDateFormat("yyyy-MM-dd");
                    System.out.println("tarihden önce:" + texttrhal1.getText() + ":");
                    try {
                        Date d1 = forma.parse(blnntrh1);
                        Date d2 = forma.parse(blnntrh2);
                        if (d1.before(d2)) {
                            long Fark = d2.getTime() - d1.getTime();//iki tarih arasındaki farkı hesaplıyoruz.
                            islemdate = d1;
                            tarihfrk = (int) (Fark / (1000 * 60 * 60 * 24));

                            new AsyncIkiTarihListele().execute();

                            System.out.println(menulist.size());
                            RefreshAdapter();


                        } else if (d1.after(d2)) {
                            Toast.makeText(getBaseContext(), "" + "Girilen son tarih ilk tarihten önce olamaz", Toast.LENGTH_LONG).show();

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        setData();
        mChart.setFitBars(true);//grafik bitiş

    }

    private void setData() {
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            yVals.add(new BarEntry(i, 0));
        }
        BarDataSet set = new BarDataSet(yVals, "Veriler");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);
        mChart.setData(data);
        mChart.invalidate();
        mChart.animateY(500);
    }

    private void RefreshAdapter() {
        myAdaptor adaptor = new myAdaptor(IkiTarih.this, 1, menulist);
        listsp.setAdapter(adaptor);
    }

    public class AsyncIkiTarihListele extends android.os.AsyncTask<Void, Void, Void> {
        String z;

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<BarEntry> yVals = new ArrayList<>();
            menulist.clear();
            geneltop = 0.0;
            for (int i = 1; i <= tarihfrk + 1; i++) {
                guntop = 0.0;
                Date lazmdate = islemdate;
                Calendar c = Calendar.getInstance();
                c.setTime(lazmdate);
                c.add(Calendar.DATE, i - 1);
                lazmdate = c.getTime();
                c.setTime(lazmdate);
                saattop = 0.0;

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                month++;
                int day = c.get(Calendar.DAY_OF_MONTH);

                SiparisService siparisService = new SiparisService();
                List<Siparis> siparisList = siparisService.GetSiparislerByTarih(day, month, year);
                for (Siparis siparis : siparisList) {
                    String gecicilist = "";
                    SiparisListesiService siparisListesiService = new SiparisListesiService();
                    List<SiparisListesi> s = siparisListesiService.GetSiparisListesiBySpId(siparis.getId());
                    for (SiparisListesi siparisListesi : s) {
                        gecicilist = gecicilist + siparisListesi.getUrunad() + "(" + siparisListesi.getUrunfiyat() + " TL )" + " , ";
                        saattop = saattop + Double.parseDouble(siparisListesi.getUrunfiyat());
                        guntop = guntop + Double.parseDouble(siparisListesi.getUrunfiyat());
                        geneltop = geneltop + Double.parseDouble(siparisListesi.getUrunfiyat());
                    }
                    menulist.add(new siparisliste(siparis.getMasano().toString(), Helper.formatDate(siparis.getTarih()), siparis.getSaat(), gecicilist));
                }
                float value = (float) (saattop);
                yVals.add(new BarEntry(i, (int) value));
            }
            txtgelir.setText("Toplam Gelir :" + geneltop);
            BarDataSet set = new BarDataSet(yVals, "Veriler");
            set.setColors(ColorTemplate.MATERIAL_COLORS);
            set.setDrawValues(true);
            BarData data = new BarData(set);
            mChart.setData(data);
            mChart.invalidate();
            z = "Aktarım Tamamlandı.";
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RefreshAdapter();
        }
    }

    /*public class listeleme extends AsyncTask<String, String, String> {

        String z = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            System.out.println("1");
            try {
                Connection con = connectionClass.connectDatabase();
                if (con == null) {
                    z = "Lütfen internet bağlantınızı kontrol edin";
                } else {
                    ArrayList<BarEntry> yVals = new ArrayList<>();

                    menulist.clear();
                    geneltop = 0.0;
                    for (int i = 1; i <= tarihfrk + 1; i++) {
                        guntop = 0.0;
                        Date lazmdate = islemdate;
                        System.out.println("trh fark ==" + tarihfrk);
                        Calendar c = Calendar.getInstance();
                        c.setTime(lazmdate);
                        c.add(Calendar.DATE, i - 1);
                        lazmdate = c.getTime();
                        c.setTime(lazmdate);
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);


                        String sgln = year + "-" + (month + 1) + "-" + day;
                        // String sgln=lazmdate.getYear() +"-"+(lazmdate.getMonth()+1)+"-"+lazmdate.getDate();

                        saattop = 0.0;

                        System.out.println("datatatatata" + sgln);
                        System.out.println("datatatatata" + lazmdate);
                        System.out.println("2");
                        String query = "SELECT * FROM siparisler WHERE tarih='" + sgln + "';";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        while (rs.next()) {
                            String gecicilist = "";
                            siparisliste spl = new siparisliste();
                            String query1 = " select * from siparislistesi where spid=" + rs.getInt("id");
                            Statement stmt1 = con.createStatement();
                            ResultSet rs1 = stmt1.executeQuery(query1);
                            while (rs1.next()) {
                                gecicilist = gecicilist + rs1.getString("urunad") + "(" + rs1.getString("urunfiyat") + " TL )" + " , ";
                                saattop = saattop + Double.parseDouble(rs1.getString("urunfiyat"));
                                guntop = guntop + Double.parseDouble(rs1.getString("urunfiyat"));
                                geneltop = geneltop + Double.parseDouble(rs1.getString("urunfiyat"));
                            }
                            menulist.add(new siparisliste(rs.getString("masano"), rs.getString("tarih"), rs.getString("saat"), gecicilist));


                        }
                        float value = (float) (guntop);
                        yVals.add(new BarEntry(i, (int) value));
                    }
                    BarDataSet set = new BarDataSet(yVals, "Veriler");
                    set.setColors(ColorTemplate.MATERIAL_COLORS);
                    set.setDrawValues(true);
                    BarData data = new BarData(set);
                    mChart.setData(data);
                    mChart.invalidate();
                    z = "Aktarım Tamamlandı.";


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
            satirView = layoutInflater.inflate(R.layout.ikitarihli_dizayn, null);

            TextView lblmasano = (TextView) satirView.findViewById(R.id.lblmasano);
            TextView lbltarih = (TextView) satirView.findViewById(R.id.lbltarih);
            TextView lblsaat = (TextView) satirView.findViewById(R.id.lblsaat);
            TextView listmenu = (TextView) satirView.findViewById(R.id.sdtylist);

            final siparisliste sp = arr.get(position);
            listmenu.setText(sp.getSdetaylst());
            lbltarih.setText("Tarih : " + sp.getTarih());
            lblsaat.setText("Saat : " + sp.getSaat());
            lblmasano.setText("Masa NO : " + sp.getMasano());
            txtgelir.setText("Toplam Geliri :" + geneltop);


            return satirView;
        }


    }


}

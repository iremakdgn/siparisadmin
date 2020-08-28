package com.corba.corbam;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.corba.corbam.Entities.Siparis;
import com.corba.corbam.Entities.SiparisListesi;
import com.corba.corbam.Services.SiparisListesiService;
import com.corba.corbam.Services.SiparisService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class belliTarih extends AppCompatActivity {
    ArrayList<siparisliste> menulist = new ArrayList<siparisliste>();
    Button cksbtn, listelebtn;
    EditText texttrhal;
    private BarChart mChart;
    TextView txtgelir;
    ListView listsp;
    String blnntrh;
    double guntop;
    double saattop;

    ArrayList<BarEntry> yVals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belli_tarih);
        cksbtn = (Button) findViewById(R.id.cksbtn);
        listelebtn = (Button) findViewById(R.id.listelebtn);
        texttrhal = (EditText) findViewById(R.id.texttrhal);
        txtgelir = (TextView) findViewById(R.id.txtgelir);
        listsp = (ListView) findViewById((R.id.listsp));
        mChart = (BarChart) findViewById(R.id.chart1);
        setData();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        texttrhal.setInputType(InputType.TYPE_NULL);
        texttrhal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        belliTarih.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "-" + month + "-" + year;
                        System.out.println(texttrhal.getText() + "afsfdfgd");
                        texttrhal.setText(date);
                        blnntrh = year + "-" + month + "-" + dayOfMonth;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        cksbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1 = new Intent(belliTarih.this, girisAdminActivity.class);
                startActivity(intentbtn1);
            }
        });

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);
        listelebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ((texttrhal.length() == 0)) {
                    Toast.makeText(getBaseContext(), "" + "Lütfen Tarih Seçiniz .", Toast.LENGTH_LONG).show();
                } else {
                    mChart.clear();
                    mChart.getDescription().setEnabled(false);

                    int ay, gun, yil;
                    String tarih = blnntrh;
                    String[] parts = tarih.split("-");
                    yil = Integer.parseInt(parts[0]); // 004
                    ay = Integer.parseInt(parts[1]); // 004
                    gun = Integer.parseInt(parts[2]); // 004

                    new AsyncListele(gun, ay, yil).execute();

                    BarDataSet set = new BarDataSet(yVals, "Veriler");
                    set.setColors(ColorTemplate.MATERIAL_COLORS);
                    set.setDrawValues(true);

                    BarData data = new BarData(set);
                    mChart.setData(data);
                    mChart.invalidate();
                    mChart.animateY(500);
                    mChart.setFitBars(true);

                    System.out.println(menulist.size());
                    RefreshAdapter();
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
        myAdaptor adaptor = new myAdaptor(belliTarih.this, 1, menulist);
        listsp.setAdapter(adaptor);
    }

    public class AsyncListele extends android.os.AsyncTask<Void, Void, Void> {
        String z;
        int gun, ay, yil;

        public AsyncListele(int gun, int ay, int yil) {
            this.gun = gun;
            this.ay = ay;
            this.yil = yil;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<BarEntry> yVals = new ArrayList<>();
            menulist.clear();
            guntop = 0.0;
            for (int i = 0; i < 24; i++) {
                saattop = 0.0;

                SiparisService siparisService = new SiparisService();
                List<Siparis> siparisList = siparisService.GetSiparislerByTarihSaat(gun, ay, yil, i);
                for (Siparis siparis : siparisList) {
                    String gecicilist = "";
                    SiparisListesiService siparisListesiService = new SiparisListesiService();
                    List<SiparisListesi> s = siparisListesiService.GetSiparisListesiBySpId(siparis.getId());
                    for (SiparisListesi siparisListesi : s) {
                        gecicilist = gecicilist + siparisListesi.getUrunad() + "(" + siparisListesi.getUrunfiyat() + " TL )" + " , ";
                        saattop = saattop + Double.parseDouble(siparisListesi.getUrunfiyat());
                        guntop = guntop + Double.parseDouble(siparisListesi.getUrunfiyat());
                    }
                    menulist.add(new siparisliste(siparis.getMasano().toString(), blnntrh, siparis.getSaat(), gecicilist));
                }
                float value = (float) (saattop);
                yVals.add(new BarEntry(i, (int) value));
            }
            txtgelir.setText("Günün Toplam Geliri :" + guntop);
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
                    guntop = 0.0;
                    for (int i = 0; i < 24; i++) {
                        saattop = 0.0;
                        System.out.println("2");

                        String query = "select * from siparisler where tarih='" + blnntrh + "' and saat>='" + i + ":00' and saat<='" + i + ":59'";
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
                            }
                            menulist.add(new siparisliste(rs.getString("masano"), blnntrh, rs.getString("saat"), gecicilist));


                        }
                        float value = (float) (saattop);
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
            satirView = layoutInflater.inflate(R.layout.bellitarihli_dizayn, null);

            TextView lblmasano = (TextView) satirView.findViewById(R.id.lblmasano);
            TextView lbltarih = (TextView) satirView.findViewById(R.id.lbltarih);
            TextView lblsaat = (TextView) satirView.findViewById(R.id.lblsaat);
            TextView listmenu = (TextView) satirView.findViewById(R.id.sdtylist);

            siparisliste sp = arr.get(position);
            listmenu.setText(sp.getSdetaylst());
            lblmasano.setText("Masa NO : " + sp.getMasano());
            lbltarih.setText("Tarih : " + sp.getTarih());
            lblsaat.setText("Saat : " + sp.getSaat());

            return satirView;
        }


    }
}








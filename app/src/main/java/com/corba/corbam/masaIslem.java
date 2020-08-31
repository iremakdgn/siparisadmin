package com.corba.corbam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.corba.corbam.Entities.Masa;
import com.corba.corbam.Services.MasaService;

import java.util.ArrayList;
import java.util.List;

public class masaIslem extends AppCompatActivity {

    ArrayList<masalar> masalist = new ArrayList<masalar>();
    private ListView lismasa;
    Button cksbtn, eklebtn;
    Spinner spinner;
    EditText mskapste;
    String masanosec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masa_islem);
        lismasa = findViewById(R.id.listmasa);
        cksbtn = findViewById(R.id.cksbtn);
        eklebtn = findViewById(R.id.eklebtn);
        spinner = findViewById(R.id.spinner);
        mskapste = findViewById(R.id.mskapste);

        new GetMasalarDesc().execute();
        RefreshAdapter();

        cksbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbtn1 = new Intent(masaIslem.this, girisAdminActivity.class);
                startActivity(intentbtn1);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                masanosec = (spinner.getItemAtPosition(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        eklebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncMasaEkle(mskapste.getText().toString(), masanosec).execute();
                new GetMasalarDesc().execute();
                RefreshAdapter();
            }
        });
    }

    private void RefreshAdapter() {
        myAdaptor adaptor = new myAdaptor(masaIslem.this, 1, masalist);
        lismasa.setAdapter(adaptor);
    }

    public class AsyncMasaEkle extends android.os.AsyncTask<Void, Void, Void> {
        String kapasite;
        String masano;
        String z;

        public AsyncMasaEkle(String kapasite, String masano) {
            this.kapasite = kapasite;
            this.masano = masano;
        }

        @Override
        protected Void doInBackground(Void... params) {
            z = "Lütfen sayısal Değer Giriniz";
            int aa = Integer.parseInt(masano);

            MasaService masaService = new MasaService();
            Masa m = masaService.GetMasaByMasaNo(masano);
            if (m != null) {
                z = "Lütfen Kayıtlı Olmayan bir masa numarası  giriniz.";
            } else {
                if (aa > 0 && aa <= 20) {
                    m = new Masa();
                    m.setKapasite(kapasite);
                    m.setDurum("pasif");
                    m.setMasano(masano);
                    masaService.PostMasalar(m);
                    z = "Ekleme İşlemi Başarılı Şekilde Oldu";
                } else {
                    z = "Ekleme İşlemi Tamamlanamadı.Tekrar Deneyin";
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
            //super.onPostExecute(aVoid);
            RefreshAdapter();
        }
    }

    public class GetMasalarDesc extends android.os.AsyncTask<Void, Void, List<Masa>> {
        @Override
        protected List<Masa> doInBackground(Void... voids) {
            MasaService m = new MasaService();
            List<Masa> list = m.GetMasalarDesc();
            return list;
        }

        @Override
        protected void onPostExecute(List<Masa> masas) {
            masalist.clear();
            for (Masa m : masas) {
                masalist.add(new masalar(m.getId(), m.getMasano(), m.getKapasite(), m.getDurum()));
            }
            RefreshAdapter();
        }
    }

    public class myAdaptor extends ArrayAdapter<masalar> {
        ArrayList<masalar> arr;
        LayoutInflater layoutInflater = getLayoutInflater();

        public myAdaptor(Context context, int textViewResourceId, ArrayList<masalar> mList) {
            super(context, textViewResourceId, mList);
            this.arr = mList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View satirView;
            satirView = layoutInflater.inflate(R.layout.masa_dizayn, null);

            TextView msid = satirView.findViewById(R.id.msid);
            TextView lblmasano = satirView.findViewById(R.id.lblmasano);
            TextView lblkpsite = satirView.findViewById(R.id.lblkpsite);
            TextView lbldrm = satirView.findViewById(R.id.lbldrm);
            ImageButton btnsil = satirView.findViewById(R.id.btnsil);


            final masalar sp = arr.get(position);
            lblmasano.setText(sp.getMasano());
            lblkpsite.setText(sp.getKapasite());
            lbldrm.setText(sp.getDurum());
            msid.setText(String.valueOf(sp.getId()));
            msid.setVisibility(TextView.VISIBLE);

            btnsil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog builder = new AlertDialog.Builder(masaIslem.this).create();

                    builder.setMessage("Masa silinsin mi?");
                    builder.setButton(AlertDialog.BUTTON_POSITIVE, "EVET", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            /*silbildiri doregister = new silbildiri();
                            System.out.println("1");
                            doregister.silincekid(sp.getId());
                            doregister.execute("");*/
                            new AsyncSilMasa(sp.getId()).execute();
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

        public class AsyncSilMasa extends android.os.AsyncTask<Void, Void, Void> {
            int id;
            String z;

            public AsyncSilMasa(int id) {
                this.id = id;
            }

            @Override
            protected Void doInBackground(Void... params) {

                MasaService masaService = new MasaService();
                masaService.DeleteMasalarById(id);

                z = "Silindi.";
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
                super.onPostExecute(aVoid);
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
                        String query1 = "DELETE FROM masalar WHERE id="+glnid+";";
                        Statement stmt1 = con.createStatement();
                        stmt1.execute(query1);

                        z="SİLİNDİ";

                    }
                    listeleme doregister = new listeleme();
                    System.out.println("1");
                    doregister.execute("");
                    System.out.println(masalist.size());
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
}

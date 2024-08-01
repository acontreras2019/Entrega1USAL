package com.example.alecontreras.datospersonalistalmacen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private int mDelay = 5000;
    private TextView tv1;
    private TextView tv2;
    private ProgressBar mProgressBar;

    private String[] l ;
    public static ArrayList<ListaPersonas> misPersonas = new ArrayList<ListaPersonas>();

    public String formatPrefered, urlPrefered,storagePrefered;

    public String dataHiloP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv2 = (TextView) findViewById(R.id.tv2);
        //image1.setImageResource(R.drawable.perro);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

    }


    public void enviarDatos(View view) {
        SharedPreferences preferencias=getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        formatPrefered = preferencias.getString("format","");
        storagePrefered= preferencias.getString("storage","");
        urlPrefered = preferencias.getString("urlFormat","");
        Log.v("Preferencias", "En run(" +storagePrefered+ " "+ formatPrefered + " "+urlPrefered+"  ) " );

        if (storagePrefered.equals("") ){
            Toast.makeText(this, "Debe configurar sus preferencias de almacenamiento.",
                    Toast.LENGTH_SHORT).show();
          return;
        }
        if (formatPrefered.equals("") ){
            Toast.makeText(this, "Debe configurar sus preferencias de Formato y URL.",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        if (storagePrefered.equals("BDD") || storagePrefered.equals("ContentP") ) {
            ArrayList<Persona>  li = new ListaPersonas(this).ObtenerLista();
            if(li.size()>0){
                Log.v("BDD", "con Datos Programar(" + "): " );
                Log.v("HTTPActivity", "En run(" + li +"): " );
                ListaPersonas list = new ListaPersonas(li);
                String [] l = list.listToTEXTformat();

                Intent i =new Intent(new Intent(MainActivity.this,
                        activity_second.class));
                i.putExtra("list", l);
                startActivity(i);
            }
            else {
               Log.v("BDD", "BDD vacia,traeyendo los datos HTTP con Hilo" + "): " );
                Intent i =new Intent(new Intent(MainActivity.this,
                        HttpXmlPostListConnection.class));
                startActivity(i);
            }
        }




       /* HiloEnvioDatos loadData = new HiloEnvioDatos();
        loadData.start();
        Log.v("CargaInicial", "En run(" + "): " );*/




    }


    public void accionPersona(Intent data, int accion) {
        //Añade un persona al almacén

    }

    public void SendToSecondActivity(String[] list) {

        Intent i=new Intent(this,activity_second.class);
        Log.v("CargaSend", "En run(" + l[1]+ "): " );
        i.putExtra("list", l);
        startActivityForResult(i,1111);
    }


    public class HiloEnvioDatos extends Thread {
        int i;
        private void publishProgress(Integer values) {
            int progreso = values.intValue();

            if (progreso == -1) {
                tv1.setText("Carga cancelada ...");
            }
            if (progreso <= 5) { // progresa adecuadamente
                mProgressBar.setProgress(progreso * 20);
                tv2.setText("... Loading" + String.valueOf(progreso * 20) + "% ... ");
            }
            else {
                tv2.setText(" ");
              /*  mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                ListaPersonas lista = new ListaPersonas(MainActivity.this);
                ArrayList<Persona> personas = lista.CargarLista();
                l = lista.listToTEXTformat();*/
                SendToSecondActivity(l);
                Log.v("CargaEnnHilo", "En run(" + l[2]+"): " );

            }

        }

        public void run() {

            for (i = 1; i <= 5; i++) {
                SystemClock.sleep((long) (Math.random() * 1000));
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishProgress(i);
                            Log.v("BarraProgres", "En run publish progrress ");
                        }
                    });
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (i < 6) publishProgress(-1);
                    else if (i == 6){
                        publishProgress(6);
                       }
                }
            });

        }
    }


    public void sharedPreference(View view) {
        Intent i =new Intent(MainActivity.this,activity_shared_preference.class);
        startActivity(i);
    }

      class DescargaTask extends Thread {

        //private static final String URLs = "http://prodiasv02.fis.usal.es/DAM/ServerJsonUTF8ns.txt";
        //Recurso (fichero) de acceso público en Internet con texto plano con datos de personas.
        // Formato JSON. Aparecen separados los campos nombre y apellidos

       // private static final String URLs = "http://prodiasv02.fis.usal.es/DAM/ServerJsonUTF8n.txt";
        //Recurso alternativo con datos de personas. Aparecen separados los campos nombre y apellidos

         private static final String URLs = "http://prodiasv02.fis.usal.es/DAM/ServerXMLs.xml";
        //Recurso alternativo con datos de personas. Aparecen separados los campos nombre y apellidos
        // formateados como colecciones etiquetadas xml

        String data = "";

        public void run() {
            HttpURLConnection httpUrlConnection = null;
            try {
                httpUrlConnection = (HttpURLConnection) new URL(URLs)
                        .openConnection();
                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
                data = readStream(in);
            } catch (MalformedURLException exception) {
                data += "MalformedURLException\n";
                Log.e("TAG", "MalformedURLException");
            } catch (IOException exception) {
                data += "IOException\n";
                Log.e("conexionhttp", exception.toString());
            } finally {
                if (null != httpUrlConnection) {
                    httpUrlConnection.disconnect();
                }
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                 dataHiloP = data;
                    Log.e("dataHilo", data);
                }
            });

            if (isInterrupted()) {
                //poner mensaje de Interrupción en la IU en hilo principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("dataHiloInterr", "data");
                        dataHiloP = "Interrumpida la conexión\n";
                    }
                });

            };
        }
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder data = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            Log.e("TAG", e.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("TAG", "IOException");
                }
            }
        }
        return data.toString();
    }

}
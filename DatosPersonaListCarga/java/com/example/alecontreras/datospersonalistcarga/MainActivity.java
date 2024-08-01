package com.example.alecontreras.datospersonalistcarga;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private int mDelay = 5000;
    private TextView tv1;
    private TextView tv2;
    private ProgressBar mProgressBar;

    private String[] l ;
    public static ArrayList<ListaPersonas> misPersonas = new ArrayList<ListaPersonas>();

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
        HiloEnvioDatos loadData = new HiloEnvioDatos();
        loadData.start();
        Log.v("CargaInicial", "En run(" + "): " );


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
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                ListaPersonas lista = new ListaPersonas();
                ArrayList<Persona> personas = lista.CargarLista();
                l = lista.listToTEXTformat();
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


}
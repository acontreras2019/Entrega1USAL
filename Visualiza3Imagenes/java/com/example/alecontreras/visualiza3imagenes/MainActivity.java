package com.example.alecontreras.visualiza3imagenes;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ImageView image1;
    private final String TAG_LOG = "TresImágenes";
    private TextView tv1;
    private int valorInicial = 0;
    private int AccionActivada = 0;
    private ProgressBar mProgressBar;
    private int mDelay = 5000;
    private Bitmap mBitmap;


    @SuppressLint("MissingInflatedId")
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
        tv1 = (TextView) findViewById(R.id.tv1);
        image1=findViewById(R.id.imageView);
        //image1.setImageResource(R.drawable.perro);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);




    }

    public void lanza() {


        this.AccionActivada =1;
        int i;
        for (i = 1; i <= 3; i++) {
            this.onPreExecute();  // prepara la vista
            DescargaImagen descargaImagen = new DescargaImagen(i);
            descargaImagen.start();

        }





    }
    protected void onPreExecute() {
        tv1.setText("Cargando Imagenes");
        Log.v(TAG_LOG, "Cargando Imagenes");
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
    }
    public class DescargaImagen extends Thread {
        private int nvalor, ldelta, lpid;
        private int svalor;
        private  String nameFile;

        String sTAG, smesg;

        public DescargaImagen(int indice) {
            switch(indice) {
                case 1:
                    nameFile = "perro";
                    break;
                case 2:
                   nameFile = "gato";
                    break;
                case 3:
                    nameFile = "leon";
                    break;
                default:
                    nameFile = "leon";
                    break;
            }
        }

        int i;

        private void publishProgress(Integer values) {
            int progreso = values.intValue();

            if (progreso == -1) {
                tv1.setText("Descarga cancelada ...");
            }
            if (progreso <= 5) { // progresa adecuadamente
                mProgressBar.setProgress(progreso * 20);
                tv1.setText("... " + String.valueOf(progreso * 20) + "% ... " + nameFile);
            }
           else {
                tv1.setText("Hilo PRINCIPAL. Descarga completada "+ nameFile);
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                Log.v(nameFile, "En descargacompletad ");


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
                            Log.v(nameFile, "En run publish progrress ");


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
                    else if (i == 6) {publishProgress(6);
                        mostrarImagen(nameFile);

                    };
                }
            });

        }

    }

    public void mostrarImagen( String nameImage) {
        Log.v(nameImage, "Desde hilo Principal ");
        int iconResId = getResources().getIdentifier(nameImage, "drawable", getPackageName());
        SystemClock.sleep((long) (Math.random() * 3000));
        image1.setImageResource(iconResId);
    }

    public void finalizar(View view) {
        this.AccionActivada =0;
        finish();

    }

    public void iniciar(View view) {
        lanza();
    }
}




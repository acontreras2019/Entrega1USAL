package com.example.coloretiqueta;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //  private ImageView image1;
    private TextView textMensaje;
    private RadioButton rbtnRojo;
    private RadioButton rbtnVerde;
    private RadioButton rbtnAzul;
    private RadioButton rbtnFondo;
    private RadioButton rbtnMensaje;
    private CheckBox ckbVisible;
    private Button btnEjecutar;

    private int colorRed;
    private int colorBlue;
    private int colorGreen;
    private int colorMensaje;
    private int colorFondo;

    private RadioGroup rbtGroupFormat;
    private RadioGroup rbtGroupColor;

    private int mensajeVisibilidad;

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

        colorRed = Color.parseColor("#FF0000");
        colorBlue = Color.parseColor("#4682B4");
        colorGreen = Color.parseColor("#6B8E23");
        colorMensaje = Color.WHITE;
        colorFondo = Color.BLACK;
        textMensaje = findViewById(R.id.textMensaje);
        rbtnFondo = findViewById(R.id.rbtn1);
        rbtnMensaje = findViewById(R.id.rbtn2);
        rbtnRojo = findViewById(R.id.rbtn3);
        rbtnVerde = findViewById(R.id.rbtn4);
        rbtnAzul = findViewById(R.id.rbtn5);
        ckbVisible = findViewById(R.id.ckb1);
        rbtGroupFormat = findViewById(R.id.rbtGroup1);
        rbtGroupColor = findViewById(R.id.rbtGroup2);
        btnEjecutar = findViewById(R.id.btn1);
        //textMensaje.setTextColor(colorRed);
        textMensaje.setBackgroundColor(colorFondo);
        textMensaje.setTextColor(colorMensaje);


        rbtGroupFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbtnAzul.setChecked(false);
                rbtnRojo.setChecked(false);
                rbtnVerde.setChecked(false);
                if( rbtnFondo.isChecked()){
                    if(colorFondo== colorBlue) rbtnAzul.setChecked(true);
                    if(colorFondo== colorRed) rbtnRojo.setChecked(true);
                    if(colorFondo== colorGreen) rbtnVerde.setChecked(true);
                }
                if( rbtnMensaje.isChecked()){
                    if(colorMensaje== colorBlue) rbtnAzul.setChecked(true);
                    if(colorMensaje== colorRed) rbtnRojo.setChecked(true);
                    if(colorMensaje== colorGreen) rbtnVerde.setChecked(true);
                }

            }
        });

        rbtGroupColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Color de Mensaje
            if(rbtnFondo.isChecked()){
                if (rbtnRojo.isChecked() && colorFondo!= colorRed) colorFondo = colorRed;
                if (rbtnAzul.isChecked() && colorFondo!= colorBlue) colorFondo = colorBlue;
                if (rbtnVerde.isChecked() && colorFondo!= colorGreen) colorFondo = colorGreen;

            }
            if(rbtnMensaje.isChecked()){
                if (rbtnRojo.isChecked() && colorMensaje!= colorRed) colorMensaje = colorRed;
                if (rbtnAzul.isChecked()  && colorMensaje!= colorBlue) colorMensaje = colorBlue;
                if (rbtnVerde.isChecked() && colorMensaje!= colorGreen) colorMensaje = colorGreen;
            }
            }

        });

        ckbVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textMensaje.setVisibility(View.VISIBLE);
                mensajeVisibilidad = View.VISIBLE;
                if(!ckbVisible.isChecked()) mensajeVisibilidad =  View.INVISIBLE;

            }
        });

        //Evento Click
        btnEjecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textMensaje.setBackgroundColor(colorFondo);
                textMensaje.setTextColor(colorMensaje);
                textMensaje.setVisibility(mensajeVisibilidad);
            }
        });

    }
}
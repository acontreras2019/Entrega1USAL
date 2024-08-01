package com.example.alecontreras.datospersonas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvDatos;
    private String datosRecibidos, datosActuales;

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

        tvDatos =(TextView)findViewById(R.id.tvDatos);
        datosRecibidos ="";
        datosActuales ="";

    }

    public void alta(View view) {
        Intent i=new Intent(this,activity_second.class);
        startActivityForResult(i,1111);
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == RESULT_OK) {
            String nombre = data.getExtras().getString("nombre");
            String apellido = data.getExtras().getString("apellido");
            String edad = data.getExtras().getString("edad");
            String telefono = data.getExtras().getString("telefono");
            String permisoConducir = data.getExtras().getString("permisoConducir");
            String nivelIngles = data.getExtras().getString("nivelIngles");
            String fecha = data.getExtras().getString("fecha");

            datosRecibidos = "Nombre: " + nombre + " " + apellido + "\n Edad: "+ edad+ " Telefono: " + telefono + " PC: " + permisoConducir + " Ingles:" + nivelIngles + " Ingreso: " + fecha+ "\n\n";
            datosActuales = datosActuales + datosRecibidos;

            tvDatos.setText(datosActuales);
            tvDatos.setVisibility(View.VISIBLE);
        }

    }

    public void salir(View view) {
        finish();

    }
}
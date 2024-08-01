package com.example.alecontreras.datospersonalist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv1;
    private TextView tvNdatos;

    public static ArrayList<Persona> misPersonas = new ArrayList<Persona>();
    private PersonaListAdapter adaptadorPersona;
    static int nElementos = -1;
    private int accion = 0; // 0 = Nuevo, 1 = Editar

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

        tvNdatos = (TextView) findViewById(R.id.lblNalumnos);
        lv1 = (ListView) findViewById(R.id.lv1);
        adaptadorPersona = new PersonaListAdapter(this, misPersonas);
        lv1.setAdapter(adaptadorPersona);


        // Listener para cuando se hace click en un item de la ListView
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               sendInfoToEdit(position);
            }
        });



        if (nElementos == -1)
            cargarPersonas(); // rutina que carga misPersonas (ArrayList). Es global. no se necesita pasar
        nElementos = misPersonas.size();
        tvNdatos.setText("Total Personas " + nElementos);
        adaptadorPersona.notifyDataSetChanged();
    }
    protected void cargarPersonas() {
        Persona persona;
        persona = new Persona(0, "PABLO", "CAÑO PASCUAL", 0, "+34 621322994","Si", "Alto","25/04/2024");
        misPersonas.add(persona);
        persona = new Persona(1, "ALEJANDRINA", "CONTRERAS ZAVALA",0, "+34 621322995","Si", "","25/04/2024");
        misPersonas.add(persona);
        persona = new Persona(2, "DAVID", "HERRERO HERNÁNDEZ",0, "+34 621322996","No", "","25/04/2024");
        misPersonas.add(persona);
        persona = new Persona(3, "BORJA", "LORENZO ADAJAS", 0, "+34 621322997","No", "","25/04/2024");
        misPersonas.add(persona);
        persona = new Persona(4, "LIENY", "RODRIGUEZ RODRIGUEZ", 0, "+34 621322998","No", "","25/04/2024");
        misPersonas.add(persona);
        persona = new Persona(5, "SERGIO", "SALINERO SANTAMARÍA", 0, "+34 621322999","", "","25/04/2024");
        misPersonas.add(persona);

        Toast toast1 = Toast.makeText(getApplicationContext(), "Cargados " + misPersonas.size() + " registros"
                , Toast.LENGTH_SHORT);
        //toast1.setGravity(Gravity.CENTER, , );
        toast1.show();
    }


    private void sendInfoToEdit(int position){
        accion = 1;
        Intent i=new Intent(this,activity_second.class);
        String vData = misPersonas.get(position).toTEXTformat();
        i.putExtra("data",vData);
        startActivityForResult(i,1111);


    }
    //Nueva Persona
    //Boton Nueva Persona
    public void nuevaPersona(View view){
        accion = 0;
        Intent i=new Intent(this,activity_second.class);
        startActivityForResult(i,1111);
    }

    //Result de Guardar viene de Activity_second;
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == RESULT_OK) {
            accionPersona(data,  accion ); // 0 para Nuevo
        }

    }
    //metodo privado para adicionar la nueva persona al ListView
    public void accionPersona(Intent data, int accion) {
        //Añade un persona al almacén
        String nombre = data.getExtras().getString("nombre");
        String apellido = data.getExtras().getString("apellido");
        //int edad = Integer.parse (data.getExtras().getString("edad"));
        int edad = tryParseInt(data.getExtras().getString("edad"),0);
        String telefono = data.getExtras().getString("telefono");
        String permisoConducir = data.getExtras().getString("permisoConducir");
        String nivelIngles = data.getExtras().getString("nivelIngles");
        String fecha = data.getExtras().getString("fecha");

        if (accion == 0){
            //Añade un persona al almacén
            Persona persona;
            persona = new Persona(
                    misPersonas.size(),
                    nombre, apellido,
                    edad,
                    telefono,
                    permisoConducir,
                    nivelIngles,
                    fecha);

            misPersonas.add(persona);
        }

        if (accion == 1){

            int id = tryParseInt(data.getExtras().getString("id"),0);
            Log.d("myTag", String.valueOf(id));
            Persona personaEdit = misPersonas.get(id);
            personaEdit.setNombre(nombre);
            personaEdit.setApellido(apellido);
            personaEdit.setEdad(edad);
            personaEdit.setTelefono(telefono);
            personaEdit.setPermisoConducir( permisoConducir);
            personaEdit.setNivelIngles(nivelIngles);
            personaEdit.setFechaAlta(fecha);

            misPersonas.set(id,personaEdit);
        }
        nElementos = misPersonas.size();
        tvNdatos.setText("Total Personas " + nElementos);
        adaptadorPersona.notifyDataSetChanged();


    }

    public int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public void salir(View view) {
        finish();
    }
}
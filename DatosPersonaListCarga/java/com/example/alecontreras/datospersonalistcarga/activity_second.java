package com.example.alecontreras.datospersonalistcarga;

import static android.view.WindowManager.*;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class activity_second extends AppCompatActivity {
    private ListView lv1;
    private TextView tvNdatos;

    public static ArrayList<Persona> misPersonas = new ArrayList<Persona>();

    private PersonaListAdapter adaptadorPersona;
    static int nElementos = -1;
    private int accion = 0; // 0 = Nuevo, 1 = Editar
    String preguntaDialog, txtBorrar, txtCancelar, txtActionCancelado, txtActionEliminado, txtTotalPersonas;

    private Button button, btnSalir;
    private TextView tv2;
    //private ProgressBar mProgressBar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvNdatos = (TextView) findViewById(R.id.lblNalumnos);
        lv1 = (ListView) findViewById(R.id.lv1);
        button = (Button) findViewById(R.id.button);
        btnSalir = (Button) findViewById(R.id.btnSalir);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String[] vData= bundle.getStringArray("list");
            if(vData!=null) {
                ListaPersonas lista = new ListaPersonas();
                misPersonas =   lista.setListFromTEXTLineformat(vData);
                button.setEnabled(false);
                btnSalir.setEnabled(false);
                Log.v("ListaSecondActivity", "En run(" + misPersonas.get(2).toString()+ "): " );
            }

        }

        cargarString();
        CargarDatosSimulacion();


        // Listener para cuando se hace click en un item de la ListView
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendInfoToEdit(position);
            }
        });
        // Listener para cuando se hace un click prolongado sobre un item de la ListView
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_second.this);
                builder.setMessage(preguntaDialog).setPositiveButton(txtBorrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = misPersonas.get(position).getId() + " - " + misPersonas.get(position).getNombre() + " " +misPersonas.get(position).getApellido();
                        misPersonas.remove(position);
                        nElementos = misPersonas.size();
                        tvNdatos.setText(txtTotalPersonas + nElementos);
                        adaptadorPersona.notifyDataSetChanged();

                        Toast toast = Toast.makeText(activity_second.this,
                                txtActionEliminado + "\nItem :" + item ,
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }).setNegativeButton(txtCancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(activity_second.this,
                                txtActionCancelado,
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();



                    }
                });
                AlertDialog dDialog = builder.create();
                dDialog.show();

                return true;
            }
        });


    }

    private void cargarString(){
        preguntaDialog = getResources().getString(R.string.preg_borrar_registro);
        txtBorrar =  getResources().getString(R.string.borrar);
        txtTotalPersonas =  getResources().getString(R.string.total_personas);
        txtActionEliminado =  getResources().getString(R.string.eliminado);
        txtCancelar = getResources().getString(R.string.cancelar);
        txtActionCancelado =  getResources().getString(R.string.accion_cancelada);
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
        Intent i=new Intent(this,activity_third.class);
        String vData = misPersonas.get(position).toTEXTformat();
        i.putExtra("data",vData);
        startActivityForResult(i,1111);


    }
    //Nueva Persona
    //Boton Nueva Persona
    public void nuevaPersona(View view){
        accion = 0;
        Intent i=new Intent(this,activity_third.class);
        startActivityForResult(i,1111);
    }

    //Result de Guardar viene de Activity_third;
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

    public void CargarDatosSimulacion() {
        this.onPreExecute();
        activity_second.HiloEnvioDatos loadData = new activity_second.HiloEnvioDatos();
        loadData.start();
        Log.v("CargaInicial", "En run(" + "): " );
    }
 public void CargarListView(){
     adaptadorPersona = new PersonaListAdapter(this, misPersonas);
     lv1.setAdapter(adaptadorPersona);
     nElementos = misPersonas.size();
     tvNdatos.setText(txtTotalPersonas+ nElementos);
     adaptadorPersona.notifyDataSetChanged();
     button.setEnabled(true);
     btnSalir.setEnabled(true);
 }
    protected void onPreExecute() {

        mProgressDialog = new ProgressDialog(activity_second.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Cargando Datos");
        mProgressDialog.setMessage("Progreso...");
        mProgressDialog.setProgressStyle(mProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgress(0);
        mProgressDialog.setMax(100);
        mProgressDialog.show();

    }
    public class HiloEnvioDatos extends Thread {

        int i;

        private void publishProgress(Integer values) {
            int progreso = values.intValue();

            if (progreso <= 10) { // progresa adecuadamente
                mProgressDialog.setProgress(progreso * 10);
                mProgressDialog.setMessage("Progreso.... " + String.valueOf(progreso * 10) + "% ... ");
            }
            else {

                mProgressDialog.dismiss();
                mProgressDialog.setMessage("");
                CargarListView();
                Log.v("CargaEnnHilo", "En finalizado Hilo: " );

            }

        }

        public void run() {

            for (i = 1; i <= 10; i++) {
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
                    if (i < 11) publishProgress(-1);
                    else if (i == 11){
                        publishProgress(11);
                    }
                }
            });



        }

    }


}
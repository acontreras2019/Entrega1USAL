package com.example.alecontreras.datospersonalistalmacen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Calendar;
import java.util.Objects;

public class activity_third extends AppCompatActivity {

    private EditText etNombre, etApellido, etEdad, etTelefono, etFecha;
    private CheckBox ckPermisoConducir;

    private RadioButton rbBajo, rbMedio, rbAlto;

    private RadioGroup rbtGroup1;
    private Button btnFecha,btnReset, btnEnviar, btnCancelar ;

    private String permisoConducir, nivelIngles;

    private DatePickerDialog dpDialog;
    private Persona personaEditar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // binding a los elementos de la actividad
        binding();
        eventListener();
        iniciarObjetos();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String vData=bundle.getString("data");
            if(vData!=null) {
                personaEditar = new Persona();
                personaEditar.setFromTEXTLineformat(vData);
                if(personaEditar!=null){
                    String permiso  = personaEditar.getPermisoConducir();
                    etNombre.setText(personaEditar.getNombre());
                    etApellido.setText(personaEditar.getApellido());
                    etEdad.setText(String.valueOf(personaEditar.getEdad()));
                    etTelefono.setText(personaEditar.getTelefono());
                    etFecha.setText(personaEditar.getFechaAlta());
                    ckPermisoConducir.setChecked(false);
                    if(Objects.equals(permiso, "Si")){ckPermisoConducir.setChecked(true);}
                    String nivel  = personaEditar.getNivelIngles();
                    switch (nivel){
                        case "Alto":
                            rbAlto.setChecked(true);
                            break;
                        case "Medio":
                            rbMedio.setChecked(true);
                            break;
                        case "Bajo":
                            rbBajo.setChecked(true);
                            break;
                        default:
                            rbBajo.setChecked(false);
                            rbMedio.setChecked(false);
                            rbAlto.setChecked(false);
                            break;
                    }
                }

            }


        }

    }
    private void binding() {
        //EditText
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido=(EditText)findViewById(R.id.etApellido);
        etEdad=(EditText)findViewById(R.id.etEdad);
        etTelefono=(EditText)findViewById(R.id.etTelefono);
        etFecha=(EditText)findViewById(R.id.etFecha);

        //Checkbox
        ckPermisoConducir=findViewById(R.id.ckPermisoConducir);

        //Botones
        btnFecha=findViewById(R.id.btnFecha);
        btnReset=findViewById(R.id.btnReset);
        btnEnviar=findViewById(R.id.btnEnviar);
        btnCancelar=findViewById(R.id.btnCancelar);

        //RadioBotones
        rbBajo=findViewById(R.id.rbBajo);
        rbMedio=findViewById(R.id.rbMedio);
        rbAlto=findViewById(R.id.rbAlto);

        rbtGroup1 = findViewById(R.id.rbtGroup1);

    }

    private void eventListener(){
        rbtGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId){
                if(rbAlto.isChecked())nivelIngles="Alto";
                if(rbMedio.isChecked())nivelIngles="Medio";
                if(rbBajo.isChecked())nivelIngles="Bajo";
            }
        });

        ckPermisoConducir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                permisoConducir="No";
                if(ckPermisoConducir.isChecked())permisoConducir="Si";
            }
        });


    }

    private void iniciarObjetos() {
        etNombre.setText("");
        etApellido.setText("");
        etEdad.setText("");
        etTelefono.setText("");
        etFecha.setText("");
        etNombre.setText("");

        //Checkbox
        ckPermisoConducir.setChecked(false);

        //RadioBotones
        rbBajo.setChecked(false);
        rbMedio.setChecked(false);
        rbAlto.setChecked(false);

        permisoConducir = "No";
        nivelIngles = "No";

        initDatePicker();
        etFecha.setText(getDateToday());
    }

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener =  new  DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month =  month+1;
                String date =  makeDateString(dayOfMonth, month, year);
                etFecha.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        dpDialog = new DatePickerDialog(this, style, dateSetListener,year, month, day);

    }

    private String getDateToday(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month =  month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return  dayOfMonth + "/" + month + "/"+ year;
    }
    public void openDialogDatePicker(View v){

        dpDialog.show();
    }

    //Botones de Accion evento Click

    public void reset(View v){
        iniciarObjetos();
    }

    public void cancelar(View v){

        Intent i=new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }
    public void enviar(View v) {
        Intent i=new Intent();
        i.putExtra("nombre", etNombre.getText().toString());
        i.putExtra("apellido", etApellido.getText().toString());
        i.putExtra("edad", etEdad.getText().toString());
        i.putExtra("telefono", etTelefono.getText().toString());
        i.putExtra("permisoConducir", permisoConducir);
        i.putExtra("nivelIngles", nivelIngles);
        i.putExtra("fecha", etFecha.getText().toString());
        if(personaEditar!=null) {
            //Log.d("myTag1", String.valueOf(personaEditar.getId()));
            i.putExtra("id", String.valueOf(personaEditar.getId()));
        }

        setResult(RESULT_OK, i);
        finish();
    }
}
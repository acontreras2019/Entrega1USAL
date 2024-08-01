package com.example.alecontreras.datospersonalistalmacen;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.content.SharedPreferences;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_shared_preference extends AppCompatActivity {

    private RadioGroup rgFormat, rgStorage;
    private RadioButton rbtXML, rbtJSON;
    private RadioButton rbtBDD, rbtContentP;
    private Spinner opcionesFormat;

    private View lyFormat;

    String formatSelected, urlSelected, storageSelected;
    private Context  context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shared_preference);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding();
        iniciarObjetos();
        eventListener();
        recuperarConfiguracion();

    }


    private void binding() {


        //RadioGroup Format - RadioBotones
        rgFormat = findViewById(R.id.rgFormat);
        rbtXML=findViewById(R.id.rbtXML);
        rbtJSON=findViewById(R.id.rbtJson);
        lyFormat = findViewById(R.id.lyFormat);

        //RadioGroup Storage- RadioBotones
        rgStorage = findViewById(R.id.rgStorage);
        rbtBDD=findViewById(R.id.rbtBDD);
        rbtContentP=findViewById(R.id.rbtContentP);
        lyFormat = findViewById(R.id.lyFormat);

        opcionesFormat = findViewById(R.id.sp1);



    }
    private void iniciarObjetos() {
        //RadioBotones
        rbtJSON.setChecked(false);
        rbtXML.setChecked(false);
        formatSelected = "";
        urlSelected = "";
        storageSelected="";

    }

    private void eventListener(){

        rgFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId){
                if(rbtXML.isChecked())formatSelected="XML";
                if(rbtJSON.isChecked())formatSelected="JSON";

                if (formatSelected.equals("XML") ){
                    lyFormat.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(context, R.array.opcionesXML, android.R.layout.simple_spinner_item);
                    opcionesFormat.setAdapter(adapter);
                    opcionesFormat.setSelection(0);


                }
                if (formatSelected.equals("JSON") ){
                    lyFormat.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(context, R.array.opcionesJSON, android.R.layout.simple_spinner_item);
                    opcionesFormat.setAdapter(adapter);
                    opcionesFormat.setSelection(0);
                }


            }
        });

        rgStorage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rbtBDD.isChecked())storageSelected="BDD";
                if(rbtContentP.isChecked())storageSelected="ContentP";
                Log.v("Storage", "Selected(" + storageSelected+ "): " );
            }
        });

        opcionesFormat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                urlSelected= opcionesFormat.getSelectedItem().toString();
                Log.v("Spinner", "Selected(" + urlSelected+ "): " );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                urlSelected="";
            }
        });

    }

    public void guardar(View view) {
        SharedPreferences preferencias=getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("format",formatSelected);
        editor.putString("storage", storageSelected);
        editor.putString("urlFormat", urlSelected);
        editor.commit();
        Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_LONG).show();


    }

    public void cancelar(View view) {
        finish();
    }
    private void recuperarConfiguracion() {
        SharedPreferences preferencias=getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String formatSaved = preferencias.getString("format","");
        String storageSaved = preferencias.getString("storage","");
        String urlSaved = preferencias.getString("urlFormat","");
        if (formatSaved.equals("XML") ) {
            rbtXML.setChecked(true);
            if (urlSaved.equals(getResources().getStringArray(R.array.opcionesXML)[0])) {
                opcionesFormat.setSelection(0);

            }
            if (urlSaved.equals(getResources().getStringArray(R.array.opcionesXML)[1])) {
                opcionesFormat.setSelection(1);
            }
        }
        if (formatSaved.equals("JSON"))
        {
            rbtJSON.setChecked(true);
            if (urlSaved.equals(getResources().getStringArray(R.array.opcionesJSON)[0])) {
                //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.opcionesJSON, android.R.layout.simple_spinner_item);
                opcionesFormat.setSelection(0);
            }
            if (urlSaved.equals(getResources().getStringArray(R.array.opcionesJSON)[1])) {
                //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.opcionesJSON, android.R.layout.simple_spinner_item);
                opcionesFormat.setSelection(1);
            }
        }
        if(storageSaved.equals("BDD")) rbtBDD.setChecked(true);
        if(storageSaved.equals("ContentP")) rbtContentP.setChecked(true);

    }



}
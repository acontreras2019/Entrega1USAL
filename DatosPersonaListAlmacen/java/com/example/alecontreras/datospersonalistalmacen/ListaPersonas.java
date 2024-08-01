package com.example.alecontreras.datospersonalistalmacen;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class ListaPersonas {
    ArrayList<Persona> lista;
    Context context;
    private String formatPrefered, urlPrefered,storagePrefered;
    public ListaPersonas(Context ctx){
        this.lista = new ArrayList<Persona>();
        this.context = ctx;
    }
    public ListaPersonas(ArrayList<Persona> l){
        this.lista = new ArrayList<Persona>(l);
    }
    public ListaPersonas(){
        this.lista = new ArrayList<Persona>();
    }

    public ArrayList<Persona> ObtenerLista(){
        SharedPreferences preferencias=context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        formatPrefered = preferencias.getString("format","");
        storagePrefered= preferencias.getString("storage","");
        urlPrefered = preferencias.getString("urlFormat","");

        ArrayList<Persona> l = new ArrayList<Persona>();

        if (storagePrefered.equals("BDD")){
           l =  obtenerDatosSQLite();
        }
        else if(storagePrefered.equals("ContentP")){
            obtenerDatosContentProvider();
        }

      return l;
    }


    // Metodos de BDDSQLite
    public ArrayList<Persona> obtenerDatosSQLite() {
        Log.v("Obteniendo Datos", "En metodo " );
        ArrayList<Persona> li= new ArrayList<Persona>();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
                "agenda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(
                "select * from personas", null);
        try{
            if (fila.moveToFirst()) {
                do {
                    int id = Integer.parseInt(fila.getString(fila.getColumnIndexOrThrow("id")));
                    String name = fila.getString(fila.getColumnIndexOrThrow("nombre"));
                    String apellidos = fila.getString(fila.getColumnIndexOrThrow("apellido"));
                    int edad = Integer.parseInt(fila.getString(fila.getColumnIndexOrThrow("edad")));
                    String telefono = fila.getString(fila.getColumnIndexOrThrow("telefono"));
                    String permiso = fila.getString(fila.getColumnIndexOrThrow("permiso"));
                    String nivel = fila.getString(fila.getColumnIndexOrThrow("nivelIngles"));
                    String fecha = fila.getString(fila.getColumnIndexOrThrow("fechaAlta"));
                    Persona p = new Persona(id,name, apellidos,edad,telefono,permiso,nivel, fecha);
                    li.add(p);
                    //results.add(fila.getString(););
                }while (fila.moveToNext());

            }

        }
        catch(Exception e){
            Log.v("Error BDD ", "Obteniendo Datos: " + e );
        }
        bd.close();

        return li;
    }

    public void GuardarLista(ArrayList<Integer> id_Eliminar){
        for (int id : id_Eliminar) {
            eliminar(id);
        }
        for (Persona p : lista) {
           int id = p.getId();
           int id_bdd = getIdBDD(id); // Consultar si existe el Id en la BDD sino existe retorna -1
           if(id_bdd>=0){
               //editar registro
               editar(p);
           }
           else {
               //adicionarRegistro
               adicionar(p);
           }

        }
    }

    private int getIdBDD(int id) {
        Log.v("getIDBDD", "En metodo " );
        int edad=0;
        int id_bdd= -1;

        String  nombre="", apellidos="",telefono="", permiso="", nivel="", fechaAlta="";
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.context,
                "agenda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(
                "select id,nombre, apellido from personas where id=" + String.valueOf(id), null);
        if (fila.moveToFirst()) {
            id_bdd = fila.getInt(0);
            nombre = fila.getString(1);
            apellidos = fila.getString(2);
           /* telefono = fila.getString(3);
            permiso = fila.getString(3);
            nivel = fila.getString(4);
            fechaAlta = fila.getString(5);*/

        }

        bd.close();
        //Persona p = new Persona(id_bdd,nombre,apellidos, edad, telefono, permiso, nivel, fechaAlta);
        return id_bdd;

    }

    public void editar(Persona p_edit) {
        Log.v("EditarrBDD", "En metodo " );
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,
                "agenda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        int id = p_edit.getId();
        ContentValues registro = getContentValues(p_edit, id);

        int cant = bd.update("personas", registro, "id=" + id, null);
        bd.close();

        if (cant == 1)
            Toast.makeText(this.context, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this.context, "no existe un persona con el código ingresado:" + id,
                    Toast.LENGTH_SHORT).show();
    }


    public void adicionar(Persona p_add) {
        Log.v("AdicionarBDD", "En metodo " );
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.context,
                "agenda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        int id = p_add.getId();
        ContentValues registro = getContentValues(p_add, id);

        bd.insert("personas", null, registro);
        bd.close();


    }
    @NonNull
    private static ContentValues getContentValues(Persona p, int id) {

        String nombre = p.getNombre();
        String apellidos = p.getApellido();
        int edad = p.getEdad();
        String telefono = p.getTelefono();
        String permiso = p.getPermisoConducir();
        String nivel = p.getNivelIngles();
        String fecha = p.getFechaAlta();

        ContentValues registro = new ContentValues();
        registro.put("id", id);
        registro.put("nombre", nombre);
        registro.put("apellido", apellidos);
        registro.put("edad", edad);
        registro.put("telefono", telefono);
        registro.put("permiso", permiso);
        registro.put("fechaAlta", fecha);
        registro.put("nivelIngles",nivel );
        return registro;
    }

    public void eliminar(int id) {
        Log.v("Eliminar", "En metodo " );
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.context,
                "agenda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        int cant = bd.delete("personas", "id=" + id, null);
        bd.close();

        if (cant == 1)
            Toast.makeText(this.context, "Articulos elimnados del almacen:",
                    Toast.LENGTH_SHORT).show();

    }



    // Metodos del ContentProvider
    public ArrayList<Persona> obtenerDatosContentProvider() {
        Log.v("ClassListaPersonas", "ObtenerLista(faltaprogramar): " );
        ArrayList<Persona> lis;
        lis = new ArrayList<Persona>();

        return lis;

    }

    public String[] listToTEXTformat() {
        int len = lista.size();
        String[] stringList = new String[len];
        for (int i = 0; i < len; i++) {
            stringList[i]  = lista.get(i).toTEXTformat();
        }

        return stringList;
    }
    public ArrayList<Persona> setListFromTEXTLineformat(String[] sdata) {
        ArrayList<Persona> l = new ArrayList<Persona>();
        String[] sListPersona = sdata;
        for (String s: sListPersona
             ) {
             Persona p = new Persona();
             p.setFromTEXTLineformat(s);
            l.add(p);
        }
        return l;


    }



}

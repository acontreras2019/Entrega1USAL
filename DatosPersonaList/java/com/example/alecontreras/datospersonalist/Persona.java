package com.example.alecontreras.datospersonalist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
public class Persona {
    private int id;   // clave
    private String nombre;
    private String apellido;
    private int edad;
    private String telefono;
    private String permisoConducir;
    private String nivelIngles;
    private String fechaAlta;

    public Persona(){
        this.id = 0;
        this.nombre = "";
        this.apellido =  "";
        this.edad  = 0;
        this.telefono =  "";
        this.permisoConducir =  "";
        this.nivelIngles =  "";
        this.fechaAlta =  "";

    }
    public Persona(int id, String nombre, String apellido, int edad, String telefono, String permisoConducir, String nivelIngles, String fechaAlta){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad  = edad;
        this.telefono = telefono;
        this.permisoConducir = permisoConducir;
        this.nivelIngles = nivelIngles;
        this.fechaAlta = fechaAlta;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPermisoConducir() {
        return permisoConducir;
    }
    public void setPermisoConducir(String permisoConducir) {
        this.permisoConducir = permisoConducir;
    }

    public String getNivelIngles() {
        return nivelIngles;
    }
    public void setNivelIngles(String nivelIngles) {
        this.nivelIngles = nivelIngles;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNombreApellido(){
        return nombre + " " + apellido;
    }

    public String getInfoPrincipal(){
        return "INFO| Edad:"+edad + " Tel:" + telefono;
    }
    public String getInfoExtra(){
        return "EXTRA| PC:"+permisoConducir + " Nivel Ingles:" + nivelIngles + " Fecha Alta:"+ fechaAlta;
    }


    public String toTEXTlineFormat() {

        StringBuilder sb = new StringBuilder();
        sb.append(id); sb.append("\t");
        sb.append(nombre);  sb.append("\t");
        sb.append(apellido); sb.append("\t");
        sb.append(edad);  sb.append("\t");
        sb.append(telefono); sb.append("\t");
        sb.append(permisoConducir);  sb.append("\t");
        sb.append(nivelIngles); sb.append("\t");
        sb.append(fechaAlta);  sb.append("\t");

        return sb.toString();
    }

    static final String ID_TAG = "ID";
    static final String nNOMBRE_TAG = "nNOMBRE";
    static final String nAPELLIDO_TAG = "nAPELLIDO";
    static final String nEDAD_TAG = "nEDAD";
    static final String nTELEFONO_TAG = "nTELEFONO";
    static final String nPERMISOCONDUCIR_TAG = "nPERMISOCONDUCIR";
    static final String nNIVELINGLES_TAG = "nNIVELINGLES";
    static final String nFECHAALTA_TAG = "nFECHAALTA";

    public String toXMLformat() {

        String lineaXML;

        StringBuilder sb = new StringBuilder();
        lineaXML = " <persona>\n";
        sb.append(lineaXML);
        lineaXML = "   <" + ID_TAG + ">" + id + "</" + ID_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nNOMBRE_TAG + ">" + nombre + "</" + nNOMBRE_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nAPELLIDO_TAG + ">" + apellido + "</" + nAPELLIDO_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nEDAD_TAG + ">" + edad + "</" + nEDAD_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nTELEFONO_TAG + ">" + telefono + "</" + nTELEFONO_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nPERMISOCONDUCIR_TAG + ">" + permisoConducir + "</" + nPERMISOCONDUCIR_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nNIVELINGLES_TAG + ">" + nivelIngles + "</" + nNIVELINGLES_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nFECHAALTA_TAG + ">" + fechaAlta + "</" + nFECHAALTA_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = " </persona>\n";
        sb.append(lineaXML);
        return sb.toString();
    }

    public String toJSONformat() {

        String lineaJSON;

        StringBuilder sb = new StringBuilder();
        // sin hacer
        //para convertir a formato JSON
        lineaJSON = " sin datos. SIN IMPLEMENTAR\n";
        sb.append(lineaJSON);
        return sb.toString();
    }

    public String toTEXTformat() {
        StringBuilder sb = new StringBuilder();
        sb.append(id); sb.append("\n");
        sb.append(nombre);  sb.append("\n");
        sb.append(apellido); sb.append("\n");
        sb.append(edad); sb.append("\n");
        sb.append(telefono); sb.append("\n");
        sb.append(permisoConducir); sb.append("\n");
        sb.append(nivelIngles); sb.append("\n");
        sb.append(fechaAlta); sb.append("\n");
        return sb.toString();
    }
    public void setFromTEXTLineformat(String sdata) {

        String[] sUnaPersona;
        sUnaPersona = sdata.trim().split("\n");

        this.setId(tryParseInt(sUnaPersona[0].trim(),0));
        this.setNombre(sUnaPersona[1].trim());
        this.setApellido(sUnaPersona[2].trim());
        this.setEdad(tryParseInt(sUnaPersona[3].trim(),0));
        this.setTelefono(sUnaPersona[4].trim());
        this.setPermisoConducir(sUnaPersona[5].trim());
        this.setNivelIngles(sUnaPersona[6].trim());
        this.setFechaAlta(sUnaPersona[7].trim());

    }

    private int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Código interno: ");
        sb.append(id);
        sb.append("\nNombre: ");
        sb.append(nombre);
        sb.append("\nApellido: ");
        sb.append(apellido);
        sb.append("\nEdad: ");
        sb.append(edad);
        sb.append("\nTelefono: ");
        sb.append(telefono);
        sb.append("\nPermiso Conducir: ");
        sb.append(permisoConducir);
        sb.append("\nNivel Ingles: ");
        sb.append(nivelIngles);
        sb.append("\nFecha Alta: ");
        sb.append(fechaAlta);
        sb.append("\n");
        return sb.toString();
    }

}

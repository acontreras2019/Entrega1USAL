
package com.example.alecontreras.datospersonalist;
public class UnAlumno {
    private int id;   // clave
    private String nombreAlumno;
    private String apellidoAlumno;

    public UnAlumno (int id, String nombreAlumno, String apellidoAlumno) {
        this.id = id;
        this.nombreAlumno = nombreAlumno;
        this.apellidoAlumno = apellidoAlumno;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getApellidoAlumno() {
        return apellidoAlumno;
    }
    public void setApellidoAlumno(String apellidoAlumno) {
        this.apellidoAlumno = apellidoAlumno;
    }

    public String toTEXTformat() {
        StringBuilder sb = new StringBuilder();
        sb.append(id); sb.append("\n");
        sb.append(nombreAlumno);  sb.append("\n");
        sb.append(apellidoAlumno); sb.append("\n");
        return sb.toString();
    }


    public String toTEXTlineFormat() {

        StringBuilder sb = new StringBuilder();
        sb.append(id); sb.append("\t");
        sb.append(nombreAlumno);  sb.append("\t");
        sb.append(apellidoAlumno); sb.append("\t");
        return sb.toString();
    }
    static final String ID_TAG = "ID";
    static final String nNOMBRE_TAG = "nNOMBRE";
    static final String nAPELLIDOS_TAG = "nAPELLIDOS";

    public String toXMLformat() {

        String lineaXML;

        StringBuilder sb = new StringBuilder();
        lineaXML = " <alumno>\n";
        sb.append(lineaXML);
        lineaXML = "   <" + ID_TAG + ">" + id + "</" + ID_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nNOMBRE_TAG + ">" + nombreAlumno + "</" + nNOMBRE_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = "   <" + nAPELLIDOS_TAG + ">" + apellidoAlumno + "</" + nAPELLIDOS_TAG + ">\n";
        sb.append(lineaXML);
        lineaXML = " </alumno>\n";
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



    public void setFromTEXTLineformat(String sdata) {

        String sUnUnAlumno[];
        sUnUnAlumno = sdata.trim().split("\n");

        this.setId(Integer.parseInt(sUnUnAlumno[0].trim()));
        this.setNombreAlumno(sUnUnAlumno[1].trim());
        this.setApellidoAlumno(sUnUnAlumno[2].trim());
    }



    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Código interno: ");
        sb.append(id);
        sb.append("\nNombre Alumno: ");
        sb.append(nombreAlumno);
        sb.append("\nApellidos Alumno: ");
        sb.append(apellidoAlumno);
        sb.append("\n");
        return sb.toString();
    }
}

package com.example.alecontreras.datospersonalistcarga;

import java.util.ArrayList;

public class ListaPersonas {
    ArrayList<Persona> lista;
    public ListaPersonas(){
        this.lista = new ArrayList<Persona>();
    }
    public ArrayList<Persona> CargarLista() {
        Persona persona;
        persona = new Persona(0, "PABLO", "CAÑO PASCUAL", 26, "+34 621322994","Si", "Alto","25/04/2024");
        lista.add(persona);
        persona = new Persona(1, "ALEJANDRINA", "CONTRERAS ZAVALA",40, "+34 621322995","Si", "","25/04/2024");
        lista.add(persona);
        persona = new Persona(2, "DAVID", "HERRERO HERNÁNDEZ",26, "+34 621322996","No", "","25/04/2024");
        lista.add(persona);
        persona = new Persona(3, "BORJA", "LORENZO ADAJAS", 26, "+34 621322997","No", "","25/04/2024");
        lista.add(persona);
        persona = new Persona(4, "LIENY", "RODRIGUEZ RODRIGUEZ", 32, "+34 621322998","No", "","25/04/2024");
        lista.add(persona);
        persona = new Persona(5, "SERGIO", "SALINERO SANTAMARÍA", 26, "+34 621322999","", "","25/04/2024");
        lista.add(persona);
        return lista;
    }

    public String[] listToTEXTformat() {
        String[] stringList = new String[6];
        for (int i = 0; i < 6; i++) {
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

package com.example.alecontreras.datospersonalist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonaListAdapter extends BaseAdapter  {
    private final Activity actividad;
    private final ArrayList<Persona> lista;

    public PersonaListAdapter(Activity actividad, ArrayList<Persona> lista) {
        super();
        this.actividad = actividad;
        this.lista = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = actividad.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.elem_persona, null, true);


        String infoPrincipal = lista.get(position).getApellido();
        String infoExtra = lista.get(position).getTelefono();
        TextView tvN = (TextView) view.findViewById(R.id.nombre);
        tvN.setText(lista.get(position).getNombreApellido());
        TextView tvAp = (TextView) view.findViewById(R.id.infoPrincipal);
        tvAp.setText(lista.get(position).getInfoPrincipal());
        TextView tvInfo = (TextView) view.findViewById(R.id.infoExtra);
        tvInfo.setText(lista.get(position).getInfoExtra());

        ImageView imageView = (ImageView) view.findViewById(R.id.foto_s);

        switch (position){
            case 0:
                imageView.setImageResource(R.drawable.silueta_femenina);
                break;
            case 1:
                imageView.setImageResource(R.drawable.silueta_masculina_lateral);
                break;
            case 2:
                imageView.setImageResource(R.drawable.silueta_mujer);
                break;
            case 3:
                imageView.setImageResource(R.drawable.hombre_calvo);
                break;
            case 4:
                imageView.setImageResource(R.drawable.silueta_femenina_pelo_largo);
                break;

            default:
                imageView.setImageResource(R.drawable.forma_hombre);
                break;
        }

        return view;
    }
    public int getCount() {
        return lista.size();
    }

    public Object getItem(int arg0) {
        return lista.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }
}

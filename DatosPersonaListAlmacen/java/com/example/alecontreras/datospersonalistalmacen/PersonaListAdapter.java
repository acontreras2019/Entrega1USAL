package com.example.alecontreras.datospersonalistalmacen;

import android.annotation.SuppressLint;
import android.widget.BaseAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.widget.ImageView;
import android.widget.TextView;


public class PersonaListAdapter  extends BaseAdapter  {
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

        String lbNombres = lista.get(position).getNombreApellido();
        String lbEdad = actividad.getResources().getString(R.string.edad) + String.valueOf(lista.get(position).getEdad());
        String lbTelefono = actividad.getResources().getString(R.string.telefono) + lista.get(position).getTelefono();
        String lbPermiso = actividad.getResources().getString(R.string.permisoConducir) + lista.get(position).getPermisoConducir();
        String lbNivelIngles = actividad.getResources().getString(R.string.nivelIngles) + lista.get(position).getNivelIngles();
        String lbFechaAlta = actividad.getResources().getString(R.string.fechaAlta) + lista.get(position).getFechaAlta();


        TextView tvN = (TextView) view.findViewById(R.id.txtNombre);
        tvN.setText(lbNombres);
        TextView tvEdad = (TextView) view.findViewById(R.id.txtEdad);
        tvEdad.setText( lbEdad );
        TextView tvTelefono = (TextView) view.findViewById(R.id.txtTelefono);
        tvTelefono.setText(lbTelefono);
        TextView tvLicencia = (TextView) view.findViewById(R.id.txtLicencia);
        tvLicencia.setText(lbPermiso);
        TextView tvNivelIngles = (TextView) view.findViewById(R.id.txtNivelIngles);
        tvNivelIngles.setText(lbNivelIngles);
        TextView tvFecha = (TextView) view.findViewById(R.id.txtFechaRegistro);
        tvFecha.setText(lbFechaAlta);

/*        TextView tvAp = (TextView) view.findViewById(R.id.infoPrincipal);
        tvAp.setText(lista.get(position).getInfoPrincipal());
        TextView tvInfo = (TextView) view.findViewById(R.id.infoExtra);
        tvInfo.setText(lista.get(position).getInfoExtra());*/

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

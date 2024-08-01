package com.example.alecontreras.datospersonalistalmacen;

import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpXmlPostListConnection extends Activity {
    public String formatPrefered, urlPrefered,storagePrefered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferencias= this.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        formatPrefered = preferencias.getString("format","");
        storagePrefered= preferencias.getString("storage","");
        urlPrefered = preferencias.getString("urlFormat","");
        //Log.v("Preferencias", "En run(" +storagePrefered+ " "+ formatPrefered + " "+urlPrefered+"  ) " );

        new HttpPostTask().execute();
        //setContentView(R.layout.activity_second);


    }

    private class HttpPostTask extends AsyncTask<Void, Void, String> {

        private static final String TAG = "HttpPostTask";

        final String PERSONA_TAG = "persona";
        final String NOMBRE_TAG = "nombre";
        final String APELLIDOS_TAG = "apellidos";
        final String TELEFONO_TAG = "telefono";
        final String PERMISO_TAG = "conduce";
        final String NIVELiNGLES_TAG = "nivel_ingles";
        final String FECHAaLTA_TAG = "datereg";
        final String EDAD_TAG = "edad";

        String mNombre, mApellido, mTelefono, mPermiso, mNivelIngles, mFechaAltaM, mEdad;
        boolean mIsParsingNom, mIsParsingApell, mIsParsingTel, mIsParsingPermiso, mIsParsingNivelIngles, mIsParsingFechaAlta, mIsParsingEdad;
        ArrayList<Persona> miList = new ArrayList<Persona>();


        @Override
        protected String doInBackground(Void... params) {

            String data = "";
            HttpURLConnection httpUrlConnection = null;

            URL url = null;

            try {
                url = new URL("http://"+ urlPrefered);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                //Log.v("HTTPActivity", "En run(" + url + "): " );

                InputStream in = null;
                in = new BufferedInputStream(conn.getInputStream());

                if(formatPrefered.equals("XML")){
                    data = readStreamXML(in);
                }
                if(formatPrefered.equals("JSON")){
                    data = readStreamJSON(in);

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }


        @Override
        protected void onPostExecute(String result) {
            if (formatPrefered.equals("XML")){
                try {
                    // Create the Pull Parser
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(result));

                    // Get the first Parser event and start iterating over the XML document
                    int eventType = xpp.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {

                        if (eventType == XmlPullParser.START_TAG) {
                            startTag(xpp.getName());
                        } else if (eventType == XmlPullParser.END_TAG) {
                            endTag(xpp.getName());
                        } else if (eventType == XmlPullParser.TEXT) {
                            text(xpp.getText());
                        }
                        eventType = xpp.next();
                    }
                } catch (XmlPullParserException e) {
                    Log.e("XmlPullParserException", e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("IOException", e.toString());
                    e.printStackTrace();
                }
            }

            ListaPersonas lista = new ListaPersonas(miList);
            String [] l = lista.listToTEXTformat();

            //Log.v("HTTPActivity", "En run(" + l[0] +"): " );
            Intent intent = new Intent(HttpXmlPostListConnection.this, activity_second.class);
            intent.putExtra("list", l);
            startActivity(intent);

        }

        private String readStreamXML(InputStream in) {

            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();

            try {
                reader = new BufferedReader(new InputStreamReader(in,  StandardCharsets.UTF_8));
                String line;
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    if(count != 0) {
                        data.append(line);
                    }
                    count++;
                }
            } catch (IOException e) {
                Log.e("TAG", e.toString());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("TAG", "IOException");
                    }
                }
            }
            return data.toString();
        }

        private String readStreamJSON(InputStream in) {
            JsonReader reader = new JsonReader(new InputStreamReader(in,  StandardCharsets.UTF_8));
            try {
                miList = readPersonasArray(reader);
                return miList.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        private ArrayList<Persona> readPersonasArray(JsonReader reader) throws IOException {
            ArrayList<Persona> personasJson = new ArrayList<Persona>();
            reader.beginObject();
            reader.nextName();
            while (reader.hasNext()) {
                reader.beginArray();
                int id = -1;
                while (reader.hasNext()) {
                        id++;
                        personasJson.add(readPersona(reader, id));
                }
                reader.endArray();
            }
            reader.endObject();
            return personasJson;
        }

        public Persona readPersona(JsonReader reader, int id) throws IOException {

            String nombre = null;
            String apellidos = null;
            String telefono = null;
            int edad = 0;
            String permiso = null;
            String nivelIngles = null;
            String fechaAlta = null;


            reader.beginObject();
            Log.v("ReadJson", "En la atributos de Persona): " );
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("nombre")) {
                    nombre = reader.nextString();
                } else if (name.equals("apellidos")) {
                    apellidos = reader.nextString();
                } else if (name.equals("tfno")) {
                    telefono = reader.nextString();
                } else if (name.equals("edad")) {
                    edad = reader.nextInt();
                } else if (name.equals("conduce")) {
                    permiso = reader.nextString();
                    if(permiso.equals("N")) permiso="No";
                    if(permiso.equals("S")) permiso="Si";
                } else if (name.equals("registro")) {
                    fechaAlta = reader.nextString();
                } else if (name.equals("ingles")) {
                    nivelIngles = reader.nextString();
                    if(nivelIngles.equals("H")) nivelIngles="Alto";
                    if(nivelIngles.equals("M")) nivelIngles="Medio";
                    if(nivelIngles.equals("L")) nivelIngles="Bajo";
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            Persona p = new Persona(id,nombre, apellidos, edad, telefono, permiso, nivelIngles,fechaAlta);
            return p;
        }
        public void startTag(String localName) {
            if (localName.equals(NOMBRE_TAG)) {
                mIsParsingNom = true;
            } else if (localName.equals(APELLIDOS_TAG)) {
                mIsParsingApell = true;
            } else if (localName.equals(TELEFONO_TAG)) {
                mIsParsingTel = true;
            } else if (localName.equals(PERMISO_TAG)) {
                mIsParsingPermiso = true;
            } else if (localName.equals(NIVELiNGLES_TAG)) {
                mIsParsingNivelIngles = true;
            } else if (localName.equals(FECHAaLTA_TAG)) {
                mIsParsingFechaAlta = true;
            }
        }

        public void text(String text) {
            if (mIsParsingNom) {
                mNombre = text.trim();
            } else if (mIsParsingApell) {
                mApellido = text.trim();
            } else if (mIsParsingTel) {
                mTelefono = text.trim();
            } else if (mIsParsingEdad) {
                mEdad = text.trim();
            } else if (mIsParsingPermiso) {
                mPermiso = text.trim();
                if(mPermiso.equals("S")) mPermiso="Si";
                if(mPermiso.equals("N")) mPermiso="No";
            } else if (mIsParsingNivelIngles) {
                mNivelIngles = text.trim();
                if(mNivelIngles.equals("H")) mNivelIngles="Alto";
                if(mNivelIngles.equals("M")) mNivelIngles="Medio";
                if(mNivelIngles.equals("L")) mNivelIngles="Bajo";
            } else if (mIsParsingFechaAlta) {
                mFechaAltaM = text.trim();
            }
        }

        public void endTag(String localName) {
            if (localName.equals(NOMBRE_TAG)) {
                mIsParsingNom = false;
            } else if (localName.equals(APELLIDOS_TAG)) {
                mIsParsingApell = false;
            } else if (localName.equals(TELEFONO_TAG)) {
                mIsParsingTel = false;
            } else if (localName.equals(EDAD_TAG)) {
                mIsParsingEdad = false;
            } else if (localName.equals(PERMISO_TAG)) {
                mIsParsingPermiso = false;
            } else if (localName.equals(NIVELiNGLES_TAG)) {
                mIsParsingNivelIngles = false;
            } else if (localName.equals(FECHAaLTA_TAG)) {
                mIsParsingFechaAlta = false;
            }else if (localName.equals(PERSONA_TAG)) {
                int c = miList.size();
                Persona p = new Persona(c,mNombre,mApellido, 0,mTelefono, mPermiso, mNivelIngles,mFechaAltaM);
                miList.add(p);
                mNombre = null;
                mTelefono = null;
                mApellido = null;
                mEdad = null;
                mPermiso = null;
                mNivelIngles = null;
                mFechaAltaM = null;
            }
        }

    }

}


package com.example.alecontreras.datospersonalistalmacen;


import android.util.Log;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpConnectionURL {
    ArrayList<Persona> lista;
   // Context context;
    List<String> milist = new ArrayList<String>();
    String URL;
    final String TAG = "HttpPostTask";

    final String ID_TAG = "id";
    final String nNOMBRE_TAG = "nombre";
    String mLat, mLng, mMag;
    boolean mIsParsingLat, mIsParsingLng, mIsParsingMag;
    public HttpConnectionURL( String url){
            this.lista = new ArrayList<Persona>();
            this.URL = url;

    }

    public ArrayList<Persona> HttpGetList() {
        String dataFromURl = HTTPGetData();
       // getDataFormat(dataFromURl);
        this.lista = new ArrayList<Persona>();
        Persona p = new Persona(0,"NAle","AContreras", 50, "fff", "SSSS", "Alto", "01/01/1987") ;this.lista.add(p);
        return lista;

    }
    private String HTTPGetData() {

            String data = "";
            HttpURLConnection httpUrlConnection = null;
            URL url = null;
            try {

                // Get your own user name at http://www.geonames.org/login
              //  String USER_NAME = "aporter";
                //String parameters = "north=44.1&south=-9.9&east=-22.4&west=55.2&username=" + USER_NAME;





               // url = new URL("http://"+ this.URL);
                 url = new URL("http://prodiasv02.fis.usal.es/DAM/ServerJsonUTF8ns.txt");
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();

                InputStream in = null;
                in = new BufferedInputStream(conn.getInputStream());

                data = readStream(in);
                Log.v("Http", "LeerData(" +data+ "): " );
                //return null;

            } catch (MalformedURLException e) {
                Log.v("Http", "MalformedURLException(" +data+ "): " );
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("Http", "IOException(" +data+ "): " );
                e.printStackTrace();
            }
            Log.v("Http", "dataFromURL(" +data+ "): " );
            return data;
        }
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder data = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {

                data.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException");
                }
            }
        }
        return data.toString();
    }
    private void getDataFormat(String result) {

            try {
                // Create the Pull Parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                // Set the Parser's input to be the XML document in the HTTP Response
//                xpp.setInput(new InputStreamReader(result.getEntity().getContent()));
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
            } catch (IOException e) {
                e.printStackTrace();
            }
         /*   milist.add("aaa");
            milist.add("bbb");
            milist.add("ccc");
            milist.add(result);

*/
       // milist.add(result);
       /* Log.v("CargaInicial", "En run(" + milist.get(1).toString() + "): " );
            ListView listView=(ListView)findViewById(R.id.lv1);
            ArrayAdapter<String> list = new ArrayAdapter<String>(HttpXmlPostListConnection.this, R.layout.list_item, R.id.textViewList1, milist);
            Log.v("CargaAdapter", "En run(" + list.getItem(1)+ "): " );
            listView.setAdapter(list);*/

        /*    setListAdapter(new ArrayAdapter<String>(
                    HttpXmlPostListConnection.this,
                    R.layout.list_item, milist));*/
        }

        public void startTag(String localName) {
            if (localName.equals(nNOMBRE_TAG)) {
                mIsParsingLat = true;
            } else if (localName.equals(ID_TAG)) {
                mIsParsingLng = true;
            }
            /*else if (localName.equals(MAGNITUDE_TAG)) {
                mIsParsingMag = true;
            }*/
        }

        public void text(String text) {
            if (mIsParsingLat) {
                mLat = text.trim();
            } else if (mIsParsingLng) {
                mLng = text.trim();
            } else if (mIsParsingMag) {
                mMag = text.trim();
            }
        }

        public void endTag(String localName) {
            if (localName.equals(nNOMBRE_TAG)) {
                mIsParsingLat = false;
            } else if (localName.equals(ID_TAG)) {
                mIsParsingLng = false;
            }
            /*else if (localName.equals(MAGNITUDE_TAG)) {
                mIsParsingMag = false;
            }
            else if (localName.equals("earthquake")) {
                milist.add(MAGNITUDE_TAG + ":" + mMag + "," + LATITUDE_TAG + ":"
                        + mLat + "," + LONGITUDE_TAG + ":" + mLng);
                mLat = null;
                mLng = null;
                mMag = null;
            }*/
        }
}



package google.android.com.myapplicationwservice_parsexml2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends Activity {
    private static final String TAG="ok";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public class Asynchrone extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection httpUrlConnection = null;
            String result="";
            try {
// Obtenir une variable de type XmlPullParserFactory
                XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
// Créer le XmlPullParser à l’aide de la méthode newPullParser
                XmlPullParser xmlPullParser = pullParserFactory.newPullParser();
// Récupérer le fichier xml sur le serveur afin d’obtenir un flux en lecture sur le //fichier
                URL url = new URL("http://10.0.2.2/formation1/document.xml");
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new  BufferedInputStream(httpUrlConnection.getInputStream());
//Définir le fichier récupéré comme entrée pour le parseur XML
                xmlPullParser.setInput(in, null);
// Récupérer les évènements décrits dans le fichier XML
                int eventType = xmlPullParser.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        Log.d(TAG, "Debut du document");
                    } else if(eventType == XmlPullParser.START_TAG) {
                        Log.d(TAG, "Dans le tag = "+ xmlPullParser.getName());
                        int attrsCount = xmlPullParser.getAttributeCount();
                        for (int i = 0; i < attrsCount; ++i) {
                            Log.d(TAG, "Attribut numero " + i + " du tag " + xmlPullParser.getName() + "est = "+ xmlPullParser.getAttributeValue(i));
                        }
                    }
                    else if(eventType == XmlPullParser.END_TAG) {
                        Log.d(TAG, "Fin du tag = "+ xmlPullParser.getName());
                    }
                    eventType = xmlPullParser.next();
                }
                in.close();

                httpUrlConnection.disconnect();
            } catch (MalformedURLException me) {
                me.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        }


    }
    public void reception(View v){
        Asynchrone as=new Asynchrone();
        as.execute("ok");
    }

}

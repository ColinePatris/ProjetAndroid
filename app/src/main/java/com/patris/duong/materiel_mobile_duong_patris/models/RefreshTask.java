package com.patris.duong.materiel_mobile_duong_patris.models;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Coline on 04/06/2017.
 */

public class RefreshTask extends AsyncTask<String,Void,String> {
    Context context;

    public RefreshTask(Context c) {
        context = c;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL("http://ama-gestion-clients.appspot.com/rest/client");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream stream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String jason = bufferedReader.readLine();
            return jason;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
    }
}

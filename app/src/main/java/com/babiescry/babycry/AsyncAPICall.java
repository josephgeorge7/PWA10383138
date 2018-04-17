package com.babiescry.babycry;


import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class AsyncAPICall extends AsyncTask<Void, Void, String> {

    private String url;
    private String endpoint;
    private NetworkCallback networkCallback;

    AsyncAPICall(NetworkCallback networkCallback, String url, String endpoint) {
        this.url = url;
        this.endpoint = endpoint;
        this.networkCallback = networkCallback;
    }

    @Override
    protected void onPreExecute() {
        networkCallback.dataFetchStarted(endpoint);
    }

    @Override
    protected String doInBackground(Void... voids) {
        return GETdata(url);
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (networkCallback!=null) {
            networkCallback.processJSONString(jsonString, endpoint);
        }
    }

    private static String GETdata(String urlString){
        HttpURLConnection urlConnection = null;
        StringBuilder result;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();

            InputStream is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result!=null?result.toString():null;
    }
}
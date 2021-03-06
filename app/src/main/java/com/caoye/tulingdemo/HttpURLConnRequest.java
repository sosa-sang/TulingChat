package com.caoye.tulingdemo;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is for HttpURLConnection Request
 * Created by admin on 7/18/16.
 */
public class HttpURLConnRequest extends AsyncTask<String, String, String> {
    String url;
    String content;
    HttpURLConnListener listener;

    public HttpURLConnRequest(String url, String content, HttpURLConnListener listener) {
        this.url = url;
        this.content = content;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json; charset = UTF-8");

            JSONObject reqData = new JSONObject();
            reqData.put("key", AppConstant.API_KEY);
            reqData.put("info", content);
            OutputStream os = conn.getOutputStream();
            os.write(reqData.toString().getBytes("UTF-8"));

            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        listener.getDataFromURL(data);
        super.onPostExecute(data);
    }
}

package com.co.ametech.heroicapp.Logic;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.co.ametech.heroicapp.Logic.model.Route;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan M.E
 */
public class FindRouteUser extends AsyncTask<Void,Integer,String> {

    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyCCC5TaV3arXIEm0o_NVYWLSU2xWXgxpLg";

    private RouteFinderListener listener;
    private String origin;
    private String destination;
    private ProgressDialog progress;

    public FindRouteUser(RouteFinderListener listener, LatLng origin, LatLng destination) {
        this.listener = listener;
        this.origin = ""+ origin.latitude + "," + origin.longitude;
        this.destination = ""+ destination.latitude + "," + destination.longitude;
    }

    private String createUrl() throws UnsupportedEncodingException {
        //crea la url
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");

        return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&key=" + GOOGLE_API_KEY;
    }

    public void addProgressDialog(ProgressDialog progress) {
        this.progress = progress;
    }

    public void onPreExecute() {
        starProgress();
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            URL url = new URL(createUrl());
            InputStream is = url.openConnection().getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            //copio linea por linea hasta que no haiga nada
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            is.close();

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String res) {
        try {
            parseJSon(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;

        List<LatLng> points = new ArrayList<>();

        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");

        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            points.addAll(new DecodePolyLine(overview_polylineJson.getString("points")).decode());
        }
        endProgress();
        listener.onDirectionFinderSuccess(points);
    }

    private void starProgress() {
        if(progress!=null) {
            progress.show();
        }
    }

    private void endProgress() {
        if(progress!=null) {
            progress.dismiss();
        }
    }

}

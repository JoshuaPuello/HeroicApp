package com.co.ametech.heroicapp.Logic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Observable;

/**
 * Created by Alan M.E
 */
public class LocationUser extends Observable implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleApiClient mClient;
    private Location lastLocation;
    private Context activity;

    public LocationUser(Context activity) {
        this.activity = activity;
        callService();
    }

    public LocationUser(Context activity, Location lastLocation) {
        this.activity = activity;
        this.lastLocation = lastLocation;
    }

    private void callService() {
        mClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        mClient.connect();
    }

    public void stopUpdate() {
        mClient.disconnect();
    }

    public void StarUpdate() {
        mClient.connect();
    }

    private void findLocation() {
        //Miro si no tengo los permisos activados
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling

            int REQUEST_LOCATION = 1;
            ActivityCompat.requestPermissions((Activity) activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION);

            findLocation();

        } else { //en caso de que si

            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);//manda la ultima localizacion conocida del dispositivo.

            if (lastLocation != null) {
                setChanged();
                notifyObservers(true);//notifico a los observadores que el lastLocation ya no es null.
                //(patron de diseño Observador)
            }

            LocationRequest respuesta = LocationRequest.create();//instancia unica
            respuesta.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//alta prescion del GPS
            respuesta.setInterval(5000);//que verifique cada 5 seg la localizacion del usuario
            LocationServices.FusedLocationApi.requestLocationUpdates(mClient, respuesta, this);//me manda la nueva localizacion en el metodo onLocationChanged
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        findLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        setChanged();
        notifyObservers();
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

}

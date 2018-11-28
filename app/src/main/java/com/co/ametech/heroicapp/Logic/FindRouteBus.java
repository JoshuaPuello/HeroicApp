package com.co.ametech.heroicapp.Logic;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.co.ametech.heroicapp.Logic.model.Route;
import com.co.ametech.heroicapp.Logic.model.Station;
import com.co.ametech.heroicapp.Logic.model.TransCaribe;
import com.co.ametech.heroicapp.dao.RutaDao;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan M.E
 */
public class FindRouteBus {

    private double max_Walking_Distance;
    private double max_Destination_Distance;

    public FindRouteBus(double max_Walking_Distance, double max_Destination_Distance) {
        this.max_Walking_Distance = max_Walking_Distance;
        this.max_Destination_Distance = max_Destination_Distance;
    }

    public FindRouteBus() {
        max_Walking_Distance = 0.45; // 450 mts
        max_Destination_Distance = 0.45; //450 mts
    }

    public List<Route> listUsefulRoutes(List<Route> routes, LatLng actualPosition, LatLng destination) {

        boolean condicion1 = false;
        boolean condicion2 = false;
        List<Route> rutasUtiles = new ArrayList<>();
        for(Route i: routes) {
            Log.d("tag", i.getBus().getName());
            if(i.getBus() instanceof TransCaribe) {
                List<Station> station = ((TransCaribe)i.getBus()).getStation();
                for(Station s: station) {
                    if (distanceBetweenPoints(s.getPoint(), actualPosition) <= max_Walking_Distance) {
                        condicion1 = true;
                    }
                    if (distanceBetweenPoints(s.getPoint(), destination) <= max_Destination_Distance) {
                        condicion2 = true;
                    }
                }
            } else {
                List<LatLng> puntos = i.getPoints();
                for (LatLng j : puntos) {
                    if (distanceBetweenPoints(j, actualPosition) <= max_Walking_Distance) {
                        condicion1 = true;
                    }
                    if (distanceBetweenPoints(j, destination) <= max_Destination_Distance) {
                        condicion2 = true;
                    }
                }
            }

            if(condicion1==true && condicion2==true) {
                rutasUtiles.add(i);
            }
            condicion1 = false;
            condicion2 = false;
        }
        return rutasUtiles;
    }

    private Location converter(LatLng latLng) {
        Location location = new Location(""+latLng);
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        return location;
    }

    public LatLng nearPoint(List<LatLng> points, LatLng actualPosition) {
        boolean flag = true;
        double nearPoint = 0.0;
        LatLng p = null;
        for(LatLng index: points) {
            double aux = distanceBetweenPoints(actualPosition, index);
            if(flag) {
                flag = false;
                nearPoint = aux;
                p = index;
            }
            if(aux<nearPoint) {
                nearPoint = aux;
                p = index;
            }
        }
        return p;
    }

    private double distanceBetweenPoints(LatLng p1, LatLng p2) {
        double x1 = p1.latitude;
        double y1 = p1.longitude;

        double x2 = p2.latitude;
        double y2 = p2.longitude;

        return Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2)) * 100;
    }

    public double getMax_Walking_Distance() {
        return max_Walking_Distance;
    }

    public void setMax_Walking_Distance(double max_Walking_Distance) {
        max_Walking_Distance = max_Walking_Distance;
    }

    public double getMax_Destination_Distance() {
        return max_Destination_Distance;
    }

    public void setMax_Destination_Distance(double max_Destination_Distance) {
        max_Destination_Distance = max_Destination_Distance;
    }

}

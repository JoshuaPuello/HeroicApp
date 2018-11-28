package com.co.ametech.heroicapp.Logic;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.co.ametech.heroicapp.Logic.model.Route;
import com.co.ametech.heroicapp.Logic.model.Station;
import com.co.ametech.heroicapp.Logic.model.TransCaribe;
import com.co.ametech.heroicapp.R;
import com.co.ametech.heroicapp.activity.MainActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan M.E
 */
public class Map implements OnMapReadyCallback, RouteFinderListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker markerUser;
    private List<Marker> markerStation;
    private List<Marker> markerTouch;
    private List<Polyline> pathRoute;
    private Marker markerDestination;
    private Polyline pathUser;
    private String titleTemporary;
    private Marker select;

    public Map() {
        pathRoute = new ArrayList<>();
        markerTouch = new ArrayList<>();
        markerStation =  new ArrayList<>();
        titleTemporary = "";
    }

    public void addUserPosition(Location location, boolean move) {
        if(markerUser!=null) {
            markerUser.remove();
        }

        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
        markerUser = mMap.addMarker(new MarkerOptions()
                        .position(sydney)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.user))
                        .title("Tu")
        );
        markerUser.setDraggable(true);

        if(move) {
            CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(sydney, 16);
            mMap.animateCamera(miUbicacion);
        }
    }

    public void addMarkerDestination(LatLng latLng, String title, String name) {
        if(markerDestination!=null) {
            markerDestination.remove();
        }

        markerDestination = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(name)
        );
        markerDestination.setDraggable(true);
    }

    public void traceRouteBus(List<LatLng> routes, int color) {
        PolylineOptions polylineOptions = new PolylineOptions().
                geodesic(true).
                color(color).
                width(10);

        polylineOptions.addAll(routes);
        pathRoute.add(mMap.addPolyline(polylineOptions));
    }

    public void addMarkerToTouch(LatLng point, String titulo, String name) {
        Marker touch;
       touch = mMap.addMarker(new MarkerOptions()
                 .position(point)
                 .icon(BitmapDescriptorFactory.fromResource(R.drawable.nearpoint))
                 .title(titulo)
                 .snippet(name)
        );
        markerTouch.add(touch);
    }

    public void addStation(LatLng point, String titulo, String name) {
        Marker station;
        if(name.equals("")) {
            station = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.paradero))
                    .title(titulo)
                    .snippet(name)
            );
        } else {
            station = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.station))
                    .title(titulo)
                    .snippet(name)
            );
        }
        markerStation.add(station);
    }

    public void clearRoute() {
        clearPathRoute();
        clearMarkerTouch();
        clearMarkerStation();
        if(pathUser!=null) {
            pathUser.remove();
        }
    }

    private void clearPathRoute() {
        for(Polyline index: pathRoute) {
            index.remove();
        }
    }

    private void clearMarkerTouch() {
        for(Marker index: markerTouch) {
            index.remove();
        }
    }

    private void clearMarkerStation() {
        for(Marker index: markerStation) {
            index.remove();
        }
    }

    public void changeTypeMap(int map) {
        mMap.setMapType(map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.showInfoWindow();
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
    }

    @Override
    public void onDirectionFinderSuccess(List<LatLng> points) {
        if(pathUser!=null) {
            pathUser.remove();
        }

        PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

        int size = points.size();
        for (int i = 0; i < size; i++) {
            polylineOptions.add(points.get(i));
        }
        pathUser = mMap.addPolyline(polylineOptions);
    }

    public GoogleMap getMap() {
        return mMap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        for(Marker index: markerTouch) {
            if(marker.getTitle().equalsIgnoreCase(index.getTitle())) {
                if(titleTemporary.equals(marker.getTitle())) {
                    select = marker;
                    FindRouteUser user = new FindRouteUser(this, markerUser.getPosition(), marker.getPosition());
                    user.execute();
                } else {
                    titleTemporary = marker.getTitle();
                }
                break;
            }
        }
        return true;
    }

    public LatLng getMarkerUser() {
        return markerUser.getPosition();
    }

    public Marker getMarker() {
        if(select!=null) {
            return select;
        }
        return null;
    }

}

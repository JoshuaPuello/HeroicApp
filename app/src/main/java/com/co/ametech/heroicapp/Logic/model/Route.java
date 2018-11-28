package com.co.ametech.heroicapp.Logic.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Alan M.E
 */
public class Route {

    private int id;
    private List<LatLng> points;
    private Bus bus;

    public Route(int id, List<LatLng> points) {
        this.id = id;
        this.points = points;
    }

    public void addBus(Bus bus) {
        this.bus = bus;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }

    public Bus getBus() {
        return bus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

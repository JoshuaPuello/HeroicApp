package com.co.ametech.heroicapp.Logic.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Alan M.E
 */

public class Station {

    private String name;
    private LatLng point;

    public Station(String name, LatLng point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }

}

package com.co.ametech.heroicapp.Logic.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Alan M.E
 */

public class Planear {

    private LatLng start;
    private String name;
    private LatLng end;
    private int idRuta;

    public Planear(String name, LatLng start, LatLng end, int idRuta) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.idRuta = idRuta;
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idBus) {
        this.idRuta = idBus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

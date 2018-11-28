package com.co.ametech.heroicapp.Logic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan M.E
 */

public class TransCaribe extends Bus {

    private List<Station> station;

    public TransCaribe(int id, String name) {
        super(id, name);
        this.station = new ArrayList<>();
    }

    public void addStation(Station station) {
        this.station.add(station);
    }

    public List<Station> getStation() {
        return station;
    }

}

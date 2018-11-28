package com.co.ametech.heroicapp.Logic;

import android.location.Location;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alan M.E
 */
public class ObserverMap implements Observer {

    private Map map;

    public ObserverMap(Map map) {
        this.map = map;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg!=null) {
            this.map.addUserPosition(((LocationUser) o).getLastLocation(), true);
        }
        this.map.addUserPosition(((LocationUser) o).getLastLocation(), false);
    }

}

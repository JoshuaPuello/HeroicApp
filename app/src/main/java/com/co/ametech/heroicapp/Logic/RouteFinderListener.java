package com.co.ametech.heroicapp.Logic;

import com.co.ametech.heroicapp.Logic.model.Route;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Alan M.E
 */

public interface RouteFinderListener {

    void onDirectionFinderSuccess(List<LatLng> points);
}

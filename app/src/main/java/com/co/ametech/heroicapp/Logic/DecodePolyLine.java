package com.co.ametech.heroicapp.Logic;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan M.E
 */

public class DecodePolyLine {

    private String line;

    public DecodePolyLine(String line) {
        this.line = line;
    }

    public List<LatLng> decode() {
        //para sacar la infromacion del cada coordenada.
        int len = line.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<>();
        int lat = 0;
        int lng = 0;
        try {
            while (index < len) {
                int b;
                int shift = 0;
                int result = 0;
                do {
                    b = line.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = line.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                decoded.add(new LatLng(
                        lat / 100000d, lng / 100000d
                ));
            }
        }catch(Exception e) {

        }
        return decoded;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLine() {
        return this.line;
    }

}

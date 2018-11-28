package com.co.ametech.heroicapp.dao.sqlitedao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.co.ametech.heroicapp.Logic.DecodePolyLine;
import com.co.ametech.heroicapp.Logic.model.Bus;
import com.co.ametech.heroicapp.Logic.model.Planear;
import com.co.ametech.heroicapp.Logic.model.Route;
import com.co.ametech.heroicapp.activity.MainActivity;
import com.co.ametech.heroicapp.dao.PlanearDao;
import com.co.ametech.heroicapp.database.Connection_DB;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan M.E
 */

public class SQLitePlanearDao extends SQLiteDao implements PlanearDao{

    public SQLitePlanearDao(Context context) {
        super(context);
    }

    @Override
    public void add(Planear planear) {

        String origin = planear.getStart().latitude+","+planear.getStart().longitude;
        String destination = planear.getEnd().latitude+","+planear.getEnd().longitude;
        try {
            getSQLiteDatabase().execSQL("INSERT INTO Planear (nombre, origen, destino, idruta)" +
                    "VALUES ('" + planear.getName() + "','" + origin + "', '" + destination + "'," + planear.getIdRuta() + ");");
        } catch (Exception e) {
           Log.d(SQLitePlanearDao.class.getName(), e.getMessage());
        }
        close();
    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public Planear find(String name) {
        Planear planear = null;
        try {
            Cursor consulta = getSQLiteDatabase().rawQuery("select nombre, origen, destino, idruta from Planear where nombre='" + name + "';", null);

            if (consulta.moveToFirst()) {
                LatLng origen = separator(consulta.getString(1));
                LatLng destino = separator(consulta.getString(2));
                planear = new Planear(consulta.getString(0), origen, destino, Integer.parseInt(consulta.getString(3)));
            }
        } catch(Exception e) {
            Log.d(SQLitePlanearDao.class.getName(), e.getMessage());
        }
        close();
        return planear;
    }

    private LatLng separator(String latlng) {
        String vector[] =  latlng.split(",");
        double lat = Double.parseDouble(vector[0]);
        double lng = Double.parseDouble(vector[1]);
        LatLng latLng = new LatLng(lat, lng);

        return latLng;
    }

    @Override
    public List<Planear> toList() {
        List<Planear> planear = new ArrayList<>();
        try {
            Cursor consulta = getSQLiteDatabase().rawQuery("select nombre, origen, destino, idruta from Planear;", null);
            if (consulta.moveToFirst()) {
                do {
                    LatLng origen = separator(consulta.getString(1));
                    LatLng destino = separator(consulta.getString(2));
                    planear.add(new Planear(consulta.getString(0), origen, destino, Integer.parseInt(consulta.getString(3))));
                } while (consulta.moveToNext());
            }
        } catch(Exception e) {
            Log.d(SQLitePlanearDao.class.getName(), e.getMessage());
        }
        close();
        return planear;
    }

}

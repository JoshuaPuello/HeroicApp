package com.co.ametech.heroicapp.dao.sqlitedao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.co.ametech.heroicapp.Logic.DecodePolyLine;
import com.co.ametech.heroicapp.Logic.model.Bus;
import com.co.ametech.heroicapp.Logic.model.Route;
import com.co.ametech.heroicapp.Logic.model.Station;
import com.co.ametech.heroicapp.Logic.model.TransCaribe;
import com.co.ametech.heroicapp.dao.RutaDao;
import com.co.ametech.heroicapp.database.Connection_DB;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan M.E
 */
public class SQLiteRutaDao extends SQLiteDao implements RutaDao {

    public SQLiteRutaDao(Context context) {
        super(context);
    }

    @Override
    public List<Route> toList() {
        List<Route> rutas = new ArrayList<>();
        try {
            Cursor consulta1 = getSQLiteDatabase().rawQuery("select id, puntos, idbus from Ruta;", null);
            if (consulta1.moveToFirst()) {
                Route route;
                List<LatLng> points;
                do {
                    int idruta = Integer.parseInt(consulta1.getString(0));
                    points = new DecodePolyLine(consulta1.getString(1)).decode();
                    String idBus = consulta1.getString(2);

                    Cursor consulta2 = getSQLiteDatabase().rawQuery("select id, nombre, idtipo from Bus where id = " + idBus + ";", null);

                    if (consulta2.moveToFirst()) {
                        Cursor consulta3 = getSQLiteDatabase().rawQuery("select nombre from TipoBus where id = " + consulta2.getString(2) + ";", null);
                        if (consulta3.moveToFirst()) {
                            if (consulta3.getString(0).equalsIgnoreCase("TransCaribe")) {
                                Cursor consulta4 = getSQLiteDatabase().rawQuery("select nombre, punto from Estacion where idbus = " + consulta2.getString(0) + ";", null);
                                if (consulta4.moveToFirst()) {
                                    TransCaribe bus = new TransCaribe(Integer.parseInt(consulta2.getString(0)), consulta2.getString(1));
                                    do {
                                        Station aux = new Station(consulta4.getString(0), separator(consulta4.getString(1)));
                                        bus.addStation(aux);
                                    } while (consulta4.moveToNext());

                                    route = new Route(idruta, points);
                                    route.addBus(bus);
                                    rutas.add(route);
                                }
                            } else {
                                route = new Route(idruta, points);
                                route.addBus(new Bus(Integer.parseInt(consulta2.getString(0)), consulta2.getString(1)));
                                rutas.add(route);
                            }
                        }
                    }
                } while (consulta1.moveToNext());
            }
        }catch (Exception e) {
            Log.d(SQLitePlanearDao.class.getName(), e.getMessage());
        }

        close();
        return rutas;
    }

    private LatLng separator(String latlng) {
        String vector[] =  latlng.split(",");
        double lat = Double.parseDouble(vector[0]);
        double lng = Double.parseDouble(vector[1]);
        LatLng latLng = new LatLng(lat, lng);

        return latLng;
    }

    @Override
    public void add(Route route) {

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Route find(Integer id) {
        Route route = null;
        try {
            Cursor consulta1 = getSQLiteDatabase().rawQuery("select id, puntos, idbus from Ruta where id=" + id + ";", null);

            if (consulta1.moveToFirst()) {
                int idruta = Integer.parseInt(consulta1.getString(0));
                List<LatLng> points;
                points = new DecodePolyLine(consulta1.getString(1)).decode();
                String idBus = consulta1.getString(2);

                Cursor consulta2 = getSQLiteDatabase().rawQuery("select id, nombre, idtipo from Bus where id = " + idBus + ";", null);

                if (consulta2.moveToFirst()) {

                    Cursor consulta3 = getSQLiteDatabase().rawQuery("select nombre from TipoBus where id = " + consulta2.getString(2) + ";", null);
                    if (consulta3.moveToFirst()) {

                        if (consulta3.getString(0).equalsIgnoreCase("TransCaribe")) {

                            Cursor consulta4 = getSQLiteDatabase().rawQuery("select nombre, punto from Estacion where idbus = " + consulta2.getString(0) + ";", null);
                            if (consulta4.moveToFirst()) {
                                TransCaribe bus = new TransCaribe(Integer.parseInt(consulta2.getString(0)), consulta2.getString(1));
                                do {
                                    Station aux = new Station(consulta4.getString(0), separator(consulta4.getString(1)));
                                    bus.addStation(aux);
                                } while (consulta4.moveToNext());

                                route = new Route(idruta, points);
                                route.addBus(bus);
                            }
                        } else {
                            route = new Route(idruta, points);
                            route.addBus(new Bus(Integer.parseInt(consulta2.getString(0)), consulta2.getString(1)));
                        }
                    }
                }
            }
        } catch(Exception e) {
            Log.d(SQLiteRutaDao.class.getName(), e.getMessage());
        }
        close();
        return route;
    }

}

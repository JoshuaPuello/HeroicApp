package com.co.ametech.heroicapp.activity;


import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.co.ametech.heroicapp.Logic.FindRouteBus;
import com.co.ametech.heroicapp.Logic.LocationUser;
import com.co.ametech.heroicapp.Logic.Map;
import com.co.ametech.heroicapp.Logic.ObserverMap;
import com.co.ametech.heroicapp.Logic.model.Planear;
import com.co.ametech.heroicapp.Logic.model.Route;
import com.co.ametech.heroicapp.Logic.model.Station;
import com.co.ametech.heroicapp.Logic.model.TransCaribe;
import com.co.ametech.heroicapp.R;
import com.co.ametech.heroicapp.dao.sqlitedao.SQLitePlanearDao;
import com.co.ametech.heroicapp.dao.sqlitedao.SQLiteRutaDao;
import com.co.ametech.heroicapp.database.Connection_DB;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Map map;
    private LocationUser user;
    List<Route> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = new Map();
        user = new LocationUser(this);
        user.addObserver(new ObserverMap(map));

        initComponet();
    }

    private void initComponet() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(map);

        String[] place = getResources().getStringArray(R.array.place);
        List<String> listPlace = Arrays.asList(place);
        txtDestino = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listPlace);
        txtDestino.setThreshold(1);
        txtDestino.setAdapter(adapter);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBtnBuscar(v);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        lblName = (TextView)header.findViewById(R.id.lblName);
        lblEmail = (TextView)header.findViewById(R.id.lblEmail);
        bundle();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave(view);
            }
        });
        fab.hide();
    }

    private void onBtnBuscar(View v) {
        String destino = txtDestino.getText().toString();
        if (!destino.equals("")) {
            final String country = ",Cartagena - Bolívar";
            destino += country;

            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> address = geocoder.getFromLocationName(destino, 10);
                if (address.size() != 0) {
                    Toast.makeText(this, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                    map.clearRoute();

                    LatLng position = map.getMarkerUser();
                    LatLng d = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());

                    map.addMarkerDestination(d, "Destino", address.get(0).getAddressLine(0));

                    FindRouteBus finder = new FindRouteBus();
                    routes = finder.listUsefulRoutes(new SQLiteRutaDao(this).toList(), position, d);

                    if (routes.size() == 0) {
                        Toast.makeText(this, "No hay rutas cercas", Toast.LENGTH_LONG).show();
                    }

                    for (Route r : routes) {
                        if (r.getBus() instanceof TransCaribe) {

                            map.traceRouteBus(r.getPoints(), Color.MAGENTA);
                            List<Station> station = ((TransCaribe) r.getBus()).getStation();
                            List<LatLng> aux = new ArrayList<>();

                            for (Station index : station) {
                                if (index.getName().equals("Paradero")) {
                                    map.addStation(index.getPoint(), index.getName(), "");
                                } else {
                                    map.addStation(index.getPoint(), "Estación", index.getName());
                                }
                                aux.add(index.getPoint());
                            }

                            LatLng p = finder.nearPoint(aux, position);
                            map.addMarkerToTouch(p, r.getBus().getName(), "Transcaribe");
                            continue;
                        } else {
                            map.traceRouteBus(r.getPoints(), Color.RED);
                            LatLng p = finder.nearPoint(r.getPoints(), position);
                            map.addMarkerToTouch(p, r.getBus().getName(), "");
                        }
                    }
                } else {
                    Toast.makeText(this, "No se encontró el destino", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                Log.d(MainActivity.class.getName(), e.getMessage());
            }
        } else {
            Toast.makeText(this, "Digite un destino", Toast.LENGTH_LONG).show();
            map.clearRoute();
        }
    }

    private void onSave(View v) {
        Snackbar.make(v, "¿Guardar esta ruta?", Snackbar.LENGTH_LONG)
                .setAction("Aceptar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (routes != null && map.getMarker() != null) {
                            LatLng d = map.getMarker().getPosition();
                            for (Route index : routes) {
                                if (map.getMarker().getTitle().equals(index.getBus().getName())) {
                                    Toast.makeText(MainActivity.this, map.getMarker().getTitle(), Toast.LENGTH_LONG).show();
                                    Planear planear = new Planear("save", map.getMarkerUser(), d, index.getId());
                                    new SQLitePlanearDao(MainActivity.this).add(planear);
                                    break;
                                }
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Ruta no guardada", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.type1) {
            map.changeTypeMap(GoogleMap.MAP_TYPE_HYBRID);
        }
        if (id == R.id.type2) {
            map.changeTypeMap(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (id == R.id.type3) {
            map.changeTypeMap(GoogleMap.MAP_TYPE_SATELLITE);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            map.clearRoute();
            user.StarUpdate();
            getSupportActionBar().setTitle("HeroicApp");
            fab.hide();
        } else if (id == R.id.nav_gallery) {
            map.clearRoute();
            user.stopUpdate();
            getSupportActionBar().setTitle(item.getTitle());
            fab.show();
        } else if (id == R.id.nav_slideshow) {
            map.clearRoute();
            user.stopUpdate();
            fab.hide();
            getSupportActionBar().setTitle(item.getTitle());
            List<Planear> p = new SQLitePlanearDao(this).toList();
            for (Planear index : p) {
                Route route = new SQLiteRutaDao(this).find(index.getIdRuta());
                if (route != null) {
                    if (route.getBus() instanceof TransCaribe) {
                        map.traceRouteBus(route.getPoints(), Color.MAGENTA);
                        List<Station> station = ((TransCaribe) route.getBus()).getStation();
                        for (Station i : station) {
                            if (index.getName().equals("Paradero")) {
                                map.addStation(i.getPoint(), index.getName(), "");
                            } else {
                                map.addStation(i.getPoint(), "Estación", index.getName());
                            }
                        }
                    } else {
                        map.traceRouteBus(route.getPoints(), Color.RED);
                    }
                }
            }
        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, InformationActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void bundle() {
        Bundle b = this.getIntent().getExtras();
        if(b!=null) {
            lblName.setText(b.getString("NOMBRE"));
            lblEmail.setText(b.getString("EMAIL"));
        }
    }

    private AutoCompleteTextView txtDestino;
    private FloatingActionButton fab;
    private Button btnBuscar;
    private TextView lblName, lblEmail;

}

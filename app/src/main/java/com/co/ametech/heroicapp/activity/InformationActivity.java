package com.co.ametech.heroicapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.co.ametech.heroicapp.R;


public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getSupportActionBar().setTitle("Informacion");
    }

}

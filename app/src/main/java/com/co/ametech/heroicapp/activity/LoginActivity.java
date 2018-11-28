package com.co.ametech.heroicapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.co.ametech.heroicapp.Logic.model.User;
import com.co.ametech.heroicapp.R;
import com.co.ametech.heroicapp.dao.sqlitedao.SQLiteUsuarioDao;

/**
 * Created by miftakhul on 26/06/16.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initComponent();
        bundle();
    }

    private void initComponent() {
        txtName = (EditText) findViewById(R.id.txtName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        boton = (Button) findViewById(R.id.btnRegister);
        btnOmitir = (Button) findViewById(R.id.btnOmitir);
        btnIniciar = (Button) findViewById(R.id.botonSignUp);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });

        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnOmitir(view);
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnIniciar(view);
            }
        });

    }

    private void onBtnOmitir(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

    private void onBtnIniciar(View v) {
        String name = txtName.getText().toString();
        String password = txtPassword.getText().toString();
        if(!name.equals("") || !password.equals("")) {
            User user = new SQLiteUsuarioDao(this).find(name, password);
            if(user!=null) {
                Intent i = new Intent(this, MainActivity.class);

                Bundle b = new Bundle();
                b.putString("NOMBRE", user.getName());
                b.putString("EMAIL", user.getEmail());

                i.putExtras(b);
                startActivity(i);
                LoginActivity.this.finish();
            } else {
                Toast.makeText(this, "Username o Contraseña, no coinciden", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Rellene los campos", Toast.LENGTH_LONG).show();
        }
    }

    private void bundle() {
        Bundle b = this.getIntent().getExtras();
        if(b!=null) {
            txtName.setText(""+b.get("NOMBRE"));
            txtPassword.setText(""+b.get("PASS"));
        }
    }

    private Button boton;
    private Button btnOmitir;
    private Button btnIniciar;
    private EditText txtName;
    private EditText txtPassword;
}

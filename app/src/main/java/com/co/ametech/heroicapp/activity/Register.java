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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maxicruzmartinez on 12/11/16.
 */
public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initComponent();
    }

    private void initComponent() {
        txtEmail = (EditText)findViewById(R.id.email);
        txtName = (EditText) findViewById(R.id.user);
        txtPassword1 = (EditText) findViewById(R.id.password);
        txtPassword2 = (EditText) findViewById(R.id.confpass);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBtnRegistrar(v);
            }
        });
    }

    public void onBtnRegistrar(View v) {
        String email = txtEmail.getText().toString();
        String name = txtName.getText().toString();
        String password1 = txtPassword1.getText().toString();
        String password2 = txtPassword2.getText().toString();

        if(!email.equals("") || !name.equals("") || !password1.equals("") || !password2.equals("")) {
            if(isEmailValid(email)) {
                if (password1.equals(password2)) {
                    new SQLiteUsuarioDao(this).add(new User(name, email, password1));
                    Intent i = new Intent(this, LoginActivity.class);

                    Bundle b = new Bundle();
                    b.putString("NOMBRE", name);
                    b.putString("PASS", password1);

                    i.putExtras(b);
                    startActivity(i);
                    this.finish();
                } else {
                    Toast.makeText(this, "Contraseñas, no coinciden", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Email, no valido", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Rellene los campos", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private EditText txtEmail, txtName, txtPassword1, txtPassword2;
    private Button btnRegistrar;
}

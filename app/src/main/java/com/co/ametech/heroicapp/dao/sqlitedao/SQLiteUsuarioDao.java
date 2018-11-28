package com.co.ametech.heroicapp.dao.sqlitedao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.co.ametech.heroicapp.Logic.model.User;
import com.co.ametech.heroicapp.dao.UsuarioDao;

import java.util.List;

/**
 * Created by LAlan M.E
 */

public class SQLiteUsuarioDao extends SQLiteDao implements UsuarioDao {

    public SQLiteUsuarioDao(Context context) {
        super(context);
    }

    @Override
    public void add(User user) {
        try {
            getSQLiteDatabase().execSQL("INSERT INTO Usuario (nombre, email, contraseña)" +
                    "VALUES ('" + user.getName() + "','" + user.getEmail() + "', '" + user.getPassword() + "');");
        } catch (Exception e) {
            Log.d(SQLiteUsuarioDao.class.getName(),e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public User find(Integer id) {
        return null;
    }

    public User find(String name, String password) {
        User user = null;
        try {
            Cursor consulta = getSQLiteDatabase().rawQuery("select nombre, email from Usuario where nombre='" + name + "' and contraseña='" + password + "';", null);
            if (consulta.moveToFirst()) {
                user = new User(consulta.getString(0), consulta.getString(1));
            }
            return user;
        } catch(Exception e) {
            Log.d(SQLiteUsuarioDao.class.getName(),e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> toList() {
        return null;
    }
}

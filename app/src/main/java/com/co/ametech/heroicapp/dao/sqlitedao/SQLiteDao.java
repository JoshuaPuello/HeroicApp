package com.co.ametech.heroicapp.dao.sqlitedao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.co.ametech.heroicapp.database.Connection_DB;

public class SQLiteDao {

    private Connection_DB connection_db;

    public SQLiteDao(Context context) {
        this.connection_db = Connection_DB.getInstance(context);
    }

    protected SQLiteDatabase getSQLiteDatabase() {
        return connection_db.OpenConnection();
    }

    protected void close() {
        connection_db.closeConnection();
    }

}

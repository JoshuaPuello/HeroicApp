package com.co.ametech.heroicapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alan M.E
 */

public class Connection_DB {

    private static Connection_DB connection_db;
    private RutaSQLiteOpenHelper rutaSQLiteOpenHelper;
    private SQLiteDatabase bd;

    private Connection_DB(Context activity) {
        rutaSQLiteOpenHelper = new RutaSQLiteOpenHelper(activity);
    }

    public static Connection_DB getInstance(Context activity) {
        return connection_db = new Connection_DB(activity);
    }

    public SQLiteDatabase OpenConnection() {
        return bd = rutaSQLiteOpenHelper.getWritableDatabase();
    }

    public void closeConnection() {
        bd.close();
    }

}

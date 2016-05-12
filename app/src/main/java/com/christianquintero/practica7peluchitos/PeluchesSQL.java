package com.christianquintero.practica7peluchitos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chris on 11/05/2016.
 */
public class PeluchesSQL extends SQLiteOpenHelper{

    private static final String DATA_BASE_NAME="PeluchesDB";
    private static final int DATA_VERSION=1;

    //creacion de la tabla
    String myTabla = "CREATE TABLE Peluches(id INTEGER , nombre TEXT, cantidad INTEGER, valor INTEGER)";
    String myGanancias = ("CREATE TABLE Ganancia(dinero INTEGER");

    public PeluchesSQL(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(myTabla);
        db.execSQL(myGanancias);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

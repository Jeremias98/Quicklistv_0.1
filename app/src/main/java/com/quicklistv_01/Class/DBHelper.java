package com.quicklistv_01.Class;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Aaron on 11/10/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATASABE_VERSION = 1;
    public static final String DATASABE_NAME = "favoritos.db";

    public DBHelper(Context context) {
        super(context, DATASABE_NAME, null, DATASABE_VERSION);
    }
    public SQLiteDatabase db;



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ListaFavoritos.FavoritosEntrada.TABLE_NAME + " ("
                + ListaFavoritos.FavoritosEntrada.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ListaFavoritos.FavoritosEntrada.ID_CURSO + " INTEGER NOT NULL, "
                + ListaFavoritos.FavoritosEntrada.CURSO + " TEXT NOT NULL " + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void open(){
        db = getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public ArrayList llenar() {

        ArrayList<String> lista = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM favoritos_cursos";
        Cursor registro = db.rawQuery(query, null);
        if(registro.moveToFirst()){
            do {
                lista.add(registro.getString(2));
            }while (registro.moveToNext());
        }
        return lista;
    }
    public ArrayList llenarIds() {

        ArrayList<Integer> ids = new ArrayList<>();


        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM favoritos_cursos";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ids.add(cursor.getInt(1));

            }while (cursor.moveToNext());
        }
        return ids;
    }

}

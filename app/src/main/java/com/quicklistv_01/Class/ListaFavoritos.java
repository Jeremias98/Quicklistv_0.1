package com.quicklistv_01.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import static com.quicklistv_01.Class.ListaFavoritos.FavoritosEntrada.CURSO;
import static com.quicklistv_01.Class.ListaFavoritos.FavoritosEntrada.TABLE_NAME;

public class ListaFavoritos {
    public static abstract class FavoritosEntrada implements BaseColumns{
        public static final String TABLE_NAME = "favoritos";
        public static final String  ID = "id";
        public static final String  CURSO = "curso";
    }
    private DBHelper helper;
    private SQLiteDatabase db;
    public ListaFavoritos(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    public ContentValues generarContentValues(String curso){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CURSO, curso);
        return contentValues;
    }
    public void insertar(String curso){
        db.insert(TABLE_NAME, null, generarContentValues(curso));
    }
    public void eliminar(String curso){
        db.delete(TABLE_NAME, CURSO+"=?", new String[]{curso});
    }
}

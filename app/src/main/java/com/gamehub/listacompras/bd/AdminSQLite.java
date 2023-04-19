package com.gamehub.listacompras.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BaseCompras.db";


    public AdminSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table Articulo (" +
                "id_Articulo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nombre TEXT NOT NULL," +
                "Cantidad INTEGER," +
                "Precio REAL," +
                "Nombre_Categoria TEXT," +
                "Nombre_Unidad TEXT," +
                "FOREIGN KEY (Nombre_Categoria) REFERENCES Categoria(Nombre)," +
                "FOREIGN KEY (Nombre_Unidad) REFERENCES Unidad(Nombre))");

        db.execSQL("CREATE TABLE Lista (" +
                "id_Lista INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nombre TEXT NOT NULL," +
                "Total REAL)");

        db.execSQL("CREATE TABLE Categoria (" +
                "Nombre TEXT PRIMARY KEY NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE Unidad (" +
                "Nombre TEXT PRIMARY KEY NOT NULL)");

        db.execSQL("CREATE TABLE lista_Articulo(id_Lista INTEGER NOT NULL" +
                ", id_Articulo INTEGER NOT NULL," +
                " FOREIGN KEY (id_Lista) REFERENCES Lista(id_Lista)," +
                " FOREIGN KEY (id_Articulo) REFERENCES Articulo(id_Articulo))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("INSERT INTO Lista(Nombre) Values('Mi Lista');");

        db.execSQL("INSERT INTO Lista(Nombre) Values('Mi Lista2');");

        onCreate(db);

    }
}

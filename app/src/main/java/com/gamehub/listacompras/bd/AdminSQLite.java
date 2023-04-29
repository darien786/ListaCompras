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
                "id_Lista INTEGER,"+
                "FOREIGN KEY (Nombre_Categoria) REFERENCES Categoria(Nombre)," +
                "FOREIGN KEY (Nombre_Unidad) REFERENCES Unidad(Nombre)," +
                "FOREIGN KEY (id_Lista) REFERENCES Lista(id_Lista))");

        db.execSQL("CREATE TABLE Lista (" +
                "id_Lista INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nombre TEXT NOT NULL)");

        db.execSQL("CREATE TABLE Categoria (" +
                "Nombre TEXT PRIMARY KEY NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE Unidad (" +
                "Nombre TEXT PRIMARY KEY NOT NULL)");
/*
        db.execSQL("CREATE TABLE lista_Articulo(id_Lista INTEGER NOT NULL" +
                ", id_Articulo INTEGER NOT NULL," +
                " FOREIGN KEY (id_Lista) REFERENCES Lista(id_Lista)," +
                " FOREIGN KEY (id_Articulo) REFERENCES Articulo(id_Articulo))");
*/

        //Insertar datos por defecto
        db.execSQL("INSERT INTO Lista(Nombre) VALUES('Mi Lista')");

        db.execSQL("INSERT INTO Lista(Nombre) VALUES('Mi Lista2')");


        //INSERTAR DATOS POR DEFECTO DE LA TABLA UNIDAD DE MEDIDA

        db.execSQL("INSERT INTO Unidad(Nombre) VALUES('un')");

        db.execSQL("INSERT INTO Unidad(Nombre) VALUES('kg')");

        db.execSQL("INSERT INTO Unidad(Nombre) VALUES('g')");

        db.execSQL("INSERT INTO Unidad(Nombre) VALUES('ml')");

        db.execSQL("INSERT INTO Unidad(Nombre) VALUES('l')");

        //INSERTAR DATOS POR DEFECTO DE LA TABLA CATEGORÍAS

        db.execSQL("INSERT INTO Categoria(Nombre) VALUES('Frutas')");

        db.execSQL("INSERT INTO Categoria(Nombre) VALUES('Verduras')");

        db.execSQL("INSERT INTO Categoria(Nombre) VALUES('Leguminosas')");

        db.execSQL("INSERT INTO Categoria(Nombre) VALUES('Alimentos de origen animal')");

        db.execSQL("INSERT INTO Categoria(Nombre) VALUES('Cereales')");

        db.execSQL("INSERT INTO Categoria(Nombre) VALUES('Sin categoría')");


        db.execSQL("INSERT INTO Articulo(Nombre,Cantidad,Precio,Nombre_Unidad,Nombre_Categoria,id_Lista) " +
                "VALUES('Prueba',1,20,'un','Sin categoria',1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE Lista");

        db.execSQL("DROP TABLE Categoria");

        db.execSQL("DROP TABLE Unidad");

        onCreate(db);
    }
}

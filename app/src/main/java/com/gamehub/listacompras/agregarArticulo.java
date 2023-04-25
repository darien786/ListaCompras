package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.gamehub.listacompras.bd.AdminSQLite;

import java.util.ArrayList;
import java.util.List;

public class agregarArticulo extends AppCompatActivity {

    protected Toolbar toolArticulo;
    protected EditText agregar_nombre;
    protected Spinner categorias;
    protected Spinner unidad;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_articulo);
        categorias = (Spinner) findViewById(R.id.datos_categorias);
        unidad = (Spinner) findViewById(R.id.id_unidad_medida);
        toolArticulo = findViewById(R.id.toolbarArticulo);
        setSupportActionBar(toolArticulo);

        AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
        SQLiteDatabase db  = baseCompras.getWritableDatabase();

        List<String> lista = new ArrayList<String>();
        String[] projection = {"Nombre"};
        Cursor vistas = db.query("Categoria", projection,null,null,null,null,null);
        while(vistas.moveToNext()){
            String nombre = vistas.getString(vistas.getColumnIndexOrThrow("Nombre"));
            lista.add(nombre);
        }
        vistas.close();

        ArrayAdapter <String> adapter = new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adapter);

        List<String> unidades = new ArrayList<>();
        String [] proyeccion = {"Nombre"};
        Cursor vistasUnidad = db.query("Unidad",proyeccion,null,null,null,null,null);
        while (vistasUnidad.moveToNext()){
            String nombre = vistasUnidad.getString(vistasUnidad.getColumnIndexOrThrow("Nombre"));
            unidades.add(nombre);
        }
        vistasUnidad.close();

        ArrayAdapter <String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,lista);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unidad.setAdapter(adapter2);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_agregar_articulo, menu);
        return true;
    }


}
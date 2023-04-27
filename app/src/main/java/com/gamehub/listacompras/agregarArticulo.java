package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gamehub.listacompras.bd.AdminSQLite;

import java.util.ArrayList;
import java.util.List;

public class agregarArticulo extends AppCompatActivity {

    protected Toolbar toolArticulo;
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

        ArrayAdapter <String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,unidades);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unidad.setAdapter(adapter2);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_agregar_articulo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.menu_aceptar_articulo:

                EditText agregar_nombre = findViewById(R.id.id_nombre_articulo);
                EditText agregar_precio = findViewById(R.id.id_precio_articulo);
                EditText agregar_cantidad = findViewById(R.id.id_cantidad_articulo);

                String nombre = agregar_nombre.getText().toString().trim();
                Float precio = Float.valueOf(agregar_precio.getText().toString().trim());
                int cantidad = Integer.parseInt(agregar_cantidad.getText().toString().trim());

                String unidad_tabla = (String) unidad.getSelectedItem().toString().trim();
                String categoria_tabla = (String) categorias.getSelectedItem().toString().trim();

                AdminSQLite adminSQLite = new AdminSQLite(getBaseContext());
                SQLiteDatabase db = adminSQLite.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("Nombre", nombre);
                values.put("Cantidad", cantidad);
                values.put("Precio", precio);
                values.put("Nombre_Unidad", unidad_tabla);
                values.put("Nombre_Categoria", categoria_tabla);

                db.insert("Articulo",null, values);


                finish();
                return true;

            default:
                finish();
                return true;
        }

    }


}
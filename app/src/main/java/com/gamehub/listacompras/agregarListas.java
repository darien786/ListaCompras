package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.gamehub.listacompras.bd.AdminSQLite;

public class agregarListas extends AppCompatActivity {

    private Toolbar toolbar_agregar_listas;
    protected EditText agregar_nombre;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_listas);

        toolbar_agregar_listas = findViewById(R.id.toolbar_agregar_listas);
        setSupportActionBar(toolbar_agregar_listas);
    }

    @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_agregar_lista, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.aceptarAgregarLista:
                agregar_nombre = (EditText) findViewById(R.id.id_nombre_lista);

                AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
                SQLiteDatabase db  = baseCompras.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("Nombre", agregar_nombre.getText().toString());
                db.insert("Lista", null, values);
                finish();
                return true;
        }

        return true;
    }
}
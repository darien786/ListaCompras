package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class agregarCategoria extends AppCompatActivity {

    private Toolbar toolbar_agregar_categoria;
    protected EditText agregar_nombre;
    protected RecyclerView categorias;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);

        toolbar_agregar_categoria = findViewById(R.id.toolbar_agregar_categoria);
        setSupportActionBar(toolbar_agregar_categoria);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_agregar_categoria, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.agregarNombreCategoria:
                agregar_nombre = (EditText) findViewById(R.id.agregar_nombre_categoria);

                AdminSQLite admin = new AdminSQLite(getBaseContext());
                SQLiteDatabase db = admin.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("Nombre", agregar_nombre.getText().toString());
                db.insert("Categoria", null, values);
                finish();


                return true;

            default:
                finish();
                return true;
        }
    }
}
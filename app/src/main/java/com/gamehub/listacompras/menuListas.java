package com.gamehub.listacompras;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class menuListas extends AppCompatActivity {


    private Toolbar tb1;
    private FloatingActionButton btn_AgregarListas;
    protected TextView pruebas;

    protected int foto = R.drawable.menus_de_listas;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listas);
        tb1 = findViewById(R.id.tolListas);
        btn_AgregarListas = findViewById(R.id.btn_AgregarLista);


        setSupportActionBar(tb1);

        btn_AgregarListas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAgregarLista = new Intent(menuListas.this, agregarListas.class);
                startActivity(ventanaAgregarLista);
            }
        });


        AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
        SQLiteDatabase db  = baseCompras.getWritableDatabase();


        String cadena = "";
        ContentValues value = new ContentValues();
        String[] projection = {"Nombre"};
        Cursor vistas = db.query("Lista", projection,null,null,null,null,null);
        while(vistas.moveToNext()){
            String nombre = vistas.getString(vistas.getColumnIndexOrThrow("Nombre"));
            cadena += nombre + "\n";
            pruebas.setText(cadena);
        }
        vistas.close();
    }

}
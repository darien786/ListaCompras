package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;

public class agregarListas extends AppCompatActivity {

    private Toolbar toolbar_agregar_listas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_listas);

        toolbar_agregar_listas = findViewById(R.id.toolbar_agregar_listas);
        setSupportActionBar(toolbar_agregar_listas);
    }


   public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_agregar_categoria, menu);

        return true;
    }
}
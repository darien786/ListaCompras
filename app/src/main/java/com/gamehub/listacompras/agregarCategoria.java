package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class agregarCategoria extends AppCompatActivity {

    private Toolbar toolbar_agregar_categoria;

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
}
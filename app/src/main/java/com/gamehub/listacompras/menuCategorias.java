package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class menuCategorias extends AppCompatActivity {

    private Toolbar tb2;

    private FloatingActionButton btn_AgregarCategoria;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_categorias);

        tb2 = findViewById(R.id.toolbarCategorias);
        btn_AgregarCategoria = findViewById(R.id.btn_AgregarCategoria);

        setSupportActionBar(tb2);

        btn_AgregarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAgregarCategoria = new Intent(menuCategorias.this, agregarCategoria.class);
                startActivity(ventanaAgregarCategoria);
            }
        });

    }
}
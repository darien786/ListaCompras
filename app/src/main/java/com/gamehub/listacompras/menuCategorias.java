package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class menuCategorias extends AppCompatActivity {

    private Toolbar tb2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_categorias);

        tb2 = findViewById(R.id.toolbarCategorias);

        setSupportActionBar(tb2);

    }
}
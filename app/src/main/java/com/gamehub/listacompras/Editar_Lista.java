package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;


public class Editar_Lista extends AppCompatActivity {


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lista);

        toolbar = findViewById(R.id.toolbar_editar_lista);
        setSupportActionBar(toolbar);
    }
}
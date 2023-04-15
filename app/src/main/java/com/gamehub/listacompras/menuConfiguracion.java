package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;


public class menuConfiguracion extends AppCompatActivity {


    private Toolbar toolbarConfiguracion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_configuracion);
        toolbarConfiguracion = findViewById(R.id.toolbarConfiguracion);

        setSupportActionBar(toolbarConfiguracion);

    }


}
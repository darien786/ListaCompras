package com.gamehub.listacompras;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;


public class menuConfiguracion extends AppCompatActivity {


    protected Toolbar toolbarConfiguracion;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;
    protected Switch switchColor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_configuracion);
        toolbarConfiguracion = findViewById(R.id.toolbarConfiguracion);

        setSupportActionBar(toolbarConfiguracion);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Intent intent = new Intent(this, fragmentoCalculadora.class);
        switchColor = (Switch) findViewById(R.id.switch_color);
        boolean switchState = sharedPreferences.getBoolean("switchState", false);
        switchColor.setChecked(switchState);
        intent.putExtra("switchState", switchState);
        switchColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Guarda la preferencia de cambio de color en SharedPreferences
                cambiarApariencia(isChecked);
                editor.putBoolean("switchState", isChecked);
                editor.apply();
            }
        });
    }

    public void cambiarApariencia(boolean checador){
        if(checador == true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }
}
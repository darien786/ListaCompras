package com.gamehub.listacompras;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class menuListas extends AppCompatActivity {


    private DrawerLayout dLayout1;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView1;
    private BottomNavigationView botonNavegation;
    private Toolbar tb1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listas);
        dLayout1 = findViewById(R.id.drawerListas);
        tb1 = findViewById(R.id.tolListas);



    }

}
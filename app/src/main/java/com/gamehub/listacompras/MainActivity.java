package com.gamehub.listacompras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dLayout1;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView1;
    private BottomNavigationView botonNavegation;
    private Toolbar tb1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dLayout1 = findViewById(R.id.drawer1);
        navigationView1 = findViewById(R.id.navegation1);
        botonNavegation = findViewById(R.id.botonBar);
        tb1 = findViewById(R.id.toolbar1);

        setSupportActionBar(tb1);
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, new fragmentoListas()).commit();

        drawerToggle = new ActionBarDrawerToggle(this, dLayout1, tb1, 0,0);
        dLayout1.addDrawerListener(drawerToggle);
        drawerToggle.syncState();



        navigationView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        botonNavegation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.listasPrincipal:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new fragmentoListas()).commit();
                        return true;

                    case R.id.calculadora:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new fragmentoCalculadora()).commit();
                        return true;
                }

                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();


        return true;
    }

}
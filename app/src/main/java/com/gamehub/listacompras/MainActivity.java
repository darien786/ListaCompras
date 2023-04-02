package com.gamehub.listacompras;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dLayout1;
    private NavigationView navigationView1;
    private Toolbar tb1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dLayout1 = findViewById(R.id.drawer1);
        navigationView1 = findViewById(R.id.navegation1);
        tb1 = findViewById(R.id.toolbar1);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, dLayout1,tb1,0,0);
        dLayout1.addDrawerListener(abdt);
        abdt.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();


        return true;
    }
}
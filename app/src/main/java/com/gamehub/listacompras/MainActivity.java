package com.gamehub.listacompras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.database.sqlite.SQLiteDatabaseKt;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dLayout1;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView1;
    private BottomNavigationView botonNavegation;
    private Toolbar tb1;
    public Spinner spinnerlistas;
    public List<String> Listas;

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

        drawerToggle = new ActionBarDrawerToggle(this, dLayout1, tb1, 0, 0);
        dLayout1.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.listas:
                        Intent ventanaListas = new Intent(MainActivity.this, menuListas.class);
                        startActivity(ventanaListas);
                        return true;

                    case R.id.configuration:
                        Intent ventanaConfiguracion = new Intent(MainActivity.this, menuConfiguracion.class);
                        startActivity(ventanaConfiguracion);
                        return true;

                    case R.id.categoria:
                        Intent ventanaCategoria = new Intent(MainActivity.this, menuCategorias.class);
                        startActivity(ventanaCategoria);
                        return true;
                }


                return false;
            }
        });


        botonNavegation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.listasPrincipal:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new fragmentoListas()).commit();

                        Bundle args = new Bundle();
                        args.putString("selectedItem", spinnerlistas.getSelectedItem().toString().trim());
                        fragmentoListas fragListas = new fragmentoListas();
                        fragListas.setArguments(args);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame1, fragListas)
                                .commit();
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

        MenuItem item = menu.findItem(R.id.spinnerlista);
        spinnerlistas = (Spinner) item.getActionView();

        AdminSQLite database = new AdminSQLite(getBaseContext());
        SQLiteDatabase db = database.getReadableDatabase();

        Listas = new ArrayList<>();
        String[] projection = {"Nombre"};
        Cursor vista = db.query("Lista", projection, null, null, null, null, null);
        while (vista.moveToNext()) {
            String nombre = vista.getString(vista.getColumnIndexOrThrow("Nombre"));
            Listas.add(nombre);
        }
        vista.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Listas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlistas.setAdapter(adapter);

        return true;
    }

}
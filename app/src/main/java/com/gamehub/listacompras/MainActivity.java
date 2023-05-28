package com.gamehub.listacompras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dLayout1;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView1;
    private BottomNavigationView botonNavegation;
    private Toolbar tb1;
    public Spinner spinnerlistas;
    public List<String> Listas;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dLayout1 = findViewById(R.id.drawer1);
        botonNavegation = findViewById(R.id.botonBar);

        getSupportFragmentManager().beginTransaction().add(R.id.frame1, new fragmentoListas()).commit();

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean switchState = sharedPreferences.getBoolean("switchState", false);

        if(switchState ==true){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //recreate();
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //recreate();
        }

        botonNavegation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

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

    /*
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
    */

}
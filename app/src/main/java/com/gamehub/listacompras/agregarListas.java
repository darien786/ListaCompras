package com.gamehub.listacompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamehub.listacompras.bd.AdminSQLite;

import java.util.ArrayList;
import java.util.List;

public class agregarListas extends AppCompatActivity {

    protected Toolbar toolbar_agregar_listas;
    protected EditText agregar_nombre;
    protected AlertDialog.Builder alerta;
    protected AlertDialog.Builder alerta2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_listas);

        toolbar_agregar_listas = findViewById(R.id.toolbar_agregar_listas);
        setSupportActionBar(toolbar_agregar_listas);
    }

    @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_agregar_lista, menu);

        return true;
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.aceptarAgregarLista:

                alerta = new AlertDialog.Builder(agregarListas.this);
                alerta2 = new AlertDialog.Builder(agregarListas.this);

                alerta.setMessage("¿Desea agregar la lista?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                agregar_nombre = (EditText) findViewById(R.id.id_nombre_lista);

                                if(agregar_nombre.getText().length() != 0){
                                    AdminSQLite admin = new AdminSQLite(getBaseContext());
                                    SQLiteDatabase db = admin.getWritableDatabase();

                                    ContentValues values = new ContentValues();
                                    values.put("Nombre", agregar_nombre.getText().toString().trim());
                                    int verificar = (int) db.insert("Lista", null, values);
                                    if (verificar > 0){
                                        Toast.makeText(getBaseContext(),"¡Lista agregada con éxito!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }else {
                                        Toast.makeText(getBaseContext(), "¡Error al agregar la lista!", Toast.LENGTH_LONG).show();
                                    }

                                }else {
                                    alerta2.setMessage("El campo nombre se encuentra vacío");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Aviso!");
                                    alert.show();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = alerta.create();
                alert.setTitle("Alerta");
                alert.show();

                return true;
            default:
                finish();
                return true;
        }
    }
}
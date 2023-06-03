package com.gamehub.listacompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.gamehub.listacompras.bd.AdminSQLite;


public class Editar_Lista extends AppCompatActivity {


    Toolbar toolbar;
    protected AlertDialog.Builder alerta;
    protected AlertDialog.Builder alerta2;
    protected EditText editar_nombre;
    protected String listaSeleccionada;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lista);

        editar_nombre = findViewById(R.id.editar_nombre);

        Bundle bundle = getIntent().getExtras();
        listaSeleccionada = bundle.getString("listaSeleccionada");

        editar_nombre.setText(listaSeleccionada);

        toolbar = findViewById(R.id.toolbar_editar_lista);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aceptar_editar_lista, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.menu_editar_aceptar_lista:

                alerta = new AlertDialog.Builder(Editar_Lista.this);
                alerta2 = new AlertDialog.Builder(Editar_Lista.this);

                alerta.setMessage("¿Desea editar la lista?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                editar_nombre = findViewById(R.id.editar_nombre);

                                if(editar_nombre.getText().length() != 0){

                                    AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
                                    SQLiteDatabase db  = baseCompras.getWritableDatabase();

                                    ContentValues values = new ContentValues();
                                    values.put("Nombre", editar_nombre.getText().toString().trim());

                                    String seleccion = "Nombre=?";
                                    String [] seleccionArgs = {listaSeleccionada.trim()};

                                    int actualizar = db.update("Lista",values,seleccion,seleccionArgs);

                                    if(actualizar >0){
                                        Toast.makeText(getBaseContext(),"¡Lista editada con éxito", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getBaseContext(),"¡Error al editar la lista!",Toast.LENGTH_LONG).show();
                                    }
                                    finish();
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
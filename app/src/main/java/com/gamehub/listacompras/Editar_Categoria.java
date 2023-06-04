package com.gamehub.listacompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.gamehub.listacompras.bd.AdminSQLite;

public class Editar_Categoria extends AppCompatActivity {

    protected Toolbar tbCategoria;
    protected EditText nombreCategoria;
    protected String categoriaSeleccionada;
    protected AlertDialog.Builder alerta;
    protected AlertDialog.Builder alerta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria);

        tbCategoria = findViewById(R.id.toolbar_editar_categoria);
        setSupportActionBar(tbCategoria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        categoriaSeleccionada = bundle.getString("categoriaSeleccionada");

        nombreCategoria = findViewById(R.id.editar_nombre_categoria);

        nombreCategoria.setText(categoriaSeleccionada);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aceptar_editar_lista, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.menu_editar_aceptar_lista:

                alerta = new AlertDialog.Builder(Editar_Categoria.this);
                alerta2 = new AlertDialog.Builder(Editar_Categoria.this);

                alerta.setMessage("¿Desea editar la categoría?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (nombreCategoria.getText().length() != 0){

                                    AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
                                    SQLiteDatabase db  = baseCompras.getWritableDatabase();

                                    ContentValues values = new ContentValues();
                                    values.put("Nombre", nombreCategoria.getText().toString().trim());

                                    String seleccion = "Nombre=?";
                                    String [] seleccionArgs = {categoriaSeleccionada.trim()};

                                    int actualizar = db.update("Categoria",values,seleccion,seleccionArgs);

                                    if(actualizar >0){
                                        Toast.makeText(getBaseContext(),"¡Categoría editada con éxito", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getBaseContext(),"¡Error al editar la categoría!",Toast.LENGTH_LONG).show();
                                    }
                                    finish();
                                }else {
                                    alerta2.setMessage("El campo nombre se encuentra vacío");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
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
                alert.setTitle("Aviso");
                alert.show();

                return true;
            default:
                finish();
                return true;
        }
    }
}
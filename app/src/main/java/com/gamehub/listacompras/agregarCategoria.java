package com.gamehub.listacompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class agregarCategoria extends AppCompatActivity {

    private Toolbar toolbar_agregar_categoria;
    protected EditText agregar_nombre;
    protected RecyclerView categorias;
    protected AlertDialog.Builder alerta;
    protected AlertDialog.Builder alerta2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);

        toolbar_agregar_categoria = findViewById(R.id.toolbar_agregar_categoria);
        setSupportActionBar(toolbar_agregar_categoria);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_agregar_categoria, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.agregarNombreCategoria:

                alerta = new AlertDialog.Builder(agregarCategoria.this);
                alerta2 = new AlertDialog.Builder(agregarCategoria.this);

                alerta.setMessage("¿Desea agregar la categoría?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                agregar_nombre = (EditText) findViewById(R.id.agregar_nombre_categoria);

                                if(agregar_nombre.getText().length() != 0){
                                    AdminSQLite admin = new AdminSQLite(getBaseContext());
                                    SQLiteDatabase db = admin.getWritableDatabase();

                                    ContentValues values = new ContentValues();
                                    values.put("Nombre", agregar_nombre.getText().toString().trim());
                                    int verificar = (int) db.insert("Categoria", null, values);
                                    if (verificar > 0){
                                        Toast.makeText(getBaseContext(),"¡Categoría agregada con éxito!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }else {
                                        Toast.makeText(getBaseContext(), "¡Error al agregar la categoría!", Toast.LENGTH_LONG).show();
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
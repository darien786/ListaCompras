package com.gamehub.listacompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gamehub.listacompras.bd.AdminSQLite;

import java.util.ArrayList;
import java.util.List;

public class agregarArticulo extends AppCompatActivity {

    //Componentes que vamos a estar utilizando
    protected Toolbar toolArticulo;
    protected Spinner categorias;
    protected Spinner unidad;
    protected int valor;
    protected Bundle bundle;
    protected String valorLista;
    protected AlertDialog.Builder alerta;
    protected AlertDialog.Builder alerta2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_articulo);

        //Declaramos las variables con los id que estan agregando al activity
        categorias = (Spinner) findViewById(R.id.datos_categorias);
        unidad = (Spinner) findViewById(R.id.id_unidad_medida);
        toolArticulo = findViewById(R.id.toolbarArticulo);
        setSupportActionBar(toolArticulo);

        //Agregamos un objeto que utilizamos para  crear la base de datos
        AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
        SQLiteDatabase db  = baseCompras.getWritableDatabase();

        //Agregamos una lista tipo String donde se guardaran los nombres de las categorias que se encuentran agregados en la base de datos
        List<String> lista = new ArrayList<String>();

        //De la columna Nombre de la tabla Categoría, recuperaremos haciendo un recorrido con un while dentro de la tabla categoría para obtener todos los nombres.
        String[] projection = {"Nombre"};
        Cursor vistas = db.query("Categoria", projection,null,null,null,null,null);
        while(vistas.moveToNext()){
            String nombre = vistas.getString(vistas.getColumnIndexOrThrow("Nombre"));
            lista.add(nombre);
        }
        vistas.close();

        //Creamos un ArrayAdapte que va a estar guardando valores de tipo string, ademas de asignarle un diseño
        ArrayAdapter <String> adapter = new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adapter);

        //De la columna Nombre de la tabla Unidad, recuperaremos haciendo un recorrido con un while dentro de la tabla categoría para obtener todos los nombres.
        List<String> unidades = new ArrayList<>();
        String [] proyeccion = {"Nombre"};
        Cursor vistasUnidad = db.query("Unidad",proyeccion,null,null,null,null,null);
        while (vistasUnidad.moveToNext()){
            String nombre = vistasUnidad.getString(vistasUnidad.getColumnIndexOrThrow("Nombre"));
            unidades.add(nombre);
        }
        vistasUnidad.close();


        //Creamos un ArrayAdapte que va a estar guardando valores de tipo string, ademas de asignarle un diseño
        ArrayAdapter <String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,unidades);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unidad.setAdapter(adapter2);

        bundle = getIntent().getExtras();
        valorLista = bundle.getString("valorLista");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_agregar_articulo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.menu_aceptar_articulo:

                EditText agregar_nombre = findViewById(R.id.id_nombre_articulo);
                EditText agregar_precio = findViewById(R.id.id_precio_articulo);
                EditText agregar_cantidad = findViewById(R.id.id_cantidad_articulo);


                alerta = new AlertDialog.Builder(agregarArticulo.this);
                alerta2 = new AlertDialog.Builder(agregarArticulo.this);

                alerta.setMessage("¿Desea agregar el artículo?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(agregar_nombre.getText().length() !=0){

                                    String nombre = agregar_nombre.getText().toString().trim();
                                    Float precio = Float.valueOf(agregar_precio.getText().toString().trim());
                                    int cantidad = Integer.parseInt(agregar_cantidad.getText().toString().trim());

                                    String unidad_tabla = (String) unidad.getSelectedItem().toString().trim();
                                    String categoria_tabla = (String) categorias.getSelectedItem().toString().trim();

                                    AdminSQLite adminSQLite = new AdminSQLite(getBaseContext());
                                    SQLiteDatabase db = adminSQLite.getWritableDatabase();

                                    int id_lista = 0;
                                    String tabla = "Lista";
                                    String where = "Lista.Nombre=?";
                                    String [] whereArgs = {valorLista.trim()};
                                    String [] project = {"id_Lista"};
                                    Cursor buscar = db.query(tabla,project,where,whereArgs,null,null,null);
                                    while (buscar.moveToNext()){
                                        id_lista = Integer.parseInt(buscar.getString(buscar.getColumnIndexOrThrow("id_Lista")));
                                    }
                                    buscar.close();

                                    ContentValues values = new ContentValues();
                                    values.put("Nombre", nombre);
                                    values.put("Cantidad", cantidad);
                                    values.put("Precio", precio);
                                    values.put("Nombre_Unidad", unidad_tabla);
                                    values.put("Nombre_Categoria", categoria_tabla);
                                    values.put("id_Lista", id_lista);

                                    int verificar = (int) db.insert("Articulo",null, values);

                                    if (verificar > 0){
                                        Toast.makeText(getBaseContext(),"¡Artículo agregado con éxito!",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getBaseContext(), "¡Error al agregar el artículo!",Toast.LENGTH_LONG).show();
                                    }
                                    db.close();
                                    finish();
                                }else {
                                    alerta2.setMessage("El campo nombre se encuentra vacío");
                                    AlertDialog alert = alerta2.create();
                                    alert.setTitle("¡Alerta!");
                                    alert.show();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
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
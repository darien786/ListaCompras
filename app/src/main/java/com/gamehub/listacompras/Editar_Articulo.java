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
import android.widget.Toast;

import com.gamehub.listacompras.bd.AdminSQLite;

import java.util.ArrayList;
import java.util.List;

public class Editar_Articulo extends AppCompatActivity {

    protected Toolbar tbArticulo;
    protected Spinner categorias;
    protected Spinner unidadMedida;
    protected int valor;
    protected Bundle bundle;
    protected String valorLista;
    protected AlertDialog.Builder alerta;
    protected AlertDialog.Builder alerta2;

    protected String nombre_anterior,precio_anterior,cantidad_anterior,categoria_anterior,unidad_anterior,listaArticulo;
    protected EditText nombre_articulo,precio_articulo,cantidad_articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_articulo);


        nombre_articulo = findViewById(R.id.editar_nombre_articulo);
        precio_articulo = findViewById(R.id.id_precio_editar_articulo);
        cantidad_articulo = findViewById(R.id.id_cantidad_editar_articulo);
        categorias = findViewById(R.id.spinner_categoria_editar_articulo);
        unidadMedida = findViewById(R.id.spinner_unidad_editar_articulo);

        tbArticulo = findViewById(R.id.toolbar_editar_articulo);
        setSupportActionBar(tbArticulo);


        //Agregamos un objeto que utilizamos para  crear la base de datos
        AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
        SQLiteDatabase db  = baseCompras.getWritableDatabase();

        //Agregamos una lista tipo String donde se guardaran los nombres de las categorias que se encuentran agregados en la base de datos
        List<String> listaCategorias = new ArrayList<String>();

        //De la columna Nombre de la tabla Categoría, recuperaremos haciendo un recorrido con un while dentro de la tabla categoría para obtener todos los nombres.
        String[] projection = {"Nombre"};
        Cursor vistas = db.query("Categoria", projection,null,null,null,null,null);
        while(vistas.moveToNext()){
            String nombre = vistas.getString(vistas.getColumnIndexOrThrow("Nombre"));
            listaCategorias.add(nombre);
        }
        vistas.close();

        //Creamos un ArrayAdapte que va a estar guardando valores de tipo string, ademas de asignarle un diseño
        ArrayAdapter<String> adapter = new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item, listaCategorias);
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
        unidadMedida.setAdapter(adapter2);


        bundle = getIntent().getExtras();
        nombre_anterior = bundle.getString("NombreArticulo");
        cantidad_anterior = bundle.getString("CantidadArticulo");
        precio_anterior = bundle.getString("PrecioArticulo");
        categoria_anterior = bundle.getString("CategoriaArticulo");
        unidad_anterior = bundle.getString("UnidadArticulo");
        listaArticulo = bundle.getString("listaArticulo");

        nombre_articulo.setText(nombre_anterior);
        precio_articulo.setText(precio_anterior);
        cantidad_articulo.setText(cantidad_anterior);

        int indiceCategoriaArticulo = listaCategorias.indexOf(categoria_anterior.trim());
        int indiceUnidadArticulo = unidades.indexOf(unidad_anterior.trim());

        categorias.setSelection(indiceCategoriaArticulo);
        unidadMedida.setSelection(indiceUnidadArticulo);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aceptar_editar_articulo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.aceptar_editar_articulo:

                alerta = new AlertDialog.Builder(Editar_Articulo.this);
                alerta2 = new AlertDialog.Builder(Editar_Articulo.this);

                alerta.setMessage("¿Desea editar el artículo?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(nombre_articulo.getText().length() !=0){

                                    String nombre = nombre_articulo.getText().toString().trim();
                                    Float precio = Float.valueOf(precio_articulo.getText().toString().trim());
                                    int cantidad = Integer.parseInt(cantidad_articulo.getText().toString().trim());

                                    String unidad_tabla = (String) unidadMedida.getSelectedItem().toString().trim();
                                    String categoria_tabla = (String) categorias.getSelectedItem().toString().trim();

                                    int id_Lista = Integer.parseInt(listaArticulo);

                                    AdminSQLite adminSQLite = new AdminSQLite(getBaseContext());
                                    SQLiteDatabase db = adminSQLite.getWritableDatabase();

                                    ContentValues values = new ContentValues();
                                    values.put("Nombre", nombre);

                                    values.put("Cantidad", cantidad);
                                    values.put("Precio", precio);
                                    values.put("Nombre_Categoria", categoria_tabla);
                                    values.put("Nombre_Unidad", unidad_tabla);
                                    values.put("id_Lista",id_Lista);

                                    String whereClause = "Nombre = ? AND Cantidad = ? AND Precio = ? AND Nombre_Categoria = ? AND Nombre_Unidad = ? AND id_Lista = ?";
                                    String[] whereArgs = {nombre_anterior,cantidad_anterior, precio_anterior,categoria_anterior,unidad_anterior,listaArticulo};

                                    int verificar = db.update("Articulo",values,whereClause,whereArgs);

                                    if (verificar > 0){
                                        Toast.makeText(getBaseContext(),"¡Artículo editado con éxito!",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getBaseContext(), "¡Error al editar el artículo!",Toast.LENGTH_LONG).show();
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
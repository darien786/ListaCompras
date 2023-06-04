package com.gamehub.listacompras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class menuCategorias extends AppCompatActivity {

    private Toolbar tb2;
    private FloatingActionButton btn_AgregarCategoria;
    protected ListView categorias;
    private String categoriaSeleccionada;
    private Object object;
    private AlertDialog.Builder alerta;
    private AlertDialog.Builder alerta2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_categorias);

        tb2 = findViewById(R.id.toolbarCategorias);
        btn_AgregarCategoria = findViewById(R.id.btn_AgregarCategoria);
        categorias = findViewById(R.id.id_Categorias);

        setSupportActionBar(tb2);

        btn_AgregarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAgregarCategoria = new Intent(menuCategorias.this, agregarCategoria.class);
                startActivity(ventanaAgregarCategoria);
            }
        });

        actualizarDatos();
        onCLick();
    }

    public void onCLick(){
        categorias.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        categorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                categoriaSeleccionada = (String) parent.getItemAtPosition(position);
                tb2.setVisibility(View.GONE);
                object = menuCategorias.this.startActionMode(acm);
                view.setSelected(true);
                return true;
            }
        });
    }


    private ActionMode.Callback acm = new ActionMode.Callback(){

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_opciones_categoria,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){

                case R.id.eliminarCategoria:

                    alerta = new AlertDialog.Builder(menuCategorias.this);

                    alerta.setMessage("¿Desea eliminar la categoría?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AdminSQLite sql = new AdminSQLite(getBaseContext());
                                    SQLiteDatabase data = sql.getReadableDatabase();

                                    String tableName = "Categoria";
                                    String whereClause = "Nombre=?";
                                    String[] whereArgs = {categoriaSeleccionada};

                                    int eliminar = data.delete(tableName,whereClause,whereArgs);


                                    if(eliminar > 0){
                                        //Se elimino con éxito
                                        Toast.makeText(getBaseContext(), "¡Categoría eliminada con éxito!", Toast.LENGTH_LONG).show();
                                        actualizarDatos();
                                    }else{
                                        //No se encontro la tupla o no se pudo
                                        Toast.makeText(getBaseContext(),"¡Error al eliminar la categoría!", Toast.LENGTH_LONG).show();
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

                    mode.finish();
                    return true;

                case R.id.editarCategoria:
                    Intent ventana = new Intent(getBaseContext(),Editar_Categoria.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("categoriaSeleccionada",categoriaSeleccionada.trim());
                    ventana.putExtras(bundle);
                    startActivity(ventana);
                    mode.finish();
                    return true;

                case R.id.copiarCategoria:
                    AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
                    SQLiteDatabase db  = baseCompras.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("Nombre", categoriaSeleccionada+ " (copia)");
                    db.insert("Categoria", null, values);
                    actualizarDatos();

                    Toast.makeText(getBaseContext(),"¡Categoría copiada con éxito!", Toast.LENGTH_LONG).show();

                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mode.finish();
            tb2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        actualizarDatos();
        onCLick();

    }

    private void actualizarDatos(){

        AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
        SQLiteDatabase db  = baseCompras.getWritableDatabase();

        ArrayList<String> categoria = new ArrayList<String>();

        String[] projection = {"Nombre"};
        Cursor vistas = db.query("Categoria", projection,null,null,null,null,null);
        while(vistas.moveToNext()){
            String nombre = vistas.getString(vistas.getColumnIndexOrThrow("Nombre"));
            categoria.add(nombre);

        }
        vistas.close();

        MyAdapterCategoria adapter = new MyAdapterCategoria(this, R.layout.item_categoria,categoria);
        categorias.setAdapter(adapter);
        
    }


    protected class MyAdapterCategoria extends BaseAdapter {

        private Context context;
        private int layout;
        private ArrayList<String> names;

        public MyAdapterCategoria(Context context, int layout, ArrayList<String> names) {
            this.context = context;
            this.layout = layout;
            this.names = names;
        }

        @Override
        public int getCount() {
            return this.names.size();
        }

        @Override
        public Object getItem(int position) {
            return this.names.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            LayoutInflater layoutInflater = LayoutInflater.from(this.context);

            v = layoutInflater.inflate(R.layout.item_categoria, null);

            String currentName = names.get(position);

            TextView nombre = (TextView) v.findViewById(R.id.itemValorCategoria);
            nombre.setText(currentName);

            return v;
        }
    }

}
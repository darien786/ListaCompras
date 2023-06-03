package com.gamehub.listacompras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class menuListas extends AppCompatActivity {

    protected Toolbar tb1;
    protected FloatingActionButton btn_AgregarListas;

    protected int foto = R.drawable.menus_de_listas;
    protected ListView listas;
    protected ActionMode mActionMode;
    protected String listaSeleccionada;
    protected AlertDialog.Builder alerta;

    @SuppressLint({"MissingInflatedId", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listas);
        tb1 = findViewById(R.id.tolListas);
        btn_AgregarListas = findViewById(R.id.btn_AgregarLista);
        listas = findViewById(R.id.id_listas);
        setSupportActionBar(tb1);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_AgregarListas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAgregarLista = new Intent(menuListas.this, agregarListas.class);
                startActivity(ventanaAgregarLista);
            }
        });

        actualizarDatos();
        onClick();
    }


    private ActionMode.Callback acm = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            tb1.getMenu().clear();
            getMenuInflater().inflate(R.menu.menu_opciones_listas,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){

                case R.id.eliminarOpcionesLista:

                    alerta = new AlertDialog.Builder(menuListas.this);

                    alerta.setMessage("¿Desea eliminar la lista?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AdminSQLite sql = new AdminSQLite(getBaseContext());
                                    SQLiteDatabase data = sql.getReadableDatabase();

                                    String tableName = "Lista";
                                    String whereClause = "Nombre=?";
                                    String[] whereArgs = {listaSeleccionada.trim()};

                                    // Ejecuta la sentencia DELETE
                                    int eliminar = data.delete(tableName, whereClause, whereArgs);

                                    if (eliminar > 0) {
                                        //Se elimino con éxito
                                        Toast.makeText(getBaseContext(),"¡Lista eliminada con éxito!",Toast.LENGTH_LONG).show();
                                        actualizarDatos();
                                    } else {
                                        // No se encontró la tupla o no se pudo eliminar por algún motivo
                                        Toast.makeText(getBaseContext(),"¡Error al eliminar la lista!",Toast.LENGTH_LONG).show();
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
                    alert.setTitle("¡Alerta!");
                    alert.show();

                    mode.finish();
                    return true;

                case R.id.editarOpcionesLista:
                    Intent ventana = new Intent(menuListas.this,Editar_Lista.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("listaSeleccionada", listaSeleccionada);
                    ventana.putExtras(bundle);
                    startActivity(ventana);
                    mode.finish();
                    return true;

                case R.id.copiarOpcionesLista:
                    AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
                    SQLiteDatabase db  = baseCompras.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("Nombre", listaSeleccionada+ " (copia)");
                    db.insert("Lista", null, values);
                    actualizarDatos();

                    mode.finish();
                    return true;

                default:
                    mode.finish();
            }

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            tb1.setVisibility(View.VISIBLE);
            mActionMode = null;
        }
    };



    public void onClick(){
        listas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listaSeleccionada = (String) parent.getItemAtPosition(position);
                if(mActionMode != null){
                    return false;
                }
                mActionMode = startActionMode(acm);
                tb1.setVisibility(View.GONE);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarDatos();
        onClick();
        tb1.setVisibility(View.VISIBLE);
        }

    private void actualizarDatos() {


        AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
        SQLiteDatabase db = baseCompras.getWritableDatabase();

        ArrayList<String> lista = new ArrayList<String>();


        String[] projection = {"Nombre"};
        Cursor vistas = db.query("Lista", projection, null, null, null, null, null);
        while (vistas.moveToNext()) {
            String nombre = vistas.getString(vistas.getColumnIndexOrThrow("Nombre"));
            lista.add(nombre);
        }
        vistas.close();

        MyAdapterListas adapter = new MyAdapterListas(this, R.layout.item_lista, lista);
        listas.setAdapter(adapter);


    }


    protected class MyAdapterListas extends BaseAdapter {

        private Context context;
        private int layout;
        private ArrayList<String> names;

        public MyAdapterListas(Context context, int layout, ArrayList<String> names) {
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

            v = layoutInflater.inflate(R.layout.item_lista, null);

            String currentName = names.get(position);

            TextView nombre = (TextView) v.findViewById(R.id.itemValorLista);
            nombre.setText(currentName);

            return v;
        }

    }
}
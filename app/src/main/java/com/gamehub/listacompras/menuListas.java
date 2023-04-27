package com.gamehub.listacompras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class menuListas extends AppCompatActivity {

    private Toolbar tb1;
    private FloatingActionButton btn_AgregarListas;

    protected int foto = R.drawable.menus_de_listas;
    protected ListView listas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_listas);
        tb1 = findViewById(R.id.tolListas);
        btn_AgregarListas = findViewById(R.id.btn_AgregarLista);
        listas = findViewById(R.id.id_listas);
        setSupportActionBar(tb1);

        btn_AgregarListas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaAgregarLista = new Intent(menuListas.this, agregarListas.class);
                startActivity(ventanaAgregarLista);
            }
        });

        actualizarDatos();

    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarDatos();
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

        MyAdapter adapter = new MyAdapter(this, R.layout.item_lista, lista);
        listas.setAdapter(adapter);

    }


    protected class MyAdapter extends BaseAdapter {

        private Context context;
        private int layout;
        private ArrayList<String> names;

        public MyAdapter(Context context, int layout, ArrayList<String> names) {
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
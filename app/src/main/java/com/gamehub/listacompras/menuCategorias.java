package com.gamehub.listacompras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class menuCategorias extends AppCompatActivity {

    private Toolbar tb2;

    private FloatingActionButton btn_AgregarCategoria;
    protected RecyclerView categorias;
    protected List<String> categoria;

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

        LinearLayoutManager linearManayer = new LinearLayoutManager(this);
        categorias.setLayoutManager(linearManayer);
        categorias.setAdapter(new AdaptadorCategorias());

        AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
        SQLiteDatabase db  = baseCompras.getWritableDatabase();

        categoria = new ArrayList<String>();
        String[] projection = {"Nombre"};
        Cursor vistas = db.query("Categoria", projection,null,null,null,null,null);
        while(vistas.moveToNext()){
            String nombre = vistas.getString(vistas.getColumnIndexOrThrow("Nombre"));
            categoria.add(nombre);
        }
        vistas.close();
    }

    private class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.AdaptadorCategoriasHolder> {


        @NonNull
        @Override
        public AdaptadorCategoriasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorCategoriasHolder(getLayoutInflater().inflate(R.layout.item_categoria, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorCategoriasHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return categoria.size();
        }

        private class AdaptadorCategoriasHolder extends RecyclerView.ViewHolder{
            protected TextView nombre_categoria;

            public AdaptadorCategoriasHolder(@NonNull View itemView) {
                super(itemView);
                nombre_categoria = itemView.findViewById(R.id.itemValorCategoria);

            }

            public void imprimir(int position) {
                nombre_categoria.setText(categoria.get(position));
            }
        }
    }

}
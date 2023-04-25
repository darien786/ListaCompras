package com.gamehub.listacompras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
    protected RecyclerView listas;
    protected List<String> lista;

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

        LinearLayoutManager linearManayer = new LinearLayoutManager(this);
        AdaptadorListas adapter = new AdaptadorListas();
        listas.setLayoutManager(linearManayer);
        listas.setAdapter(adapter);

        lista = new ArrayList<String>();
    }

    @Override
    protected void onResume(){
        super.onResume();
        actualizarDatos();
    }

    private void actualizarDatos(){
        lista.clear();

        AdminSQLite baseCompras = new AdminSQLite(getBaseContext());
        SQLiteDatabase db  = baseCompras.getWritableDatabase();

        String[] projection = {"Nombre"};
        Cursor vistas = db.query("Lista", projection,null,null,null,null,null);
        while(vistas.moveToNext()){
            String nombre = vistas.getString(vistas.getColumnIndexOrThrow("Nombre"));
            lista.add(nombre);
        }
        vistas.close();

        if(listas.getAdapter() != null){
            listas.getAdapter().notifyDataSetChanged();
        }
    }


    protected class AdaptadorListas extends RecyclerView.Adapter<AdaptadorListas.AdaptadorListasHolder> {


        @NonNull
        @Override
        public AdaptadorListas.AdaptadorListasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorListas.AdaptadorListasHolder(getLayoutInflater().inflate(R.layout.item_lista, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorListas.AdaptadorListasHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return lista.size();
        }


        private class AdaptadorListasHolder extends RecyclerView.ViewHolder{
            protected TextView nombre_categoria;

            public AdaptadorListasHolder(@NonNull View itemView) {
                super(itemView);
                nombre_categoria = itemView.findViewById(R.id.itemValorLista);
            }

            public void imprimir(int position) {
                nombre_categoria.setText(lista.get(position));
            }
        }
    }

}
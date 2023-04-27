package com.gamehub.listacompras;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentoListas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentoListas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentoListas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentoListas.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentoListas newInstance(String param1, String param2) {
        fragmentoListas fragment = new fragmentoListas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    protected FloatingActionButton agregar_articulo;
    protected ListView mostrar_articulos;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).getSupportActionBar().show();

        View view = inflater.inflate(R.layout.fragment_fragmento_listas,container,false);

        agregar_articulo = view.findViewById(R.id.btn_AgregarArticulo);
        agregar_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ventanaAgregar = new Intent(getActivity(),agregarArticulo.class);
                startActivity(ventanaAgregar);

            }
        });


        mostrar_articulos = view.findViewById(R.id.mostrar_articulos_list);

        actualizarDatos();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        actualizarDatos();
    }

    protected void actualizarDatos(){

        AdminSQLite adminSQLite = new AdminSQLite(getContext());
        SQLiteDatabase db = adminSQLite.getReadableDatabase();

        List<String> articulos = new ArrayList<String>();

        Cursor vista = db.query("Articulo", null,null,null,null,null,null);
        while(vista.moveToNext()){

            String nombre = vista.getString(vista.getColumnIndexOrThrow("Nombre"));
            String cantidad  = vista.getString(vista.getColumnIndexOrThrow("Cantidad"));
            String precio = vista.getString(vista.getColumnIndexOrThrow("Precio"));
            String unidad = vista.getString(vista.getColumnIndexOrThrow("Nombre_Unidad"));
            String categoria = vista.getString(vista.getColumnIndexOrThrow("Nombre_Categoria"));

            //String mostrar = "-" + nombre + "-" + cantidad + "-"+precio+"-"+unidad+"-"+categoria;

            articulos.add(nombre+cantidad+precio+unidad+categoria);
        }

        vista.close();

        //MyAdapter adapter = new MyAdapter( getActivity(), R.layout.item_articulo,articulos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,articulos);
        mostrar_articulos.setAdapter(adapter);


    }

    /*
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

            v = layoutInflater.inflate(R.layout.item_categoria, null);

            String currentName = names.get(position);

            TextView nombre = (TextView) v.findViewById(R.id.id_nombre_articulo);
            nombre.setText(currentName);

            return v;
        }
    }*/

    /*protected class Articulo {
        protected String nombre;
        protected String cantidad;
        protected String precio;
        protected String unidad;
        protected String categoria;

        public Articulo(String nombre,String cantidad, String precio, String unidad, String categoria){
            this.nombre=nombre;
            this.cantidad=cantidad;
            this.precio=precio;
            this.unidad=unidad;
            this.categoria=categoria;
        }

        public void setNombre(String nombre){
            this.nombre=nombre;
        }

        public void setCantidad(String cantidad){
            this.cantidad=cantidad;
        }

        public void setPrecio(String precio){
            this.precio=precio;
        }

        public void setUnidad(String unidad){
            this.unidad=unidad;
        }

        public void setCategoria(String categoria){
            this.categoria=categoria;
        }

        public String getNombre(){
            return nombre;
        }
        public String getCantidad(){
            return cantidad;
        }
        public String getPrecio(){
            return precio;
        }

        public String getUnidad(){
            return unidad;
        }
        public String getCategoria(){
            return categoria;
        }

    }*/

}



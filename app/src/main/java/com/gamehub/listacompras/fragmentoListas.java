package com.gamehub.listacompras;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.gamehub.listacompras.bd.AdminSQLite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
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
        setHasOptionsMenu(true);
    }

    protected FloatingActionButton agregar_articulo;
    protected ListView mostrar_articulos;
    protected TextView total;
    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Spinner spinnerlistas;
    protected String listaActual="Mi Lista";
    protected String valorLista;
    protected Button boton_descargar;
    protected String totalTxt;
    protected String nombreArticulo, precioArticulo, cantidadArticulo, unidadArticulo, categoriaArticulo, listaArticulo;

    protected AlertDialog.Builder alerta;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        boton_descargar = (Button) getActivity().findViewById(R.id.menuCompartir);

        View view = inflater.inflate(R.layout.fragment_fragmento_listas,container,false);

        toolbar = view.findViewById(R.id.id_Opciones);
        drawerLayout = view.findViewById(R.id.frameList);
        navigationView = view.findViewById(R.id.navigation_view);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.listas:
                        Intent ventanaListas = new Intent(getContext(), menuListas.class);
                        startActivity(ventanaListas);
                        return true;

                    case R.id.configuration:
                        Intent ventanaConfiguracion = new Intent(getContext(), menuConfiguracion.class);
                        startActivity(ventanaConfiguracion);
                        return true;

                    case R.id.categoria:
                        Intent ventanaCategoria = new Intent(getContext(), menuCategorias.class);
                        startActivity(ventanaCategoria);
                        return true;
                }


                return false;

            }
        });

        agregar_articulo = view.findViewById(R.id.btn_AgregarArticulo);
        agregar_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ventanaAgregar = new Intent(getActivity(),agregarArticulo.class);
                valorLista = spinnerlistas.getSelectedItem().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("valorLista",valorLista);
                ventanaAgregar.putExtras(bundle);
                startActivity(ventanaAgregar);

            }
        });


        mostrar_articulos = view.findViewById(R.id.mostrar_articulos_list);
        total = view.findViewById(R.id.id_Total_Lista);

        actualizarDatos(listaActual);
        // Inflate the layout for this fragment

        onClick();

        return view;
    }

    protected void  onClick(){
        mostrar_articulos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mostrar_articulos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Articulo articulo = (Articulo) parent.getItemAtPosition(position);

                toolbar.startActionMode(acm);
                nombreArticulo = articulo.getNombre().trim();
                cantidadArticulo = articulo.getCantidad().trim();
                precioArticulo = articulo.getPrecio().trim();
                unidadArticulo = articulo.getUnidad().trim();
                categoriaArticulo = articulo.getCategoria().trim();
                listaArticulo = articulo.getLista().trim();
                toolbar.setVisibility(View.GONE);
                parent.setVisibility(View.VISIBLE);

                return true;
            }
        });
    }

    protected ActionMode.Callback acm = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_opciones_articulo,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.eliminarArticulo:

                    alerta = new AlertDialog.Builder(getActivity());
                    alerta.setMessage("¿Desea el eliminar el artículo?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AdminSQLite database =  new AdminSQLite(getContext());
                                    SQLiteDatabase db = database.getWritableDatabase();

                                    String tableName = "Articulo";
                                    String whereClause = "Nombre = ? AND Cantidad = ? AND Precio = ? AND Nombre_Categoria = ? AND Nombre_Unidad = ? AND id_Lista = ?";
                                    String[] whereArgs = {nombreArticulo,cantidadArticulo,precioArticulo,categoriaArticulo,unidadArticulo,listaArticulo};

                                    int verificar = db.delete(tableName, whereClause, whereArgs);

                                    if (verificar > 0){
                                        Toast.makeText(getContext(),"¡Artículo eliminado con éxito!",Toast.LENGTH_LONG).show();
                                        actualizarDatos(listaActual);
                                    }else {
                                        Toast.makeText(getContext(), "¡Error al eliminar el artículo!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alerta.create();
                    alertDialog.setTitle("Aviso");
                    alertDialog.show();
                    mode.finish();
                    return true;

                case R.id.editarArticulo:
                    Intent ventana = new Intent(getContext(),Editar_Articulo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("NombreArticulo",nombreArticulo);
                    bundle.putString("CantidadArticulo",cantidadArticulo);
                    bundle.putString("PrecioArticulo",precioArticulo);
                    bundle.putString("UnidadArticulo",unidadArticulo);
                    bundle.putString("CategoriaArticulo",categoriaArticulo);
                    bundle.putString("listaArticulo",listaArticulo);
                    ventana.putExtras(bundle);
                    startActivity(ventana);
                    mode.finish();
                    return true;

                case R.id.copiarArticulo:
                    AdminSQLite database = new AdminSQLite(getActivity());
                    SQLiteDatabase db = database.getWritableDatabase();

                    Float precio = Float.valueOf(precioArticulo.trim());
                    int cantidad = Integer.parseInt(cantidadArticulo.trim());
                    int id_list = Integer.parseInt(listaArticulo.trim());

                    ContentValues values = new ContentValues();
                    values.put("Nombre", nombreArticulo + " (copia)");
                    values.put("Cantidad", cantidad);
                    values.put("Precio", precio);
                    values.put("Nombre_Unidad", unidadArticulo);
                    values.put("Nombre_Categoria", categoriaArticulo);
                    values.put("id_Lista", id_list);


                    int verificar = (int) db.insert("Articulo",null, values);

                    if(verificar > 0){
                        Toast.makeText(getContext(),"¡Artículo copiado con éxito!", Toast.LENGTH_LONG).show();
                        actualizarDatos(listaActual);
                    }else {
                        Toast.makeText(getContext(),"¡Error al copiar el artículo!", Toast.LENGTH_LONG).show();
                    }
                    mode.finish();
                    return true;
                default:
                    mode.finish();

            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            toolbar.setVisibility(View.VISIBLE);
            mode.finish();
        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menuopciones, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.spinnerlista);
        spinnerlistas = (Spinner) item.getActionView();

       //Para descargar
        MenuItem menuItem = menu.findItem(R.id.menuDescargar);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.menuDescargar){
                    guardarArchivo();
                }
                return true;
            }
        });

        //Para compartir
        MenuItem menuItemCompartir = menu.findItem(R.id.menuCompartir);
        menuItemCompartir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.menuCompartir){
                    compartirArchivo();
                }
                return true;
            }
        });


        AdminSQLite database = new AdminSQLite(getContext());
        SQLiteDatabase db = database.getReadableDatabase();

        ArrayList<String> Listas = new ArrayList<>();
        String[] projection = {"Nombre"};
        Cursor vista = db.query("Lista", projection, null, null, null, null, null);
        while (vista.moveToNext()) {
            String nombre = vista.getString(vista.getColumnIndexOrThrow("Nombre"));
            Listas.add(nombre);
        }
        vista.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Listas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlistas.setAdapter(adapter);

        listaActual = spinnerlistas.getSelectedItem().toString();

        spinnerlistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listaActual = (String) parent.getItemAtPosition(position);
                actualizarDatos(listaActual);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        }


    @Override
    public void onResume(){
        super.onResume();
        actualizarDatos(listaActual);
        onClick();
    }

    protected void actualizarDatos(String listaNueva){

            AdminSQLite adminSQLite = new AdminSQLite(getContext());
            SQLiteDatabase db = adminSQLite.getReadableDatabase();

            String tabla = "Articulo INNER JOIN Lista ON Articulo.id_Lista=Lista.id_Lista";
            String where = "Lista.Nombre=?";
            String [] whereArgs = {listaNueva};
            String [] project = {"Articulo.*"};

            List<Articulo> articulos = new ArrayList<>();

            Float totalLista = Float.valueOf(0),multiplicador;

            Cursor vista = db.query(tabla,project,where,whereArgs,null,null,null);
            while(vista.moveToNext()) {
                String nombre = vista.getString(vista.getColumnIndexOrThrow("Nombre"));
                String cantidad = vista.getString(vista.getColumnIndexOrThrow("Cantidad"));
                String precio = vista.getString(vista.getColumnIndexOrThrow("Precio"));
                String unidad = vista.getString(vista.getColumnIndexOrThrow("Nombre_Unidad"));
                String categoria = vista.getString(vista.getColumnIndexOrThrow("Nombre_Categoria"));
                String lista = vista.getString(vista.getColumnIndexOrThrow("id_Lista"));

                articulos.add(new Articulo(nombre, cantidad, precio, unidad, categoria,lista));

                int cantidadEntero = Integer.parseInt(cantidad);
                Float precioFloat =Float.valueOf(precio);

                multiplicador = precioFloat * cantidadEntero;
                totalLista += multiplicador;
            }

            vista.close();

            total.setText("Total: $" + totalLista);

            totalTxt = totalLista.toString();

            // Guardar el valor en SharedPreferences
            SharedPreferences preferences = getActivity().getSharedPreferences("mis", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("dato", totalLista.toString());
            editor.apply();

            MyAdapter adapter = new MyAdapter( getActivity(),articulos);
            mostrar_articulos.setAdapter(adapter);

    }


    public void guardarArchivo(){
        String nombreLista = listaActual+".txt";
        File carpetaAlmacenamiento = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File  archivo = new File(nombreLista);

        StringBuilder datos = new StringBuilder();
        int itemCount = mostrar_articulos.getAdapter().getCount();

        datos.append(listaActual).append("\n");
        int c=1;

        for (int i = 0; i < itemCount; i++) {
            Object item = mostrar_articulos.getAdapter().getItem(i);
            if (item instanceof Articulo) {
                Articulo articulo = (Articulo) item;
                String nombre = articulo.getNombre();
                String cantidad = articulo.getCantidad();
                String unidad = articulo.getUnidad();
                String precio = articulo.getPrecio();
                String categoria = articulo.getCategoria();

                datos.append("\n"+"Articulo "+ c++).append("\n"+"    Nombre: "+nombre).append("\n"+"    Categoría: "+categoria).append("\n"+"    Cantidad: "+cantidad+" "+unidad).append("\n"+"    Precio: "+"$"+precio).append("\n");
            }
        }

        datos.append("\n"+"\n"+"\n").append("Total de la lista:  $" +totalTxt);

        try {
            FileOutputStream fos = new FileOutputStream(archivo);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            osw.write(datos.toString());

            osw.close();
            fos.close();

            Toast.makeText(getContext(), "Archivo Guardado en: " + archivo, Toast.LENGTH_LONG).show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void compartirArchivo(){
        StringBuilder datos = new StringBuilder();
        int itemCount = mostrar_articulos.getAdapter().getCount();

        datos.append("¡Te comparto mi lista!"+"\n"+"\n");
        datos.append(listaActual).append("\n");
        int c=1;

        for (int i = 0; i < itemCount; i++) {
            Object item = mostrar_articulos.getAdapter().getItem(i);
            if (item instanceof Articulo) {
                Articulo articulo = (Articulo) item;
                String nombre = articulo.getNombre();
                String cantidad = articulo.getCantidad();
                String unidad = articulo.getUnidad();
                String precio = articulo.getPrecio();
                String categoria = articulo.getCategoria();

                datos.append("\n"+"Articulo "+ c++).append("\n"+"    Nombre: "+nombre).append("\n"+"    Categoría: "+categoria).append("\n"+"    Cantidad: "+cantidad+" "+unidad).append("\n"+"    Precio: "+"$"+precio).append("\n");
            }
        }

        datos.append("\n"+"\n"+"\n").append("Total de la lista:  $" +totalTxt);

        Intent intent_compartir = new Intent(Intent.ACTION_SEND);
        intent_compartir.setType("text/plain");

        // Agregar un texto adicional al mensaje (opcional)
        intent_compartir.putExtra(Intent.EXTRA_TEXT, datos.toString());

        // Iniciar el Intent para compartir
        startActivity(Intent.createChooser(intent_compartir, "Compartir archivo a través de:"));
    }

}



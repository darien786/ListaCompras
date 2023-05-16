package com.gamehub.listacompras;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Articulo> {

    private Context context;
    private int layout;
    private ArrayList<String> names;

    public MyAdapter(Context context, List<Articulo> articulos) {
        super(context,0,articulos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Articulo articulo = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_articulo,parent,false);
        }

        TextView nombre = convertView.findViewById(R.id.id_valor_articulo);
        TextView id_datos = convertView.findViewById(R.id.id_mas_datos);

        String datos = articulo.getCantidad() + " "
                + articulo.getUnidad() + " = $"
                + articulo.getPrecio() + " ";

        nombre.setText(articulo.getNombre());
        id_datos.setText(datos);

        return convertView;
    }
}

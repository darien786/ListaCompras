package com.gamehub.listacompras.bd;

public class Producto {

    protected String Nombre;

    public Producto(String nombre) {
        this.Nombre = nombre;
    }

    public void setNombre(String nombre){
        this.Nombre = nombre;
    }

    public String getNombre(){
        return Nombre;
    }
}

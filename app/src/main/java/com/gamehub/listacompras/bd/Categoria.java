package com.gamehub.listacompras.bd;

public class Categoria {

    protected String Nombre;

    public Categoria(String nombre) {
        this.Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
}

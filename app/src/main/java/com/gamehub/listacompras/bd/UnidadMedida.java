package com.gamehub.listacompras.bd;

public class UnidadMedida {

    protected String Nombre;

    public UnidadMedida(String nombre) {
        this.Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
}

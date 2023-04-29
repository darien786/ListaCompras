package com.gamehub.listacompras;

public class Articulo{
    protected String nombre;
    protected String cantidad;
    protected String precio;
    protected String unidad;
    protected String categoria;
    protected String lista;

    public Articulo(String nombre,String cantidad, String precio, String unidad, String categoria, String lista){
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.precio=precio;
        this.unidad=unidad;
        this.categoria=categoria;
        this.lista=lista;
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

    public void setLista(String lista){ this.lista=lista;}

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
    public String getLista() { return lista;}
}


package com.example.carlosje.lectorcodbarrastpv;

/**
 * Created by carlosje on 8/9/2017.
 */

public class Cube {
    private String usuario, fecha, ubicacion;

    public Cube(String usuario, String fecha, String ubicacion) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
    }

    public String getUsuario(){
        return usuario;
    }
    public String getFecha(){
        return fecha;
    }
    public String getUbicacion(){
        return ubicacion;
    }
}
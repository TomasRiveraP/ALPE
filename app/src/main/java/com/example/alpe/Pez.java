package com.example.alpe;

public class Pez {
    private String nombre;
    private int cantidad;

    public Pez() {

    }

    public Pez(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }
}

package com.example.alpe;

public class Registro {
    private String id;
    private String fecha;
    private String contenido;


    public Registro() {
    }

    public Registro(String id, String fecha, String contenido) {
        this.id = id;
        this.fecha = fecha;
        this.contenido = contenido;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
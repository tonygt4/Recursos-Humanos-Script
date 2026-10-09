package com.kina.reclutamiento.model;

public class Rol {
    private final int id;
    private final String nombre;

    public Rol(int id, String nombre) { this.id = id; this.nombre = nombre; }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
}

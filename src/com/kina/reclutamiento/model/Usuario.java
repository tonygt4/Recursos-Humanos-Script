package com.kina.reclutamiento.model;

public class Usuario {
    private final int id;
    private final String nombreUsuario;
    private final String correo;
    private final String passwordHash;
    private final Rol rol;

    public Usuario(int id, String nombreUsuario, String correo, String passwordHash, Rol rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }
    public int getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getCorreo() { return correo; }
    public String getPasswordHash() { return passwordHash; }
    public Rol getRol() { return rol; }
}

package com.kina.reclutamiento.dto;

public class UsuarioDTO {
    private final String nombreUsuario;
    private final String rol;

    public UsuarioDTO(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getRol() { return rol; }
}

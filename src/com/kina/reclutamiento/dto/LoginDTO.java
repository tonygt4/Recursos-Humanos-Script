package com.kina.reclutamiento.dto;

public class LoginDTO {
    private final String nombreUsuario;
    private final String password;

    public LoginDTO(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getPassword() { return password; }
}

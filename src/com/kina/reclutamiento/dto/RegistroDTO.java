package com.kina.reclutamiento.dto;

public class RegistroDTO {
    private final String nombreUsuario;
    private final String correo;
    private final String password;
    private final String confirmacion;

    public RegistroDTO(String nombreUsuario, String correo, String password, String confirmacion) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.password = password;
        this.confirmacion = confirmacion;
    }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
    public String getConfirmacion() { return confirmacion; }
}

package com.kina.reclutamiento.util;

import com.kina.reclutamiento.dto.UsuarioDTO;

public class SesionUsuario {
    private UsuarioDTO usuarioActual;

    public void iniciar(UsuarioDTO usuario) { this.usuarioActual = usuario; }
    public void cerrar() { this.usuarioActual = null; }
    public UsuarioDTO getUsuarioActual() { return usuarioActual; }
}

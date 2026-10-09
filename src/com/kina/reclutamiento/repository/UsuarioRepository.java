package com.kina.reclutamiento.repository;

import com.kina.reclutamiento.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario);
    boolean existePorNombreUsuarioOCorreo(String nombreUsuario, String correo);
    void guardar(Usuario usuario);
}

package com.kina.reclutamiento.service.impl;

import com.kina.reclutamiento.dto.LoginDTO;
import com.kina.reclutamiento.dto.RegistroDTO;
import com.kina.reclutamiento.dto.UsuarioDTO;
import com.kina.reclutamiento.exception.AuthException;
import com.kina.reclutamiento.exception.ValidationException;
import com.kina.reclutamiento.model.Rol;
import com.kina.reclutamiento.model.Usuario;
import com.kina.reclutamiento.repository.RolRepository;
import com.kina.reclutamiento.repository.UsuarioRepository;
import com.kina.reclutamiento.service.AuthService;
import com.kina.reclutamiento.util.Validador;
import org.mindrot.jbcrypt.BCrypt;

public class AuthServiceImpl implements AuthService {
    private static final int LONGITUD_MINIMA_PASSWORD = 6;

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public void registrar(RegistroDTO registro) {
        Validador.requerido(registro.getNombreUsuario(), "Usuario");
        Validador.requerido(registro.getCorreo(), "Correo");
        Validador.requerido(registro.getPassword(), "Contraseña");
        Validador.requerido(registro.getConfirmacion(), "Confirmar contraseña");
        Validador.correoValido(registro.getCorreo());

        if (registro.getPassword().length() < LONGITUD_MINIMA_PASSWORD) {
            throw new ValidationException("La contraseña debe tener al menos " + LONGITUD_MINIMA_PASSWORD + " caracteres");
        }
        if (!registro.getPassword().equals(registro.getConfirmacion())) {
            throw new ValidationException("Las contraseñas no coinciden");
        }

        String nombreUsuario = registro.getNombreUsuario().trim();
        String correo = registro.getCorreo().trim();
        if (usuarioRepository.existePorNombreUsuarioOCorreo(nombreUsuario, correo)) {
            throw new AuthException("El usuario o el correo ya están registrados");
        }

        String correoNormalizado = correo.toLowerCase();
        String nombreRol = correoNormalizado.contains("admin") ? "ADMIN" : "Usuario";
        Rol rol = rolRepository.buscarPorNombre(nombreRol)
                .orElseThrow(() -> new AuthException("El rol " + nombreRol + " no existe en la base de datos"));
        String hash = BCrypt.hashpw(registro.getPassword(), BCrypt.gensalt());
        usuarioRepository.guardar(new Usuario(0, nombreUsuario, correo, hash, rol));
    }

    @Override
    public UsuarioDTO iniciarSesion(LoginDTO login) {
        Validador.requerido(login.getNombreUsuario(), "Usuario");
        Validador.requerido(login.getPassword(), "Contraseña");

        Usuario usuario = usuarioRepository.buscarPorNombreUsuario(login.getNombreUsuario().trim())
                .orElseThrow(() -> new AuthException("Usuario o contraseña incorrectos"));
        if (!BCrypt.checkpw(login.getPassword(), usuario.getPasswordHash())) {
            throw new AuthException("Usuario o contraseña incorrectos");
        }
        return new UsuarioDTO(usuario.getNombreUsuario(), usuario.getRol().getNombre());
    }
}

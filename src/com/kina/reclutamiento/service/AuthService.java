package com.kina.reclutamiento.service;

import com.kina.reclutamiento.dto.LoginDTO;
import com.kina.reclutamiento.dto.RegistroDTO;
import com.kina.reclutamiento.dto.UsuarioDTO;

public interface AuthService {
    void registrar(RegistroDTO registro);
    UsuarioDTO iniciarSesion(LoginDTO login);
}

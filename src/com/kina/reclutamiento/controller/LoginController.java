package com.kina.reclutamiento.controller;

import com.kina.reclutamiento.dto.LoginDTO;
import com.kina.reclutamiento.dto.UsuarioDTO;
import com.kina.reclutamiento.exception.AppException;
import com.kina.reclutamiento.service.AuthService;
import com.kina.reclutamiento.util.AlertHelper;
import com.kina.reclutamiento.util.SceneManager;
import com.kina.reclutamiento.util.SceneManager.Vista;
import com.kina.reclutamiento.util.SesionUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private final AuthService authService;
    private final SesionUsuario sesion;
    private final SceneManager sceneManager;

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;

    public LoginController(AuthService authService, SesionUsuario sesion, SceneManager sceneManager) {
        this.authService = authService;
        this.sesion = sesion;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void onIniciarSesion() {
        try {
            UsuarioDTO usuario = authService.iniciarSesion(new LoginDTO(txtUsuario.getText(), txtPassword.getText()));
            sesion.iniciar(usuario);
            if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
                sceneManager.mostrar(Vista.INVENTARIO);
            } else {
                sceneManager.mostrar(Vista.INVENTARIO_USER);
            }
        } catch (AppException e) {
            AlertHelper.error(e.getMessage());
        }
    }

    @FXML
    private void onIrARegistro() {
        sceneManager.mostrar(Vista.REGISTRO);
    }
}

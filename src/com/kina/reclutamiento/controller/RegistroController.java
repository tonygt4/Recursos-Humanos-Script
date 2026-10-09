package com.kina.reclutamiento.controller;

import com.kina.reclutamiento.dto.RegistroDTO;
import com.kina.reclutamiento.exception.AppException;
import com.kina.reclutamiento.service.AuthService;
import com.kina.reclutamiento.util.AlertHelper;
import com.kina.reclutamiento.util.SceneManager;
import com.kina.reclutamiento.util.SceneManager.Vista;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {
    private final AuthService authService;
    private final SceneManager sceneManager;

    @FXML private TextField txtUsuario;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmacion;

    public RegistroController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void onRegistrar() {
        try {
            authService.registrar(new RegistroDTO(txtUsuario.getText(), txtCorreo.getText(),
                    txtPassword.getText(), txtConfirmacion.getText()));
            AlertHelper.info("Usuario registrado correctamente. Ya puedes iniciar sesión.");
            sceneManager.mostrar(Vista.LOGIN);
        } catch (AppException e) {
            AlertHelper.error(e.getMessage());
        }
    }

    @FXML
    private void onVolver() {
        sceneManager.mostrar(Vista.LOGIN);
    }
}

package com.kina.reclutamiento;

import com.kina.reclutamiento.config.DatabaseConnection;
import com.kina.reclutamiento.controller.InventarioController;
import com.kina.reclutamiento.controller.InventarioUserController;
import com.kina.reclutamiento.controller.LoginController;
import com.kina.reclutamiento.controller.RegistroController;
import com.kina.reclutamiento.exception.AppException;
import com.kina.reclutamiento.repository.AspiranteRepository;
import com.kina.reclutamiento.repository.RolRepository;
import com.kina.reclutamiento.repository.UsuarioRepository;
import com.kina.reclutamiento.repository.jdbc.AspiranteJdbcRepository;
import com.kina.reclutamiento.repository.jdbc.RolJdbcRepository;
import com.kina.reclutamiento.repository.jdbc.UsuarioJdbcRepository;
import com.kina.reclutamiento.service.AspiranteService;
import com.kina.reclutamiento.service.AuthService;
import com.kina.reclutamiento.service.impl.AspiranteServiceImpl;
import com.kina.reclutamiento.service.impl.AuthServiceImpl;
import com.kina.reclutamiento.util.AlertHelper;
import com.kina.reclutamiento.util.SceneManager;
import com.kina.reclutamiento.util.SceneManager.Vista;
import com.kina.reclutamiento.util.SesionUsuario;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            DatabaseConnection db = new DatabaseConnection();

            RolRepository rolRepository = new RolJdbcRepository(db);
            UsuarioRepository usuarioRepository = new UsuarioJdbcRepository(db);
            AspiranteRepository aspiranteRepository = new AspiranteJdbcRepository(db);

            AuthService authService = new AuthServiceImpl(usuarioRepository, rolRepository);
            AspiranteService aspiranteService = new AspiranteServiceImpl(aspiranteRepository);

            SesionUsuario sesion = new SesionUsuario();
            SceneManager sceneManager = new SceneManager(stage);
            sceneManager.registrar(Vista.LOGIN, () -> new LoginController(authService, sesion, sceneManager));
            sceneManager.registrar(Vista.REGISTRO, () -> new RegistroController(authService, sceneManager));
            sceneManager.registrar(Vista.INVENTARIO, () -> new InventarioController(aspiranteService, sesion, sceneManager));
            sceneManager.registrar(Vista.INVENTARIO_USER, () -> new InventarioUserController(aspiranteService, sesion, sceneManager));

            sceneManager.mostrar(Vista.LOGIN);
        } catch (AppException e) {
            AlertHelper.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

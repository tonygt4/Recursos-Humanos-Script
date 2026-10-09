package com.kina.reclutamiento.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class SceneManager {

    public enum Vista {
        LOGIN("/com/kina/reclutamiento/view/login.fxml", "Iniciar sesión"),
        REGISTRO("/com/kina/reclutamiento/view/registro.fxml", "Registro"),
        INVENTARIO("/com/kina/reclutamiento/view/inventario.fxml", "Inventario de aspirantes - Administrador"),
        INVENTARIO_USER("/com/kina/reclutamiento/view/inventariouser.fxml", "Inventario de aspirantes");

        private final String ruta;
        private final String titulo;

        Vista(String ruta, String titulo) { this.ruta = ruta; this.titulo = titulo; }
    }

    private final Stage stage;
    private final Map<Vista, Supplier<Object>> fabricas = new EnumMap<>(Vista.class);

    public SceneManager(Stage stage) { this.stage = stage; }

    public void registrar(Vista vista, Supplier<Object> fabricaControlador) {
        fabricas.put(vista, fabricaControlador);
    }

    public void mostrar(Vista vista) {
        Supplier<Object> fabrica = fabricas.get(vista);
        if (fabrica == null) {
            throw new IllegalStateException("No hay controlador registrado para " + vista);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista.ruta));
            loader.setController(fabrica.get());
            Parent raiz = loader.load();
            stage.setScene(new Scene(raiz));
            stage.setTitle(vista.titulo);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la vista " + vista, e);
        }
    }
}

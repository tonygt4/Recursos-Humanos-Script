package com.kina.reclutamiento.controller;

import com.kina.reclutamiento.dto.AspiranteDTO;
import com.kina.reclutamiento.exception.AppException;
import com.kina.reclutamiento.exception.ValidationException;
import com.kina.reclutamiento.service.AspiranteService;
import com.kina.reclutamiento.util.AlertHelper;
import com.kina.reclutamiento.util.SceneManager;
import com.kina.reclutamiento.util.SceneManager.Vista;
import com.kina.reclutamiento.util.SesionUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventarioController {
    private final AspiranteService aspiranteService;
    private final SesionUsuario sesion;
    private final SceneManager sceneManager;

    @FXML private Label lblUsuario;
    @FXML private TableView<AspiranteDTO> tablaAspirantes;
    @FXML private TableColumn<AspiranteDTO, Integer> colId;
    @FXML private TableColumn<AspiranteDTO, String> colNombre;
    @FXML private TableColumn<AspiranteDTO, String> colApellido;
    @FXML private TableColumn<AspiranteDTO, String> colCorreo;
    @FXML private TableColumn<AspiranteDTO, String> colTelefono;
    @FXML private TableColumn<AspiranteDTO, String> colDireccion;
    @FXML private TableColumn<AspiranteDTO, Integer> colEdad;
    @FXML private TableColumn<AspiranteDTO, String> colEstado;

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEdad;

    public InventarioController(AspiranteService aspiranteService, SesionUsuario sesion, SceneManager sceneManager) {
        this.aspiranteService = aspiranteService;
        this.sesion = sesion;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tablaAspirantes.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, seleccionado) -> llenarFormulario(seleccionado));

        lblUsuario.setText("Sesión: " + sesion.getUsuarioActual().getNombreUsuario()
                + " (" + sesion.getUsuarioActual().getRol() + ")");
        cargarTabla();
    }

    @FXML
    private void onAgregar() {
        ejecutar(() -> {
            aspiranteService.agregar(leerFormulario(0));
            AlertHelper.info("Aspirante agregado correctamente");
        });
    }

    @FXML
    private void onActualizar() {
        AspiranteDTO seleccionado = tablaAspirantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            AlertHelper.advertencia("Selecciona un aspirante de la tabla para actualizar");
            return;
        }
        ejecutar(() -> {
            aspiranteService.actualizar(leerFormulario(seleccionado.getId()));
            AlertHelper.info("Aspirante actualizado correctamente");
        });
    }

    @FXML
    private void onEliminar() {
        AspiranteDTO seleccionado = tablaAspirantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            AlertHelper.advertencia("Selecciona un aspirante de la tabla para eliminar");
            return;
        }
        if (!AlertHelper.confirmar("¿Eliminar a " + seleccionado.getNombre() + " " + seleccionado.getApellido() + "?")) {
            return;
        }
        ejecutar(() -> {
            aspiranteService.eliminar(seleccionado.getId());
            AlertHelper.info("Aspirante eliminado correctamente");
        });
    }

    @FXML
    private void onLimpiar() {
        tablaAspirantes.getSelectionModel().clearSelection();
        llenarFormulario(null);
    }

    @FXML
    private void onCerrarSesion() {
        sesion.cerrar();
        sceneManager.mostrar(Vista.LOGIN);
    }

        private void ejecutar(Runnable accion) {
        try {
            accion.run();
            cargarTabla();
            onLimpiar();
        } catch (AppException e) {
            AlertHelper.error(e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            ObservableList<AspiranteDTO> datos = FXCollections.observableArrayList(aspiranteService.listar());
            tablaAspirantes.setItems(datos);
        } catch (AppException e) {
            AlertHelper.error(e.getMessage());
        }
    }

    private AspiranteDTO leerFormulario(int id) {
        return new AspiranteDTO(id, txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtDireccion.getText(), leerEdad(), null);
    }

    private int leerEdad() {
        try {
            return Integer.parseInt(txtEdad.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("La edad debe ser un número entero");
        }
    }

    private void llenarFormulario(AspiranteDTO a) {
        txtNombre.setText(a == null ? "" : a.getNombre());
        txtApellido.setText(a == null ? "" : a.getApellido());
        txtCorreo.setText(a == null ? "" : a.getCorreo());
        txtTelefono.setText(a == null ? "" : a.getTelefono());
        txtDireccion.setText(a == null ? "" : a.getDireccion());
        txtEdad.setText(a == null ? "" : String.valueOf(a.getEdad()));
    }
}

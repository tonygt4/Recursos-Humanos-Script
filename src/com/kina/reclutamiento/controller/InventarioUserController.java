package com.kina.reclutamiento.controller;

import com.kina.reclutamiento.dto.AspiranteDTO;
import com.kina.reclutamiento.exception.AppException;
import com.kina.reclutamiento.service.AspiranteService;
import com.kina.reclutamiento.util.AlertHelper;
import com.kina.reclutamiento.util.SesionUsuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventarioUserController {
    private final AspiranteService aspiranteService;
    private final SesionUsuario sesion;

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

    public InventarioUserController(AspiranteService aspiranteService, SesionUsuario sesion,
                                    com.kina.reclutamiento.util.SceneManager sceneManager) {
        this.aspiranteService = aspiranteService;
        this.sesion = sesion;
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
        lblUsuario.setText("Sesión: " + sesion.getUsuarioActual().getNombreUsuario()
                + " (" + sesion.getUsuarioActual().getRol() + ")");
        cargarTabla();
    }

    private void cargarTabla() {
        try {
            tablaAspirantes.setItems(FXCollections.observableArrayList(aspiranteService.listar()));
        } catch (AppException e) {
            AlertHelper.error(e.getMessage());
        }
    }
}

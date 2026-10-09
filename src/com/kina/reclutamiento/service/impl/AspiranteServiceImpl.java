package com.kina.reclutamiento.service.impl;

import com.kina.reclutamiento.dto.AspiranteDTO;
import com.kina.reclutamiento.model.Aspirante;
import com.kina.reclutamiento.repository.AspiranteRepository;
import com.kina.reclutamiento.service.AspiranteService;
import com.kina.reclutamiento.util.Validador;

import java.util.ArrayList;
import java.util.List;

public class AspiranteServiceImpl implements AspiranteService {
    private static final int EDAD_MINIMA = 16;
    private static final int EDAD_MAXIMA = 99;

    private final AspiranteRepository repository;

    public AspiranteServiceImpl(AspiranteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AspiranteDTO> listar() {
        List<AspiranteDTO> resultado = new ArrayList<>();
        for (Aspirante a : repository.listar()) {
            resultado.add(aDto(a));
        }
        return resultado;
    }

    @Override
    public void agregar(AspiranteDTO dto) {
        validar(dto);
        repository.guardar(aEntidad(dto));
    }

    @Override
    public void actualizar(AspiranteDTO dto) {
        validar(dto);
        repository.actualizar(aEntidad(dto));
    }

    @Override
    public void eliminar(int id) {
        repository.eliminar(id);
    }

    private void validar(AspiranteDTO dto) {
        Validador.requerido(dto.getNombre(), "Nombre");
        Validador.requerido(dto.getApellido(), "Apellido");
        Validador.requerido(dto.getCorreo(), "Correo");
        Validador.requerido(dto.getTelefono(), "Teléfono");
        Validador.requerido(dto.getDireccion(), "Dirección");
        Validador.correoValido(dto.getCorreo());
        Validador.telefonoValido(dto.getTelefono());
        Validador.edadEnRango(dto.getEdad(), EDAD_MINIMA, EDAD_MAXIMA);
    }

    private Aspirante aEntidad(AspiranteDTO dto) {
        return new Aspirante(dto.getId(), dto.getNombre().trim(), dto.getApellido().trim(),
                dto.getCorreo().trim(), dto.getTelefono().trim(), dto.getDireccion().trim(),
                dto.getEdad(), dto.getEstado());
    }

    private AspiranteDTO aDto(Aspirante a) {
        return new AspiranteDTO(a.getId(), a.getNombre(), a.getApellido(), a.getCorreo(),
                a.getTelefono(), a.getDireccion(), a.getEdad(), a.getEstado());
    }
}

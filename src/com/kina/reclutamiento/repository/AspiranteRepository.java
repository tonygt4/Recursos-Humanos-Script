package com.kina.reclutamiento.repository;

import com.kina.reclutamiento.model.Aspirante;
import java.util.List;

public interface AspiranteRepository {
    List<Aspirante> listar();
    void guardar(Aspirante aspirante);
    void actualizar(Aspirante aspirante);
    void eliminar(int id);
}

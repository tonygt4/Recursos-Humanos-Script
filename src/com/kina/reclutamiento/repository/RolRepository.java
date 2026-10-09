package com.kina.reclutamiento.repository;

import com.kina.reclutamiento.model.Rol;
import java.util.Optional;

public interface RolRepository {
    Optional<Rol> buscarPorNombre(String nombre);
}

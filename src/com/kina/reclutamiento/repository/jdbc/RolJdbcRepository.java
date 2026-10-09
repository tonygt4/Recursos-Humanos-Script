package com.kina.reclutamiento.repository.jdbc;

import com.kina.reclutamiento.config.DatabaseConnection;
import com.kina.reclutamiento.exception.DataAccessException;
import com.kina.reclutamiento.model.Rol;
import com.kina.reclutamiento.repository.RolRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RolJdbcRepository implements RolRepository {
    private static final String SELECT_POR_NOMBRE = "SELECT id_rol, nombre FROM rol WHERE nombre = ?";
    private final DatabaseConnection db;

    public RolJdbcRepository(DatabaseConnection db) { this.db = db; }

    @Override
    public Optional<Rol> buscarPorNombre(String nombre) {
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_POR_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(new Rol(rs.getInt("id_rol"), rs.getString("nombre")))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al consultar el rol", e);
        }
    }
}

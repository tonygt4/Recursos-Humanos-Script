package com.kina.reclutamiento.repository.jdbc;

import com.kina.reclutamiento.config.DatabaseConnection;
import com.kina.reclutamiento.exception.AspiranteNotFoundException;
import com.kina.reclutamiento.exception.DataAccessException;
import com.kina.reclutamiento.exception.DuplicateRecordException;
import com.kina.reclutamiento.model.Aspirante;
import com.kina.reclutamiento.repository.AspiranteRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class AspiranteJdbcRepository implements AspiranteRepository {
    private static final String SELECT_TODOS =
            "SELECT a.id_aspirante, a.nombre, a.apellido, a.correo, a.telefono, a.direccion, a.edad, e.nombre AS estado "
          + "FROM aspirante a JOIN estado_aspirante e ON e.id_estado = a.id_estado ORDER BY a.id_aspirante";
    private static final String INSERT =
            "INSERT INTO aspirante (nombre, apellido, correo, telefono, direccion, edad) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE aspirante SET nombre = ?, apellido = ?, correo = ?, telefono = ?, direccion = ?, edad = ? "
          + "WHERE id_aspirante = ?";
    private static final String DELETE = "DELETE FROM aspirante WHERE id_aspirante = ?";

    private final DatabaseConnection db;

    public AspiranteJdbcRepository(DatabaseConnection db) { this.db = db; }

    @Override
    public List<Aspirante> listar() {
        List<Aspirante> aspirantes = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                aspirantes.add(new Aspirante(rs.getInt("id_aspirante"), rs.getString("nombre"),
                        rs.getString("apellido"), rs.getString("correo"), rs.getString("telefono"),
                        rs.getString("direccion"), rs.getInt("edad"), rs.getString("estado")));
            }
            return aspirantes;
        } catch (SQLException e) {
            throw new DataAccessException("Error al listar los aspirantes", e);
        }
    }

    @Override
    public void guardar(Aspirante a) {
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getCorreo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getDireccion());
            ps.setInt(6, a.getEdad());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateRecordException("Ya existe un aspirante con el correo " + a.getCorreo());
        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar el aspirante", e);
        }
    }

    @Override
    public void actualizar(Aspirante a) {
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getCorreo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getDireccion());
            ps.setInt(6, a.getEdad());
            ps.setInt(7, a.getId());
            if (ps.executeUpdate() == 0) {
                throw new AspiranteNotFoundException(a.getId());
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateRecordException("Ya existe un aspirante con el correo " + a.getCorreo());
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar el aspirante", e);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new AspiranteNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar el aspirante", e);
        }
    }
}

package com.kina.reclutamiento.repository.jdbc;

import com.kina.reclutamiento.config.DatabaseConnection;
import com.kina.reclutamiento.exception.DataAccessException;
import com.kina.reclutamiento.model.Rol;
import com.kina.reclutamiento.model.Usuario;
import com.kina.reclutamiento.repository.UsuarioRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioJdbcRepository implements UsuarioRepository {
    private static final String SELECT_POR_USUARIO =
            "SELECT u.id_usuario, u.nombre_usuario, u.correo, u.password_hash, r.id_rol, r.nombre AS rol "
          + "FROM usuario u JOIN rol r ON r.id_rol = u.id_rol WHERE u.nombre_usuario = ?";
    private static final String EXISTE =
            "SELECT 1 FROM usuario WHERE nombre_usuario = ? OR correo = ?";
    private static final String INSERT =
            "INSERT INTO usuario (nombre_usuario, correo, password_hash, id_rol) VALUES (?, ?, ?, ?)";

    private final DatabaseConnection db;

    public UsuarioJdbcRepository(DatabaseConnection db) { this.db = db; }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_POR_USUARIO)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("rol"));
                return Optional.of(new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"),
                        rs.getString("correo"), rs.getString("password_hash"), rol));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al consultar el usuario", e);
        }
    }

    @Override
    public boolean existePorNombreUsuarioOCorreo(String nombreUsuario, String correo) {
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(EXISTE)) {
            ps.setString(1, nombreUsuario);
            ps.setString(2, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar el usuario", e);
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPasswordHash());
            ps.setInt(4, usuario.getRol().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al registrar el usuario", e);
        }
    }
}

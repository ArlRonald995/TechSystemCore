package com.techsystem.Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorUsuarios {

    // 1. INICIAR SESIÓN
    public Usuario login(String email, String password) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Extraer datos de la BD
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String rol = rs.getString("rol");
                    String dir = rs.getString("direccion");

                    // FÁBRICA DE OBJETOS: Decidimos qué crear según el rol
                    if ("admin".equalsIgnoreCase(rol)) {
                        return new Administrador(id, nombre, email, password, dir);
                    } else {
                        return new Cliente(id, nombre, email, password, dir);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return null; // Si falla o no existe
    }

    // 2. REGISTRAR CLIENTE NUEVO
    public boolean registrarCliente(String nombre, String email, String password, String direccion) {
        // Por defecto el rol es 'cliente' en la BD, así que no necesitamos enviarlo
        String sql = "INSERT INTO usuarios (nombre, email, password, direccion, rol) VALUES (?, ?, ?, ?, 'cliente')";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, direccion);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }
}
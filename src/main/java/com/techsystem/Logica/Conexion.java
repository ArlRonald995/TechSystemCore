package com.techsystem.Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Configuración de Docker
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "AdminTienda";
    private static final String PASS = "admin123";

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
        return con;
    }

    // --- MAIN DE PRUEBA ---
    public static void main(String[] args) {
        Connection test = conectar();
        if (test != null) {
            System.out.println("✅ ¡CONEXIÓN EXITOSA! Java 25 está hablando con Docker.");
        } else {
            System.out.println("❌ FALLÓ LA CONEXIÓN.");
        }
    }
}
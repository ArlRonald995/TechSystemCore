package com.techsystem.Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorProductos {

    // Método principal que llama tu interfaz
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> inventario = new ArrayList<>();
        // Asegúrate que la tabla en Docker se llame 'productostienda_raw'
        String sql = "SELECT * FROM productostienda_raw";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                try {
                    Producto p = crearProductoDesdeRS(rs);
                    if (p != null) {
                        inventario.add(p);
                    }
                } catch (Exception ex) {
                    System.err.println("Error al procesar un producto (SKU: " + rs.getString("sku") + "): " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fatal de conexión o SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return inventario;
    }

    // --- FÁBRICA DE OBJETOS (El Corazón del Polimorfismo) ---
    private Producto crearProductoDesdeRS(ResultSet rs) throws SQLException {

        // 1. DATOS BASE (Producto)
        String tipo = rs.getString("tipo"); // La columna discriminadora
        if (tipo == null) return null;

        String sku = rs.getString("sku");
        String nombre = rs.getString("nombre");
        String desc = rs.getString("descripcion");
        double precio = rs.getDouble("precio");
        int stock = rs.getInt("stock");
        String marca = rs.getString("marca");
        // Asegúrate que la columna de imagen se llame 'imagen_ruta' en tu BD
        String img = rs.getString("imagen_ruta");

        // 2. DATOS TÉCNICOS COMUNES (Valores por defecto o leídos si existen columnas)
        // Nota: Para que el código corra con tu tabla actual, usaremos valores genéricos
        // para los datos que quizás aún no tienes en columnas (como voltaje o sockets).
        double voltaje = 110.0;
        double consumo = 50.0;
        int garantia = 12; // meses

        // 3. SWITCH: DECIDIR QUÉ CLASE INSTANCIAR
        switch (tipo.toLowerCase().trim()) {

            // --- COMPUTADORES Y DISPOSITIVOS MÓVILES ---
            case "laptop":
            case "portatil":
                return new Laptop(sku, nombre, desc, precio, stock, marca, img, tipo,
                        19.5, 65.0, 12, 512, "Intel Core i5", 3, true, true, false, // Elec
                        16, "SSD", // Computador
                        "1920x1080", 15.6, "IPS", 60, // Pantalla
                        true, 50, 2.1); // <--- (Webcam=true, Bateria, Peso)
            case "celular":
            case "smartphone":
            case "movil":
                // Aquí definimos el OS según la marca (lógica simple para el ejemplo)
                String os = marca.equalsIgnoreCase("Apple") ? "iOS" : "Android";

                return new Celular(sku, nombre, desc, precio, stock, marca, img, tipo,
                        5.0, 5.0, 12, 128, "Snapdragon", 2, true, true, true, // Elec + 5G
                        50, os, // <--- Pasamos el OS nuevo
                        "2400x1080", 6.5, "AMOLED", 120); // Pantalla

            case "tablet":
                // Lógica simple para el ejemplo del OS
                String osTablet = marca.equalsIgnoreCase("Apple") ? "iPadOS" : "Android";
                return new Tablet(sku, nombre, desc, precio, stock, marca, img, tipo,
                        5.0, 10.0, 12, 64, "M2", 2, true, true, false, // false en 5G = Solo WiFi
                        "2000x1200", 10.9, "LCD", 60,
                        true, osTablet); // Stylus, OS

            case "smartwatch":
            case "reloj":
                return new SmartWatch(sku, nombre, desc, precio, stock, marca, img, tipo,
                        5.0, 1.0, 12, 4, "ARM Cortex", 1, true, true, false, // Elec
                        "400x400", 1.4, "OLED", 60, // Pantalla
                        "Silicona", true, "Ritmo Cardíaco, Pasos, Sueño"); // <--- Sensores añadidos

            case "escritorio":
            case "pc":
            case "computadora":
                return new Escritorio(sku, nombre, desc, precio, stock, marca, img, tipo,
                        110.0, 450.0, 24, 1024, "Intel Core i7", 4, true, true, false,
                        32, "SSD NVMe",
                        "Torre ATX", false); // Factor, Perifericos incluidos

            // --- PERIFÉRICOS ---
            case "teclado":
                return new Teclado(sku, nombre, desc, precio, stock, marca, img, tipo,
                        5.0, 2.5, 24,   // Elec (Voltaje, Consumo, Garantia)
                        "USB-C", true,  // Periferico
                        "Mecánico Blue", true); // Teclado

            case "raton":
            case "mouse":
                return new Raton(sku, nombre, desc, precio, stock, marca, img, tipo,
                        5.0, 1.0, 24,   // Elec
                        "Wireless 2.4G", true, // Periferico
                        16000, 6);      // Raton

            case "monitor":
            case "pantalla":
                return new Monitor(sku, nombre, desc, precio, stock, marca, img, tipo,
                        110.0, 35.0, 36, // Elec
                        "HDMI 2.1", true, // Periferico
                        "3840x2160", 27.0, "IPS", 144, // Pantalla
                        false, true);    // Monitor

            // --- COMPONENTES INTERNOS ---
            case "procesador":
            case "cpu":
                return new Procesador(sku, nombre, desc, precio, stock, marca, img, tipo,
                        1.2, 125.0, 36, // Elec
                        "LGA 1700",    // Socket
                        "i9-14900K", 5, 24); // Modelo, GHz, Nucleos

            case "ram":
            case "memoria":
                return new MemoriaRam(sku, nombre, desc, precio, stock, marca, img, tipo,
                        1.35, 99,       // Voltaje, Garantia
                        "DIMM",         // Socket
                        16, "DDR5", 5200); // Cap, Tipo, Mhz

            case "almacenamiento":
            case "disco":
                return new Almacenamiento(sku, nombre, desc, precio, stock, marca, img, tipo,
                        3.3, 5.0, 60,   // Elec
                        "M.2",          // Socket
                        "NVMe Gen4", 1, 5000); // Tipo, TB, MBs

            default:
                // Si el tipo no coincide, lo imprimimos en consola para depurar
                System.out.println("⚠️ Tipo de producto desconocido en BD: " + tipo);
                return null;
        }
    }
}
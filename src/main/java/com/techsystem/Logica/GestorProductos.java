package com.techsystem.Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorProductos {

    // ==============================================================
    // 1. MÉTODO PARA FILTRAR POR GRUPOS (CATEGORÍAS DE LA VENTANA)
    // ==============================================================
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        if (categoria == null || categoria.equalsIgnoreCase("TODO")) {
            return obtenerTodosLosProductos();
        }

        List<Producto> listaFiltrada = new ArrayList<>();
        String sql;

        // LÓGICA DE AGRUPACIÓN
        switch (categoria) {
            case "GRUPO_COMPUTADORES":
                sql = "SELECT * FROM productostienda_raw WHERE LOWER(tipo) IN ('portátil', 'laptop', 'escritorio', 'pc', 'computadora')";
                break;
            case "GRUPO_MOVILES":
                sql = "SELECT * FROM productostienda_raw WHERE LOWER(tipo) IN ('celular', 'smartphone', 'movil', 'tablet', 'smartwatch', 'reloj')";
                break;
            case "GRUPO_COMPONENTES":
                sql = "SELECT * FROM productostienda_raw WHERE LOWER(tipo) IN ('memoriaram', 'ram', 'memoria', 'almacenamiento', 'disco', 'ssd', 'hdd', 'procesador', 'cpu')";
                break;
            case "GRUPO_PERIFERICOS":
                sql = "SELECT * FROM productostienda_raw WHERE LOWER(tipo) IN ('monitor', 'pantalla', 'teclado', 'raton', 'mouse', 'periferico')";
                break;
            default:
                sql = "SELECT * FROM productostienda_raw WHERE LOWER(tipo) = LOWER(?)";
                break;
        }

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (!categoria.startsWith("GRUPO_")) {
                ps.setString(1, categoria);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    Producto p = crearProductoDesdeRS(rs);
                    if (p != null) listaFiltrada.add(p);
                } catch (Exception ex) {
                    System.err.println("Error saltado en producto: " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaFiltrada;
    }

    // ==============================================================
    // 2. MÉTODO PRINCIPAL (TRAE TODO)
    // ==============================================================
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> inventario = new ArrayList<>();
        String sql = "SELECT * FROM productostienda_raw";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                try {
                    Producto p = crearProductoDesdeRS(rs);
                    if (p != null) inventario.add(p);
                } catch (Exception ex) {
                    System.err.println("Error procesando SKU " + rs.getString("sku") + ": " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventario;
    }

    // ==============================================================
    // 3. FÁBRICA DE OBJETOS
    // ==============================================================
    private Producto crearProductoDesdeRS(ResultSet rs) throws SQLException {

        // A) DATOS BASE
        String tipo = rs.getString("tipo");
        if (tipo == null) return null;

        String sku = rs.getString("sku");
        String nombre = rs.getString("nombre");
        String desc = rs.getString("descripcion");
        double precio = rs.getDouble("precio");
        int stock = rs.getInt("stock");
        String marca = rs.getString("marca");

        // B) ALGORITMO DE IMAGEN (Sin SQL)
// B) ALGORITMO DE IMAGEN MEJORADO
        String nombreBase = nombre.replace('\u00A0', ' ').trim();

        // 2. Estandarización agresiva
        String nombreLimpio = nombreBase
                .replace(" ", "_")      // Espacios -> Guiones bajos
                .replace("-", "_")      // Guiones medios -> Guiones bajos (Para Intel Core i3-13100 -> i3_13100)
                .replace("\"", "")      // Comillas de pulgadas
                .replace("'", "")
                .replace("/", "_")
                .replace(":", "")
                .replace("(", "")       // Quitamos paréntesis para evitar problemas de "(9na_Gen)" vs "(9na Gen)"
                .replace(")", "");

        // 3. Eliminar guiones bajos duplicados (ej: "Adata__Ddr4" -> "Adata_Ddr4")
        while (nombreLimpio.contains("__")) {
            nombreLimpio = nombreLimpio.replace("__", "_");
        }

        // 4. INTENTO DE BÚSQUEDA INTELIGENTE
        // Primero buscamos el nombre super limpio (ej: iPad_10.2_9na_Gen_1)
        String img = buscarImagenEnRecursos(nombreLimpio);

        // C) SWITCH DE INSTANCIACIÓN (Solo clases Concretas)
        switch (tipo.toLowerCase().trim()) {
            // COMPUTADORES
            case "portátil": case "laptop":
                return new Laptop(sku, nombre, desc, precio, stock, marca, img, tipo, 19.5, 65.0, 12, 512, "Intel i5", 3, true, true, false, 16, "SSD", "FHD", 15.6, "IPS", 60, true, 50, 2.1);

            case "escritorio": case "pc": case "computadora":
                return new Escritorio(sku, nombre, desc, precio, stock, marca, img, tipo, 110.0, 450.0, 24, 1024, "Intel i7", 4, true, true, false, 32, "SSD", "ATX", false);

            // MÓVILES
            case "celular": case "smartphone": case "movil":
                return new Celular(sku, nombre, desc, precio, stock, marca, img, tipo, 5.0, 15.0, 24, 128, "Snapdragon", 2, true, true, true, 80, "Android", "AMOLED", 6.5, "Gorilla", 120);

            case "tablet":
                return new Tablet(sku, nombre, desc, precio, stock, marca, img, tipo, 5.0, 10.0, 12, 64, "M2", 2, true, true, false, "Liquid Retina", 10.9, "IPS", 60, true, "iPadOS");

            case "smartwatch": case "reloj":
                return new SmartWatch(sku, nombre, desc, precio, stock, marca, img, tipo, 5.0, 2.0, 12, 32, "S9 SiP", 1, true, true, false, "OLED", 1.9, "Sapphire", 60, "Fluoroelastómero", true, "Salud Completa");

            // COMPONENTES
            case "memoriaram": case "ram": case "memoria":
                return new MemoriaRam(sku, nombre, desc, precio, stock, marca, img, tipo, 1.35, 99, "DIMM", 16, "DDR5", 5200);

            case "almacenamiento": case "disco": case "ssd": case "hdd":
                return new Almacenamiento(sku, nombre, desc, precio, stock, marca, img, tipo, 3.3, 5.0, 36, "M.2", "NVMe", 1, 3500);

            case "procesador": case "cpu":
                return new Procesador(sku, nombre, desc, precio, stock, marca, img, tipo, 1.2, 65.0, 36, "LGA1700", "i5-12400", 4, 6);

            // PERIFÉRICOS
            case "monitor": case "pantalla":
                return new Monitor(sku, nombre, desc, precio, stock, marca, img, tipo, 110.0, 35.0, 36, "HDMI", true, "FHD", 24.0, "IPS", 75, false, true);

            case "teclado":
                return new Teclado(sku, nombre, desc, precio, stock, marca, img, tipo, 5.0, 2.0, 24, "USB", true, "Mecánico", true);

            case "raton": case "mouse":
                return new Raton(sku, nombre, desc, precio, stock, marca, img, tipo, 5.0, 1.0, 24, "USB", true, 16000, 6);

            default:
                // CORRECCIÓN: Si el tipo no es reconocido o es una clase abstracta (Periferico, Componente),
                // retornamos null para que el gestor simplemente ignore este producto.
                System.out.println("⚠️ Tipo de producto no soportado: " + tipo + " (SKU: " + sku + ")");
                return null;
        }
    }

    // ==============================================================
    // 4. ALGORITMO DE BÚSQUEDA DE IMAGEN
    // ==============================================================
    private String buscarImagenEnRecursos(String nombreBase) {
        String[] extensiones = {".jpeg", ".jpg", ".png", ".JPG", ".PNG"};

        for (String ext : extensiones) {
            String nombreArchivo = nombreBase + ext;
            if (getClass().getResource("/imagenes/" + nombreArchivo) != null) {
                return nombreArchivo; // ¡ENCONTRADO!
            }
        }
        System.out.println("❌ NO ENCONTRADO: Busqué el archivo '" + nombreBase + "' (con varias extensiones) pero no existe.");
        return "Logo1.png";
    }
}
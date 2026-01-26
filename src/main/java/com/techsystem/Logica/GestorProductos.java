package com.techsystem.Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorProductos {

    // ==============================================================
    // MÉTODO PARA FILTRAR POR GRUPOS (CATEGORÍAS DE LA VENTANA)
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
    // MÉTODO PRINCIPAL
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

    // FÁBRICA DE OBJETOS
    // ==============================================================
    private Producto crearProductoDesdeRS(ResultSet rs) throws SQLException {

        // DATOS BASE
        String tipo = rs.getString("tipo");
        if (tipo == null) return null;

        String sku = rs.getString("sku");
        String nombre = rs.getString("nombre");
        String desc = rs.getString("descripcion");
        double precio = rs.getDouble("precio");
        int stock = rs.getInt("stock");
        String marca = rs.getString("marca");

        //ALGORITMO DE IMAGEN (Sin SQL)
        String nombreBase = nombre.replace('\u00A0', ' ').trim();

        //Estandarización agresiva
        String nombreLimpio = nombreBase
                .replace(" ", "_")      // Espacios -> Guiones bajos
                .replace("-", "_")      // Guiones medios -> Guiones bajos
                .replace("\"", "")      // Comillas de pulgadas
                .replace("'", "")
                .replace("/", "_")
                .replace(":", "")
                .replace("(", "")       // Quitamos paréntesis para evitar problemas
                .replace(")", "");

        // Eliminar guiones bajos duplicados
        while (nombreLimpio.contains("__")) {
            nombreLimpio = nombreLimpio.replace("__", "_");
        }

        // INTENTO DE BÚSQUEDA INTELIGENTE
        String img = buscarImagenEnRecursos(nombreLimpio);

        //SWITCH DE INSTANCIACIÓN
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
                //Se retorna null para que el gestor ignore este producto.
                System.out.println("⚠️ Tipo de producto no soportado: " + tipo + " (SKU: " + sku + ")");
                return null;
        }
    }

    // ALGORITMO DE BÚSQUEDA DE IMAGEN
    // ==============================================================
    private String buscarImagenEnRecursos(String nombreBase) {
        String[] extensiones = {".jpeg", ".jpg", ".png", ".JPG", ".PNG"};

        for (String ext : extensiones) {
            String nombreArchivo = nombreBase + ext;
            if (getClass().getResource("/imagenes/" + nombreArchivo) != null) {
                return nombreArchivo; // ¡ENCONTRADO!
            }
        }
        System.out.println("NO ENCONTRADO: Busqué el archivo '" + nombreBase + "' (con varias extensiones) pero no existe.");
        return "Logo1.png";
    }
    public double obtenerPromedioValoracion(String sku) {
        String sql = "SELECT AVG(puntuacion) FROM valoraciones WHERE producto_sku = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sku);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // getDouble devuelve 0.0 si es null
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    public java.util.List<String[]> obtenerResenasDetalladas(String sku) {
        java.util.List<String[]> lista = new java.util.ArrayList<>();

        // Hacemos JOIN con usuarios para saber quién escribió
        String sql = "SELECT u.nombre, v.puntuacion, v.comentario, v.fecha " +
                "FROM valoraciones v " +
                "JOIN usuarios u ON v.usuario_id = u.id " +
                "WHERE v.producto_sku = ? " +
                "ORDER BY v.fecha DESC";

        try (java.sql.Connection con = Conexion.conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sku);
            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] datos = new String[4];
                datos[0] = rs.getString("nombre");      // Quién
                datos[1] = String.valueOf(rs.getInt("puntuacion")); // Cuántas estrellas
                datos[2] = rs.getString("comentario");  // Qué dijo
                datos[3] = rs.getString("fecha");       // Cuándo
                lista.add(datos);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public boolean registrarProducto(Producto p, String jsonEspecificaciones) {
        // CORRECCIÓN: Eliminamos 'ruta_imagen' del SQL porque esa columna NO existe.
        // El sistema calcula la imagen automáticamente por el nombre.
        String sql = "INSERT INTO productostienda_raw (sku, nombre, descripcion, precio, stock, tipo, marca, especificaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (java.sql.Connection con = Conexion.conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getSku());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());

            // Usamos el nombre de la clase para la columna 'tipo'
            ps.setString(6, p.getClass().getSimpleName());

            ps.setString(7, p.getMarca());

            // Ahora el JSON es el parámetro 8
            ps.setString(8, jsonEspecificaciones);

            ps.executeUpdate();
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
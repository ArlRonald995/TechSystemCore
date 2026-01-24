package com.techsystem.Logica;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pedido {
    private int id;
    private int usuarioId;
    private Timestamp fechaCompra;
    private double total;
    private String estadoEnvio;
    private String ubicacionActual;

    private List<DetallePedido> detalles;

    // --- CONSTRUCTOR 1: Para LEER de la Base de Datos (Ya lo tenías) ---
    public Pedido(int id, int usuarioId, Timestamp fechaCompra, double total, String estado, String ubicacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fechaCompra = fechaCompra;
        this.total = total;
        this.estadoEnvio = estado;
        this.ubicacionActual = ubicacion;
        this.detalles = new ArrayList<>();
    }

    // --- CONSTRUCTOR 2: Para CREAR un pedido NUEVO (Desde el Carrito) ---
    public Pedido(int usuarioId, Map<Producto, Integer> carrito, double totalCalculado) {
        this.usuarioId = usuarioId;
        this.total = totalCalculado;
        // La fecha la ponemos automática al momento de crear el objeto
        this.fechaCompra = new Timestamp(System.currentTimeMillis());
        // Estado inicial por defecto
        this.estadoEnvio = "Enviado";
        this.ubicacionActual = "Centro de Distribución";
        this.detalles = new ArrayList<>();

        // Convertimos el Mapa del Carrito a Lista de Detalles
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();

            // Usamos tu clase DetallePedido existente
            this.detalles.add(new DetallePedido(
                    p.getSku(),
                    p.getNombre(),
                    cantidad,
                    p.getPrecio()
            ));
        }
    }

    // --- GETTERS Y SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; } // Necesario para asignar el ID que genera SQL
    public int getUsuarioId() { return usuarioId; }
    public double getTotal() { return total; }
    public String getEstadoEnvio() { return estadoEnvio; }
    public Timestamp getFechaCompra() { return fechaCompra; }
    public String getUbicacionActual() { return ubicacionActual; }

    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }

    // --- MÉTODOS VISUALES (Para la tabla de Mis Pedidos) ---

    // 1. Fecha legible (Ej: "25/01/2026 14:30")
    public String getFechaFormateada() {
        if (fechaCompra == null) return "-";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(fechaCompra);
    }

    // 2. Resumen de productos (Ej: "1x Laptop, 2x Mouse...")
    public String getResumenProductos() {
        if (detalles == null || detalles.isEmpty()) return "Cargando detalles...";

        StringBuilder sb = new StringBuilder();
        for (DetallePedido det : detalles) {
            sb.append(det.getCantidad()).append("x ").append(det.getNombreProducto()).append(", ");
        }
        // Quitamos la última coma
        if (sb.length() > 2) {
            return sb.substring(0, sb.length() - 2);
        }
        return sb.toString();
    }
}
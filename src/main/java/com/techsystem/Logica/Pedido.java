package com.techsystem.Logica;

import java.sql.Timestamp;
import java.util.List;

public class Pedido {
    private int id;
    private int usuarioId;
    private Timestamp fechaCompra;
    private double total;
    private String estadoEnvio; // "En Bodega", "Enviado", "Entregado"
    private String ubicacionActual;

    // Lista de detalles (productos comprados en este pedido)
    private List<DetallePedido> detalles;

    public Pedido(int id, int usuarioId, Timestamp fechaCompra, double total, String estado, String ubicacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fechaCompra = fechaCompra;
        this.total = total;
        this.estadoEnvio = estado;
        this.ubicacionActual = ubicacion;
    }

    // Getters y Setters necesarios
    public int getId() { return id; }
    public double getTotal() { return total; }
    public String getEstadoEnvio() { return estadoEnvio; }
    public Timestamp getFechaCompra() { return fechaCompra; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
    public List<DetallePedido> getDetalles() { return detalles; }
}
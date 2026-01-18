package com.techsystem.Logica;

public class DetallePedido {
    private String skuProducto;
    private String nombreProducto; // Útil guardarlo para mostrarlo fácil en la factura
    private int cantidad;
    private double precioUnitario;

    public DetallePedido(String sku, String nombre, int cantidad, double precio) {
        this.skuProducto = sku;
        this.nombreProducto = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precio;
    }

    public double getSubtotal() { return cantidad * precioUnitario; }
    public String getSkuProducto() { return skuProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
}

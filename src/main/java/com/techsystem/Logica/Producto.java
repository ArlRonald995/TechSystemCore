package com.techsystem.Logica;

public abstract class Producto implements IProductoInventariable {

    protected String sku;
    protected String nombre;
    protected String descripcion;
    protected double precio;
    protected int stock;
    protected String marca;
    protected String rutaImagen;
    protected String clase;

    public Producto() {}

    public Producto(String sku, String nombre, String descripcion, double precio,
                    int stock, String marca, String rutaImagen, String clase) {
        this.sku = sku;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.marca = marca;
        this.rutaImagen = rutaImagen;
        this.clase = clase;
    }

    // Implementación básica de la interfaz
    @Override public String getSku() { return sku; }
    @Override public double getPrecio() { return precio; }
    @Override public int getStock() { return stock; }
    @Override public void incrementarStock(int cantidad) { this.stock += cantidad; }

    @Override
    public void decrementarStock(int cantidad) {
        if(stock >= cantidad) this.stock -= cantidad;
    }

    // Getters extra
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getMarca() { return marca; }
    public String getRutaImagen() { return rutaImagen; }
    public String getClase() { return clase; }

    // Método abstracto
    public abstract String mostrarDetallesEspecificos();
}
package com.techsystem.Logica;

// CORRECCIÓN DEFINITIVA: Hereda de Producto, pero centraliza la lógica eléctrica aquí.
public abstract class Componente extends Producto implements IProductoElectronico {

    // Atributos Componente
    protected String socketCompatibilidad;

    // Atributos de IProductoElectronico (Centralizados aquí)
    protected double voltajeOperacion;
    protected double consumoEnergetico;
    protected int garantiaMeses;

    public Componente() { super(); }

    public Componente(String sku, String nombre, String descripcion, double precio, int stock,
                      String marca, String rutaImagen, String clase,
                      // Datos Eléctricos (IProductoElectronico)
                      double voltaje, double consumo, int garantia,
                      // Datos Componente
                      String socket) {

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase);

        this.voltajeOperacion = voltaje;
        this.consumoEnergetico = consumo;
        this.garantiaMeses = garantia;
        this.socketCompatibilidad = socket;
    }

    // --- Implementación ÚNICA de IProductoElectronico ---
    // Así los hijos (RAM, CPU, Disco) no tienen que volver a escribir esto.
    @Override public double getVoltajeOperacion() { return voltajeOperacion; }
    @Override public double getConsumoEnergetico() { return consumoEnergetico; }
    @Override public int getGarantiaMeses() { return garantiaMeses; }

    @Override
    public void mostrarInformacionElectronica() {
        System.out.println("⚡ Componente Interno: " + voltajeOperacion + "V, " + consumoEnergetico + "W");
    }

    public String getSocketCompatibilidad() {
        return socketCompatibilidad;
    }
}
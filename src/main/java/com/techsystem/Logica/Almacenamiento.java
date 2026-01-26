package com.techsystem.Logica;

public class Almacenamiento extends Componente implements IProductoConAlmacenamiento {

    private String tipoAlmacenamiento; // NVMe, SATA
    private int capacidadTB;
    private int velocidadLecturaMBs;

    public Almacenamiento(String sku, String nombre, String descripcion, double precio, int stock,
                          String marca, String rutaImagen, String clase,
                          // Datos Eléctricos
                          double voltaje, double consumo, int garantia,
                          // Datos Componente
                          String socket, // Interfaz M.2 / SATA
                          // Datos Almacenamiento
                          String tipo, int capTB, int velocidad) {

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, socket);

        this.tipoAlmacenamiento = tipo;
        this.capacidadTB = capTB;
        this.velocidadLecturaMBs = velocidad;
    }

    @Override
    public int getCapacidadAlmacenamientoGB() {
        return capacidadTB * 1024;
    }

    @Override
    public String mostrarDetallesEspecificos() {
        return "DISCO " + tipoAlmacenamiento + "\n" +
                " Capacidad: " + capacidadTB + "TB (" + getCapacidadAlmacenamientoGB() + "GB)\n" +
                " Lectura: " + velocidadLecturaMBs + " MB/s\n" +
                " Interfaz: " + socketCompatibilidad + "\n" +
                "----------------\n" + descripcion;
    }
}
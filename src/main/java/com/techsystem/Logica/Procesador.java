package com.techsystem.Logica;

public class Procesador extends Componente implements IProductoConProcesador {

    private String modeloProcesador;
    private int velocidadGHz;
    private int nucleos;

    public Procesador(String sku, String nombre, String descripcion, double precio, int stock,
                      String marca, String rutaImagen, String clase,
                      // Datos Eléctricos
                      double voltaje, double consumo, int garantia,
                      // Datos Componente
                      String socket,
                      // Datos Procesador
                      String modelo, int velocidad, int nucleos) {

        // Pasamos todo al padre Componente
        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, socket);

        this.modeloProcesador = modelo;
        this.velocidadGHz = velocidad;
        this.nucleos = nucleos;
    }

    // Solo implementamos lo específico del procesador
    @Override public String getModeloProcesador() { return modeloProcesador; }
    @Override public int getVelocidadGHz() { return velocidadGHz; }
    public int getNucleos() { return nucleos; }

    @Override
    public String mostrarDetallesEspecificos() {
        return "🧠 PROCESADOR " + marca + " " + modeloProcesador + "\n" +
                "🔧 Socket: " + socketCompatibilidad + "\n" +
                "⚡ Núcleos: " + nucleos + " @ " + velocidadGHz + "GHz\n" +
                "🔋 Consumo (TDP): " + consumoEnergetico + "W\n" + // Variable heredada de Componente
                "----------------\n" + descripcion;
    }
}
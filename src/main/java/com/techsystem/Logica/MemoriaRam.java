package com.techsystem.Logica;

public class MemoriaRam extends Componente {

    private int capacidadGB;
    private String tipoRAM;
    private int velocidadMHz;

    public MemoriaRam(String sku, String nombre, String descripcion, double precio, int stock,
                      String marca, String rutaImagen, String clase,
                      // Datos Eléctricos
                      double voltaje, int garantia, // Consumo lo fijamos dentro
                      // Datos Componente
                      String socket, // DIMM/SODIMM
                      // Datos RAM
                      int capacidad, String tipo, int velocidad) {

        // Asumimos 5W de consumo base para RAM
        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, 5.0, garantia, socket);

        this.capacidadGB = capacidad;
        this.tipoRAM = tipo;
        this.velocidadMHz = velocidad;
    }

    public int getCapacidadGB() { return capacidadGB; }

    @Override
    public String mostrarDetallesEspecificos() {
        return "💾 MEMORIA RAM " + tipoRAM + "\n" +
                "📦 Capacidad: " + capacidadGB + "GB\n" +
                "🚀 Velocidad: " + velocidadMHz + "MHz\n" +
                "🔧 Formato: " + socketCompatibilidad + "\n" +
                "⚡ Voltaje: " + voltajeOperacion + "V\n" +
                "----------------\n" + descripcion;
    }
}
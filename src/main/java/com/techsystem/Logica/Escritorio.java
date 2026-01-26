package com.techsystem.Logica;

public class Escritorio extends Computador {

    private String factorForma; //Forma de la pc
    private boolean incluyeTecladoMouse;

    public Escritorio() { super(); }

    public Escritorio(String sku, String nombre, String descripcion, double precio, int stock,
                      String marca, String rutaImagen, String clase,
                      // Dispositivo
                      double voltaje, double consumo, int garantia, int almacenamiento,
                      String procesador, int velocidad, boolean wifi, boolean bt, boolean cincoG,
                      // Computador
                      int ram, String tipoDisco,
                      // Propios
                      String factor, boolean perif) {

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, almacenamiento, procesador, velocidad, wifi, bt, cincoG,
                ram, tipoDisco);

        this.factorForma = factor;
        this.incluyeTecladoMouse = perif;
    }
    public String getFactorForma() { return factorForma; }

    @Override
    public String mostrarDetallesEspecificos() {
        return " PC DE ESCRITORIO (" + factorForma + ")\n" +
                " CPU: " + modeloProcesador + " | " + velocidadGHz + "GHz\n" +
                " RAM: " + memoriaRAMGB + "GB | Disco: " + capacidadAlmacenamientoGB + "GB " + tipoAlmacenamiento + "\n" +
                "Extras: " + (incluyeTecladoMouse ? "Incluye Teclado/Mouse" : "Solo Torre") + "\n" +
                "Fuente: " + consumoEnergetico + "W\n" +
                "------------------------------\n" + descripcion;
    }
}
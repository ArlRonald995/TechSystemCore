package com.techsystem.Logica;

public class Raton extends Periferico {

    private int dpiMaximo;
    private int botonesProgramables;

    public Raton(String sku, String nombre, String descripcion, double precio, int stock,
                 String marca, String rutaImagen, String clase,
                 // Datos Eléctricos
                 double voltaje, double consumo, int garantia,
                 // Datos Periférico
                 String conexion, boolean gamer,
                 // Datos Ratón
                 int dpi, int botones) {

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, conexion, gamer);

        this.dpiMaximo = dpi;
        this.botonesProgramables = botones;
    }

    public int getDpiMaximo() { return dpiMaximo; }

    @Override
    public String mostrarDetallesEspecificos() {
        return "🖱️ RATÓN " + marca + "\n" +
                "🎯 Precisión: " + dpiMaximo + " DPI\n" +
                "🔘 Botones: " + botonesProgramables + "\n" +
                "🔌 Conexión: " + tipoConexion + "\n" +
                "----------------\n" + descripcion;
    }
}
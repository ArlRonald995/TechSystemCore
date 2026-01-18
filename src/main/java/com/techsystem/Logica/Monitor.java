package com.techsystem.Logica;

public class Monitor extends Periferico implements IProductoConPantalla {

    private SpecsPantalla pantalla;
    private boolean esCurvo;
    private boolean tieneAltavoces;

    public Monitor(String sku, String nombre, String descripcion, double precio, int stock,
                   String marca, String rutaImagen, String clase,
                   // Datos Eléctricos (Van al Periférico)
                   double voltaje, double consumo, int garantia,
                   // Datos Periférico
                   String tipoConexion, boolean esGamer,
                   // Datos Pantalla (Para composición)
                   String resPantalla, double tamPantalla, String tipoPanel, int hzPantalla,
                   // Datos Monitor
                   boolean esCurvo, boolean altavoces) {

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, tipoConexion, esGamer);

        this.pantalla = new SpecsPantalla(resPantalla, tamPantalla, tipoPanel, hzPantalla);
        this.esCurvo = esCurvo;
        this.tieneAltavoces = altavoces;
    }

    @Override
    public SpecsPantalla getPantalla() { return this.pantalla; }

    @Override
    public String mostrarDetallesEspecificos() {
        return "🖥️ MONITOR " + (esGamer ? "GAMING " : "") + (esCurvo ? "CURVO " : "PLANO ") + marca + "\n" +
                mostrarDetallesPantalla() + "\n" +
                "🔌 Conexión: " + tipoConexion + "\n" +
                "🔊 Altavoces: " + (tieneAltavoces ? "SÍ" : "NO") + "\n" +
                "⚡ Consumo: " + consumoEnergetico + "W\n" + // Dato heredado
                "------------------------------\n" +
                descripcion;
    }
}

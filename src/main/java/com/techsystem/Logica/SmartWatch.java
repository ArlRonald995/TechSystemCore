package com.techsystem.Logica;

public class SmartWatch extends DispositivoElectronico implements IProductoConPantalla {

    // Composición
    private SpecsPantalla pantalla;

    // Atributos Exclusivos
    private String materialCorrea; // Silicona, Cuero, Metal
    private boolean resistenteAgua; // IP68, 5ATM
    private String sensores;        // <--- NUEVO ATRIBUTO (Ej: "HR, GPS, SpO2")

    public SmartWatch() { super(); }

    public SmartWatch(String sku, String nombre, String descripcion, double precio, int stock,
                      String marca, String rutaImagen, String clase,
                      // Datos Dispositivo (Heredados)
                      double voltaje, double consumo, int garantia, int almacenamiento,
                      String procesador, int velocidad, boolean wifi, boolean bt, boolean tieneConectividadMovil, // (Algunos tienen eSIM)
                      // Datos Pantalla
                      String res, double tam, String panel, int hz,
                      // Datos Propios
                      String material, boolean agua, String listaSensores) { // <--- Nuevo parámetro

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, almacenamiento, procesador, velocidad, wifi, bt, tieneConectividadMovil);

        this.pantalla = new SpecsPantalla(res, tam, panel, hz);
        this.materialCorrea = material;
        this.resistenteAgua = agua;
        this.sensores = listaSensores;
    }

    // --- Getter Nuevo ---
    public String getSensores() { return sensores; }

    @Override
    public SpecsPantalla getPantalla() { return pantalla; }

    @Override
    public String mostrarDetallesEspecificos() {
        return "⌚ SMARTWATCH " + marca + "\n" +
                mostrarDetallesPantalla() + "\n" +
                "❤️ Sensores: " + sensores + "\n" + // <--- Se muestra aquí
                "💧 Resistente al agua: " + (resistenteAgua ? "SÍ" : "NO") + "\n" +
                "🔗 Correa: " + materialCorrea + "\n" +
                "📡 Conectividad: " + (tieneBluetooth ? "BT " : "") + (tieneWifi ? "WiFi " : "") + (tiene5G ? "LTE" : "") + "\n" +
                "------------------------------\n" + descripcion;
    }
}
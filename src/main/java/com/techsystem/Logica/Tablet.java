package com.techsystem.Logica;

public class Tablet extends DispositivoElectronico implements IProductoConPantalla {

    // Composición: Pantalla
    private SpecsPantalla pantalla;

    // Atributos Exclusivos
    private boolean soportaStylus;
    private String sistemaOperativo; // <--- AGREGADO

    // (Eliminado 'tieneSimCard' porque usaremos el 'tiene5G' del padre)

    public Tablet() { super(); }

    public Tablet(String sku, String nombre, String descripcion, double precio, int stock,
                  String marca, String rutaImagen, String clase,
                  // Datos Dispositivo (Heredados)
                  double voltaje, double consumo, int garantia, int almacenamiento,
                  String procesador, int velocidad, boolean wifi, boolean bt, boolean tieneConectividadMovil, // Usamos 5G como "Móvil"
                  // Datos Pantalla
                  String res, double tam, String panel, int hz,
                  // Datos Propios Tablet
                  boolean stylus, String os) { // <--- Nuevo parámetro OS

        // Pasamos 'tieneConectividadMovil' al campo 'tiene5G' del padre
        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, almacenamiento, procesador, velocidad, wifi, bt, tieneConectividadMovil);

        this.pantalla = new SpecsPantalla(res, tam, panel, hz);
        this.soportaStylus = stylus;
        this.sistemaOperativo = os;
    }

    // --- Getters Nuevos ---
    public boolean isSoportaStylus() { // Naming convention: booleanos suelen usar 'is'
        return soportaStylus;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    @Override
    public SpecsPantalla getPantalla() { return pantalla; }

    @Override
    public String mostrarDetallesEspecificos() {
        return "📱 TABLET " + marca + " (" + pantalla.getTamanoPulgadas() + "\")\n" +
                "🤖 OS: " + sistemaOperativo + "\n" +
                mostrarDetallesPantalla() + "\n" +
                "✏️ Stylus: " + (soportaStylus ? "Compatible" : "No soporta") + "\n" +
                "📶 Conectividad: " + (tiene5G ? "WiFi + 4G/5G" : "Solo WiFi") + "\n" + // Usamos la variable heredada
                "💾 " + capacidadAlmacenamientoGB + "GB | CPU: " + modeloProcesador + "\n" +
                "------------------------------\n" + descripcion;
    }
}
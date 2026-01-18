package com.techsystem.Logica;

public class Celular extends DispositivoElectronico implements IProductoConPantalla {

    // Composición: El celular "tiene una" pantalla
    private SpecsPantalla pantalla;

    // Atributos exclusivos de celular (ELIMINADO tiene5G porque ya lo hereda)
    private int camaraPrincipalMP;
    private String sistemaOperativo; // <--- NUEVO ATRIBUTO

    public Celular() { super(); }

    public Celular(String sku, String nombre, String descripcion, double precio, int stock,
                   String marca, String rutaImagen, String clase,
                   // Datos de DispositivoElectronico (Heredados)
                   double voltaje, double consumo, int garantia, int almacenamiento,
                   String procesador, int velocidad, boolean wifi, boolean bt, boolean cincoG,
                   // Datos PROPIOS de Celular
                   int camaraMP, String os, // <--- Recibimos el OS aquí
                   // Datos para crear el objeto Pantalla
                   String resPantalla, double tamPantalla, String tipoPanel, int hzPantalla) {

        // Pasamos 'cincoG' al padre DispositivoElectronico
        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, almacenamiento, procesador, velocidad, wifi, bt, cincoG);

        this.camaraPrincipalMP = camaraMP;
        this.sistemaOperativo = os; // <--- Asignamos el OS

        // Instanciamos la clase auxiliar aquí mismo
        this.pantalla = new SpecsPantalla(resPantalla, tamPantalla, tipoPanel, hzPantalla);
    }

    // --- Getters Propios ---
    public String getSistemaOperativo() { return sistemaOperativo; }
    public int getCamaraPrincipalMP() { return camaraPrincipalMP; }

    // --- Implementación de IProductoConPantalla ---
    @Override
    public SpecsPantalla getPantalla() {
        return this.pantalla;
    }

    @Override
    public String mostrarDetallesEspecificos() {
        return "📱 SMARTPHONE " + marca + " " + nombre + "\n" +
                "🤖 OS: " + sistemaOperativo + "\n" + // Mostramos el OS
                "📸 Cámara: " + camaraPrincipalMP + "MP\n" +
                mostrarDetallesPantalla() + "\n" +
                "📡 5G: " + (tiene5G ? "SÍ" : "NO") + "\n" + // Usamos la variable heredada
                "CPU: " + modeloProcesador + " | Almacenamiento: " + capacidadAlmacenamientoGB + "GB\n" +
                "------------------------------\n" +
                descripcion;
    }
}
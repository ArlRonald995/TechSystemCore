package com.techsystem.Logica;

public class Laptop extends Computador implements IProductoConPantalla {

    // Composición: Pantalla
    private SpecsPantalla pantalla;

    // Atributos Exclusivos
    private boolean tieneWebcam; // <--- AGREGADO (Estaba olvidado)
    private int capacidadBateriaWh;
    private double pesoKg; // (Mantenido por si acaso)

    // (ELIMINADO: tieneTecladoRetroiluminado)

    public Laptop() { super(); }

    public Laptop(String sku, String nombre, String descripcion, double precio, int stock,
                  String marca, String rutaImagen, String clase,
                  // Datos Dispositivo
                  double voltaje, double consumo, int garantia, int almacenamiento,
                  String procesador, int velocidad, boolean wifi, boolean bt, boolean tieneConectividadMovil,
                  // Datos Computador
                  int ram, String tipoDisco,
                  // Datos Pantalla
                  String res, double tam, String panel, int hz,
                  // Datos Propios Laptop
                  boolean webcam, int bateriaWh, double peso) { // <--- Nuevo orden

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, almacenamiento, procesador, velocidad, wifi, bt, tieneConectividadMovil,
                ram, tipoDisco);

        this.pantalla = new SpecsPantalla(res, tam, panel, hz);

        this.tieneWebcam = webcam;
        this.capacidadBateriaWh = bateriaWh;
        this.pesoKg = peso;
    }

    @Override
    public SpecsPantalla getPantalla() {
        return this.pantalla;
    }

    @Override
    public String mostrarDetallesEspecificos() {
        return "💻 LAPTOP " + marca + "\n" +
                mostrarDetallesPantalla() + "\n" +
                "📷 Webcam: " + (tieneWebcam ? "SÍ" : "NO") + "\n" + // <--- Se muestra aquí
                "⚙️ CPU: " + modeloProcesador + " | RAM: " + memoriaRAMGB + "GB\n" +
                "💾 Disco: " + capacidadAlmacenamientoGB + "GB " + tipoAlmacenamiento + "\n" +
                "🔋 Batería: " + capacidadBateriaWh + "Wh | Peso: " + pesoKg + "kg\n" +
                "------------------------------\n" +
                descripcion;
    }
}
package com.techsystem.Logica;

public class Teclado extends Periferico {

    private String tipoMecanismo; // Mecánico, Membrana
    private boolean esRGB;        // Compacto

    public Teclado(String sku, String nombre, String descripcion, double precio, int stock,
                   String marca, String rutaImagen, String clase,
                   // Datos Eléctricos
                   double voltaje, double consumo, int garantia,
                   // Datos Periférico
                   String conexion, boolean gamer,
                   // Datos Teclado
                   String mecanismo, boolean rgb) {

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, conexion, gamer);

        this.tipoMecanismo = mecanismo;
        this.esRGB = rgb;
    }

    @Override
    public String mostrarDetallesEspecificos() {
        return "⌨️ TECLADO " + (esGamer ? "GAMER" : "ESTÁNDAR") + "\n" +
                "🔌 Conexión: " + tipoConexion + "\n" +
                "⚙️ Mecanismo: " + tipoMecanismo + "\n" +
                "📏 Formato: " + (esRGB ? "Es RGB" : "No es RGB") + "\n" +
                "----------------\n" + descripcion;
    }
}
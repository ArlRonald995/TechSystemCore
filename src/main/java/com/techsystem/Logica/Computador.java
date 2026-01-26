package com.techsystem.Logica;

public abstract class Computador extends DispositivoElectronico {

    // Atributos específicos de Computador
    protected int memoriaRAMGB;
    protected String tipoAlmacenamiento; // "SSD", "HDD"

    public Computador() {
        super();
    }

    public Computador(String sku, String nombre, String descripcion, double precio, int stock,
                      String marca, String rutaImagen, String clase,
                      double voltaje, double consumo, int garantia, int almacenamiento,
                      String procesador, int velocidad, boolean wifi, boolean bt, boolean cincoG,
                      int ram, String tipoDisco) {

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase,
                voltaje, consumo, garantia, almacenamiento, procesador, velocidad, wifi, bt, cincoG);
        this.memoriaRAMGB = ram;
        this.tipoAlmacenamiento = tipoDisco;
    }

    public int getMemoriaRAMGB() { return memoriaRAMGB; }
    public String getTipoAlmacenamiento() { return tipoAlmacenamiento; }

    @Override
    public String mostrarDetallesEspecificos() {
        return "COMPUTADOR " + marca + " " + nombre + "\n" +
                "CPU: " + modeloProcesador + " (" + velocidadGHz + "GHz)\n" +
                "RAM: " + memoriaRAMGB + "GB | Disco: " + capacidadAlmacenamientoGB + "GB " + tipoAlmacenamiento + "\n" +
                "Conectividad: " + (tieneWifi ? "WiFi " : "") + (tieneBluetooth ? "BT " : "") + "\n" +
                "----------------\n" + descripcion;
    }
}
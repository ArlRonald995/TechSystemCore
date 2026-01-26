package com.techsystem.Logica;

public abstract class DispositivoElectronico extends Producto
        implements IProductoElectronico, IProductoConAlmacenamiento,
        IProductoConProcesador, IProductoConConectividad {

    // Atributos definidos en tu diseño (Pág. 10)
    protected double voltajeOperacion;
    protected double consumoEnergetico;
    protected int garantiaMeses;
    protected int capacidadAlmacenamientoGB;
    protected String modeloProcesador;
    protected int velocidadGHz;
    protected boolean tieneWifi;
    protected boolean tieneBluetooth;
    protected boolean tiene5G;

    public DispositivoElectronico() {
        super();
    }

    // Constructor gigante para pasar todos los datos al padre y llenar los propios
    public DispositivoElectronico(String sku, String nombre, String descripcion, double precio,
                                  int stock, String marca, String rutaImagen, String clase,
                                  double voltaje, double consumo, int garantia, int almacenamiento,
                                  String procesador, int velocidad, boolean wifi, boolean bt, boolean cincoG) {
        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase);
        this.voltajeOperacion = voltaje;
        this.consumoEnergetico = consumo;
        this.garantiaMeses = garantia;
        this.capacidadAlmacenamientoGB = almacenamiento;
        this.modeloProcesador = procesador;
        this.velocidadGHz = velocidad;
        this.tieneWifi = wifi;
        this.tieneBluetooth = bt;
        this.tiene5G = cincoG;
    }

    // --- Implementación de Interfaces (Getters) ---
    @Override public double getVoltajeOperacion() { return voltajeOperacion; }
    @Override public double getConsumoEnergetico() { return consumoEnergetico; }
    @Override public int getGarantiaMeses() { return garantiaMeses; }
    @Override public int getCapacidadAlmacenamientoGB() { return capacidadAlmacenamientoGB; }
    @Override public String getModeloProcesador() { return modeloProcesador; }
    @Override public int getVelocidadGHz() { return velocidadGHz; }
    @Override public boolean tieneWifi() { return tieneWifi; }
    @Override public boolean tieneBluetooth() { return tieneBluetooth; }
    @Override public boolean tiene5G() { return tiene5G; }

    // Método común para mostrar info técnica (Pág. 10)
    @Override
    public void mostrarInformacionElectronica() {
        System.out.println(" Info Eléctrica: " + voltajeOperacion + "V, " + consumoEnergetico + "W");
        System.out.println(" Garantía: " + garantiaMeses + " meses");
    }
}
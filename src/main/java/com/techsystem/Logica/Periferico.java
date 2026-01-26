package com.techsystem.Logica;

public abstract class Periferico extends Producto implements IProductoElectronico {

    // Atributos Propios de Periférico
    protected String tipoConexion; // "USB", "Bluetooth", "Wireless"
    protected boolean esGamer;

    // Atributos de IProductoElectronico
    protected double voltajeOperacion;
    protected double consumoEnergetico;
    protected int garantiaMeses;

    public Periferico() { super(); }

    public Periferico(String sku, String nombre, String descripcion, double precio, int stock,
                      String marca, String rutaImagen, String clase,
                      // Datos Eléctricos (IProductoElectronico)
                      double voltaje, double consumo, int garantia,
                      // Datos Periférico
                      String conexion, boolean gamer) {

        super(sku, nombre, descripcion, precio, stock, marca, rutaImagen, clase);

        this.voltajeOperacion = voltaje;
        this.consumoEnergetico = consumo;
        this.garantiaMeses = garantia;
        this.tipoConexion = conexion;
        this.esGamer = gamer;
    }
    public String getTipoConexion() { return tipoConexion; }
    //  Implementación IProductoElectronico
    @Override public double getVoltajeOperacion() { return voltajeOperacion; }
    @Override public double getConsumoEnergetico() { return consumoEnergetico; }
    @Override public int getGarantiaMeses() { return garantiaMeses; }

    @Override
    public void mostrarInformacionElectronica() {
        System.out.println("⚡ Periférico: " + voltajeOperacion + "V, " + consumoEnergetico + "W");
    }
}
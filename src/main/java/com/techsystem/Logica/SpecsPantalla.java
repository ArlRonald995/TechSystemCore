package com.techsystem.Logica;

public class SpecsPantalla {
    private String resolucion;       // Ej: "1920x1080", "4K UHD"
    private double tamanoPulgadas;   // Ej: 15.6, 6.7
    private String tipoPanel;        // Ej: "IPS", "OLED", "AMOLED"
    private int tasaRefrescoHz;      // Ej: 60, 144, 120

    public SpecsPantalla() {}

    public SpecsPantalla(String resolucion, double tamanoPulgadas, String tipoPanel, int tasaRefrescoHz) {
        this.resolucion = resolucion;
        this.tamanoPulgadas = tamanoPulgadas;
        this.tipoPanel = tipoPanel;
        this.tasaRefrescoHz = tasaRefrescoHz;
    }

    public String getResolucion() { return resolucion; }
    public double getTamanoPulgadas() { return tamanoPulgadas; }
    public String getTipoPanel() { return tipoPanel; }
    public int getTasaRefrescoHz() { return tasaRefrescoHz; }


    public void setResolucion(String resolucion) { this.resolucion = resolucion; }
    public void setTamanoPulgadas(double tamanoPulgadas) { this.tamanoPulgadas = tamanoPulgadas; }
    public void setTipoPanel(String tipoPanel) { this.tipoPanel = tipoPanel; }
    public void setTasaRefrescoHz(int tasaRefrescoHz) { this.tasaRefrescoHz = tasaRefrescoHz; }

    @Override
    public String toString() {
        return tamanoPulgadas + "\" " + tipoPanel + " (" + resolucion + ") @ " + tasaRefrescoHz + "Hz";
    }
}
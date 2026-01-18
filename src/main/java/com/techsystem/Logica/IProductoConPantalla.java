package com.techsystem.Logica;

public interface IProductoConPantalla {
    // Devuelve el objeto completo con todas las especificaciones
    SpecsPantalla getPantalla();

    // Método auxiliar para mostrar los datos formateados en la UI
    default String mostrarDetallesPantalla() {
        SpecsPantalla p = getPantalla();
        if (p == null) return "Sin especificaciones de pantalla.";
        return "🖥️ PANTALLA: " + p.getTamanoPulgadas() + "\" " + p.getTipoPanel() +
                "\n   Resolución: " + p.getResolucion() +
                "\n   Refresco: " + p.getTasaRefrescoHz() + "Hz";
    }
}
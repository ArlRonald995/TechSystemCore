package com.techsystem.Logica;

public interface IMetodoPago {
    boolean procesarPago(double monto);
    String getNombreMetodo(); // Ej: "Tarjeta Simulado", "PayPal"
}
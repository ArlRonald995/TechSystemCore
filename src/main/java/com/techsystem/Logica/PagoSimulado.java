package com.techsystem.Logica;

public class PagoSimulado implements IMetodoPago {
    @Override
    public boolean procesarPago(double monto) {
        // Aquí podrías poner un Thread.sleep(2000) para simular que "piensa"
        System.out.println("Procesando pago simulado de $" + monto + "...");
        return true; // Siempre aprobado
    }

    @Override
    public String getNombreMetodo() {
        return "Simulación de Crédito";
    }
}
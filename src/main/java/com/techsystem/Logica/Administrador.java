package com.techsystem.Logica;

public class Administrador extends Usuario {

    public Administrador(int id, String nombre, String email, String password, String direccion) {
        super(id, nombre, email, password, "admin", direccion);
    }

    @Override
    public String obtenerVistaInicial() {
        return "VISTA_DASHBOARD_ADMIN";
    }

    // Aquí podrías poner métodos exclusivos como generarReporte(), etc.
}
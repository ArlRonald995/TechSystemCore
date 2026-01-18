package com.techsystem.Logica;

public class Sesion {
    // Variable estática que vive mientras la app esté abierta
    public static Usuario usuarioLogueado = null;

    public static void login(Usuario u) {
        usuarioLogueado = u;
    }

    public static void logout() {
        usuarioLogueado = null;
        // Al hacer null, el "new Carrito()" del Cliente se pierde en el Limbo (Garbage Collector),
        // cumpliendo tu deseo de que se borre al cerrar sesión.
    }
}
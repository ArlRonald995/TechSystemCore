package com.techsystem.Logica;

public class Sesion {
    // Variable estática que vive mientras la app esté abierta
    public static Usuario usuarioLogueado = null;

    public static void login(Usuario u) {
        usuarioLogueado = u;
    }

    public static void logout() {
        usuarioLogueado = null;
    }
}
package com.techsystem.Logica;

public class Cliente extends Usuario {

    // COMPOSICIÓN: Un cliente "tiene un" carrito
    private CarritoCompra carrito;

    public Cliente(int id, String nombre, String email, String password, String direccion) {
        super(id, nombre, email, password, "cliente", direccion);
        this.carrito = new CarritoCompra(); // Se crea vacío al iniciar sesión
    }

    public CarritoCompra getCarrito() {
        return carrito;
    }

    @Override
    public String obtenerVistaInicial() {
        return "VISTA_CATALOGO";
    }
}
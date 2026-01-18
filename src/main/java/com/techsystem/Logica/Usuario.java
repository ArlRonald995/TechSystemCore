package com.techsystem.Logica;

public abstract class Usuario {
    protected int id;
    protected String nombre;
    protected String email;
    protected String password;
    protected String rol;
    protected String direccion; // Nuevo campo

    public Usuario(int id, String nombre, String email, String password, String rol, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.direccion = direccion;
    }

    // Getters necesarios
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
    public String getDireccion() { return direccion; }

    // Método abstracto para definir a dónde va el usuario tras loguearse
    public abstract String obtenerVistaInicial();
}
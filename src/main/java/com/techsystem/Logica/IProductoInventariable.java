package com.techsystem.Logica;

public interface IProductoInventariable {
    String getSku();
    double getPrecio();
    int getStock();

    void decrementarStock(int cantidad);
    void incrementarStock(int cantidad);
}
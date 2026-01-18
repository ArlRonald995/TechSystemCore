package com.techsystem.Logica;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompra {
    private List<ItemCarrito> items;

    public CarritoCompra() {
        this.items = new ArrayList<>();
    }

    public void agregarProducto(Producto p, int cantidad) {
        // Verificar si ya existe para sumar cantidad en vez de duplicar fila
        for (ItemCarrito item : items) {
            if (item.getProducto().getSku().equals(p.getSku())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        // Si no existe, lo agrego
        items.add(new ItemCarrito(p, cantidad));
    }

    public void vaciar() {
        items.clear();
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public List<ItemCarrito> getItems() { return items; }
}
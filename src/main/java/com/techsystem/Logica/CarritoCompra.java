package com.techsystem.Logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoCompra {
    // Usamos una lista de items lógicos (Producto + Cantidad)
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
        // Si no existe, lo agrego como nuevo item
        items.add(new ItemCarrito(p, cantidad));
    }

    // --- EL MÉTODO QUE FALTABA ---
    public void eliminarProducto(Producto p) {
        // Usamos una función lambda para remover el item si el SKU coincide
        items.removeIf(item -> item.getProducto().getSku().equals(p.getSku()));
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

    public List<ItemCarrito> getItems() {
        return items;
    }

    // --- MÉTODO ADAPTADOR PARA LA INTERFAZ GRÁFICA ---
    // La ventana 'CarritoDeCompras' espera un Map<Producto, Integer>.
    // Este método convierte tu lista al formato que necesita la ventana.
    public Map<Producto, Integer> getProductos() {
        Map<Producto, Integer> mapa = new HashMap<>();
        for (ItemCarrito item : items) {
            mapa.put(item.getProducto(), item.getCantidad());
        }
        return mapa;
    }
}
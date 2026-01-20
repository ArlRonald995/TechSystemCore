package Interfaz;

import javax.swing.*;

public class VisualizacionProductos {
    public static void cargarProductos(JPanel panel) {
        panel.removeAll();

        // Limite de productos
        int limite = BaseDeDatos.listaProductos.length;
        if (BaseDeDatos.listaDescripciones.length < limite) limite = BaseDeDatos.listaDescripciones.length;
        if (BaseDeDatos.listaSKU.length < limite) limite = BaseDeDatos.listaSKU.length;

        for (int i = 0; i < limite; i++) {
            try {
                String nombre = BaseDeDatos.listaProductos[i];
                String precio = BaseDeDatos.listaPrecios[i];
                String marca = BaseDeDatos.listaMarcas[i];
                String stock = BaseDeDatos.listaStock[i];
                String nombreImagen = BaseDeDatos.listaImagenes[i];
                String sku = BaseDeDatos.listaSKU[i];
                String descripcion = BaseDeDatos.listaDescripciones[i];

                Icon imagen = Estilos.redimensionarImagen(nombreImagen, 150, 150);

                TarjetaProducto tarjeta = new TarjetaProducto(sku, nombre, marca, precio, stock, descripcion, imagen);

                panel.add(tarjeta.getPanel());

            } catch (Exception e) {
                System.out.println("Error en producto índice " + i + ": " + e.getMessage());
            }
        }
    }
}

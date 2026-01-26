package Interfaz;

import com.techsystem.Logica.Cliente;
import com.techsystem.Logica.GestorPedidos;
import com.techsystem.Logica.Pedido;
import com.techsystem.Logica.Sesion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Pedidos extends JFrame {
    private JPanel panelPedidos;
    private JPanel panelHeader;
    private JLabel txtTienda;
    private JButton seguirComprandoButton;
    private JButton btnActualizar;
    private JPanel panelContent;
    private JButton buttcerrarsesion;

    public Pedidos() {
        this.setContentPane(panelPedidos);
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
        this.setTitle("Mis Pedidos - TechSystem");

        buttcerrarsesion.addActionListener(e -> volverLogin());

        // Configurar Layout y Scroll
        configurarEstructuraVisual();

        // Estilos de botones
        try {
            Estilos.botonesBonitos2(seguirComprandoButton);
            Estilos.botonesBonitos2(buttcerrarsesion);


            // Reutilizamos este botón para la función "Refrescar"
            btnActualizar.setText("↻ Actualizar Estado");
            Estilos.botonesBonitos(btnActualizar);
        } catch (Exception e) {}

        // Cargar datos reales de la BD
        cargarListaPedidos();

        // ACCIONES
        seguirComprandoButton.addActionListener(e -> abrirCatalogo());

        btnActualizar.addActionListener(e -> {
            cargarListaPedidos(); // Vuelve a consultar la BD
        });
    }

    private void volverLogin() {
        InicioDeSesion login = new InicioDeSesion();
        login.setVisible(true);
        this.dispose();
    }

    private void configurarEstructuraVisual() {
        // Aseguramos que el panel principal use BorderLayout
        panelPedidos.setLayout(new BorderLayout());
        if (panelHeader != null) {
            panelPedidos.add(panelHeader, BorderLayout.NORTH);
        }

        // Configuración del panel que tendrá la lista
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
        panelContent.setBackground(Color.WHITE);
        panelContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Configuración del Scroll
        // El Scroll envuelve al panelContent
        JScrollPane scroll = new JScrollPane(panelContent);
        scroll.getVerticalScrollBar().setUnitIncrement(16); // Scroll más rápido
        scroll.setBorder(null);

        panelPedidos.add(scroll, BorderLayout.CENTER);
    }

    private void cargarListaPedidos() {
        // Limpiar lo que había antes
        panelContent.removeAll();

        // Obtener Usuario y Pedidos
        if (Sesion.usuarioLogueado instanceof Cliente) {
            Cliente cliente = (Cliente) Sesion.usuarioLogueado;
            GestorPedidos gestor = new GestorPedidos();

            // CONSULTA A BASE DE DATOS
            List<Pedido> misPedidos = gestor.obtenerPedidosPorUsuario(cliente.getId());

            if (misPedidos.isEmpty()) {
                mostrarMensajeVacio();
            } else {
                // Llenar la lista con tarjetas ItemPedido
                for (Pedido p : misPedidos) {
                    // Creamos la tarjeta pasándole el objeto Pedido
                    ItemPedido tarjeta = new ItemPedido(p);

                    // Ajustamos tamaño máximo para que no se estiren
                    tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
                    tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);

                    panelContent.add(tarjeta);
                    panelContent.add(Box.createVerticalStrut(15)); // Espacio entre items
                }
            }
        }

        // Refrescar visualmente
        panelContent.revalidate();
        panelContent.repaint();
    }

    private void mostrarMensajeVacio() {
        JLabel lbl = new JLabel("No tienes pedidos registrados.");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbl.setForeground(Color.GRAY);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelContent.add(Box.createVerticalStrut(50));
        panelContent.add(lbl);
    }

    private void abrirCatalogo() {
        // Cerrar esta ventana de pedidos
        this.dispose();

        // Abrir una NUEVA instancia del catálogo
        // Asegúrate de que VentanaCatalogo sea visible
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaCatalogo().setVisible(true);
        });
    }

    // Método main para pruebas rápidas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Pedidos().setVisible(true));
    }

}
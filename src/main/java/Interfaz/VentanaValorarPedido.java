package Interfaz;

import com.techsystem.Logica.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaValorarPedido extends JDialog {

    private JPanel panelLista;

    public VentanaValorarPedido(Window parent, Pedido pedido) {
        super(parent, "Selecciona un producto para valorar", ModalityType.APPLICATION_MODAL);
        setSize(500, 500);
        setLocationRelativeTo(parent);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("  Productos en el Pedido #" + pedido.getId());
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        main.add(titulo, BorderLayout.NORTH);

        // Lista Scrollable
        panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(Color.WHITE);
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.setBorder(null);
        main.add(scroll, BorderLayout.CENTER);

        // Cargar Items
        cargarItems(pedido);

        add(main);
    }

    private void cargarItems(Pedido pedido) {
        GestorPedidos gestor = new GestorPedidos();
        int userId = Sesion.usuarioLogueado.getId();

        for (DetallePedido det : pedido.getDetalles()) {
            JPanel panelItem = new JPanel(new BorderLayout(10, 10));
            panelItem.setBackground(Color.WHITE);
            panelItem.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230,230,230)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            panelItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            // Nombre del producto
            JLabel lblNombre = new JLabel(det.getNombreProducto());
            lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));

            // Botón Acción
            JButton btnAccion = new JButton("Calificar");
            btnAccion.setOpaque(true);           // 1. Fuerza a pintar el color
            btnAccion.setBorderPainted(false);   // 2. Quita el borde 3D feo
            btnAccion.setFocusPainted(false);
            // VERIFICAR SI YA CALIFICÓ
            boolean yaCalificado = gestor.yaValoroProducto(userId, det.getSkuProducto());

            if (yaCalificado) {
                btnAccion.setText("✅ Listo");
                btnAccion.setEnabled(false);
                btnAccion.setBackground(new Color(240, 240, 240));
            } else {
                btnAccion.setBackground(new Color(255, 193, 7)); // Amarillo
                btnAccion.setFocusPainted(false);
                btnAccion.addActionListener(e -> {
                    new FormularioResena(this, det).setVisible(true);
                });
            }

            panelItem.add(lblNombre, BorderLayout.CENTER);
            panelItem.add(btnAccion, BorderLayout.EAST);

            panelLista.add(panelItem);
            panelLista.add(Box.createVerticalStrut(10));
        }
    }
}
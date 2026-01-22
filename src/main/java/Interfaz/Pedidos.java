package Interfaz;

import javax.swing.*;
import java.awt.*;

public class Pedidos extends JFrame {
    private JPanel panelPedidos;
    private JPanel panelHeader;
    private JLabel txtTienda;
    private JButton seguirComprandoButton;
    private JButton misPedidosButton;
    private JPanel panelContent;

    Pedidos(){
        this.setContentPane(panelPedidos);
        this.setSize(700, 600); // Ajustar a un tamaño fijo
        this.setLocationRelativeTo(null);
        armarEstucturaPanel();
        Estilos.botonesBonitos(misPedidosButton);
        Estilos.botonesBonitos2(seguirComprandoButton);
        seguirComprandoButton.addActionListener(e -> {
            abrirCatalogo();
        });
    }

    private void abrirCatalogo() {
        VentanaCatalogo catalogo = new VentanaCatalogo();
        catalogo.setVisible(true);
        this.dispose();
    }
    private void armarEstucturaPanel() {
        // Configuración del panel principal (Padre)
        panelPedidos.setLayout(new BorderLayout());
        panelPedidos.add(panelHeader, BorderLayout.NORTH);
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
        panelContent.setBackground(Color.WHITE);
        panelContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        // Llenamos los items EN EL PANEL CONTENT
        for (int i = 0; i < 10; i++) {
            ItemPedido panel = new ItemPedido();
            panelContent.add(panel.getItemPedidoPanel());
            panelContent.add(Box.createVerticalStrut(15));
        }

        // Configuración del Scroll
        JScrollPane scroll = new JScrollPane(panelContent);
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        scroll.setBorder(null);

        panelPedidos.add(scroll, BorderLayout.CENTER);
    }
}

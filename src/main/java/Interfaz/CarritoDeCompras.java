package Interfaz;

import javax.swing.*;
import java.awt.*;

public class CarritoDeCompras extends JFrame{
    private JPanel panelCarrito;
    private JPanel panelHeader;
    private JLabel txtTienda;
    private JButton seguirComprandoButton;
    private JPanel panelContent;
    private JButton carritoDeComprasButton;

    public CarritoDeCompras() {
        // Configuración inicial de la ventana (título)
        super("Tech System Core"); // Es es nombre en la parte superior de la ventana
        this.setContentPane(panelCarrito);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 600); // Ajustar a un tamaño fijo
        this.setLocationRelativeTo(null); // Centrar en pantalla


        armarEstucturaPanel();

        Estilos.botonesBonitos(carritoDeComprasButton);
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
        panelCarrito.setLayout(new BorderLayout());
        panelCarrito.add(panelHeader, BorderLayout.NORTH);
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
        panelContent.setBackground(Color.WHITE);
        panelContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        // Llenamos los items EN EL PANEL CONTENT
        for (int i = 0; i < 10; i++) {
            ItemCarrito panel = new ItemCarrito();
            panelContent.add(panel.getItemPanel());
            panelContent.add(Box.createVerticalStrut(15));
        }

        // Configuración del Scroll
        JScrollPane scroll = new JScrollPane(panelContent);
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        scroll.setBorder(null);

        panelCarrito.add(scroll, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        //Asegura que la ventana se dibuje en el hilo correcto de memoria para evitar errores
        SwingUtilities.invokeLater(() -> {
            CarritoDeCompras frame = new CarritoDeCompras();
            frame.setVisible(true);
        });
    }
}

package Interfaz;

import javax.swing.*;
import java.awt.*;

public class PantallaCarga extends JDialog {

    public PantallaCarga(JFrame parent) {
        super(parent, "Cargando...", true); // 'true' hace que bloquee la ventana de atrás
        setUndecorated(true); // Quita los bordes de ventana para que se vea moderno

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setBackground(Color.WHITE);

        // Mensaje y Gif de carga (si tuvieras) o solo texto
        JLabel lblMensaje = new JLabel("Cargando catálogo, por favor espere...", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMensaje.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Barra de progreso indeterminada (se mueve de lado a lado)
        JProgressBar barra = new JProgressBar();
        barra.setIndeterminate(true);
        barra.setForeground(new Color(0, 102, 204)); // Azul bonito

        panel.add(lblMensaje, BorderLayout.CENTER);
        panel.add(barra, BorderLayout.SOUTH);

        add(panel);
        setSize(300, 100);
        setLocationRelativeTo(parent); // Centrar sobre la ventana padre
    }
}
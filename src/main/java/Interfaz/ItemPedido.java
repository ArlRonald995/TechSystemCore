package Interfaz;

import com.techsystem.Logica.Pedido;
import javax.swing.*;
import java.awt.*;

public class ItemPedido extends JPanel {
    private Pedido pedido;

    // Componentes gráficos
    private JLabel lblId;
    private JLabel lblFecha;
    private JLabel lblTotal;
    private JLabel lblEstado;
    private JLabel lblUbicacion; // Mostrar dónde está el paquete
    private JButton btnValorar;

    public ItemPedido(Pedido pedido) {
        this.pedido = pedido;
        construirDiseño();
        llenarDatos();
    }

    private void construirDiseño() {
        this.setLayout(new BorderLayout(15, 0));
        this.setBackground(Color.WHITE);
        // Borde bonito y margen interno
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)), // Línea divisoria abajo
                BorderFactory.createEmptyBorder(15, 20, 15, 20) // Márgenes
        ));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110)); // Altura fija

        // --- 1. IZQUIERDA: ID y FECHA ---
        JPanel pIzq = new JPanel(new GridLayout(2, 1, 5, 5));
        pIzq.setOpaque(false);
        pIzq.setPreferredSize(new Dimension(150, 0));

        lblId = new JLabel();
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblId.setForeground(new Color(50, 50, 50));

        lblFecha = new JLabel();
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFecha.setForeground(Color.GRAY);

        pIzq.add(lblId);
        pIzq.add(lblFecha);

        // --- 2. CENTRO: ESTADO Y UBICACIÓN ---
        JPanel pCentro = new JPanel(new GridLayout(2, 1, 5, 5));
        pCentro.setOpaque(false);

        lblEstado = new JLabel();
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lblUbicacion = new JLabel();
        lblUbicacion.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblUbicacion.setIcon(UIManager.getIcon("FileView.computerIcon")); // Icono genérico temporal

        pCentro.add(lblEstado);
        pCentro.add(lblUbicacion);

        // --- 3. DERECHA: TOTAL Y BOTÓN ---
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 5)); // Usamos Grid para apilar botones
        panelBotones.setOpaque(false);

        JButton btnDetalles = new JButton("Ver Detalles");
        btnDetalles.setBackground(new Color(230, 230, 230));
        btnDetalles.setFocusPainted(false);
        btnDetalles.addActionListener(e -> {
            // Abrir la ventana nueva pasando el pedido actual
            Window parent = SwingUtilities.getWindowAncestor(this);
            new VentanaDetallesPedido(parent, this.pedido).setVisible(true);
        });

        lblTotal = new JLabel();
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(new Color(0, 100, 0)); // Verde oscuro
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        btnValorar = new JButton("Valorar Compra");
        btnValorar.setFocusPainted(false);
        btnValorar.setBackground(new Color(255, 193, 7)); // Amarillo estrella
        btnValorar.setForeground(Color.BLACK);
        btnValorar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnDetalles);
        panelBotones.add(btnValorar);

        JPanel pDer = new JPanel(new BorderLayout(0, 10));
        pDer.setOpaque(false);
        pDer.add(lblTotal, BorderLayout.NORTH);
        pDer.add(panelBotones, BorderLayout.CENTER); // Agregamos el panel de botones

        add(pDer, BorderLayout.EAST);

        // Acción del botón (Por ahora solo mensaje)
        btnValorar.addActionListener(e -> {
            // AQUÍ ABRIREMOS LA VENTANA DE VALORACIONES MÁS ADELANTE
            JOptionPane.showMessageDialog(this, "¡Pronto podrás dejar tu reseña aquí!");
        });

        pDer.add(lblTotal, BorderLayout.NORTH);
        pDer.add(btnValorar, BorderLayout.SOUTH);

        // Agregar secciones al panel principal
        add(pIzq, BorderLayout.WEST);
        add(pCentro, BorderLayout.CENTER);
        add(pDer, BorderLayout.EAST);
    }

    private void llenarDatos() {
        if (pedido == null) return;

        // Datos básicos
        lblId.setText("Pedido #" + pedido.getId());
        lblFecha.setText(pedido.getFechaFormateada()); // Asegúrate de tener este método en Pedido.java
        lblTotal.setText(String.format("$ %.2f", pedido.getTotal()));
        lblUbicacion.setText("📍 " + pedido.getUbicacionActual()); // Asegúrate de tener getUbicacionActual()

        // Lógica de Estado (Colores)
        String estado = pedido.getEstadoEnvio();
        lblEstado.setText(estado.toUpperCase());

        if (estado.equalsIgnoreCase("Entregado")) {
            lblEstado.setForeground(new Color(34, 139, 34)); // Verde
            btnValorar.setEnabled(true);
            btnValorar.setText("⭐ Valorar");
        } else if (estado.equalsIgnoreCase("Enviado")) {
            lblEstado.setForeground(new Color(255, 140, 0)); // Naranja
            btnValorar.setEnabled(false);
            btnValorar.setText("En camino...");
        } else {
            lblEstado.setForeground(Color.GRAY);
            btnValorar.setEnabled(false);
            btnValorar.setText("Procesando...");
        }
    }
}
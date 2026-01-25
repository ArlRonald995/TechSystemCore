package Interfaz;

import com.techsystem.Logica.Pedido;
import javax.swing.*;
import java.awt.*;

public class ItemPedido extends JPanel {

    private Pedido pedido;

    // Componentes UI
    private JLabel lblId;
    private JLabel lblFecha;
    private JLabel lblTotal;
    private JLabel lblEstado;
    private JLabel lblUbicacion;
    private JButton btnValorar;
    private JButton btnDetalles;

    public ItemPedido(Pedido pedido) {
        this.pedido = pedido;
        construirDiseño();
        llenarDatos();
    }

    private void construirDiseño() {
        this.setLayout(new BorderLayout(15, 0));
        this.setBackground(Color.WHITE);

        // Borde inferior gris suave para separar tarjetas
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // --- 1. IZQUIERDA (ID y Fecha) ---
        JPanel pIzq = new JPanel(new GridLayout(2, 1, 5, 5));
        pIzq.setOpaque(false);
        pIzq.setPreferredSize(new Dimension(140, 0));

        lblId = new JLabel();
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblId.setForeground(new Color(50, 50, 50));

        lblFecha = new JLabel();
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFecha.setForeground(Color.GRAY);

        pIzq.add(lblId);
        pIzq.add(lblFecha);

        // --- 2. CENTRO (Estado y Ubicación) ---
        JPanel pCentro = new JPanel(new GridLayout(2, 1, 5, 5));
        pCentro.setOpaque(false);

        lblEstado = new JLabel();
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lblUbicacion = new JLabel();
        lblUbicacion.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblUbicacion.setForeground(new Color(100, 100, 100));

        pCentro.add(lblEstado);
        pCentro.add(lblUbicacion);

        // --- 3. DERECHA (Total y Botones) ---
        // Usamos un panel para apilar el Precio arriba y los Botones abajo
        JPanel pDer = new JPanel(new BorderLayout(0, 8));
        pDer.setOpaque(false);

        lblTotal = new JLabel();
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(new Color(0, 100, 0)); // Verde dinero
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        // -- Panel de Botones (Detalles + Valorar) --
        JPanel pBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        pBotones.setOpaque(false);

        // A) Botón Ver Detalles
        btnDetalles = new JButton("Ver Detalles");
        estilizarBoton(btnDetalles, new Color(230, 230, 230), Color.BLACK); // Gris claro
        btnDetalles.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            new VentanaDetallesPedido(parent, this.pedido).setVisible(true);
        });

        // B) Botón Valorar
        btnValorar = new JButton("Valorar");
        estilizarBoton(btnValorar, new Color(255, 193, 7), Color.BLACK); // Amarillo
        btnValorar.addActionListener(e -> {
            if (this.pedido.getDetalles() == null || this.pedido.getDetalles().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cargando detalles... Intenta de nuevo.");
                return;
            }
            Window parent = SwingUtilities.getWindowAncestor(this);
            new VentanaValorarPedido(parent, this.pedido).setVisible(true);
        });

        pBotones.add(btnDetalles);
        pBotones.add(btnValorar);

        pDer.add(lblTotal, BorderLayout.NORTH);
        pDer.add(pBotones, BorderLayout.SOUTH);

        // Armar todo
        add(pIzq, BorderLayout.WEST);
        add(pCentro, BorderLayout.CENTER);
        add(pDer, BorderLayout.EAST);
    }

    // --- MÉTODO MÁGICO PARA ARREGLAR LOS BOTONES ---
    private void estilizarBoton(JButton btn, Color fondo, Color texto) {
        btn.setBackground(fondo);
        btn.setForeground(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // ESTAS 3 LÍNEAS QUITAN EL FONDO BLANCO FEO:
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 30));
    }

    private void llenarDatos() {
        if (pedido == null) return;

        // Datos básicos
        lblId.setText("Pedido #" + pedido.getId());
        lblFecha.setText(pedido.getFechaFormateada());
        lblTotal.setText(String.format("$ %.2f", pedido.getTotal()));
        lblUbicacion.setText("📍 " + pedido.getUbicacionActual());

        // Colores según estado
        String estado = pedido.getEstadoEnvio();
        lblEstado.setText(estado.toUpperCase());

        if (estado.equalsIgnoreCase("Entregado")) {
            lblEstado.setForeground(new Color(34, 139, 34)); // Verde
            btnValorar.setEnabled(true);
            btnValorar.setText("⭐ Valorar");
            // Restaurar color amarillo si se habilitó
            btnValorar.setBackground(new Color(255, 193, 7));
        } else if (estado.equalsIgnoreCase("Enviado")) {
            lblEstado.setForeground(new Color(255, 140, 0)); // Naranja
            btnValorar.setEnabled(false);
            btnValorar.setText("En camino");
            btnValorar.setBackground(new Color(240, 240, 240)); // Gris desactivado
        } else {
            lblEstado.setForeground(Color.GRAY);
            btnValorar.setEnabled(false);
            btnValorar.setText("Procesando");
            btnValorar.setBackground(new Color(240, 240, 240));
        }
    }
}
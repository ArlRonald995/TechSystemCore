package Interfaz;

import com.techsystem.Logica.Producto;

import javax.swing.*;
import java.awt.*;

// La clase extiende JPanel para ser agregada al Grid del catálogo
public class TarjetaProducto extends JPanel {

    // --- VARIABLES DEL DISEÑO ---
    private JPanel panelTarjeta;
    private JLabel lblImagen;
    private JButton verDetallesButton;
    private JLabel txtPrecio;
    private JLabel txtNombre;
    private JLabel lblEstrellas;

    // --- VARIABLES DE LÓGICA ---
    // Guardamos el objeto entero para poder enviarlo al carrito después
    private Producto productoCompleto;

    public TarjetaProducto() {
        // 1. Configuración Básica
        setLayout(new BorderLayout());

        // AJUSTE DE TAMAÑO: Ancho 260, Alto 450 para que no se corte el botón
        this.setPreferredSize(new Dimension(260, 450));

        // Agregamos el panel del diseño gráfico al centro
        add(panelTarjeta, BorderLayout.CENTER);

        // 2. Estilos
        try {
            Estilos.hacerBotonRedondo(verDetallesButton);
        } catch (Exception e) {
            // Ignorar si falla
        }

        // 3. Acción del botón
        verDetallesButton.addActionListener(e -> mostrarDetalleProducto());
    }

    /**
     * Este método recibe el producto de la BD y llena la tarjeta visualmente.
     */
    public void configurarProducto(Producto p) {
        // A) Guardamos el objeto completo (VITAL PARA EL CARRITO)
        this.productoCompleto = p;

        // B) Llenamos textos básicos
        if (txtNombre != null) txtNombre.setText(p.getNombre());
        if (txtPrecio != null) txtPrecio.setText(String.format("$ %.2f", p.getPrecio()));

        // ---------------------------------------------------------
        // NUEVO: LÓGICA DE VALORACIONES (ESTRELLAS)
        // ---------------------------------------------------------
        if (lblEstrellas != null) {
            com.techsystem.Logica.GestorProductos gestor = new com.techsystem.Logica.GestorProductos();
            double promedio = gestor.obtenerPromedioValoracion(p.getSku());

            lblEstrellas.setText(convertirAEstrellas(promedio));
            lblEstrellas.setForeground(new java.awt.Color(255, 140, 0));
            lblEstrellas.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 14));
            lblEstrellas.setHorizontalAlignment(SwingConstants.CENTER);

            // >>> LO NUEVO: CAMBIAR EL CURSOR Y AGREGAR EL CLICK <<<
            lblEstrellas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            lblEstrellas.setToolTipText("Clic para leer los comentarios");

            // Limpiamos listeners anteriores para no duplicar acciones al recargar
            for (java.awt.event.MouseListener ml : lblEstrellas.getMouseListeners()) {
                lblEstrellas.removeMouseListener(ml);
            }

            // Agregamos el evento de clic
            lblEstrellas.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Abrimos la ventana de comentarios pasando el producto actual
                    java.awt.Window parent = javax.swing.SwingUtilities.getWindowAncestor(lblEstrellas);
                    new VentanaComentarios(parent, p).setVisible(true);
                }
            });
        }

        // C) ALGORITMO DE IMAGEN "FIT CENTER" (Mejorado)
        String ruta = "/imagenes/" + p.getRutaImagen();

        try {
            // 1. Buscar imagen
            java.net.URL url = getClass().getResource(ruta);
            if (url == null) url = getClass().getResource("/imagenes/Logo1.png");

            if (url != null) {
                ImageIcon iconoOriginal = new ImageIcon(url);
                Image imgOriginal = iconoOriginal.getImage();

                // 2. Definir tamaño máximo (dejando margen)
                int anchoMaximo = 220;
                int altoMaximo = 220;

                int anchoReal = iconoOriginal.getIconWidth();
                int altoReal = iconoOriginal.getIconHeight();

                // 3. Matemática para mantener proporción (Aspect Ratio)
                double proporcionAncho = (double) anchoMaximo / anchoReal;
                double proporcionAlto = (double) altoMaximo / altoReal;

                // Elegimos la menor proporción para que la imagen entre completa
                double proporcionFinal = Math.min(proporcionAncho, proporcionAlto);

                int anchoFinal = (int) (anchoReal * proporcionFinal);
                int altoFinal = (int) (altoReal * proporcionFinal);

                // 4. Escalar con alta calidad
                Image imgEscalada = imgOriginal.getScaledInstance(anchoFinal, altoFinal, Image.SCALE_SMOOTH);

                // 5. Asignar al Label
                if (lblImagen != null) {
                    lblImagen.setIcon(new ImageIcon(imgEscalada));
                    lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
                    lblImagen.setVerticalAlignment(SwingConstants.CENTER);
                    lblImagen.setText(""); // Borrar texto por defecto
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + p.getNombre());
            if (lblImagen != null) lblImagen.setText("Sin Img");
        }
    }

    /**
     * Abre la ventana modal con los detalles.
     */
    private void mostrarDetalleProducto() {
        // Obtenemos la ventana padre (VentanaCatalogo)
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JFrame parentFrame = (parentWindow instanceof JFrame) ? (JFrame) parentWindow : null;

        // Creamos la ventana de detalles
        DetalleProducto miniVentana = new DetalleProducto(parentFrame);

        // LE PASAMOS EL PRODUCTO COMPLETO
        if (this.productoCompleto != null) {
            miniVentana.setProducto(this.productoCompleto);
        }

        miniVentana.setVisible(true);
    }
    private String convertirAEstrellas(double promedio) {
        if (promedio == 0) return "Sin calificaciones";

        StringBuilder sb = new StringBuilder();
        int estrellasLlenas = (int) Math.round(promedio);

        // Dibujamos hasta 5 estrellas
        for (int i = 0; i < 5; i++) {
            if (i < estrellasLlenas) {
                sb.append("⭐"); // Estrella llena
            } else {
                sb.append("☆");  // Estrella vacía (opcional, si quieres que se vea el hueco)
            }
        }
        return sb.toString();
    }
    // --- COMPONENTES PERSONALIZADOS (Bordes Redondos del Panel) ---
    private void createUIComponents() {
        panelTarjeta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Importante para evitar glitches visuales
                Graphics2D g2 = (Graphics2D) g.create();

                // Suavizado de bordes (Antialiasing)
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo Blanco
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                // Borde Gris Claro
                g2.setColor(new Color(220, 220, 220));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

                g2.dispose();
            }
        };
        // Hacemos el panel transparente para que se vea solo lo que dibujamos arriba
        panelTarjeta.setOpaque(false);
    }
}
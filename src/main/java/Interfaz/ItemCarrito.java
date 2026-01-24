package Interfaz;

import com.techsystem.Logica.Producto;

import javax.swing.*;
import java.awt.*;

public class ItemCarrito extends JPanel {

    // Variables lógicas
    private Producto producto;
    private int cantidad;
    private Runnable onCambioCantidad; // Para avisar al carrito que recalcule el total

    // Componentes UI
    private JLabel lblCantidad;
    private JLabel lblSubtotal;

    public ItemCarrito(Producto p, int cantidadInicial, Runnable alCambiar) {
        this.producto = p;
        this.cantidad = cantidadInicial;
        this.onCambioCantidad = alCambiar;

        construirContenido();
    }

    private void construirContenido() {
        // Configuración del Panel Principal (Tarjeta)
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setOpaque(false);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // Altura fija bonita

        // --- 1. IZQUIERDA: IMAGEN ---
        JPanel panelIzq = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIzq.setOpaque(false);

        JLabel lblImg = new JLabel();
        cargarImagen(lblImg, producto.getRutaImagen());
        panelIzq.add(lblImg);

        // --- 2. CENTRO: DATOS ---
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false);

        JLabel lblNombre = new JLabel("<html><b>" + producto.getNombre() + "</b></html>");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblPrecioUnit = new JLabel(String.format("Precio Unitario: $ %.2f", producto.getPrecio()));
        lblPrecioUnit.setForeground(Color.GRAY);

        lblSubtotal = new JLabel(); // Se actualiza solo
        actualizarSubtotalVisual();
        lblSubtotal.setForeground(new Color(0, 102, 204));
        lblSubtotal.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panelCentro.add(lblNombre);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(lblPrecioUnit);
        panelCentro.add(Box.createVerticalStrut(5));
        panelCentro.add(lblSubtotal);

        // --- 3. DERECHA: CONTROLES ---
        JPanel panelDer = new JPanel();
        panelDer.setLayout(new BoxLayout(panelDer, BoxLayout.Y_AXIS));
        panelDer.setOpaque(false);

        // Panel de botones +/-
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBtns.setOpaque(false);

        JButton btnMenos = new JButton("-");
        JButton btnMas = new JButton("+");
        lblCantidad = new JLabel(" " + cantidad + " ");
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Estilos botones pequeños
        estilizarBotonPeque(btnMenos);
        estilizarBotonPeque(btnMas);

        // LÓGICA DE BOTONES
        btnMas.addActionListener(e -> {
            // Verificar stock antes de subir
            if (cantidad < producto.getStock()) {
                cantidad++;
                lblCantidad.setText(" " + cantidad + " ");
                actualizarSubtotalVisual();
                onCambioCantidad.run(); // Avisar al padre
            } else {
                JOptionPane.showMessageDialog(this, "No hay más stock disponible.");
            }
        });

        btnMenos.addActionListener(e -> {
            if (cantidad > 1) {
                cantidad--;
                lblCantidad.setText(" " + cantidad + " ");
                actualizarSubtotalVisual();
                onCambioCantidad.run();
            }
        });

        panelBtns.add(btnMenos);
        panelBtns.add(lblCantidad);
        panelBtns.add(btnMas);

        // Botón Eliminar
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 53, 69)); // Rojo
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // ESTAS 3 LÍNEAS SON EL SECRETO:
        btnEliminar.setFocusPainted(false);  // Quita el recuadro punteado al hacer clic
        btnEliminar.setBorderPainted(false); // Quita el borde 3D/Gris feo
        btnEliminar.setOpaque(true);         // Obliga a pintar el fondo rojo

        // (Opcional) Cursor de mano al pasar por encima
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(e -> {
            // Lógica especial: seteamos cantidad a 0 para que el padre sepa que debe borrarlo
            cantidad = 0;
            onCambioCantidad.run();
        });

        panelDer.add(panelBtns);
        panelDer.add(btnEliminar);

        // --- AGREGAR AL LAYOUT ---
        add(panelIzq, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
        add(panelDer, BorderLayout.EAST);
    }

    private void actualizarSubtotalVisual() {
        double sub = cantidad * producto.getPrecio();
        lblSubtotal.setText(String.format("Subtotal: $ %.2f", sub));
    }

    private void cargarImagen(JLabel lbl, String ruta) {
        // Algoritmo Fit Center Miniatura (80x80)
        try {
            java.net.URL url = getClass().getResource("/imagenes/" + ruta);
            if (url == null) url = getClass().getResource("/imagenes/Logo1.png");
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lbl.setText("Sin img");
        }
    }

    private void estilizarBotonPeque(JButton b) {
        b.setPreferredSize(new Dimension(40, 30));
        b.setFocusPainted(false);
        b.setBackground(Color.WHITE);
        b.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    // Getters para que el padre calcule el total
    public double getSubtotal() {
        return cantidad * producto.getPrecio();
    }

    public int getCantidad() {
        return cantidad;
    }

    public Producto getProducto() {
        return producto;
    }
}
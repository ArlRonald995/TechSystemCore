package Interfaz;

import com.techsystem.Logica.Cliente;
import com.techsystem.Logica.Producto;
import com.techsystem.Logica.Sesion;

import javax.swing.*;
import java.awt.*;

public class DetalleProducto extends JDialog {
    // --- TUS VARIABLES DEL DISEÑO ---
    private JLabel txtNombre;
    private JLabel txtPrecio;
    private JButton agregarAlCarritoButton;
    private JPanel panelDetalles;
    private JPanel panelizquierdo;
    private JPanel panelDerecho;
    private JButton regresarButton;
    private JLabel txtStock;
    private JLabel txtMarca;
    private JLabel icon;
    private JLabel txtSKU;
    private JButton marcaButton;
    private JButton stockButton;
    private JButton descripcionButton;
    private JButton skuButton;
    private JTextArea descripcionTextArea;

    // --- VARIABLE LÓGICA ---
    private Producto productoActual;

    public DetalleProducto(JFrame parent) {
        super(parent, true); // Modal
        setContentPane(panelDetalles);
        getRootPane().setDefaultButton(regresarButton);

        // 1. CORRECCIÓN TAMAÑO: Forzamos un tamaño grande y fijo
        this.setSize(900, 600);
        this.setMinimumSize(new Dimension(800, 500)); // Para que no la puedan hacer miniatura
        this.setLocationRelativeTo(parent); // Centrar sobre la ventana padre

        aplicarEstilos();

        // Acciones
        regresarButton.addActionListener(e -> dispose());
        agregarAlCarritoButton.addActionListener(e -> agregarAlCarrito());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setProducto(Producto p) {
        this.productoActual = p;

        txtNombre.setText(p.getNombre());
        txtSKU.setText(p.getSku());
        txtMarca.setText(p.getMarca());
        txtPrecio.setText(String.format("$ %.2f", p.getPrecio()));
        txtStock.setText(p.getStock() + " unidades");

        // Asignamos descripción
        descripcionTextArea.setText(p.getDescripcion());
        // Movemos el scroll al principio por si el texto es muy largo
        descripcionTextArea.setCaretPosition(0);

        cargarImagenAjustada(p.getRutaImagen());
    }

    private void agregarAlCarrito() {
        if (Sesion.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(this, "Debes iniciar sesión para comprar.");
            return;
        }

        if (Sesion.usuarioLogueado instanceof Cliente) {
            Cliente cliente = (Cliente) Sesion.usuarioLogueado;
            if (productoActual.getStock() > 0) {
                cliente.getCarrito().agregarProducto(productoActual, 1);
                JOptionPane.showMessageDialog(this, "¡Producto agregado al carrito!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Producto agotado.", "Sin Stock", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Los administradores no pueden comprar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarImagenAjustada(String rutaRelativa) {
        try {
            java.net.URL url = getClass().getResource("/imagenes/" + rutaRelativa);
            if (url == null) url = getClass().getResource("/imagenes/Logo1.png");

            if (url != null) {
                ImageIcon original = new ImageIcon(url);
                Image img = original.getImage();

                // Usamos un tamaño grande para el detalle
                int anchoMax = 350;
                int altoMax = 350;

                int w = original.getIconWidth();
                int h = original.getIconHeight();

                double ratio = Math.min((double) anchoMax / w, (double) altoMax / h);
                int anchoFinal = (int) (w * ratio);
                int altoFinal = (int) (h * ratio);

                Image escalada = img.getScaledInstance(anchoFinal, altoFinal, Image.SCALE_SMOOTH);
                icon.setIcon(new ImageIcon(escalada));
                icon.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            icon.setText("Sin img");
        }
    }

    private void aplicarEstilos() {
        try {
            Estilos.hacerBotonRedondo(agregarAlCarritoButton);
            Estilos.hacerBotonRedondo(regresarButton);
            // Los botones decorativos (Labels disfrazados)
            Estilos.hacerBotonRedondo(marcaButton);
            Estilos.hacerBotonRedondo(stockButton);
            Estilos.hacerBotonRedondo(descripcionButton);
            Estilos.hacerBotonRedondo(skuButton);
        } catch (Exception e) {}
    }

    private void createUIComponents() {
        // 2. CORRECCIÓN TEXT AREA
        descripcionTextArea = new JTextArea();

        // A) Ajuste de línea (Párrafo)
        descripcionTextArea.setLineWrap(true);
        descripcionTextArea.setWrapStyleWord(true);

        // B) Solo Lectura (No modificable)
        descripcionTextArea.setEditable(false);
        descripcionTextArea.setFocusable(false); // Para que no se pueda seleccionar ni aparezca el cursor

        // C) Estética: Fondo igual al panel para que parezca transparente (o blanco si prefieres)
        descripcionTextArea.setBackground(new Color(255, 255, 255)); // Blanco limpio
        descripcionTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Letra legible

        // D) ALINEACIÓN VERTICAL (El truco del margen)
        // Como JTextArea no tiene "Vertical Alignment Center", le ponemos un margen superior (Top)
        // grande para empujar el texto hacia el centro visualmente.
        // Insets(Arriba, Izquierda, Abajo, Derecha)
        descripcionTextArea.setMargin(new Insets(25, 10, 10, 10));

        // Borde sutil para ver el área (Opcional, si no te gusta bórralo)
        descripcionTextArea.setBorder(BorderFactory.createLineBorder(new Color(230,230,230)));
    }
}
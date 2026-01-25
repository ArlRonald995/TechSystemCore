package Interfaz;

import com.techsystem.Logica.Cliente;
import com.techsystem.Logica.DetallePedido;
import com.techsystem.Logica.GestorPedidos;
import com.techsystem.Logica.Sesion;

import javax.swing.*;
import java.awt.*;

public class FormularioResena extends JDialog {

    private JComboBox<String> cmbEstrellas;
    private JTextArea txtComentario;
    private JButton btnEnviar;
    private DetallePedido productoAValorar;
    private JDialog ventanaPadre; // Para cerrarla al terminar si queremos

    public FormularioResena(JDialog owner, DetallePedido detalle) {
        super(owner, "Valorar Producto", true);
        this.productoAValorar = detalle;
        this.ventanaPadre = owner;

        setSize(400, 350);
        setLocationRelativeTo(owner);
        setResizable(false);

        construirUI();
    }

    private void construirUI() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- ENCABEZADO ---
        JLabel lblTitulo = new JLabel("<html>Opina sobre: <br/><b>" + productoAValorar.getNombreProducto() + "</b></html>");
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // --- FORMULARIO ---
        JPanel form = new JPanel(new GridLayout(4, 1, 10, 5));
        form.setOpaque(false);

        // Selector de Estrellas (Usamos unicode para que se vea bonito)
        String[] opciones = {"Select...", "⭐ (1) Malo", "⭐⭐ (2) Regular", "⭐⭐⭐ (3) Bueno", "⭐⭐⭐⭐ (4) Muy Bueno", "⭐⭐⭐⭐⭐ (5) Excelente"};
        cmbEstrellas = new JComboBox<>(opciones);
        cmbEstrellas.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14)); // Emoji font si es posible

        // Área de texto
        txtComentario = new JTextArea(5, 20);
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        txtComentario.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollComment = new JScrollPane(txtComentario);

        form.add(new JLabel("Puntuación:"));
        form.add(cmbEstrellas);
        form.add(new JLabel("Tu opinión:"));
        form.add(scrollComment);

        // --- BOTÓN ---
        btnEnviar = new JButton("Publicar Reseña");
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnEnviar.setBackground(new Color(0, 102, 204)); // Azul
        btnEnviar.setForeground(Color.WHITE);            // Texto Blanco

        // --- AGREGAR ESTAS LÍNEAS ---
        btnEnviar.setOpaque(true);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // ----------------------------

        btnEnviar.addActionListener(e -> enviar());

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(btnEnviar, BorderLayout.SOUTH);

        setContentPane(panel);
    }

    private void enviar() {
        int indice = cmbEstrellas.getSelectedIndex();
        if (indice == 0) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una puntuación.");
            return;
        }

        String comentario = txtComentario.getText().trim();
        if (comentario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un breve comentario.");
            return;
        }

        if (Sesion.usuarioLogueado instanceof Cliente) {
            Cliente c = (Cliente) Sesion.usuarioLogueado;
            GestorPedidos gestor = new GestorPedidos();

            // El índice del combo coincide con las estrellas (indice 1 = 1 estrella)
            boolean exito = gestor.enviarValoracion(c.getId(), productoAValorar.getSkuProducto(), indice, comentario);

            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Gracias por tu opinión!");
                dispose();
                ventanaPadre.dispose(); // Cerramos la lista para obligar a recargar (y que se bloquee el botón)
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
package Interfaz;

import com.techsystem.Logica.GestorProductos;
import com.techsystem.Logica.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class VentanaComentarios extends JDialog {

    public VentanaComentarios(Window parent, Producto producto) {
        super(parent, "Opiniones sobre: " + producto.getNombre(), ModalityType.APPLICATION_MODAL);
        setSize(500, 600);
        setLocationRelativeTo(parent);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);



        // TÍTULO
        JLabel lblTitulo = new JLabel("Reseñas de Clientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBorder(new EmptyBorder(15, 20, 10, 20));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        //  LISTA DE COMENTARIOS
        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(Color.WHITE);
        panelLista.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Cargar datos
        cargarComentarios(producto.getSku(), panelLista);

        // Scroll
        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // BOTÓN CERRAR
        JButton btnCerrar = new JButton("Cerrar");
        Estilos.hacerBotonRedondo(btnCerrar);

        btnCerrar.addActionListener(e -> dispose());
        JPanel pSur = new JPanel();
        pSur.setBackground(Color.WHITE);
        pSur.add(btnCerrar);
        panelPrincipal.add(pSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void cargarComentarios(String sku, JPanel contenedor) {
        GestorProductos gestor = new GestorProductos();
        List<String[]> resenas = gestor.obtenerResenasDetalladas(sku);

        if (resenas.isEmpty()) {
            JLabel lbl = new JLabel("Este producto aún no tiene comentarios.");
            lbl.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            lbl.setForeground(Color.GRAY);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            contenedor.add(Box.createVerticalStrut(50));
            contenedor.add(lbl);
            return;
        }

        for (String[] r : resenas) {

            JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
            tarjeta.setBackground(new Color(250, 250, 250));
            tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

            // Cabecera: Nombre + Estrellas + Fecha
            JPanel header = new JPanel(new BorderLayout());
            header.setOpaque(false);

            JLabel lblNombre = new JLabel(r[0]);
            lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));

            String estrellas = convertirPuntosAEstrellas(Integer.parseInt(r[1]));
            JLabel lblStars = new JLabel(estrellas);
            lblStars.setForeground(new Color(255, 140, 0)); // Naranja

            JLabel lblFecha = new JLabel(r[3]);
            lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            lblFecha.setForeground(Color.GRAY);

            JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            izq.setOpaque(false);
            izq.add(lblNombre);
            izq.add(lblStars);

            header.add(izq, BorderLayout.WEST);
            header.add(lblFecha, BorderLayout.EAST);

            //Comentario
            JTextArea txtComentario = new JTextArea(r[2]);
            txtComentario.setLineWrap(true);
            txtComentario.setWrapStyleWord(true);
            txtComentario.setEditable(false);
            txtComentario.setOpaque(false);
            txtComentario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txtComentario.setBorder(new EmptyBorder(5, 5, 0, 0));

            tarjeta.add(header, BorderLayout.NORTH);
            tarjeta.add(txtComentario, BorderLayout.CENTER);

            contenedor.add(tarjeta);
            contenedor.add(Box.createVerticalStrut(10)); // Espacio entre comentarios
        }
    }

    private String convertirPuntosAEstrellas(int puntos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < puntos) sb.append("⭐");
            else sb.append("☆");
        }
        return sb.toString();
    }
}
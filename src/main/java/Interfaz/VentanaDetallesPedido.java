package Interfaz;

import com.techsystem.Logica.DetallePedido;
import com.techsystem.Logica.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaDetallesPedido extends JDialog {

    public VentanaDetallesPedido(Window parent, Pedido pedido) {
        super(parent, "Detalles del Pedido #" + pedido.getId(), ModalityType.APPLICATION_MODAL);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Productos Comprados");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Producto", "Cant.", "Precio Unit.", "Subtotal"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Llenar tabla
        for (DetallePedido det : pedido.getDetalles()) {
            modelo.addRow(new Object[]{
                    det.getNombreProducto(),
                    det.getCantidad(),
                    String.format("$ %.2f", det.getPrecioUnitario()),
                    String.format("$ %.2f", det.getSubtotal())
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setEnabled(false); // Para que no editen las celdas
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);

        // Botón Cerrar
        JButton btnCerrar = new JButton("Cerrar");

        // Estilo Dark/Moderno
        btnCerrar.setBackground(new Color(50, 50, 50));
        btnCerrar.setForeground(Color.WHITE);

        // LAS 3 PROPIEDADES MÁGICAS:
        btnCerrar.setOpaque(true);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);

        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setPreferredSize(new Dimension(100, 35));

        btnCerrar.addActionListener(e -> dispose());

        // Panel inferior para darle un poco de margen y que no quede pegado
        JPanel panelSur = new JPanel();
        panelSur.setBackground(Color.WHITE);
        panelSur.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelSur.add(btnCerrar);

        panel.add(panelSur, BorderLayout.SOUTH);

        add(panel);
    }
}
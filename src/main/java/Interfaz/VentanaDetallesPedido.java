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
        btnCerrar.addActionListener(e -> dispose());
        panel.add(btnCerrar, BorderLayout.SOUTH);

        add(panel);
    }
}
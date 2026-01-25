package Interfaz;

import com.techsystem.Logica.GestorPedidos;
import com.techsystem.Logica.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaAdmin extends JFrame {

    private JPanel panelVentanaAdmin;
    private JPanel panelHeader;
    private JLabel txtTienda;
    private JButton cerrarSesionButton;
    private JButton ADMINbutton; // Botón "Actualizar"
    private JPanel panelContent;
    private JButton agregarProductoButton;

    public VentanaAdmin(){
        super("Administración - TechSystem");

        this.setContentPane(panelVentanaAdmin);
        this.setSize(900, 600); // Un poco más ancha para ver bien los datos
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. Armar estructura
        armarEstructuraPanel();

        // 2. Estilos
        try {
            Estilos.botonesBonitos(ADMINbutton); // Este botón servirá para REFRESCAR la tabla
            ADMINbutton.setText("🔄 Actualizar Tabla");

            Estilos.botonesBonitos2(cerrarSesionButton);
            Estilos.botonesBonitos2(agregarProductoButton);
        } catch (Exception e) {}

        // 3. Cargar datos reales al iniciar
        cargarTablaPedidos();

        // --- LISTENERS ---
        agregarProductoButton.addActionListener(e -> abrirAgregarProducto());

        cerrarSesionButton.addActionListener(e -> {
            InicioDeSesion inicioDeSesion = new InicioDeSesion();
            inicioDeSesion.setVisible(true);
            this.dispose();
        });

        // Botón del header (antes ADMINbutton) ahora refresca la lista
        ADMINbutton.addActionListener(e -> cargarTablaPedidos());
    }

    private void abrirAgregarProducto() {
        AgregarProducto agregarProducto = new AgregarProducto();
        agregarProducto.setVisible(true);
    }

    // Método para limpiar y reconstruir la tabla con datos reales
    private void cargarTablaPedidos() {
        panelContent.removeAll(); // Limpiamos la tabla anterior

        // 1. Definir columnas
        String[] columnas = {"ID", "Fecha", "Cliente ID", "Estado", "Total ($)", "Detalles"};

        // 2. Modelo de tabla no editable
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Que no puedan editar celdas manualmente
            }
        };

        // 3. Obtener datos de la BD
        GestorPedidos gestor = new GestorPedidos();
        List<Pedido> listaPedidos = gestor.obtenerTodosLosPedidos();

        for (Pedido p : listaPedidos) {
            Object[] fila = {
                    p.getId(),
                    p.getFechaFormateada(),
                    p.getUsuarioId(), // Podríamos hacer un JOIN para sacar el nombre, pero el ID sirve por ahora
                    p.getEstadoEnvio(),
                    String.format("%.2f", p.getTotal()),
                    p.getResumenProductos() // Usamos el método que creamos antes
            };
            model.addRow(fila);
        }

        JTable tabla = new JTable(model);
        tabla.setRowHeight(25);

        // Ajustar anchos de columnas específicos
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120); // Fecha
        tabla.getColumnModel().getColumn(2).setPreferredWidth(80);  // Cliente
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100); // Estado
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100); // Total
        tabla.getColumnModel().getColumn(5).setPreferredWidth(300); // Detalles (más ancho)

        // 4. Scroll de la tabla
        JScrollPane scrollTabla = new JScrollPane(tabla);
        panelContent.add(scrollTabla);

        // Refrescar visualmente
        panelContent.revalidate();
        panelContent.repaint();
    }

    private void armarEstructuraPanel() {
        // Layout principal
        panelVentanaAdmin.setLayout(new BorderLayout());
        if (panelHeader != null) {
            panelVentanaAdmin.add(panelHeader, BorderLayout.NORTH);
        }

        // El panelContent será ahora un BorderLayout para que la tabla llene el espacio
        panelContent.setLayout(new BorderLayout());
        panelContent.setBackground(Color.WHITE);

        // Borde interno para que no quede pegado
        panelContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Scroll general (Opcional, si la tabla ya tiene scroll interno a veces no hace falta,
        // pero lo dejamos por si agregas más cosas abajo)
        panelVentanaAdmin.add(panelContent, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaAdmin().setVisible(true));
    }
}
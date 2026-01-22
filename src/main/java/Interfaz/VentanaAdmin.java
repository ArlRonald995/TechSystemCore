package Interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaAdmin extends JFrame {

    private JPanel panelVentanaAdmin;
    private JPanel panelHeader;
    private JLabel txtTienda;
    private JButton cerrarSesionButton;
    private JButton ADMINbutton;
    private JPanel panelContent;
    private JButton agregarProductoButton;

    public VentanaAdmin(){
        super("Administración"); // Título de la ventana

        this.setContentPane(panelVentanaAdmin);
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. Armar la estructura y cargar la tabla
        armarEstucturaPanel();
        Estilos.botonesBonitos(ADMINbutton);
        Estilos.botonesBonitos2(cerrarSesionButton);
        Estilos.botonesBonitos2(agregarProductoButton);

        agregarProductoButton.addActionListener(e -> {

            abrirAgregarProducto();
        });

        cerrarSesionButton.addActionListener(e -> {
            InicioDeSesion inicioDeSesion = new InicioDeSesion();
            inicioDeSesion.setVisible(true);
            this.dispose();
        });
    }

    private void abrirAgregarProducto() {
        AgregarProducto agregarProducto = new AgregarProducto();
        agregarProducto.setVisible(true);
    }

    private void armarEstucturaPanel() {
        // --- Configuración del Layout Principal ---
        // Aseguramos que el panel principal use BorderLayout
        panelVentanaAdmin.setLayout(new BorderLayout());

        // Añadimos el Header al Norte
        panelVentanaAdmin.add(panelHeader, BorderLayout.NORTH);

        // Configuración de panelContent (donde irán los elementos)
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
        panelContent.setBackground(Color.WHITE);

        // --- CREACIÓN DE LA TABLA ---
        String[] columnas = {"ID", "Pedido", "Cliente", "Total", "Fecha"};
        Object[][] datos = {
                {1, "Cel", "Juanito", 4500.00, "12/05/2024"},
                {2, "Laptop", "Maria", 1200.50, "13/05/2024"},
                {3, "Tablet", "Carlos", 300.75, "14/05/2024"},
                {4, "Monitor", "Ana", 250.00, "15/05/2024"},
                {5, "Teclado", "Luis", 75.20, "16/05/2024"},

        };

        // Usamos DefaultTableModel para que sea más fácil editar luego
        DefaultTableModel model = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(model);

        // --- EL SECRETO DE LOS ENCABEZADOS ---
        // Creamos un ScrollPane EXCLUSIVO para la tabla.
        // Esto garantiza que se vean los títulos de las columnas.
        JScrollPane scrollTabla = new JScrollPane(tabla);

        // Opcional: Definir un tamaño preferido para que no ocupe todo o sea muy chica
        scrollTabla.setPreferredSize(new Dimension(600, 200));

        // Añadimos el Scroll de la tabla al panel de contenido
        panelContent.add(scrollTabla);

        // --- SCROLL GENERAL DE LA VENTANA ---
        // Si panelContent crece mucho, necesitamos un scroll externo
        JScrollPane scrollGeneral = new JScrollPane(panelContent);
        scrollGeneral.getVerticalScrollBar().setUnitIncrement(15);
        scrollGeneral.setBorder(null);

        // Añadimos el scroll general al centro de la ventana
        panelVentanaAdmin.add(scrollGeneral, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // AQUI ESTABA EL ERROR:
            // No creamos un 'new JFrame' genérico, instanciamos TU clase.
            VentanaAdmin v = new VentanaAdmin();
            v.setVisible(true);
        });
    }
}

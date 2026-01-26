package Interfaz;

import com.techsystem.Logica.Cliente;
import com.techsystem.Logica.Producto;
import com.techsystem.Logica.Sesion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class CarritoDeCompras extends JFrame {

    private JPanel panelItems; // Aquí van las tarjetas
    private JLabel lblTotalPagar;
    private JButton btnPagar;
    private JButton btnVolver;

    // Lista de componentes gráficos para poder calcular el total
    private java.util.List<ItemCarrito> listaItemsGraficos;

    public CarritoDeCompras() {
        super("Mi Carrito - TechSystem");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listaItemsGraficos = new ArrayList<>();

        // ESTRUCTURA PRINCIPAL
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        //  HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(50, 50, 50)); // Gris oscuro
        JLabel titulo = new JLabel("  Tu Carrito de Compras");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.add(titulo);

        // CUERPO DE LA VENTANA
        panelItems = new JPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS)); // Lista vertical
        panelItems.setBackground(new Color(245, 245, 245)); // Gris muy claro

        JScrollPane scroll = new JScrollPane(panelItems);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(null);

        //BOTON PARA COMPRAR
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(new EmptyBorder(20, 30, 20, 30));
        footer.setBackground(Color.WHITE);

        lblTotalPagar = new JLabel("Total: $ 0.00");
        lblTotalPagar.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVolver = new JButton("Seguir Comprando");
        btnVolver.setPreferredSize(new Dimension(180, 50));
        btnVolver.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Estilo: Fondo Gris Claro, Texto Negro
        btnVolver.setBackground(new Color(230, 230, 230));
        btnVolver.setForeground(Color.BLACK);

        btnVolver.setOpaque(true);
        btnVolver.setBorderPainted(false); // O true si quieres un borde fino
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setPreferredSize(new Dimension(150, 50));

        btnPagar = new JButton("Finalizar Compra");


//  BOTÓN PAGAR (Verde Sólido)
        btnPagar = new JButton("Finalizar Compra");
        btnPagar.setPreferredSize(new Dimension(200, 50));
        btnPagar.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnPagar.setBackground(new Color(86, 117, 221)); // Verde Bosque
        btnPagar.setForeground(Color.WHITE);            // Texto Blanco

        btnPagar.setOpaque(true);
        btnPagar.setBorderPainted(false);
        btnPagar.setFocusPainted(false);
        btnPagar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPagar.setPreferredSize(new Dimension(200, 50));

        panelBotones.add(btnVolver);
        panelBotones.add(btnPagar);

        footer.add(lblTotalPagar, BorderLayout.WEST);
        footer.add(panelBotones, BorderLayout.EAST);

        //  ARMAR
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // LÓGICA
        cargarItemsDelUsuario();

        // Acciones
        btnVolver.addActionListener(e -> {
            new VentanaCatalogo().setVisible(true);
            this.dispose();
        });

        btnPagar.addActionListener(e -> {
            // Validar que el carrito no esté vacío antes de abrir el pago
            Cliente c = (Cliente) Sesion.usuarioLogueado;
            if (c.getCarrito().getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tu carrito está vacío. Agrega productos primero.");
                return;
            }

            // ABRIR LA CLASE PROCESO DE PAGO
            // Pasamos 'this'para cerrarla si la compra sale bien
            ProcesoDePago pago = new ProcesoDePago(this);
            pago.setVisible(true);
        });
    }

    private void cargarItemsDelUsuario() {
        panelItems.removeAll();
        listaItemsGraficos.clear();

        if (Sesion.usuarioLogueado instanceof Cliente) {
            Cliente cliente = (Cliente) Sesion.usuarioLogueado;
            Map<Producto, Integer> mapaProductos = cliente.getCarrito().getProductos();

            if (mapaProductos.isEmpty()) {
                mostrarCarritoVacio();
            } else {
                // Recorremos el mapa y creamos tarjetas
                for (Map.Entry<Producto, Integer> entry : mapaProductos.entrySet()) {
                    Producto prod = entry.getKey();
                    int cant = entry.getValue();

                    // Creamos la tarjeta y le pasamos una "función callback" (lambda)
                    // para que cuando cambie algo, recalculemos el total aquí.
                    ItemCarrito item = new ItemCarrito(prod, cant, () -> recalcularTotal());

                    panelItems.add(item);
                    panelItems.add(Box.createVerticalStrut(10)); // Espacio entre items
                    listaItemsGraficos.add(item);
                }
            }
        }
        recalcularTotal();
        panelItems.revalidate();
        panelItems.repaint();
    }

    private void recalcularTotal() {
        double total = 0;

        // Lista temporal para borrar lo que tenga cantidad 0
        java.util.List<ItemCarrito> paraBorrar = new ArrayList<>();

        for (ItemCarrito item : listaItemsGraficos) {
            if (item.getCantidad() == 0) {
                paraBorrar.add(item);
            } else {
                total += item.getSubtotal();
            }
        }

        // Si hay items eliminados, se saca de la vista y del carrito lógico
        for (ItemCarrito itemBorrado : paraBorrar) {
            panelItems.remove(itemBorrado);
            listaItemsGraficos.remove(itemBorrado);

            // Actualizar el carrito REAL del cliente
            if (Sesion.usuarioLogueado instanceof Cliente) {
                ((Cliente)Sesion.usuarioLogueado).getCarrito().eliminarProducto(itemBorrado.getProducto());
            }
        }

        if (!paraBorrar.isEmpty()) {
            panelItems.revalidate();
            panelItems.repaint();
        }

        // Si quedó vacío
        if (listaItemsGraficos.isEmpty()) {
            mostrarCarritoVacio();
        }

        lblTotalPagar.setText(String.format("Total: $ %.2f", total));
    }

    private void mostrarCarritoVacio() {
        JLabel vacio = new JLabel("Tu carrito está vacío.");
        vacio.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelItems.add(Box.createVerticalStrut(50));
        panelItems.add(vacio);
    }


    // Main para probar visualmente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarritoDeCompras().setVisible(true));
    }
}
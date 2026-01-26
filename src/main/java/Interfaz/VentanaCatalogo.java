package Interfaz;

import com.techsystem.Logica.GestorProductos;
import com.techsystem.Logica.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// La clase extiende JFrame Ventana1 ES la ventana.
public class VentanaCatalogo extends JFrame {

    // Componentes del .form
    private JPanel panelVentana1;
    private JPanel panelHeader;
    private JPanel panelContent;
    private JLabel txtTienda;

    // Botones de Categorías (Filtros)
    private JButton button1; //  "Ver Todo"
    private JButton button2; //  "Laptops"
    private JButton button3; // "Celulares"
    private JButton button4; // "Monitores"
    private JButton button5; //  "Componentes" / "Otros"

    private JButton carritoDeComprasButton;
    private JButton cerrarSesionButton;
    private JButton pedidosButton;

    public VentanaCatalogo() {
        super("Tech System Core");
        configurarVentana();
        estilosBotones();
        armarEstucturaPanel();

        // Listeners...
        cerrarSesionButton.addActionListener(e -> volverLogin());
        carritoDeComprasButton.addActionListener(e -> abrirCarrito());
        configurarBotonesDeFiltro();

        pedidosButton.addActionListener(e -> {
            this.dispose();
            new Pedidos().setVisible(true);
        });

        // PANTALLA BLANCA: Cargar SOLO cuando la ventana esté lista
        // -------------------------------------------------------------
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                // Ahora sí, lanzamos la carga inicial
                iniciarCargaConPantalla("TODO");
            }
        });

    }
    private void iniciarCargaConPantalla(String categoria) {
        // Mostrar pantalla de carga
        PantallaCarga loading = new PantallaCarga(this);

        // Crear un Hilo de trabajo (Worker)
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Barrita de carga
                cargarCatalogoEnSegundoPlano(categoria);
                return null;
            }

            @Override
            protected void done() {
                // ESTO SE EJECUTA CUANDO TERMINA LA CARGA
                loading.dispose(); // Cerrar mensajito de carga
                panelContent.revalidate(); // Refrescar panel
                panelContent.repaint();
            }
        };

        // Arrancar el hilo
        worker.execute();

        // Mostrar el dialog
        loading.setVisible(true);
    }
    private void cargarCatalogoEnSegundoPlano(String categoria) {
        // Limpiar panel
        SwingUtilities.invokeLater(() -> panelContent.removeAll());

        GestorProductos gestor = new GestorProductos();
        List<Producto> listaProductos = gestor.obtenerProductosPorCategoria(categoria);

        if (listaProductos.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                JLabel lblVacio = new JLabel("No hay productos.");
                panelContent.add(lblVacio);
            });
        } else {
            for (Producto p : listaProductos) {
                // Creamos la tarjeta (esto tarda un poco por la imagen)
                TarjetaProducto tarjeta = new TarjetaProducto();
                tarjeta.configurarProducto(p);

                // Agregamos al panel VISUALMENTE
                SwingUtilities.invokeLater(() -> panelContent.add(tarjeta));
                try { Thread.sleep(1); } catch (Exception e) {}
            }
        }
    }

    // MÉTODOS DE LÓGICA DE NEGOCIO (BACKEND -> FRONTEND)

     // Este método borra lo que hay en pantalla y carga los productos desde la BD
    private void cargarCatalogo(String categoria) {
        // Limpiar el panel para que no se acumulen las tarjetas viejas
        panelContent.removeAll();

        // Conectar con el Gestor (Backend)
        GestorProductos gestor = new GestorProductos();
        List<Producto> listaProductos = gestor.obtenerProductosPorCategoria(categoria);

        // Validar si hay resultados
        if (listaProductos.isEmpty()) {
            JLabel lblVacio = new JLabel("No se encontraron productos en esta categoría.");
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setFont(new Font("Arial", Font.BOLD, 18));
            panelContent.add(lblVacio);
        } else {
            // Crear una tarjeta por cada producto encontrado
            for (Producto p : listaProductos) {
                // Instanciamos la clase TarjetaProducto
                TarjetaProducto tarjeta = new TarjetaProducto();

                // Pasamos los datos reales del producto
                tarjeta.configurarProducto(p);

                // La añadimos al grid
                panelContent.add(tarjeta);
            }
        }

        //Refrescar visualmente el panel
        panelContent.revalidate();
        panelContent.repaint();
    }

    private void configurarBotonesDeFiltro() {
        // Configuramos el texto y la acción de cada botón

        button1.setText("Ver Todo");
        button1.addActionListener(e -> iniciarCargaConPantalla("TODO"));

        button2.setText("Computadores"); // Laptops + PC
        button2.addActionListener(e -> iniciarCargaConPantalla("GRUPO_COMPUTADORES"));

        button3.setText("Móviles"); // Celulares + Tablets + Relojes
        button3.addActionListener(e -> iniciarCargaConPantalla("GRUPO_MOVILES"));

        button4.setText("Componentes"); // RAM + Discos + CPU
        button4.addActionListener(e -> iniciarCargaConPantalla("GRUPO_COMPONENTES"));

        button5.setText("Periféricos"); // Monitores + Teclados + Mouse
        button5.addActionListener(e -> iniciarCargaConPantalla("GRUPO_PERIFERICOS"));
    }

    // MÉTODOS DE CONFIGURACIÓN VISUAL
    // ========================================================================

    private void configurarVentana() {
        this.setContentPane(panelVentana1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(1910, 1000); // Tamaño fallback
        this.setLocationRelativeTo(null);
    }

    private void armarEstucturaPanel() {
        // Layout principal
        panelVentana1.setLayout(new BorderLayout());
        panelVentana1.add(panelHeader, BorderLayout.NORTH);

        // Quitamos el panelContent original del diseño automático
        // para meterlo dentro de un ScrollPane manualmente
        panelVentana1.remove(panelContent);

        // Configuración del Scroll
        JScrollPane scroll = new JScrollPane(panelContent);
        scroll.getVerticalScrollBar().setUnitIncrement(20); // Velocidad de scroll
        scroll.setBorder(null);

        // Agregamos el scroll al centro
        panelVentana1.add(scroll, BorderLayout.CENTER);

        // Configuración del Grid de Productos
        panelContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelContent.setLayout(new GridLayout(0, 3, 20, 20));
        panelContent.setBackground(Color.WHITE);
    }

    private void volverLogin() {
        InicioDeSesion login = new InicioDeSesion();
        login.setVisible(true);
        this.dispose();
    }

    private void abrirCarrito(){
        CarritoDeCompras carritoDeCompras = new CarritoDeCompras();
        carritoDeCompras.setVisible(true);
        this.dispose();
    }

    private void estilosBotones() {
        Estilos.botonesBonitos(button1);
        Estilos.botonesBonitos(button2);
        Estilos.botonesBonitos(button3);
        Estilos.botonesBonitos(button4);
        Estilos.botonesBonitos(button5);
        Estilos.botonesBonitos2(carritoDeComprasButton);
        Estilos.botonesBonitos2(cerrarSesionButton);
        Estilos.botonesBonitos2(pedidosButton);

    }

 //MAIN para probar la ventanita
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaCatalogo frame = new VentanaCatalogo();
            frame.setVisible(true);
        });
    }
}
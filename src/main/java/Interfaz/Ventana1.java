package Interfaz;

import javax.swing.*;
import java.awt.*;

// La clase extiende JFrame Ventana1 ES la ventana.
public class Ventana1 extends JFrame {

    // Componentes del .form
    private JPanel panelVentana1;
    private JPanel panelHeader;
    private JPanel panelContent;
    private JLabel txtTienda;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton carritoDeComprasButton;
    private JButton cerrarSesionButton;

    public Ventana1() {
        super("Tech System Core");
        //Interfaz.Configuraciones de la ventana
        configurarVentana();
        //Agupacion de los ajustes para que no haya tanta cosa en el constructor
        estilosBotones();
//Aca se arregla la estructura del panel, se coloca el header arriba, se agrega es scroll y se dividen en cuadritos para poner las tarjetas
        armarEstucturaPanel();
        //Aca se van llenando los datos en las tarjetas
        VisualizacionProductos.cargarProductos(panelContent);

        cerrarSesionButton.addActionListener(e -> {

            volverLogin();
        });

        // Refrescar
        panelVentana1.revalidate();
        panelVentana1.repaint();
    }

    private void configurarVentana() {
        this.setContentPane(panelVentana1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1910, 1790);
        this.setLocationRelativeTo(null);
    }

    private void armarEstucturaPanel() {
        // Para dejar de usar el diseño de IntelliJ y usa BorderLayout
        //Le ordena al panelVentana1 que se comporte usando el esquema Norte, Sur, Este, Oeste y Centro
        panelVentana1.setLayout(new BorderLayout());
        //Toma el panelHeader y lo pega en el Norte (arriba) de la ventana.
        panelVentana1.add(panelHeader, BorderLayout.NORTH);
        // Sacamos el panelContent original del flujo antiguo
        panelVentana1.remove(panelContent);

        // Creamos el Scroll y metemos el contenido
        JScrollPane scroll = new JScrollPane(panelContent);
        scroll.getVerticalScrollBar().setUnitIncrement(15); //Para que el scroll vaya más rápido
        scroll.setBorder(null); //Quita el borde gris de 1 píxel que trae el scroll por defecto

        // Pegamos el Scroll al CENTRO
        panelVentana1.add(scroll, BorderLayout.CENTER);
        panelContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelContent.setLayout(new GridLayout(0, 5, 15, 15));
        panelContent.setBackground(Color.WHITE);

    }

    private void volverLogin() {
        InicioDeSesion login = new InicioDeSesion();
        login.setVisible(true);
        this.dispose();
    }

    private void estilosBotones() {
        // Interfaz.Estilos
        Estilos.botonesBonitos(button1);
        Estilos.botonesBonitos(button2);
        Estilos.botonesBonitos(button3);
        Estilos.botonesBonitos(button4);
        Estilos.botonesBonitos(button5);
        Estilos.botonesBonitos2(carritoDeComprasButton);
        Estilos.botonesBonitos2(cerrarSesionButton);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MAIN<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public static void main(String[] args) {

        //Asegura que la ventana se dibuje en el hilo correcto de memoria para evitar errores
        SwingUtilities.invokeLater(() -> {
            Ventana1 frame = new Ventana1();
            frame.setVisible(true);
        });
    }

}
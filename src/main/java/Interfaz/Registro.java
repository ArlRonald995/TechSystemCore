package Interfaz;

import javax.swing.*;

public class Registro extends JFrame {
    private JPanel panelRegistro;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton registreseButton;
    private JButton cancelarButton;

    public Registro() {
        // Configuración inicial de la ventana (título)
        super("Registrese"); // Es es nombre en la parte superior de la ventana

        // Creacion del Scroll y le metemos el panel del diseño
        JScrollPane scroll = new JScrollPane(panelRegistro);
        scroll.getVerticalScrollBar().setUnitIncrement(15); // Velocidad del scroll

        // Decimos: "El contenido de esta ventana es el scroll (que tiene el panel)"
        this.setContentPane(scroll);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setSize(700, 600); // Ajustar a un tamaño fijo
        this.setLocationRelativeTo(null); // Centrar en pantalla
        this.pack();//Para que el tamaño se ajuste al contenido automáticamente

        configuracionBotones();

        registreseButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Cuenta creada con exito");
            regresarLogin();
        });

        cancelarButton.addActionListener(e -> {
            regresarLogin();
        });

    }

    private void configuracionBotones() {
        Estilos.botonesBonitos2(registreseButton);
        Estilos.botonesBonitos2(cancelarButton);
        Estilos.hacerBotonRedondo(registreseButton);
        Estilos.hacerBotonRedondo(cancelarButton);
    }

    private void regresarLogin() {
        InicioDeSesion login = new InicioDeSesion();
        login.setVisible(true);
        login.setLocationRelativeTo(null);
        this.dispose();

    }

    public static void main(String[] args) {
        //Asegura que la ventana se dibuje en el hilo correcto de memoria para evitar errores
        SwingUtilities.invokeLater(() -> {
            Registro registro = new Registro();
            registro.setVisible(true);
        });
    }}
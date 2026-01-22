package Interfaz;


import javax.swing.*;

public class InicioDeSesion extends JFrame {
    private JPanel panelLogin;
    private JButton registreseButton;
    private JTextField entradaUsuario;
    private JPasswordField entradaContraseña;
    private JButton accederButton;

    public InicioDeSesion() {
        // Configuración inicial de la ventana (título)
        super("Inicie Sesión"); // Es es nombre en la parte superior de la ventana

        this.setContentPane(panelLogin);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setSize(700, 600); // Ajustar a un tamaño fijo
        this.setLocationRelativeTo(null); // Centrar en pantalla
        this.pack();//Para que el tamaño se ajuste al contenido automáticamente

        configuracionBotones();

        registreseButton.addActionListener(e -> {
            redireccionRegistro();
        });

        accederButton.addActionListener(e -> {
            ingresoCatalogo();
        });

    }

    private void configuracionBotones() {
        Estilos.botonesBonitos2(registreseButton);
        Estilos.botonesBonitos2(accederButton);
        Estilos.hacerBotonRedondo(registreseButton);
        Estilos.hacerBotonRedondo(accederButton);

    }

    private void redireccionRegistro() {
        Registro registro = new Registro();
        registro.setVisible(true);
        registro.setLocationRelativeTo(null);
        this.dispose();
    }

    private void ingresoCatalogo() {
        VentanaCatalogo v1 = new VentanaCatalogo();
        v1.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        try {
            // Esto hace que el programa use el diseño del sistema operativo Windows
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Asegura que la ventana se dibuje en el hilo correcto de memoria para evitar errores
        SwingUtilities.invokeLater(() -> {
            InicioDeSesion login = new InicioDeSesion();
            login.setVisible(true);
        });
    }




}

package Interfaz;

import com.techsystem.Logica.GestorUsuarios;
import javax.swing.*;

public class Registro extends JFrame {
    // Componentes del .form
    private JPanel panelRegistro;
    private JTextField txtNombre; // Nombre
    private JTextField txtEmail;// Email
    private JTextField txtContrasena; // Contraseña
    private JTextField txtDireccion;

    private JButton registreseButton;
    private JButton cancelarButton;


    public Registro() {
        super("Registrese");

        // Configuración del Scroll y Panel
        JScrollPane scroll = new JScrollPane(panelRegistro);
        scroll.getVerticalScrollBar().setUnitIncrement(15);
        this.setContentPane(scroll);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); // Ajusta tamaño al contenido
        this.setLocationRelativeTo(null);

        // Aplicar estilos
        configuracionBotones();

        //  ACCIÓN: REGISTRARSE                                 //Tixi deja de poner los comentarios en mayuscula >:(
        registreseButton.addActionListener(e -> {
            procesarRegistro();
        });

        //  ACCIÓN: CANCELAR
        cancelarButton.addActionListener(e -> {
            regresarLogin();
        });
    }

    private void procesarRegistro() {
        // Mapear los textField a variables con sentido
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtContrasena.getText().trim();
        String direccion = txtDireccion.getText().trim();

        // Validaciones básicas
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor llene los campos obligatorios (Nombre, Email, Pass, Dirección).",
                    "Campos Vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Conexión con Base de Datos
        GestorUsuarios gestor = new GestorUsuarios();
        boolean exito = gestor.registrarCliente(nombre, email, password, direccion);

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Cuenta creada con éxito! Bienvenido.");
            regresarLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar. Verifique que el correo no esté ya en uso.",
                    "Error de Registro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configuracionBotones() {
        try {
            Estilos.botonesBonitos2(registreseButton);
            Estilos.botonesBonitos2(cancelarButton);
            Estilos.hacerBotonRedondo(registreseButton);
            Estilos.hacerBotonRedondo(cancelarButton);
        } catch (Exception e) {
            // Ignoramos si la clase Estilos no está disponible o falla
        }
    }

    private void regresarLogin() {
        InicioDeSesion login = new InicioDeSesion();
        login.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Registro registro = new Registro();
            registro.setVisible(true);
        });
    }
}
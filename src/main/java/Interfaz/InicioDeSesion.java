package Interfaz;

import com.techsystem.Logica.Administrador;
import com.techsystem.Logica.GestorUsuarios;
import com.techsystem.Logica.Sesion;
import com.techsystem.Logica.Usuario;

import javax.swing.*;

public class InicioDeSesion extends JFrame {

    private JPanel panelLogin;

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnRegistrarse;
    private JLabel LnombreUsuario;
    private JLabel Lcontrasena;

    // Componentes decorativos
    private JPanel panelHeader;
    private JLabel txtTitulo;


    public InicioDeSesion() {
        super("Inicio de Sesión - TechSystem");

        // Vinculamos el panel correcto
        this.setContentPane(panelLogin);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(480, 400); // Tamaño sugerido
        this.setLocationRelativeTo(null); // Centrar en pantalla


        // ACCIONES DE LOS BOTONES

        // Botón Ingresar
        btnIngresar.addActionListener(e -> {
            validarIngreso();
        });

        // Botón Registrarse
        btnRegistrarse.addActionListener(e -> {
            abrirRegistro();
        });

        configurarBotones_Labels();
    }

    private void validarIngreso() {
        String correo = txtCorreo.getText().trim();
        String pass = new String(txtContrasena.getPassword()).trim();

        // Validar campos vacíos
        if (correo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese correo y contraseña.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Consultar al Backend (Base de Datos)
        GestorUsuarios gestor = new GestorUsuarios();
        Usuario usuarioEncontrado = gestor.login(correo, pass);

        if (usuarioEncontrado != null) {
            // Guardamos la sesión GLOBAL
            Sesion.usuarioLogueado = usuarioEncontrado;

            // Redirigimos según el ROL
            if (usuarioEncontrado instanceof Administrador) {
                // Si es admin, abre su ventana
                new VentanaAdmin().setVisible(true);
            } else {
                // Si es cliente, abre el catálogo
                new VentanaCatalogo().setVisible(true);
            }

            // Cerramos esta ventana de login
            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this,
                    "Credenciales incorrectas o usuario no registrado.",
                    "Error de Acceso",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        Registro ventanaRegistro = new Registro();
        ventanaRegistro.setVisible(true);
        this.dispose(); // Cerramos el login temporalmente
    }

    public void configurarBotones_Labels(){

        Estilos.botonesBonitos2(btnRegistrarse);
        Estilos.hacerBotonRedondo(btnRegistrarse);
        Estilos.botonesBonitos(btnIngresar);
        Estilos.hacerBotonRedondo(btnIngresar);
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
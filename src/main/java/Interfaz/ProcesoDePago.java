package Interfaz;

import com.techsystem.Logica.*; // Importar lógica necesaria

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ProcesoDePago extends JFrame {
    private JPanel panelProcesoDePago;
    private JTextField TFnumeroTarjeta;
    private JTextField TFfechaVen;
    private JTextField TFcvv;
    private JButton finalizarCompraButton;
    private JLabel IconPayPal;
    private JLabel IconAmerican;
    private JLabel IconMastercard;
    private JLabel IconVisa;
    private JLabel LmetodoPago;
    private JTextField TFnombreTarjeta;

    // Referencia al carrito para cerrarlo si la compra es exitosa
    private JFrame ventanaCarrito;

    // Constructor actualizado para recibir la ventana padre
    public ProcesoDePago(JFrame parent) {
        this.ventanaCarrito = parent; // Guardamos referencia del carrito

        this.setContentPane(panelProcesoDePago);
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setTitle("Pasarela de Pago Segura");

        // Configuración visual
        configurarInputs(TFnumeroTarjeta, "Numero de tarjeta");
        configurarInputs(TFfechaVen, "Fecha de vencimiento (Mes/Año)");
        configurarInputs(TFcvv, "CVV");
        configurarInputs(TFnombreTarjeta, "Nombre en la tarjeta");
        configurarIconosPago();


        try {
            Estilos.LabelsBonitos2(LmetodoPago);
        } catch (Exception e) { }

        // LÓGICA DEL BOTÓN FINALIZAR
        finalizarCompraButton.addActionListener(e -> procesarCompra());
    }

    private void procesarCompra() {
        String numTarjeta = TFnumeroTarjeta.getText();
        String fechaVenc = TFfechaVen.getText();
        String cvv = TFcvv.getText();
        String nombre = TFnombreTarjeta.getText();

        // 1. Validar que no sean los textos de ejemplo
        if (esInvalido(TFnumeroTarjeta, "Numero de tarjeta") ||
                esInvalido(TFcvv, "CVV") ||
                esInvalido(TFnombreTarjeta, "Nombre en la tarjeta")) {

            JOptionPane.showMessageDialog(this, "Por favor completa todos los datos bancarios.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;

        }

        //Validar que los datos sean correctos
        //===============================================================

        if(numTarjeta.length()!= 10 ){
            JOptionPane.showMessageDialog(this,
                    "El numero de tarjeta es invalido. " +
                            "Por favor ingrese los 10 digitos que aparecen en la parte posterior de su tarjeta",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (fechaVenc.length()!=5){
            JOptionPane.showMessageDialog(this,
                    "La fecha ingresada es invalida. Por favor guiese en el ejemplo dado",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cvv.length()!=3){
            JOptionPane.showMessageDialog(this,
                    "El codigo de verificacion (cvv) ingresado es incorrecto",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        //==========================================

        //Validar tipos de dato logicos
        //=========================================
        if(!numTarjeta.matches("\\d++") ){
            JOptionPane.showMessageDialog(this,
                    "El numero de tarjeta solo pueden ser numeros",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!cvv.matches("\\d++")){
            JOptionPane.showMessageDialog(this,
                    "El codigo de verificacion debe contener solo numeros",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!nombre.matches("[a-zA-Z\\\\s]+")){
            JOptionPane.showMessageDialog(this,
                    "El nombre solo puede tener letras",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        //====================================


        // Ejecutar Compra Real
        if (Sesion.usuarioLogueado instanceof Cliente) {
            Cliente cliente = (Cliente) Sesion.usuarioLogueado;

            // Instancias de lógica
            GestorPedidos gestor = new GestorPedidos();
            IMetodoPago metodo = new PagoSimulado(); // Usamos tu clase de pago

            // Bloquear botón para evitar doble click
            finalizarCompraButton.setEnabled(false);
            finalizarCompraButton.setText("Procesando...");

            // Usamos un hilo para no congelar la ventana mientras "piensa"
            new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() {
                    // Llamada al Gestor (BD, Stock, Hilo de Envío)
                    return gestor.realizarCompra(cliente, metodo);
                }

                @Override
                protected void done() {
                    try {
                        boolean exito = get();

                        if (exito) {
                            JOptionPane.showMessageDialog(rootPane,
                                    "¡Pago Aprobado!\nTu pedido ha sido enviado al almacén.",
                                    "Compra Exitosa", JOptionPane.INFORMATION_MESSAGE);

                            // Cerrar esta ventana de pago
                            dispose();

                            // Cerrar el carrito de compras (si existe)
                            if (ventanaCarrito != null) ventanaCarrito.dispose();

                            // Abrir ventana de Pedidos
                            new Pedidos().setVisible(true);

                        } else {
                            JOptionPane.showMessageDialog(rootPane,
                                    "Hubo un error procesando la compra.\nVerifique saldo o stock.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            finalizarCompraButton.setEnabled(true);
                            finalizarCompraButton.setText("Intentar de nuevo");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.execute();
        }
    }

    // Helper para validar campos
    private boolean esInvalido(JTextField tf, String placeholder) {
        return tf.getText().trim().isEmpty() || tf.getText().equals(placeholder);
    }


    public void configurarIconosPago() {
        int anchoIcon = 70;
        int altoIcon = 25;

        // Usamos "../" para salir de /imagenes/ y entrar en /imagenesTarjetas/, ya que no hay un metodo para mis iconos del metodo de pago :(
        String carpeta = "../imagenesTarjetas/";

        try {
            // Corregimos la ruta con el truco del nivel superior

            IconPayPal.setIcon(Estilos.redimensionarImagenIconos(carpeta + "payPal.png", anchoIcon, altoIcon));
            IconAmerican.setIcon(Estilos.redimensionarImagenIconos(carpeta + "American.png", anchoIcon, altoIcon));
            IconMastercard.setIcon(Estilos.redimensionarImagenIconos(carpeta + "Mastercard.png", anchoIcon, altoIcon));
            IconVisa.setIcon(Estilos.redimensionarImagenIconos(carpeta + "Visa.png", anchoIcon, altoIcon));

        } catch (Exception e) {
            System.err.println("Error cargando iconos tarjetas: " + e.getMessage());
        }
    }

    private void configurarInputs(JTextField textField, String textoInformativo) {
        textField.setText(textoInformativo);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(textoInformativo)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(textoInformativo);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
}


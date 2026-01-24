package Interfaz;

import com.techsystem.Logica.*; // Importar lógica necesaria

import javax.swing.*;
import java.awt.*;

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
        } catch (Exception e) { /* Ignorar si falla estilos */ }

        // --- LÓGICA DEL BOTÓN FINALIZAR ---
        finalizarCompraButton.addActionListener(e -> procesarCompra());
    }

    private void procesarCompra() {
        // 1. Validar que no sean los textos de ejemplo
        if (esInvalido(TFnumeroTarjeta, "Numero de tarjeta") ||
                esInvalido(TFcvv, "CVV") ||
                esInvalido(TFnombreTarjeta, "Nombre en la tarjeta")) {

            JOptionPane.showMessageDialog(this, "Por favor completa todos los datos bancarios.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Ejecutar Compra Real
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
        try {
            IconPayPal.setIcon(Estilos.redimensionarImagen("/imagenesTarjetas/payPal.png", anchoIcon, altoIcon));
            IconAmerican.setIcon(Estilos.redimensionarImagen("/imagenesTarjetas/American.png", anchoIcon, altoIcon));
            IconMastercard.setIcon(Estilos.redimensionarImagen("/imagenesTarjetas/Mastercard.png", anchoIcon, altoIcon));
            IconVisa.setIcon(Estilos.redimensionarImagen("/imagenesTarjetas/Visa.png", anchoIcon, altoIcon));
        } catch (Exception e) {
            System.err.println("Error cargando iconos tarjetas: " + e.getMessage());
        }

        JLabel[] labels = {IconPayPal, IconAmerican, IconMastercard, IconVisa};
        for (JLabel lbl : labels) {
            lbl.setText("");
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
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
                    textField.setForeground(Color.BLACK); // Mejor negro para que se lea
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
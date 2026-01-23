package Interfaz;

import javax.swing.*;

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


    ProcesoDePago() {
        this.setContentPane(panelProcesoDePago);
        this.setSize(500, 700); // Ajustar a un tamaño fijo
        this.setLocationRelativeTo(null);
        finalizarCompraButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Compra realizada con exito!");
            this.dispose();
            Pedidos pedidos = new Pedidos();
            pedidos.setVisible(true);


        });

        configurarInputs(TFnumeroTarjeta,"Numero de tarjeta");
        configurarInputs(TFfechaVen,"Fecha de vencimiento   (Mes/Año)  ejm. (12/2026)");
        configurarInputs(TFcvv,"CVV (Se encuentra en la parte trasera de tu tarjeta)");
        configurarInputs(TFnombreTarjeta,"Nombre en la tarjeta");
        configurarIconosPago();
        Estilos.LabelsBonitos2(LmetodoPago);
    }


    public void configurarIconosPago() {
        // Definimos el tamaño para los iconos de las tarjetas

        int anchoIcon = 70;
        int altoIcon = 25;

        // Usamos una ruta relativa para llegar a la carpeta imagenesTarjetas
        // sin tener que modificar el código de la clase Estilos
        IconPayPal.setIcon(Estilos.redimensionarImagen("../imagenesTarjetas/payPal.png", anchoIcon, altoIcon));
        IconAmerican.setIcon(Estilos.redimensionarImagen("../imagenesTarjetas/American.png", anchoIcon, altoIcon));
        IconMastercard.setIcon(Estilos.redimensionarImagen("../imagenesTarjetas/Mastercard.png", anchoIcon, altoIcon));
        IconVisa.setIcon(Estilos.redimensionarImagen("../imagenesTarjetas/Visa.png", anchoIcon, altoIcon));

        // Limpiamos el texto y centramos los labels para un acabado profesional
        JLabel[] labels = {IconPayPal, IconAmerican, IconMastercard, IconVisa};
        for (JLabel lbl : labels) {
            lbl.setText(""); // Borra el texto "Label" para que solo se vea la imagen
            lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }
    }

    private void configurarInputs(JTextField textField, String textoInformativo) {
        // Configuramos el estado inicial
        textField.setText(textoInformativo);
        textField.setForeground(java.awt.Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(textoInformativo)) {
                    textField.setText("");
                    textField.setForeground(java.awt.Color.WHITE); // O BLACK según tu tema
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(textoInformativo);
                    textField.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }
}

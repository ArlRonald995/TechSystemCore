package Interfaz;

import javax.swing.*;
import java.awt.*;

public class ItemCarrito extends JFrame{
    private JPanel itemPanel;
    private JButton comprarButton;

    ItemCarrito(){
        comprarButton.addActionListener(e ->{
            ProcesoDePago pago = new ProcesoDePago();
            pago.setVisible(true);
            Window ventanaPadre = SwingUtilities.getWindowAncestor(itemPanel);

            // Si la encontramos, la cerramos
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });


    }

    public JPanel getItemPanel() {
        return itemPanel;
    }

    //toda esta vaina es para hacer el borde redondeado del item del carrito
    private void createUIComponents() {
        itemPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Configuramos los gráficos
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Pintamos el fondo blanco con bordes redondeados
                g2.setColor(Color.WHITE);
                // (x, y, ancho, alto, radioX, radioY) -> Radio 30 para esquinas redondas
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                // 3. Pintamos un borde GRIS suave alrededor
                g2.setColor(new Color(220, 220, 220));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

                g2.dispose();
            }
        };
    }
}

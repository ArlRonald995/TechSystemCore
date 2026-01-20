package Interfaz;


import javax.swing.*;
import java.awt.*;

public class TarjetaProducto extends JFrame {
    private JPanel panelTarjeta;
    private JLabel iconCelular;
    private JButton verDetallesButton;
    private JLabel txtPrecio;
    private JLabel txtNombre;
    private String skuGuardado;
    private String descripcionGuardada;
    private String stockGuardado;
    private String marcaGuardada;

    // CONSTRUCTOR: Aquí recibimos los datos únicos de cada producto
    public TarjetaProducto(String sku, String nombre, String marca, String precio, String stock, String descripcion, Icon imagen) {

        // Asignar el texto que se va a mostrar
        txtNombre.setText(nombre);
        txtPrecio.setText("$ " + precio);
        iconCelular.setIcon(imagen);

        this.skuGuardado = sku;
        this.descripcionGuardada = descripcion;
        this.stockGuardado = stock;
        this.marcaGuardada = marca;

        // Asignar la imagen
        Estilos.hacerBotonRedondo(verDetallesButton);
        // Acción del botón
        verDetallesButton.addActionListener(e -> {
            mostrarDetalleProducto();
        });

    }

    private void mostrarDetalleProducto() {
        DetalleProducto miniVentana = new DetalleProducto();
        miniVentana.pack();
        miniVentana.setDatos(this.skuGuardado, txtNombre.getText(), this.marcaGuardada, txtPrecio.getText(),
                this.stockGuardado, this.descripcionGuardada, iconCelular.getIcon());
        miniVentana.setSize(1000, 600);
        miniVentana.setLocationRelativeTo(null); // Centrar
        miniVentana.setVisible(true);
    }

    // Método necesario para que la Ventana1 pueda "agarrar" este panel y mostrarlo
    public JPanel getPanel() {
        return panelTarjeta;
    }

    private void createUIComponents() {
        panelTarjeta = new JPanel() {
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
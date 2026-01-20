package Interfaz;


import javax.swing.*;

public class DetalleProducto extends JDialog {
    private JLabel txtNombre;
    private JLabel txtPrecio;
    private JButton agregarAlCarritoButton;
    private JPanel panelDetalles;
    private JPanel panelizquierdo;
    private JPanel panelDerecho;
    private JButton regresarButton;
    private JLabel txtStock;
    private JLabel txtMarca;
    private JTextArea descripciónTextArea;
    private JLabel icon;
    private JLabel txtSKU;
    private JButton marcaButton;
    private JButton stockButton;
    private JButton descripcionButton;
    private JButton skuButton;
    private JLabel descripcionTextArea;

    public DetalleProducto() {
        setContentPane(panelDetalles);
        setModal(true);
        getRootPane().setDefaultButton(regresarButton);
        Estilos.hacerBotonRedondo(agregarAlCarritoButton);
        Estilos.hacerBotonRedondo(regresarButton);
        Estilos.hacerBotonRedondo(marcaButton);
        Estilos.hacerBotonRedondo(stockButton);
        Estilos.hacerBotonRedondo(descripcionButton);
        Estilos.hacerBotonRedondo(skuButton);

        regresarButton.addActionListener(e -> dispose());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


    }

    public void setDatos(String SKU, String nombre, String marca, String precio, String stock, String descripcion, Icon rutaImagen) {
        txtNombre.setText(nombre);
        txtSKU.setText(SKU);
        txtMarca.setText(marca);
        txtPrecio.setText(precio);
        txtStock.setText(stock + " unidades");
        descripcionTextArea.setText(descripcion);
        icon.setIcon(rutaImagen);

        // lblImagen.setIcon(new ImageIcon(rutaImagen));
    }


}

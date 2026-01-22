package Interfaz;

import javax.swing.*;

public class AgregarProducto extends JFrame{
    private JPanel panelAgregarProd;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton agregarProductoButton;

    AgregarProducto(){
        this.setContentPane(panelAgregarProd);
        this.setSize(700, 600); // Ajustar a un tamaño fijo
        this.setLocationRelativeTo(null);
        agregarProductoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Producto agregado con éxito");
            this.dispose();
        });
    }
}

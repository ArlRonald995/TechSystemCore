package Interfaz;

import javax.swing.*;

public class ProcesoDePago extends JFrame {
    private JPanel panelProcesoDePago;
    private JButton finalizarCompraButton;

    ProcesoDePago(){
        this.setContentPane(panelProcesoDePago);
        this.setSize(700, 600); // Ajustar a un tamaño fijo
        this.setLocationRelativeTo(null);
        finalizarCompraButton.addActionListener(e ->{
            JOptionPane.showMessageDialog(null, "Compra realizada con exito!");
            this.dispose();
            Pedidos pedidos = new Pedidos();
            pedidos.setVisible(true);


        });
    }
}

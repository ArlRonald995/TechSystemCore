package Interfaz;

import javax.swing.*;

public class AgregarProducto extends JFrame{
    private JPanel panelAgregarProd;
    private JTextField Bttsku;
    private JTextField Bttmarca;
    private JButton agregarProductoButton;
    private JLabel LagregarProducto;
    private JTextField BttPrecioVenta;
    private JComboBox Bttcategoria;
    private JTextField Bttnombre;
    private JTextField Bttdescripcion;
    private JLabel Lcategoria;
    private JLabel Lsku;
    private JLabel Lnombre;
    private JLabel Lmarca;
    private JLabel LprecioVenta;
    private JLabel Ldescripcion;

    AgregarProducto(){
        this.setContentPane(panelAgregarProd);
        this.setSize(700, 600); // Ajustar a un tamaño fijo
        this.setLocationRelativeTo(null);
        agregarProductoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Producto agregado con éxito");
            this.dispose();
        });

        configuracionLabelsyBotones();

    }

    private void configuracionLabelsyBotones (){
        Estilos.LabelsBonitos1(LagregarProducto);
        Estilos.hacerLabelRedondo(LagregarProducto);

        Estilos.LabelsBonitos1(Lcategoria);
        Estilos.hacerLabelRedondo(Lcategoria);

        Estilos.LabelsBonitos1(Lsku);
        Estilos.hacerLabelRedondo(Lsku);

        Estilos.LabelsBonitos1(Lnombre);
        Estilos.hacerLabelRedondo(Lnombre);
        Estilos.LabelsBonitos1(Lmarca);
        Estilos.hacerLabelRedondo(Lmarca);
        Estilos.LabelsBonitos1(LprecioVenta);
        Estilos.hacerLabelRedondo(LprecioVenta);
        Estilos.LabelsBonitos1(Ldescripcion);
        Estilos.hacerLabelRedondo(Ldescripcion);

        Estilos.botonesBonitos2(agregarProductoButton);
        Estilos.hacerBotonRedondo(agregarProductoButton);
    }
}

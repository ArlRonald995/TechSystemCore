package Interfaz;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicLabelUI;

public class Estilos {
    // Método para transformar un botón cuadrado en uno redondo y elegante
    public static void hacerBotonRedondo(JButton btn) {
        btn.setContentAreaFilled(false); // Quitamos el relleno gris feo
        btn.setFocusPainted(false); // Quitamos el recuadro azul al hacer click
        btn.setBorderPainted(false);// Quitamos el borde 3D
        btn.setFocusable(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Manito
        btn.setForeground(Color.WHITE); // Letra blanca
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Para hacer el boton bonito :3
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Suavizar bordes

                // Color de fondo (Cambia este color al que gustes)
                g2.setColor(new Color(51, 101, 173));

                // Dibujamos un rectángulo con esquinas redondeadas (Radio 20)
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);

                super.paint(g, c); // Pintar el texto encima
                g2.dispose();
            }
        });
    }


    // Función para redimensionar una imagen desde la ruta (String)
    public static Icon redimensionarImagen(String nombreArchivo, int ancho, int alto) {
        try {
            java.net.URL url = Estilos.class.getResource("/imagenes/" + nombreArchivo);
            if (url == null) return null; // Si no existe, devolvemos null

            // La cargamos como imagen original
            ImageIcon imagenOriginal = new ImageIcon(url);
            Image imagen = imagenOriginal.getImage();

            // La redimensionamos
            // SCALE_SMOOTH hace que se vea nítida y no borrosa
            Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

            // Devolvemos el nuevo Icono del tamaño perfecto
            return new ImageIcon(imagenRedimensionada);

        } catch (Exception e) {
            return null;
        }
    }

    public static void botonesBonitos(JButton boton){
        boton.setBackground(new Color(51, 101, 173)); //Color del fondo
        boton.setForeground(Color.WHITE); // Letra blanca
        boton.setFocusPainted(false); // Quita el recuadro feo del click
        boton.setBorderPainted(false); // Quita el borde 3D viejo
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Letra negrita
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    public static void botonesBonitos2(JButton boton2){
        boton2.setBackground(new Color(238, 237, 233)); //Color del fondo
        boton2.setForeground(Color.black); // Letra blanca
        boton2.setFocusPainted(false); // Quita el recuadro feo del click
        boton2.setBorderPainted(false); // Quita el borde 3D viejo
        boton2.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Letra negrita
        boton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void LabelsBonitos1(JLabel jLabel){

        jLabel.setBackground(new Color(34,  69,  123)); //Color del fondo
        jLabel.setForeground (Color.black); // Letra blanca
        jLabel.setBorder (null); // Quita el bonde 3D vieio
        jLabel.setFont(new Font("Segoe UI", Font. BOLD,  14));
        jLabel.setOpaque(true);
    }

    public static void LabelsBonitos2(JLabel jLabel){ //Hacer que el label se vea elegante

        jLabel.setBackground(new Color(209,  211,  217)); //Color del fondo
        jLabel.setForeground (Color.black); // Letra blanca
        jLabel.setFont(new Font("Segoe UI", Font. BOLD,  25));

    }

    public static void hacerLabelRedondo(JLabel label) {
        // 1. FUENTE Y COLOR: Segoe UI es ideal para este look moderno
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Un poquito más grande

        // 2. CENTRADO TOTAL: Crucial para que no se vea "chueco"
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        // 3. PADDING (ESPACIADO): Esto es lo que le da la altura y el ancho al fondo azul
        // Formato: (Arriba, Izquierda, Abajo, Derecha)
        // Aumentamos los valores laterales para que se vea como una píldora
        label.setBorder(new EmptyBorder(8, 20, 8, 20));

        // 4. TRANSPARENCIA: Para que el redondeo sea perfecto
        label.setOpaque(false);
        label.setFocusable(false);
        label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        // 5. PINTADO DEL FONDO REDONDEADO
        label.setUI(new BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();

                // Antialiasing para bordes ultra suaves
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // El color azul de tu imagen
                g2.setColor(new Color(51, 101, 173));

                // Dibujamos el fondo usando el alto total para el arco
                // Esto hace que las esquinas sean perfectamente circulares
                int arc = c.getHeight();
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), arc, arc);

                g2.dispose();
                super.paint(g, c); // Dibuja el texto al final
            }
        });
    }


}

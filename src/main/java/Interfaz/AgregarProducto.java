package Interfaz;

import com.techsystem.Logica.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AgregarProducto extends JFrame {

    private JPanel panelPrincipal;

    // Selectores
    private JComboBox<String> cmbGrupo; // "Computadores", "Moviles"...
    private JComboBox<String> cmbTipoConcreto; // "Laptop", "Tablet", "Raton"...

    // Campos Comunes
    private JTextField txtSku, txtNombre, txtMarca, txtPrecio, txtStock, txtDescripcion;
    private JButton btnGuardar;

    // Campos Dinámicos (Solo pediremos 3 datos clave, el resto serán defaults)
    private JPanel panelEspecifico;
    private JTextField txtDato1, txtDato2, txtDato3;
    private JLabel lblDato1, lblDato2, lblDato3;

    // Mapa de relaciones
    private Map<String, String[]> tiposPorGrupo;

    public AgregarProducto() {
        super("Nuevo Producto - TechSystem");
        setSize(900, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarGrupos();
        construirInterfaz();

        // Eventos
        cmbGrupo.addActionListener(e -> actualizarComboTipos());
        cmbTipoConcreto.addActionListener(e -> actualizarCamposDinamicos());
        btnGuardar.addActionListener(e -> guardarProducto());
    }

    private void inicializarGrupos() {
        tiposPorGrupo = new HashMap<>();
        // Mapeo completo basado en el GestorProductos
        tiposPorGrupo.put("Computadores", new String[]{"Laptop", "Escritorio"});
        tiposPorGrupo.put("Móviles", new String[]{"Celular", "Tablet", "SmartWatch"});
        tiposPorGrupo.put("Componentes", new String[]{"Procesador", "MemoriaRam", "Almacenamiento"});
        tiposPorGrupo.put("Periféricos", new String[]{"Monitor", "Teclado", "Raton"});
    }

    private void construirInterfaz() {
        panelPrincipal = new JPanel(new BorderLayout(20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Registrar Nuevo Producto");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(Color.WHITE);

        // PANEL DATOS COMUNES
        JPanel pComun = new JPanel(new GridLayout(0, 2, 10, 10));
        pComun.setBackground(Color.WHITE);
        pComun.setBorder(BorderFactory.createTitledBorder("Datos Generales"));

        cmbGrupo = new JComboBox<>(tiposPorGrupo.keySet().toArray(new String[0]));
        cmbTipoConcreto = new JComboBox<>();

        txtSku = new JTextField();
        txtNombre = new JTextField();
        txtMarca = new JTextField();
        txtPrecio = new JTextField();
        txtStock = new JTextField();
        txtDescripcion = new JTextField();

        agregar(pComun, "Grupo (Catálogo):", cmbGrupo);
        agregar(pComun, "Tipo Específico:", cmbTipoConcreto);
        agregar(pComun, "SKU:", txtSku);
        agregar(pComun, "Nombre:", txtNombre);
        agregar(pComun, "Marca:", txtMarca);
        agregar(pComun, "Precio ($):", txtPrecio);
        agregar(pComun, "Stock:", txtStock);
        agregar(pComun, "Descripción:", txtDescripcion);

        // PANEL ESPECIFICACIONES
        panelEspecifico = new JPanel(new GridLayout(3, 2, 10, 10));
        panelEspecifico.setBackground(new Color(240, 248, 255));
        panelEspecifico.setBorder(BorderFactory.createTitledBorder("Especificaciones Técnicas"));

        lblDato1 = new JLabel("Dato 1"); txtDato1 = new JTextField();
        lblDato2 = new JLabel("Dato 2"); txtDato2 = new JTextField();
        lblDato3 = new JLabel("Dato 3"); txtDato3 = new JTextField();

        panelEspecifico.add(lblDato1); panelEspecifico.add(txtDato1);
        panelEspecifico.add(lblDato2); panelEspecifico.add(txtDato2);
        panelEspecifico.add(lblDato3); panelEspecifico.add(txtDato3);

        centro.add(pComun);
        centro.add(Box.createVerticalStrut(15));
        centro.add(panelEspecifico);

        panelPrincipal.add(centro, BorderLayout.CENTER);

        btnGuardar = new JButton("GUARDAR PRODUCTO");
        btnGuardar.setBackground(new Color(0, 102, 204)); // Azul fuerte
        btnGuardar.setForeground(Color.WHITE);            // Letra blanca
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // PROPIEDADES PARA ARREGLAR EL COLOR
        btnGuardar.setOpaque(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // --------------------------------------------------

        btnGuardar.setPreferredSize(new Dimension(200, 50));

        // Panel contenedor inferior para darle margen y fondo blanco
        JPanel panelSur = new JPanel();
        panelSur.setBackground(Color.WHITE);
        panelSur.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelSur.add(btnGuardar);

        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        actualizarComboTipos(); // Carga inicial
    }

    private void agregar(JPanel p, String t, JComponent c) {
        p.add(new JLabel(t)); p.add(c);
    }

    private void actualizarComboTipos() {
        String grupo = (String) cmbGrupo.getSelectedItem();
        cmbTipoConcreto.removeAllItems();
        if (grupo != null && tiposPorGrupo.containsKey(grupo)) {
            for (String tipo : tiposPorGrupo.get(grupo)) {
                cmbTipoConcreto.addItem(tipo);
            }
        }
        actualizarCamposDinamicos();
    }

    private void actualizarCamposDinamicos() {
        String tipo = (String) cmbTipoConcreto.getSelectedItem();
        if (tipo == null) return;

        txtDato1.setText(""); txtDato2.setText(""); txtDato3.setText("");

        switch (tipo) {
            // COMPUTADORES
            case "Laptop":
            case "Escritorio":
                lblDato1.setText("Procesador (Ej: Intel i5):");
                lblDato2.setText("RAM (GB - Solo número):");
                lblDato3.setText("Disco (Ej: SSD 512GB):");
                break;

            // MÓVILES
            case "Celular":
                lblDato1.setText("Sistema Operativo:");
                lblDato2.setText("Cámara (MP):");
                lblDato3.setText("Pantalla (Pulgadas):");
                break;
            case "Tablet":
                lblDato1.setText("Sistema Operativo:");
                lblDato2.setText("Almacenamiento (GB):");
                lblDato3.setText("Pantalla (Pulgadas):");
                break;
            case "SmartWatch":
                lblDato1.setText("Duración Batería (H):");
                lblDato2.setText("Material Correa:");
                lblDato3.setText("Sensores (Ej: Salud):");
                break;

            // PERIFÉRICOS
            case "Monitor":
                lblDato1.setText("Tamaño (Pulgadas):");
                lblDato2.setText("Resolución:");
                lblDato3.setText("Tipo Panel (IPS/VA):");
                break;
            case "Teclado":
                lblDato1.setText("Tipo (Mecánico/Membrana):");
                lblDato2.setText("Idioma (Español/Inglés):");
                lblDato3.setText("Retroiluminado (Si/No):");
                break;
            case "Raton":
                lblDato1.setText("DPI Máximo:");
                lblDato2.setText("Botones:");
                lblDato3.setText("Inalámbrico (Si/No):");
                break;

            // COMPONENTES
            case "MemoriaRam":
                lblDato1.setText("Capacidad (GB):");
                lblDato2.setText("Tecnología (DDR4/5):");
                lblDato3.setText("Velocidad (MHz):");
                break;
            case "Almacenamiento":
                lblDato1.setText("Capacidad (GB/TB):");
                lblDato2.setText("Tipo (SSD/HDD):");
                lblDato3.setText("Interfaz (SATA/M.2):");
                break;
            case "Procesador":
                lblDato1.setText("Núcleos:");
                lblDato2.setText("Frecuencia (GHz):");
                lblDato3.setText("Socket:");
                break;

            default:
                lblDato1.setText("Especif. 1:"); lblDato2.setText("Especif. 2:"); lblDato3.setText("Especif. 3:");
        }
    }

    private void guardarProducto() {
        try {
            //  Validaciones
            if (txtSku.getText().isEmpty() || txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "SKU, Nombre y Precio son obligatorios.");
                return;
            }

            //  Recolectar Datos Base
            String sku = txtSku.getText();
            String nombre = txtNombre.getText();
            String marca = txtMarca.getText();
            String desc = txtDescripcion.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            String grupo = (String) cmbGrupo.getSelectedItem();
            String tipo = (String) cmbTipoConcreto.getSelectedItem();

            // Lógica de Imagen
            String nombreLimpio = nombre.trim().replace(" ", "_").replace("-", "_").replace("\"", "");
            String img = nombreLimpio + ".png";

            // INSTANCIACIÓN
            Producto p = null;
            String jsonSpecs = "{}"; // JSON Auxiliar

            String d1 = txtDato1.getText();
            String d2 = txtDato2.getText();
            String d3 = txtDato3.getText();

            // SWITCH MASIVO
            switch (tipo) {
                // COMPUTADORES
                case "Laptop":

                    int ramLap = tryParseInt(d2, 8); // Default 8GB si falla
                    p = new Laptop(sku, nombre, desc, precio, stock, marca, img, grupo,
                            19.5, 65.0, 12, 512, d1, 3, true, true, false,
                            ramLap, d3, "FHD", 15.6, "IPS", 60, true, 50, 2.1);
                    jsonSpecs = String.format("{\"procesador\":\"%s\", \"ram\":\"%dGB\", \"disco\":\"%s\"}", d1, ramLap, d3);
                    break;

                case "Escritorio":
                    int ramEsc = tryParseInt(d2, 16);
                    p = new Escritorio(sku, nombre, desc, precio, stock, marca, img, grupo,
                            110.0, 450.0, 24, 1024, d1, 4, true, true, false,
                            ramEsc, d3, "ATX", false);
                    jsonSpecs = String.format("{\"procesador\":\"%s\", \"ram\":\"%dGB\", \"disco\":\"%s\"}", d1, ramEsc, d3);
                    break;

                // MÓVILES
                case "Celular":
                    int camara = tryParseInt(d2, 50);
                    double pantallaCel = tryParseDouble(d3, 6.5);
                    p = new Celular(sku, nombre, desc, precio, stock, marca, img, grupo,
                            5.0, 15.0, 24, 128, "Generico", 2, true, true, true,
                            camara, d1, "FHD+", pantallaCel, "Gorilla", 120);
                    jsonSpecs = String.format("{\"os\":\"%s\", \"camara\":\"%dMP\", \"pantalla\":\"%.1f\"}", d1, camara, pantallaCel);
                    break;

                case "Tablet":

                    int almTab = tryParseInt(d2, 64);
                    double pantTab = tryParseDouble(d3, 10.0);
                    p = new Tablet(sku, nombre, desc, precio, stock, marca, img, grupo,
                            5.0, 10.0, 12, almTab, "Generico", 2, true, true, false,
                            "LCD", pantTab, "IPS", 60, true, d1);
                    jsonSpecs = String.format("{\"os\":\"%s\", \"almacenamiento\":\"%dGB\", \"pantalla\":\"%.1f\"}", d1, almTab, pantTab);
                    break;

                case "SmartWatch":

                    double batWatch = tryParseDouble(d1, 24.0);
                    p = new SmartWatch(sku, nombre, desc, precio, stock, marca, img, grupo,
                            5.0, 1.0, 12, 32, "SiP", 1, true, true, false,
                            "OLED", 1.5, "Glass", 60, d2, true, d3);
                    jsonSpecs = String.format("{\"bateria\":\"%.1fh\", \"correa\":\"%s\", \"sensores\":\"%s\"}", batWatch, d2, d3);
                    break;

                // PERIFÉRICOS
                case "Monitor":

                    double tamMon = tryParseDouble(d1, 24.0);
                    p = new Monitor(sku, nombre, desc, precio, stock, marca, img, grupo,
                            110.0, 35.0, 24, "HDMI", true,
                            d2, tamMon, d3, 60, false, false);
                    jsonSpecs = String.format("{\"tamano\":\"%.1f\", \"resolucion\":\"%s\", \"panel\":\"%s\"}", tamMon, d2, d3);
                    break;

                case "Teclado":

                    boolean retro = d3.equalsIgnoreCase("Si");
                    p = new Teclado(sku, nombre, desc, precio, stock, marca, img, grupo,
                            5.0, 0.5, 12, "USB", true, d1, retro); // Asumiendo constructor Teclado
                    jsonSpecs = String.format("{\"tipo\":\"%s\", \"idioma\":\"%s\", \"retro\":\"%s\"}", d1, d2, d3);
                    break;

                case "Raton":

                    int dpi = tryParseInt(d1, 1000);
                    int btn = tryParseInt(d2, 3);
                    boolean wireless = d3.equalsIgnoreCase("Si");
                    String con = wireless ? "Wireless" : "USB";
                    p = new Raton(sku, nombre, desc, precio, stock, marca, img, grupo,
                            5.0, 0.1, 12, con, wireless, dpi, btn); // Asumiendo constructor Raton
                    jsonSpecs = String.format("{\"dpi\":\"%d\", \"botones\":\"%d\", \"inalambrico\":\"%s\"}", dpi, btn, d3);
                    break;

                // COMPONENTES
                case "MemoriaRam":

                    int capRam = tryParseInt(d1, 8);
                    int velRam = tryParseInt(d3, 3200);
                    p = new MemoriaRam(sku, nombre, desc, precio, stock, marca, img, grupo,
                            1.2, 5, "DIMM", capRam, d2, velRam);
                    jsonSpecs = String.format("{\"capacidad\":\"%dGB\", \"tecnologia\":\"%s\", \"velocidad\":\"%dMHz\"}", capRam, d2, velRam);
                    break;

                case "Almacenamiento":

                    int capDisk = tryParseInt(d1, 500);
                    p = new Almacenamiento(sku, nombre, desc, precio, stock, marca, img, grupo,
                            3.3, 5.0, 24, d3, d2, 1, 500); // Valores default vel lectura/escritura
                    jsonSpecs = String.format("{\"capacidad\":\"%s\", \"tipo\":\"%s\", \"interfaz\":\"%s\"}", d1, d2, d3);
                    break;

                case "Procesador":

                    int cores = tryParseInt(d1, 6);
                    double freq = tryParseDouble(d2, 3.5);
                    p = new Procesador(sku, nombre, desc, precio, stock, marca, img, grupo,
                            1.2, 65.0, 36, d3, "Generico", cores, (int)freq);
                    jsonSpecs = String.format("{\"nucleos\":\"%d\", \"frecuencia\":\"%.1fGHz\", \"socket\":\"%s\"}", cores, freq, d3);
                    break;

                default:
                    JOptionPane.showMessageDialog(this, "Tipo no implementado: " + tipo);
                    return;
            }

            //  GUARDAR EN BASE DE DATOS
            if (p != null) {
                GestorProductos gestor = new GestorProductos();
                if (gestor.registrarProducto(p, jsonSpecs)) {
                    String msg = "✅ " + tipo + " Guardado.\n\n" +
                            "Nombre de imagen esperado en recursos:\n👉 " + img;
                    JOptionPane.showMessageDialog(this, msg);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar en la Base de Datos.");
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error de formato numérico: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage());
        }
    }

    // Métodos auxiliares para evitar crashes si el usuario escribe texto en campos numéricos
    private int tryParseInt(String val, int def) {
        try { return Integer.parseInt(val.replaceAll("[^0-9]", "")); } catch (Exception e) { return def; }
    }
    private double tryParseDouble(String val, double def) {
        try { return Double.parseDouble(val.replaceAll("[^0-9.]", "")); } catch (Exception e) { return def; }
    }
}
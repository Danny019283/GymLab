package Vista;

import Modelo.Medicion;
import Vista.Tablas.TablaMedicion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaMedicion extends JFrame {
    private TablaMedicion tblMedicion = new TablaMedicion();
    private JTable tabla = new JTable();
    private TableRowSorter<TablaMedicion> sorter;

    private final JTextField txtBuscarCedula = new JTextField();
    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnReporte = new JButton("Generar Reporte");
    private JButton btnAtras = new JButton("Atras");

    private final Color colorFondo = Color.decode("#1a1a1a");

    public VistaMedicion() {
        setTitle("Historial de Mediciones - Gym PowerLab");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        buildUI();
    }

    public void buildUI() {
        JPanel pnlContenido = new JPanel(new BorderLayout(12, 12));
        pnlContenido.setBackground(colorFondo);
        pnlContenido.setBorder(new EmptyBorder(12, 20, 12, 20));
        setContentPane(pnlContenido);

        JLabel lblTitulo = new JLabel("Historial de Mediciones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.YELLOW);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        pnlContenido.add(lblTitulo, BorderLayout.NORTH);

        // Panel de búsqueda
        JPanel pnlBuscar = new JPanel(new BorderLayout(10, 10));
        pnlBuscar.setBackground(colorFondo);
        pnlBuscar.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel lblBuscar = new JLabel("Buscar por Cédula:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 15));
        lblBuscar.setForeground(Color.WHITE);

        JPanel pnlBuscarCedula = new JPanel(new BorderLayout(10, 10));
        pnlBuscarCedula.setBackground(colorFondo);
        pnlBuscarCedula.add(lblBuscar, BorderLayout.WEST);
        pnlBuscarCedula.add(txtBuscarCedula, BorderLayout.CENTER);
        configurarBoton(btnBuscar);
        pnlBuscarCedula.add(btnBuscar, BorderLayout.EAST);

        pnlBuscar.add(pnlBuscarCedula, BorderLayout.CENTER);
        pnlContenido.add(pnlBuscar, BorderLayout.NORTH);

        // Tabla
        tabla = new JTable(tblMedicion);
        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setBackground(new Color(220, 220, 220));
        tabla.setForeground(colorFondo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));

        sorter = new TableRowSorter<>(tblMedicion);
        tabla.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(colorFondo);
        pnlContenido.add(scrollPane, BorderLayout.CENTER);

        // Botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        pnlBotones.setBackground(colorFondo);
        pnlBotones.setBorder(new EmptyBorder(15, 0, 0, 0));

        configurarBoton(btnAgregar);
        configurarBoton(btnReporte);
        configurarBoton(btnAtras);

        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnReporte);
        pnlBotones.add(btnAtras);

        pnlContenido.add(pnlBotones, BorderLayout.SOUTH);

    }

    public void mensajeInicial() {
        JOptionPane.showMessageDialog(null, "Debe ingresar una cedula para que aparezcan los registros");
    }

    public TablaMedicion getTablaMedicion() { return tblMedicion; }
    public String getTextoBusqueda() { return txtBuscarCedula.getText().trim(); }
    public int getFilaSeleccionada() { return tabla.getSelectedRow(); }

    public void addBuscarListener(ActionListener accion) { btnBuscar.addActionListener(accion); }
    public void addAgregarListener(ActionListener accion) { btnAgregar.addActionListener(accion); }
    public void addReporteListener(ActionListener accion) { btnReporte.addActionListener(accion); }
    public void addAtrasListener(ActionListener accion) { btnAtras.addActionListener(accion); }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void configurarBoton(JButton boton) {
        boton.setBackground(new Color(200, 200, 200));
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.YELLOW);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(200, 200, 200));
            }
        });
    }

    public Medicion getMedicionSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int modelRow = tabla.convertRowIndexToModel(fila);
            return tblMedicion.getMedicionAt(modelRow);
        }
        return null;
    }
}
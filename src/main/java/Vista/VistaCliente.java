package Vista;

import Modelo.ClaseGrupal;
import Modelo.DTOs.ClienteDTO;
import Modelo.DTOs.MedicionDTO;
import Vista.Tablas.TablaCliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaCliente extends JFrame {

    private TablaCliente tblCliente = new TablaCliente(); // Intermediario entre la tabla y los datos
    private JTable tabla = new JTable();
    private TableRowSorter<TablaCliente> sorter;

    public TablaCliente getTablaCliente() {
        return tblCliente;
    }

    // Campo para búsqueda
    private final JTextField txtBuscarCedula = new JTextField();

    // Botones
    private final JButton btnBuscarCedula = new JButton("Buscar");
    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnModificar = new JButton("Modificar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnMatricularClase = new JButton("Matricular Clase");
    private final JButton btnListarClases = new JButton("Listar Clases");
    private final JButton btnMedicion = new JButton("Medición");
    private final JButton btnVerRutina = new JButton("Ver Rutina");
    private final JButton btnAtras = new  JButton("Atras");

    private final Color colorFondo = Color.decode("#1a1a1a");

    public VistaCliente() {
        setTitle("Gestión de Clientes - Gym PowerLab");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        buildUI();
    }

    public void buildUI() {
        // Panel principal con fondo negro
        JPanel pnlContenido = new JPanel(new BorderLayout(12, 12));
        pnlContenido.setBackground(colorFondo);
        pnlContenido.setBorder(new EmptyBorder(12, 20, 12, 20));
        setContentPane(pnlContenido);

        // Título con letras amarillas
        JLabel lblTitulo = new JLabel("Gestión de Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.YELLOW);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        pnlContenido.add(lblTitulo, BorderLayout.NORTH);

        // Panel superior de búsqueda
        JPanel pnlArriba = new JPanel(new BorderLayout(10, 10));
        pnlArriba.setBackground(colorFondo);
        pnlArriba.setBorder(new EmptyBorder(0, 0, 20, 0));
        pnlContenido.add(pnlArriba, BorderLayout.NORTH);

        // Búsqueda por cédula
        JPanel pnlBuscarCedula = new JPanel(new BorderLayout(10, 10));
        pnlBuscarCedula.setBackground(colorFondo);
        JLabel lblBuscarCedula = new JLabel("Buscar por Cédula:");
        lblBuscarCedula.setFont(new Font("Arial", Font.BOLD, 15));
        lblBuscarCedula.setForeground(Color.WHITE);
        pnlBuscarCedula.add(lblBuscarCedula, BorderLayout.WEST);
        pnlBuscarCedula.add(txtBuscarCedula, BorderLayout.CENTER);
        configurarBoton(btnBuscarCedula);
        pnlBuscarCedula.add(btnBuscarCedula, BorderLayout.EAST);
        pnlArriba.add(pnlBuscarCedula, BorderLayout.CENTER);

        // Centro: tabla
        tabla = new JTable(tblCliente);
        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setBackground(new Color(220, 220, 220)); // Gris claro
        tabla.setForeground(colorFondo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));

        sorter = new TableRowSorter<>(tblCliente);
        tabla.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(colorFondo);
        pnlContenido.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        pnlBotones.setBackground(colorFondo);
        pnlBotones.setBorder(new EmptyBorder(15, 0, 0, 0));

        configurarBoton(btnAgregar);
        configurarBoton(btnModificar);
        configurarBoton(btnEliminar);
        configurarBoton(btnMatricularClase);
        configurarBoton(btnListarClases);
        configurarBoton(btnMedicion);
        configurarBoton(btnVerRutina);
        configurarBoton(btnAtras);

        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnModificar);
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnMatricularClase);
        pnlBotones.add(btnListarClases);
        pnlBotones.add(btnMedicion);
        pnlBotones.add(btnVerRutina);
        pnlBotones.add(btnAtras);

        pnlContenido.add(pnlBotones, BorderLayout.SOUTH);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                // Lógica para cargar datos del cliente seleccionado (se implementa en el controlador)
            }
        });
    }

    //JOptions
    public String pedirDato(String mensaje){
        return JOptionPane.showInputDialog(mensaje);
    }

    public void mostrarMensaje(String titulo, String mensaje){
        VistaCliente vista = new VistaCliente();
        JOptionPane.showMessageDialog(vista, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarClases(ArrayList<ClaseGrupal> clases) {
        StringBuilder mostrar = new StringBuilder();
        for (ClaseGrupal clase: clases) {
            mostrar.append(clase.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, mostrar.toString(), "Clases", JOptionPane.INFORMATION_MESSAGE);
    }

    //ActionListeners
    public void addRegistrarListener(ActionListener accion) {
        btnAgregar.addActionListener(accion);
    }
    public void addModificarListener(ActionListener accion) {
        btnModificar.addActionListener(accion);
    }
    public void addEliminarListener(ActionListener accion) {
        btnEliminar.addActionListener(accion);
    }
    public void addMatricularListener(ActionListener accion) {
        //redirige a ventana clases
        btnMatricularClase.addActionListener(accion);
    }
    public void addMedicionListener(ActionListener accion) {
        //redirige a historial de medicion
        btnMedicion.addActionListener(accion);
    }
    public void addAtrasListener(ActionListener accion) {
        btnAtras.addActionListener(accion);
    }

    public void addVerRutinaListener(ActionListener accion) {
        btnVerRutina.addActionListener(accion);
    }

    public void addBuscarListener(ActionListener accion) {btnBuscarCedula.addActionListener(accion);}

    public void addListarClasesListener(ActionListener accion) {btnListarClases.addActionListener(accion);}

    public ClienteDTO getClienteSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int modelRow = tabla.convertRowIndexToModel(fila);
            return tblCliente.getClienteAt(modelRow);
        }
        return null;
    }

    private void configurarBoton(JButton boton) {
        boton.setBackground(new Color(200, 200, 200)); // Gris claro
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.YELLOW);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(200, 200, 200));
            }
        });
    }

    private void limpiarBusqueda() {
        txtBuscarCedula.setText("");
        sorter.setRowFilter(null);
        tabla.clearSelection();
    }

    // Getteres
    public String getTextoBusqueda() {
        return txtBuscarCedula.getText().trim();
    }

    public TableRowSorter<TablaCliente> getSorter() {
        return sorter;
    }

}
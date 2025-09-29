package Vista;

import Vista.Tablas.TablaClaseGrupal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaClaseGrupal extends JFrame {
    private TablaClaseGrupal tblClaseGrupal = new TablaClaseGrupal();
    private JTable tabla = new JTable();
    private TableRowSorter<TablaClaseGrupal> sorter;

    public TablaClaseGrupal getTablaClaseGrupal() {
        return tblClaseGrupal;
    }

    // Campo para búsqueda
    private final JTextField txtBuscarCodigo = new JTextField();

    // Botones
    private final JButton btnBuscarCodigo = new JButton("Buscar");
    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnModificar = new JButton("Modificar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnListarClientes = new JButton("Listar Clientes Por Clase");
    private final JButton btnAtras = new JButton("Atras");

    private final Color colorFondo = Color.decode("#1a1a1a");

    public VistaClaseGrupal() {
        setTitle("Gestión de Clases Grupales - Gym PowerLab");
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
        JLabel lblTitulo = new JLabel("Gestión de Clases Grupales", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.YELLOW);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        pnlContenido.add(lblTitulo, BorderLayout.NORTH);

        // Panel superior de búsqueda
        JPanel pnlArriba = new JPanel(new BorderLayout(10, 10));
        pnlArriba.setBackground(colorFondo);
        pnlArriba.setBorder(new EmptyBorder(0, 0, 20, 0));
        pnlContenido.add(pnlArriba, BorderLayout.NORTH);

        // Búsqueda por código
        JPanel pnlBuscarCodigo = new JPanel(new BorderLayout(10, 10));
        pnlBuscarCodigo.setBackground(colorFondo);
        JLabel lblBuscarCodigo = new JLabel("Buscar por Código:");
        lblBuscarCodigo.setFont(new Font("Arial", Font.BOLD, 15));
        lblBuscarCodigo.setForeground(Color.WHITE);
        pnlBuscarCodigo.add(lblBuscarCodigo, BorderLayout.WEST);
        pnlBuscarCodigo.add(txtBuscarCodigo, BorderLayout.CENTER);
        configurarBoton(btnBuscarCodigo);
        pnlBuscarCodigo.add(btnBuscarCodigo, BorderLayout.EAST);
        pnlArriba.add(pnlBuscarCodigo, BorderLayout.CENTER);

        // Centro: tabla
        tabla = new JTable(tblClaseGrupal);
        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setBackground(new Color(220, 220, 220));
        tabla.setForeground(colorFondo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));

        sorter = new TableRowSorter<>(tblClaseGrupal);
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
        configurarBoton(btnListarClientes);
        configurarBoton(btnAtras);

        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnModificar);
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnListarClientes);
        pnlBotones.add(btnAtras);

        pnlContenido.add(pnlBotones, BorderLayout.SOUTH);
    }

    //JOptions
    public String pedirDato(String mensaje) {
        return JOptionPane.showInputDialog(mensaje);
    }

    public void mostrarToSting(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
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

    public void addListarClientesListener(ActionListener accion) {
        btnListarClientes.addActionListener(accion);
    }

    public void addAtrasListener(ActionListener accion) {
        btnAtras.addActionListener(accion);
    }

    public void addBuscarListener(ActionListener accion) {btnBuscarCodigo.addActionListener(accion);}

    private void configurarBoton(JButton boton) {
        boton.setBackground(new Color(200, 200, 200));
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

    //getters
    public TableRowSorter<TablaClaseGrupal>  getSorter() {return sorter;}

    public String getTxtBuscarCodigo() {return txtBuscarCodigo.getText();}
}
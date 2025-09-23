package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VistaMenuPrincipal extends JFrame {
    private final JButton btnSubMenuCliente = new JButton("Clientes");
    private final JButton btnSubMenuInstructor = new JButton("Instructores");
    private final JButton btnSubmenuSucursal = new JButton("Sucursales");

    private final Color colorFondo = Color.decode("#1a1a1a");

    public VistaMenuPrincipal() {
        // Configuración de la ventana principal
        setTitle("Gym PowerLab");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BuildUI();
        setVisible(true);
    }

    public void BuildUI() {
        // Panel principal con fondo negro
        JPanel pnlContenido = new JPanel(new BorderLayout(12, 12));
        pnlContenido.setBackground(colorFondo);
        pnlContenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(pnlContenido);

        // Encabezado con título
        JLabel lblTitulo = new JLabel("Gym Power Lab", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 50));
        lblTitulo.setForeground(Color.YELLOW);
        lblTitulo.setBorder(new EmptyBorder(20, 0, 40, 0));
        pnlContenido.add(lblTitulo, BorderLayout.NORTH);

        // Panel central con botones
        JPanel pnlCentro = new JPanel();
        pnlCentro.setLayout(new GridLayout(3, 2, 20, 20));
        pnlCentro.setBackground(colorFondo);
        pnlCentro.setBorder(new EmptyBorder(50, 100, 50, 100));
        pnlContenido.add(pnlCentro, BorderLayout.CENTER);

        // Configuración de botones
        configurarBoton(btnSubMenuCliente);
        configurarBoton(btnSubMenuInstructor);
        configurarBoton(btnSubmenuSucursal);

        // Agregar botones al panel
        pnlCentro.add(btnSubMenuCliente);
        pnlCentro.add(btnSubMenuInstructor);
        pnlCentro.add(btnSubmenuSucursal);
        // Panel inferior con información adicional
        JPanel pnlInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlInferior.setBackground(colorFondo);
        JLabel lblInfo = new JLabel("Sistema de Gestión - Gym PowerLab © 2025");
        lblInfo.setForeground(Color.YELLOW);
        pnlInferior.add(lblInfo);
        pnlContenido.add(pnlInferior, BorderLayout.SOUTH);
        //agregar listeners
    }

    private void configurarBoton(JButton boton) {
        boton.setBackground(Color.LIGHT_GRAY); // Gris claro
        boton.setForeground(colorFondo);
        boton.setFont(new Font("Arial", Font.BOLD, 25));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.YELLOW, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Efecto hover para los botones
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.YELLOW);
                boton.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(200, 200, 200));
                boton.setForeground(Color.BLACK);
            }
        });
    }

    public void ActionMenuClientes(ActionListener l) {
        btnSubMenuCliente.addActionListener(l);
    }

    public void ActionMenuInstructor(ActionListener l) {
        btnSubMenuInstructor.addActionListener(l);
    }

    public void ActionMenuSucursal(ActionListener l) {
        btnSubmenuSucursal.addActionListener(l);
    }

}

package Controlador;

import Vista.VistaMenuPrincipal;
import javax.swing.*;

public class ControladorMenuPrincipal {
    private VistaMenuPrincipal vistaMenuPrincipal;
    private ControladorCliente controladorCliente;
    private ControladorInstructor controladorInstructor;
    private ControladorSucursal controladorSucursal;

    public ControladorMenuPrincipal() {
        this.vistaMenuPrincipal = new VistaMenuPrincipal();
        // Crear los controladores una sola vez
        this.controladorCliente = new ControladorCliente();
        this.controladorInstructor = new ControladorInstructor();
        this.controladorSucursal = new ControladorSucursal();

        configurarListeners();
    }

    private void configurarListeners() {
        // Configurar los listeners una sola vez en el constructor
        vistaMenuPrincipal.ActionMenuClientes(e -> abrirGestionClientes());
        vistaMenuPrincipal.ActionMenuInstructor(e -> abrirGestionInstructores());
        vistaMenuPrincipal.ActionMenuSucursal(e -> abrirGestionSucursales());
    }

    private void abrirGestionClientes() {
        // Ocultar menú principal y mostrar ventana de clientes
        vistaMenuPrincipal.setVisible(false);
        controladorCliente.mostrarVentana();

        // Configurar el "Atrás" en la ventana de clientes
        controladorCliente.configurarBotonAtras(() -> {
            controladorCliente.cerrarVentana();
            vistaMenuPrincipal.setVisible(true);
        });
    }

    private void abrirGestionInstructores() {
        vistaMenuPrincipal.setVisible(false);
        controladorInstructor.mostrarVentana();

        controladorInstructor.configurarBotonAtras(() -> {
            controladorInstructor.cerrarVentana();
            vistaMenuPrincipal.setVisible(true);
        });
    }

    private void abrirGestionSucursales() {
        vistaMenuPrincipal.setVisible(false);
        controladorSucursal.mostrarVentana();

        controladorSucursal.configurarBotonAtras(() -> {
            controladorSucursal.cerrarVentana();
            vistaMenuPrincipal.setVisible(true);
        });
    }


    public void mostrarVentana() {
        vistaMenuPrincipal.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorMenuPrincipal controlador = new ControladorMenuPrincipal();
            controlador.mostrarVentana();
        });
    }
}
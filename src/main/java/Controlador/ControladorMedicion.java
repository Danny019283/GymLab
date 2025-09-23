package Controlador;

import AccesoADatos.*;
import Modelo.Medicion;
import Modelo.Cliente;
import Vista.Formularios.FormularioMedicion;
import Vista.VistaMedicion;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorMedicion {
    private final ServicioMedicion servicioMedicion;
    private final VistaMedicion vistaMedicion;

    public ControladorMedicion() {
        this.servicioMedicion = new ServicioMedicion();
        this.vistaMedicion = new VistaMedicion();
    }

    public void buscarMedicionesPorCliente() {
        String cedula = vistaMedicion.getTextoBusqueda();
        if (cedula.isEmpty()) {
            vistaMedicion.mostrarError("Ingrese una cédula para buscar");
            return;
        }

        try {
            ArrayList<Medicion> mediciones = servicioMedicion.buscarMedicion(cedula);
            vistaMedicion.getTablaMedicion().refrescarData(mediciones);
        } catch (GlobalException e) {
            vistaMedicion.mostrarError("Error al buscar mediciones: " + e.getMessage());
        }
    }

    public void registrarMedicion() {
        FormularioMedicion formulario = new FormularioMedicion();
        boolean resultado = formulario.mostrarDialogo("Agregar Medición");

        if (!resultado) return;

        try {
            // Buscar cliente
            ControladorCliente controladorCliente = new ControladorCliente();
            Cliente cliente = controladorCliente.buscarCliente(formulario.getCedula());

            if (cliente == null) {
                vistaMedicion.mostrarError("Cliente no encontrado");
                return;
            }

            Medicion medicion = new Medicion.Builder()
                    .cliente(cliente)
                    .peso(formulario.getPeso())
                    .estatura(formulario.getEstatura())
                    .porcGrasa(formulario.getPorcGrasa())
                    .porcMusculo(formulario.getPorcMusculo())
                    .porcGrasaVis(formulario.getPorcGrasaVis())
                    .cintura(formulario.getCintura())
                    .pecho(formulario.getPecho())
                    .muslo(formulario.getMuslo())
                    .fechaDeMedicion(formulario.getFecha())
                    .build();

            servicioMedicion.insertarMedicion(medicion);
            vistaMedicion.mostrarMensaje("Medición registrada exitosamente");

            // Refrescar búsqueda si ya se estaba mostrando este cliente
            if (vistaMedicion.getTextoBusqueda().equals(formulario.getCedula())) {
                buscarMedicionesPorCliente();
            }

        } catch (Exception e) {
            vistaMedicion.mostrarError("Error al registrar medición: " + e.getMessage());
        }
    }

    public void generarReporte() {
        int fila = vistaMedicion.getFilaSeleccionada();
        if (fila == -1) {
            vistaMedicion.mostrarError("Seleccione una medición para generar reporte");
            return;
        }

        try {
            Medicion medicion = vistaMedicion.getMedicionSeleccionada();
            ControladorCliente controladorCliente = new ControladorCliente();
            Cliente cliente = controladorCliente.buscarCliente(medicion.getCliente().getCedula());
            medicion.setCliente(cliente);

            boolean haceEjercicio = JOptionPane.showConfirmDialog(vistaMedicion,
                    "¿El cliente hace ejercicio regularmente?", "Reporte",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

            String reporte = medicion.reporteDeMedicion(haceEjercicio);
            JOptionPane.showMessageDialog(vistaMedicion, reporte, "Reporte de Medición",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            vistaMedicion.mostrarError("Error al generar reporte: " + e.getMessage());
        }
    }

    public void handleBuscar() {
        vistaMedicion.addBuscarListener(e -> buscarMedicionesPorCliente());
    }
    public void handleAgregar() {
        vistaMedicion.addAgregarListener(e -> registrarMedicion());
    }
    public void handleReporte() {
        vistaMedicion.addReporteListener(e -> generarReporte());
    }

    // Método para configurar el botón Atrás
    public void configurarBotonAtras(Runnable accionAtras) {
        vistaMedicion.addAtrasListener(e -> accionAtras.run());
    }

    // Método para mostrar la ventana
    public void mostrarVentana() {
        try {
            configurarListeners();
            vistaMedicion.setVisible(true);
            vistaMedicion.mensajeInicial();
        } catch (Exception e) {
            vistaMedicion.mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    // Método para cerrar la ventana
    public void cerrarVentana() {
        vistaMedicion.setVisible(false);
        vistaMedicion.dispose();
    }

    // Configurar todos los listeners en un solo método
    private void configurarListeners() {
        handleAgregar();
        handleBuscar();
        handleReporte();
    }
}
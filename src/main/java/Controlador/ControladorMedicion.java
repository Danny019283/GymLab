package Controlador;

import AccesoADatos.*;
import Modelo.DTOs.MedicionDTO;
import Modelo.Servicios.ServicioMedicion;
import Vista.Formularios.FormularioMedicion;
import Vista.VistaMedicion;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorMedicion {
    private final VistaMedicion vistaMedicion;
    private final ServicioMedicion servicioMedicion;

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
            ArrayList<MedicionDTO> mediciones = servicioMedicion.buscarHistorialDeMedicionEnBD(cedula);
            refrescarMedicionnes(mediciones);
        } catch (GlobalException e) {
            vistaMedicion.mostrarError("Error al buscar mediciones: " + e.getMessage());
        }
    }



    public void registrarMedicion() {
        FormularioMedicion formulario = new FormularioMedicion();
        boolean resultado = formulario.mostrarDialogo("Agregar Medición");

        if (!resultado) {
            return;
        }
        if(!formulario.validarDatos()) {
            return;
        }
        try {
            MedicionDTO medicion = new MedicionDTO.Builder()
                    .cedulaCliente(formulario.getCedula())
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

            switch (servicioMedicion.insertarMedicionEnBD(medicion)){
                case 0:
                    vistaMedicion.mostrarMensaje("Medición registrada exitosamente");
                    // Refrescar búsqueda si ya se estaba mostrando este cliente
                    if (vistaMedicion.getTextoBusqueda().equals(formulario.getCedula())) {
                        buscarMedicionesPorCliente();
                    }
                    break;
                case 1:
                    vistaMedicion.mostrarError("El cliente no existe");
                    break;
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
        boolean haceEjercicio = JOptionPane.showConfirmDialog(vistaMedicion,
                "¿El cliente hace ejercicio regularmente?", "Reporte",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        try {
            MedicionDTO medicion = vistaMedicion.getMedicionSeleccionada();
            String reporte = servicioMedicion.generarReporte(medicion, haceEjercicio);
            JOptionPane.showMessageDialog(vistaMedicion, reporte, "Reporte de Medición",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            vistaMedicion.mostrarError("Error al generar reporte: " + e.getMessage());
        }
    }

    public void refrescarMedicionnes(ArrayList<MedicionDTO> mediciones) {
        vistaMedicion.getTablaMedicion().refrescarData(mediciones);
    }

    /////////////////////////////////////////////HANDLERS

    public void handlerBuscar() {
        vistaMedicion.addBuscarListener(e -> buscarMedicionesPorCliente());
    }
    public void handlerAgregar() {
        vistaMedicion.addAgregarListener(e -> registrarMedicion());
    }
    public void handlerReporte() {
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
        handlerAgregar();
        handlerBuscar();
        handlerReporte();
    }
}
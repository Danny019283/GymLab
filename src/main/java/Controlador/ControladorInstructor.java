package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.DAOInstructor;
import Modelo.DTOs.InstructorDTO;
import Modelo.Instructor;
import Modelo.Rutina;
import Modelo.Servicios.ServicioInstructor;
import Modelo.Servicios.ServicioRutina;
import Vista.Formularios.FormularioAsignarRutina;
import Vista.Formularios.FormularioInstructor;
import Vista.VistaInstructor;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorInstructor {
    private final ServicioInstructor servicioInstructor;
    private final VistaInstructor vistaInstructor;
    private Runnable accionAtras;

    //Constructor
    public ControladorInstructor() {
        this.vistaInstructor = new VistaInstructor();
        this.servicioInstructor = new ServicioInstructor();
    }

    ////////////////////////////////////////FUNCIONES DE NEGOCIO
    /////////////////////////////////////////////////

    public void registrarInstructor() throws GlobalException {
        FormularioInstructor formulario = new FormularioInstructor();
        boolean resultado = formulario.mostrarDialogo("Agregar Instructor");

        if (!resultado) {
            return;
        }
        if (!formulario.validarDatos()) {
            return;
        }

        // Procesar especialidades (separadas por comas)
        ArrayList<String> especialidades = new ArrayList<>();
        String[] especialidadesArray = formulario.getEspecialidades().split(",");
        for (String esp : especialidadesArray) {
            especialidades.add(esp.trim());
        }

        InstructorDTO dto = new InstructorDTO.Builder()
                .cedula(formulario.getCedula())
                .nombreCom(formulario.getNombreCom())
                .telef(formulario.getTelefono())
                .correo(formulario.getCorreo())
                .fechaNac(formulario.getFechaNac())
                .especialidad(especialidades)
                .codigoSucursal(formulario.getCodigoSucursal())
                .build();

        switch (servicioInstructor.insertarInstructorEnBD(dto)) {
            case 0:
                refrescarTabla();
            case 1:
                vistaInstructor.mostrarError("El instructor ya existe.");
                break;
            case 2:
                vistaInstructor.mostrarError("La sucursal no existe.");
                break;
            default:
                vistaInstructor.mostrarError("Error desconocido al insertar instructor.");
                break;
        }
    }

    public void modificarInstructor() throws GlobalException, NoDataException {
        FormularioInstructor formulario = new FormularioInstructor();
        boolean resultado = formulario.mostrarDialogo("Modificar Instructor");

        if (!resultado) {
            return;
        }
        if(!formulario.validarDatos()) {
            return;
        }

        ArrayList<String> especialidades = new ArrayList<>();
        String[] especialidadesArray = formulario.getEspecialidades().split(",");
        for (String esp : especialidadesArray) {
            especialidades.add(esp.trim());
        }

        InstructorDTO instructor = new InstructorDTO.Builder()
                .cedula(formulario.getCedula())
                .nombreCom(formulario.getNombreCom())
                .telef(formulario.getTelefono())
                .correo(formulario.getCorreo())
                .fechaNac(formulario.getFechaNac())
                .especialidad(especialidades)
                .codigoSucursal(formulario.getCodigoSucursal())
                .build();

        if (!servicioInstructor.actualizarInstructorEnBD(instructor)) {
            vistaInstructor.mostrarError("El instructor no existe.");
        }
        refrescarTabla();
    }

    public void refrescarTabla() {
        ArrayList<InstructorDTO> instructores = servicioInstructor.obtenerInstructoresEnBD();
        if (instructores == null || instructores.isEmpty()) {
            return;
        }
        vistaInstructor.getTablaInstructor().refrescarData(instructores);
    }

    public void asignarRutinaCliente() {
        try {
            FormularioAsignarRutina formulario = new FormularioAsignarRutina();
            boolean resultado = formulario.mostrarDialogo("Asignar Rutina a Cliente");

            if (!resultado) {
                return;
            }
            if(!formulario.validarDatos()) {
                return;
            }

            String pecho = formulario.getPecho();
            String triceps = formulario.getTriceps();
            String biceps = formulario.getBiceps();
            String piernas = formulario.getPiernas();
            String espalda = formulario.getEspalda();
            Rutina rutina = new Rutina(pecho, triceps, biceps, piernas, espalda);
            if(!servicioInstructor.asignarRutinaACliente(formulario.getCedulaCliente(), rutina)) {
                vistaInstructor.mostrarError("El cliente no existe.");
                return;
            }
            vistaInstructor.mensaje("Éxito", "Rutina asignada correctamente al cliente");

        } catch (Exception ex) {
            vistaInstructor.mostrarError("Error al asignar rutina: " + ex.getMessage());
        }
    }

    ////////////////////////////////ActionListener/Handler
    /////////////////////////////////////////////////////

    public void handlerRegistrar() {
        this.vistaInstructor.addRegistrarListener(e -> {
            try {
                registrarInstructor();
            } catch (GlobalException ex) {
                vistaInstructor.mostrarError("Error al registrar instructor: " + ex.getMessage());
            }
        });
    }

    public void handlerModificar() {
        this.vistaInstructor.addModificarListener(e -> {
            try {
                modificarInstructor();
            } catch (GlobalException | NoDataException ex) {
                vistaInstructor.mostrarError("Error al modificar instructor: " + ex.getMessage());
            }
        });
    }

    public void handlerAsignarRutina() {
        this.vistaInstructor.addAsignarRutinaListener(e -> {
            asignarRutinaCliente();
        });
    }

    public void handlerBarraBusqueda () {
        vistaInstructor.addBuscarListener(e ->
        {
            String cedula = vistaInstructor.getTxtBuscarCedula();
            if (cedula.isEmpty()) {
                vistaInstructor.getSorter().setRowFilter(null); // mostrar todo
            } else {
                vistaInstructor.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + cedula));
            }
        });
    }

    /// ///////////////////////////Cargar datos a la tabla
    /// //////////////////////////////////////////////////////////////

    public void agregarTodosLosInstructores() throws NoDataException, GlobalException {
        refrescarTabla();
    }

    ////////////////////////////////CONFIGURACIÓN DE VENTANA
    ///////////////////////////////////////////////////////

    public void configurarBotonAtras(Runnable accionAtras) {
        this.accionAtras = accionAtras;
        vistaInstructor.addAtrasListener(e -> accionAtras.run());
    }

    public void mostrarVentana() {
        try {
            agregarTodosLosInstructores();
            configurarListeners();
            vistaInstructor.setVisible(true);
        } catch (Exception e) {
            vistaInstructor.mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    public void cerrarVentana() {
        vistaInstructor.setVisible(false);
        vistaInstructor.dispose();
    }

    private void configurarListeners() {
        handlerRegistrar();
        handlerModificar();
        handlerAsignarRutina();
        handlerBarraBusqueda();
    }
}
package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.DTOs.ClaseGrupalDTO;
import Modelo.Servicios.ServicioClaseGrupal;
import Vista.Formularios.FormularioClaseGrupal;
import Vista.Formularios.FormularioMatricula;
import Vista.VistaClaseGrupal;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorClaseGrupal {
    ////////////////////////////////Instancias
    //////////////////////////////////////////
    private final ServicioClaseGrupal servicioClaseGrupal = new ServicioClaseGrupal();
    private final VistaClaseGrupal vistaClaseGrupal;
    private Runnable accionAtras;

    //Constructor
    public ControladorClaseGrupal() {
        this.vistaClaseGrupal = new VistaClaseGrupal();
    }

    ////////////////////////////////////////FUNCIONES DE NEGOCIO
    /////////////////////////////////////////////////
    public void registrarClaseGrupal() throws GlobalException {
        FormularioClaseGrupal formulario = new FormularioClaseGrupal();
        boolean resultado = formulario.mostrarDialogo("Agregar Clase Grupal");

        if (!resultado) {
            return;
        }
        if(!formulario.validarDatos()) {
            return;
        }

        String codigo = formulario.getCodigo();
        int cupoMax = formulario.getCupoMax();
        int numSalon = formulario.getNumSalon();
        String especialidad = formulario.getEspecialidad();
        String horario = formulario.getHorario();
        String instructorCedula = formulario.getInstructor();

        ClaseGrupalDTO dto = new ClaseGrupalDTO.Builder()
                .codigo(codigo)
                .cupoMax(cupoMax)
                .numSalon(numSalon)
                .especialidad(especialidad)
                .horario(horario)
                .cedulaInstructor(instructorCedula)
                .build();

        switch (servicioClaseGrupal.insertarClaseGrupalEnBD(dto)) {
            case 0:
                refrescarTabla();
                vistaClaseGrupal.mostrarMensaje("Éxito", "Clase grupal registrada correctamente");
                break;
            case 1:
                vistaClaseGrupal.mostrarError("El instructor no existe");
                break;
            case 2:
                vistaClaseGrupal.mostrarError("La clase grupal ya existe");
                break;
            case 3:
                vistaClaseGrupal.mostrarError("El instructor no tiene la especialidad requerida");
                break;
            default:
                vistaClaseGrupal.mostrarError("No se pudo registrar la clase grupal");
                break;
        }
    }

    public void modificarClaseGrupal() throws GlobalException {
        FormularioClaseGrupal formulario = new FormularioClaseGrupal();
        boolean resultado = formulario.mostrarDialogo("Modificar Clase Grupal");

        if (!resultado) {
            return;
        }
        if(!formulario.validarDatos()) {
            return;
        }

        String codigo = formulario.getCodigo();
        int cupoMax = formulario.getCupoMax();
        int numSalon = formulario.getNumSalon();
        String especialidad = formulario.getEspecialidad();
        String horario = formulario.getHorario();
        String instructorCedula = formulario.getInstructor();

        ClaseGrupalDTO dto = new ClaseGrupalDTO.Builder()
                .codigo(codigo)
                .cupoMax(cupoMax)
                .numSalon(numSalon)
                .especialidad(especialidad)
                .horario(horario)
                .cedulaInstructor(instructorCedula)
                .build();

        switch (servicioClaseGrupal.actualizarClaseGrupalEnBD(dto)) {
            case 0:
                vistaClaseGrupal.mostrarMensaje("Éxito", "Clase grupal modificada correctamente");
                refrescarTabla();
                break;
            case 1:
                vistaClaseGrupal.mostrarError("El instructor no existe");
                break;
            case 2:
                vistaClaseGrupal.mostrarError("La clase grupal no existe");
                break;
            default:
                vistaClaseGrupal.mostrarError("No se pudo modificar la clase grupal");
                break;
        }
    }

    public void eliminarClaseGrupal() throws GlobalException {
        String codigo = vistaClaseGrupal.pedirDato("Ingrese el código de la clase a eliminar");
        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }

        if (!servicioClaseGrupal.eliminarClaseGrupalEnBD(codigo)) {
            vistaClaseGrupal.mostrarError("Clase inexistente.");
            return;
        }
        refrescarTabla();
        vistaClaseGrupal.mostrarMensaje("Éxito", "Clase grupal eliminada correctamente");
    }

    public void refrescarTabla() {
        ArrayList<ClaseGrupalDTO> clases = servicioClaseGrupal.obtenerClasesGrupalesEnBD();
        if (clases == null || clases.isEmpty()) {
            return;
        }
        vistaClaseGrupal.getTablaClaseGrupal().refrescarData(clases);
    }

    public void listarClientesPorClase() {
        String codigo = vistaClaseGrupal.pedirDato("Ingrese el código de la clase");
        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }
        String clientes = servicioClaseGrupal.listarClientesPorClase(codigo);
        if (clientes.isEmpty()) {
            vistaClaseGrupal.mostrarError("El cliente no tiene clases registradas");
        }
        vistaClaseGrupal.mostrarMensaje("Clases", clientes);
    }

    public void matricularCliente() throws NoDataException, GlobalException {
        FormularioMatricula formularioMatricula = new FormularioMatricula();
        boolean resultado = formularioMatricula.mostrarDialogo("Matricular Cliente");
        if (!resultado) {
            return;
        }
        if(!formularioMatricula.validarDatos()) {
            vistaClaseGrupal.mostrarError("Todos los campos son obligatorios.");
            return;
        }

        String cedula = formularioMatricula.getCedulaCliente();
        String codClase = formularioMatricula.getCodClase();

        switch (servicioClaseGrupal.matricularClienteEnClase(codClase, cedula)) {
            case 0:
                vistaClaseGrupal.mostrarMensaje("Confirmación", "Cliente matriculado con éxito.");
                break;
            case 1:
                vistaClaseGrupal.mostrarError("La clase no existe.");
                break;
            case 2:
                vistaClaseGrupal.mostrarError("El cliente no existe.");
                break;
            case 3:
                vistaClaseGrupal.mostrarError("No hay cupos disponibles en la clase.");
                break;
            case 4:
                vistaClaseGrupal.mostrarError("El cliente ya está inscrito en 3 clases.");
                break;
            default:
                vistaClaseGrupal.mostrarError("No se pudo matricular el cliente.");
                break;
        }
    }

    ////////////////////////////////ActionListener/Handler
    /////////////////////////////////////////////////////
    public void handlerRegistrar() {
        this.vistaClaseGrupal.addRegistrarListener(e -> {
            try {
                registrarClaseGrupal();
            } catch (GlobalException ex) {
                vistaClaseGrupal.mostrarError("Error al registrar: " + ex.getMessage());
            }
        });
    }

    public void handlerModificar() {
        this.vistaClaseGrupal.addModificarListener(e -> {
            try {
                modificarClaseGrupal();
            } catch (GlobalException ex) {
                vistaClaseGrupal.mostrarError("Error al modificar: " + ex.getMessage());
            }
        });
    }

    public void handlerEliminar() {
        this.vistaClaseGrupal.addEliminarListener(e -> {
            try {
                eliminarClaseGrupal();
            } catch (GlobalException ex) {
                vistaClaseGrupal.mostrarError("Error al eliminar: " + ex.getMessage());
            }
        });
    }

    public void handlerListarClientes() {
        this.vistaClaseGrupal.addListarClientesListener(e -> {
            listarClientesPorClase();
        });
    }

    public void handlerBarraBusqueda() {
        this.vistaClaseGrupal.addBuscarListener(e -> {
            String codigo = vistaClaseGrupal.getTxtBuscarCodigo();
            if (codigo.isEmpty()) {
                vistaClaseGrupal.getSorter().setRowFilter(null); // mostrar todo
            } else {
                vistaClaseGrupal.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + codigo));
            }
        });
    }

    /// ///////////////////////////Cargar datos a la tabla
    /// //////////////////////////////////////////////////////////////
    public void agregarTodasLasClasesGrupales() {
        refrescarTabla();
    }

    ////////////////////////////////CONFIGURACIÓN DE VENTANA
    ///////////////////////////////////////////////////////
    public void configurarBotonAtras(Runnable accionAtras) {
        this.accionAtras = accionAtras;
        vistaClaseGrupal.addAtrasListener(e -> accionAtras.run());
    }

    public void mostrarVentana() {
        try {
            agregarTodasLasClasesGrupales();
            configurarListeners();
            vistaClaseGrupal.setVisible(true);
        } catch (Exception e) {
            vistaClaseGrupal.mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    public void cerrarVentana() {
        vistaClaseGrupal.setVisible(false);
        vistaClaseGrupal.dispose();
    }

    private void configurarListeners() {
        handlerRegistrar();
        handlerModificar();
        handlerEliminar();
        handlerListarClientes();
        handlerBarraBusqueda();
    }
}
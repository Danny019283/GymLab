package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.DAORegistroClases;
import AccesoADatos.DAOClaseGrupal;
import Modelo.ClaseGrupal;
import Modelo.Cliente;
import Vista.Formularios.FormularioClaseGrupal;
import Vista.Formularios.FormularioMatricula;
import Vista.VistaClaseGrupal;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorClaseGrupal {
    ////////////////////////////////Instancias
    //////////////////////////////////////////
    private final DAOClaseGrupal DAOClaseGrupal;
    private final DAORegistroClases DAORegistroClases;
    private final VistaClaseGrupal vistaClaseGrupal;
    private Runnable accionAtras;

    //Constructor
    public ControladorClaseGrupal() {
        this.DAOClaseGrupal = new DAOClaseGrupal();
        this.DAORegistroClases = new DAORegistroClases();
        this.vistaClaseGrupal = new VistaClaseGrupal();
    }

    ////////////////////////////////////////FUNCIONES
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

        // Validar que el instructor tenga la especialidad
        ControladorInstructor controlInstructor = new ControladorInstructor();
        String instructor = controlInstructor.buscarInstructor(instructorCedula);

        boolean coincide = false;
        for (int i = 0; i < instructor.getEspecialidad().size(); i++) {
            if (instructor.getEspecialidad().get(i).equals(especialidad)) {
                coincide = true;
                break;
            }
        }

        if (!coincide) {
            vistaClaseGrupal.mostrarError("El instructor no tiene la especialidad requerida");
            return;
        }

        ClaseGrupal claseGrupal = new ClaseGrupal(codigo, cupoMax, numSalon, especialidad, horario, instructor);
        try {
            DAOClaseGrupal.insertarClaseGrupal(claseGrupal);
            vistaClaseGrupal.getTablaClaseGrupal().add(claseGrupal);
            vistaClaseGrupal.mostrarToSting("Éxito", "Clase grupal registrada correctamente");
        } catch (GlobalException | NoDataException e) {
            vistaClaseGrupal.mostrarError("Error al registrar clase: " + e.getMessage());
        }
    }

    public void modificarClaseGrupal() throws GlobalException, NoDataException {
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

        ClaseGrupal claseGrupal = DAOClaseGrupal.buscarClaseGrupal(codigo);
        if (claseGrupal == null) {
            vistaClaseGrupal.mostrarError("No se encontró la clase con código: " + codigo);
            return;
        }

        claseGrupal.setCupoMax(cupoMax);
        claseGrupal.setNumSalon(numSalon);
        claseGrupal.setEspecialidad(especialidad);
        claseGrupal.setHorario(horario);

        ControladorInstructor controlInstructor = new ControladorInstructor();
        String instructor = controlInstructor.buscarInstructor(instructorCedula);
        claseGrupal.setInstructor(instructor);

        try {
            DAOClaseGrupal.modificarClaseGrupal(claseGrupal);
            vistaClaseGrupal.getTablaClaseGrupal().refrescarData(mostrarClasesGrupales());
            vistaClaseGrupal.mostrarToSting("Éxito", "Clase grupal modificada correctamente");
        } catch (GlobalException e) {
            vistaClaseGrupal.mostrarError("Error al modificar clase: " + e.getMessage());
        }
    }

    public boolean eliminarClaseGrupal() throws GlobalException, NoDataException {
        String codigo = vistaClaseGrupal.pedirDato("Ingrese el código de la clase a eliminar");
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        boolean seElimino = DAOClaseGrupal.eliminarClaseGrupal(codigo);
        if (seElimino) {
            vistaClaseGrupal.getTablaClaseGrupal().refrescarData(mostrarClasesGrupales());
            vistaClaseGrupal.mostrarToSting("Éxito", "Clase grupal eliminada correctamente");
            return true;
        } else {
            vistaClaseGrupal.mostrarError("No se pudo eliminar la clase");
            return false;
        }
    }

    public ClaseGrupal buscarClaseGrupal(String codigo) throws GlobalException {
        return DAOClaseGrupal.buscarClaseGrupal(codigo);
    }

    public ArrayList<ClaseGrupal> mostrarClasesGrupales() throws GlobalException {
        return DAOClaseGrupal.listarClasesGrupales();
    }

    public void listarClientesPorClase() throws GlobalException {
        String codigoClase = vistaClaseGrupal.pedirDato("Ingrese el código de la clase");
        if (codigoClase == null || codigoClase.trim().isEmpty()) {
            return;
        }

        ArrayList<Cliente> clientes = DAORegistroClases.buscarClientesSegunClase(codigoClase);

        if (clientes != null && !clientes.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Clientes inscritos en la clase ").append(codigoClase).append(":\n\n");
            for (Cliente cliente : clientes) {
                sb.append("- ").append(cliente.getNombre()).append(" (").append(cliente.getCedula()).append(")\n");
            }
            vistaClaseGrupal.mostrarToSting("Clientes por Clase", sb.toString());
        } else {
            vistaClaseGrupal.mostrarToSting("Clientes por Clase", "No hay clientes inscritos en esta clase");
        }
    }

    public int matricularCliente() throws NoDataException, GlobalException {
        FormularioMatricula formularioMatricula = new FormularioMatricula();
        boolean resultado = formularioMatricula.mostrarDialogo("Matricular Cliente");
        if (!resultado) {
            return 3;
        }

        String cedula = formularioMatricula.getCedulaCliente();
        String codClase = formularioMatricula.getCodClase();

        int clasesInscritas = DAORegistroClases.verificarClasesCliente(cedula);
        if (clasesInscritas >= 3) {
            return 1; //el cliente ya está inscrito en 3 clases
        }
        int cuposDisponibles = DAORegistroClases.verificarCupoClase(codClase);
        if (cuposDisponibles <= 0) {
            return 2; //no hay cupo
        }
        DAORegistroClases.insertarClaseCliente(codClase, cedula);
        return 0; //se inserto con exito
    }

    ////////////////////////////////ActionListener/Handle
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
            } catch (GlobalException | NoDataException ex) {
                vistaClaseGrupal.mostrarError("Error al modificar: " + ex.getMessage());
            }
        });
    }

    public void handlerEliminar() {
        this.vistaClaseGrupal.addEliminarListener(e -> {
            try {
                eliminarClaseGrupal();
            } catch (GlobalException | NoDataException ex) {
                vistaClaseGrupal.mostrarError("Error al eliminar: " + ex.getMessage());
            }
        });
    }

    public void handlerListarClientes() {
        this.vistaClaseGrupal.addListarClientesListener(e -> {
            try {
                listarClientesPorClase();
            } catch (GlobalException ex) {
                vistaClaseGrupal.mostrarError("Error al listar clientes: " + ex.getMessage());
            }
        });
    }

    public void handlerBarraBusqueda() {
        this.vistaClaseGrupal.addBuscarListener(e ->
        {
            String codigo = vistaClaseGrupal.getTxtBuscarCodigo();
            if (codigo.isEmpty()) {
                vistaClaseGrupal.getSorter().setRowFilter(null); // mostrar todo
            } else {
                vistaClaseGrupal.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + codigo));
            }
        });
    }

    // Configurar para volver al menu anterior
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

    public void agregarTodasLasClasesGrupales() throws NoDataException, GlobalException {
        vistaClaseGrupal.getTablaClaseGrupal().refrescarData(mostrarClasesGrupales());
    }

}
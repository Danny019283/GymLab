package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioInstructor;
import Modelo.Instructor;
import Modelo.Rutina;
import Vista.Formularios.FormularioAsignarRutina;
import Vista.Formularios.FormularioInstructor;
import Vista.VistaInstructor;

import java.util.ArrayList;

public class ControladorInstructor {

    ////////////////////////////////Instancias
    //////////////////////////////////////////
    private final ServicioInstructor servicioInstructor;
    private final VistaInstructor vistaInstructor;
    private Runnable accionAtras;

    //Constructor
    public ControladorInstructor() {
        this.servicioInstructor = new ServicioInstructor();
        this.vistaInstructor = new VistaInstructor();
    }

    public ServicioInstructor getServicioInstructor() {
        return servicioInstructor;
    }

    ////////////////////////////////////////FUNCIONES DE NEGOCIO
    /////////////////////////////////////////////////

    /**
     * Registra un instructor en la base de datos
     */
    public void registrarInstructor() throws GlobalException {
        FormularioInstructor formulario = new FormularioInstructor();
        boolean resultado = formulario.mostrarDialogo("Agregar Instructor");

        if (!resultado) {
            return;
        }

        // Procesar especialidades (separadas por comas)
        ArrayList<String> especialidades = new ArrayList<>();
        String[] especialidadesArray = formulario.getEspecialidades().split(",");
        for (String esp : especialidadesArray) {
            especialidades.add(esp.trim());
        }

        Instructor instructor = new Instructor.Builder()
                .cedula(formulario.getCedula())
                .nombreCom(formulario.getNombreCom())
                .telef(formulario.getTelefono())
                .correo(formulario.getCorreo())
                .fechaNac(formulario.getFechaNac())
                .especialidad(especialidades)
                .codigoSucursal(formulario.getCodigoSucursal())
                .build();

        try {
            servicioInstructor.insertarInstructor(instructor);
            vistaInstructor.mensaje("Éxito", "Instructor registrado correctamente");
            vistaInstructor.getTablaInstructor().refrescarData(listarInstructores());
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Modifica los datos de un instructor existente
     */
    public void modificarInstructor() throws GlobalException, NoDataException {
        FormularioInstructor formulario = new FormularioInstructor();
        boolean resultado = formulario.mostrarDialogo("Modificar Instructor");

        if (!resultado) {
            return;
        }

        ArrayList<String> especialidades = new ArrayList<>();
        String[] especialidadesArray = formulario.getEspecialidades().split(",");
        for (String esp : especialidadesArray) {
            especialidades.add(esp.trim());
        }

        Instructor instructor = new Instructor.Builder()
                .cedula(formulario.getCedula())
                .nombreCom(formulario.getNombreCom())
                .telef(formulario.getTelefono())
                .correo(formulario.getCorreo())
                .fechaNac(formulario.getFechaNac())
                .especialidad(especialidades)
                .codigoSucursal(formulario.getCodigoSucursal())
                .build();

        try {
            servicioInstructor.actualizarInstructor(instructor);
            vistaInstructor.mensaje("Éxito", "Instructor modificado correctamente");
            vistaInstructor.getTablaInstructor().refrescarData(listarInstructores());
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un instructor de la base de datos
     */
    public boolean eliminarInstructor() throws GlobalException, NoDataException {
        String cedula = vistaInstructor.pedirDato("Ingrese la cédula del instructor a eliminar:");
        if (cedula != null && !cedula.trim().isEmpty()) {
            servicioInstructor.eliminarInstructor(cedula);
            vistaInstructor.mensaje("Éxito", "Instructor eliminado correctamente");
            vistaInstructor.getTablaInstructor().refrescarData(listarInstructores());
            return true;
        }
        return false;
    }

    /**
     * Busca un instructor por su cédula
     */
    public Instructor buscarInstructor(String cedula) throws GlobalException {
        return servicioInstructor.buscarInstructor(cedula);
    }

    /**
     * Obtiene la lista de todos los instructores
     */
    public ArrayList<Instructor> listarInstructores() throws GlobalException {
        return servicioInstructor.listarInstructores();
    }

    /**
     * Asigna una rutina a un cliente (funcionalidad del instructor)
     */
    public void asignarRutinaCliente() {
        try {
            FormularioAsignarRutina formulario = new FormularioAsignarRutina();
            boolean resultado = formulario.mostrarDialogo("Asignar Rutina a Cliente");

            if (resultado) {
                ControladorRutina controladorRutina = new ControladorRutina();
                String pecho = formulario.getPecho();
                String triceps = formulario.getTriceps();
                String biceps = formulario.getBiceps();
                String piernas = formulario.getPiernas();
                String espalda = formulario.getEspalda();
                Rutina rutina = controladorRutina.crearRutina(pecho, triceps, biceps, piernas, espalda);
                controladorRutina.asignarRutinaACliente(formulario.getCedulaCliente(), rutina);

                vistaInstructor.mensaje("Éxito", "Rutina asignada correctamente al cliente");
            }
        } catch (Exception ex) {
            vistaInstructor.mostrarError("Error al asignar rutina: " + ex.getMessage());
        }
    }

    ////////////////////////////////ActionListener/Handle
    /////////////////////////////////////////////////////

    public void handleRegistrar() {
        this.vistaInstructor.addRegistrarListener(e -> {
            try {
                registrarInstructor();
            } catch (GlobalException ex) {
                vistaInstructor.mostrarError("Error al registrar instructor: " + ex.getMessage());
            }
        });
    }

    public void handleModificar() {
        this.vistaInstructor.addModificarListener(e -> {
            try {
                modificarInstructor();
            } catch (GlobalException | NoDataException ex) {
                vistaInstructor.mostrarError("Error al modificar instructor: " + ex.getMessage());
            }
        });
    }

    public void handleEliminar() {
        this.vistaInstructor.addEliminarListener(e -> {
            try {
                eliminarInstructor();
            } catch (GlobalException | NoDataException ex) {
                vistaInstructor.mostrarError("Error al eliminar instructor: " + ex.getMessage());
            }
        });
    }

    public void handleAsignarRutina() {
        this.vistaInstructor.addAsignarRutinaListener(e -> {
            asignarRutinaCliente();
        });
    }

    /// ///////////////////////////Cargar datos a la tabla
    /// //////////////////////////////////////////////////////////////

    /**
     * Carga todos los instructores en la tabla
     */
    public void agregarTodosLosInstructores() throws NoDataException, GlobalException {
        vistaInstructor.getTablaInstructor().refrescarData(listarInstructores());
    }

    ////////////////////////////////CONFIGURACIÓN DE VENTANA
    ///////////////////////////////////////////////////////

    /**
     * Configura la acción del botón Atrás
     */
    public void configurarBotonAtras(Runnable accionAtras) {
        this.accionAtras = accionAtras;
        vistaInstructor.addAtrasListener(e -> accionAtras.run());
    }

    /**
     * Muestra la ventana de gestión de instructores
     */
    public void mostrarVentana() {
        try {
            agregarTodosLosInstructores();
            configurarListeners();
            vistaInstructor.setVisible(true);
        } catch (Exception e) {
            vistaInstructor.mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Cierra la ventana de gestión de instructores
     */
    public void cerrarVentana() {
        vistaInstructor.setVisible(false);
        vistaInstructor.dispose();
    }

    /**
     * Configura todos los listeners de la interfaz
     */
    private void configurarListeners() {
        handleRegistrar();
        handleModificar();
        handleEliminar();
        handleAsignarRutina();
    }
}
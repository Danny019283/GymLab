package Controlador;
import AccesoADatos.*;
import Modelo.Cliente;
import Modelo.Instructor;
import Modelo.Rutina;
import Modelo.Sucursal;
import Vista.Formularios.FormularioCliente;
import Vista.VistaCliente;
import java.util.ArrayList;

public class ControladorCliente {
    ////////////////////////////////Instancias
    //////////////////////////////////////////
    private final ServicioCliente servicioCliente;
    private final VistaCliente vistaCliente;
    private final ControladorMedicion controladorMedicion;
    private Runnable accionAtras;

    //Constructor
    public ControladorCliente() {
        this.servicioCliente = new ServicioCliente();
        this.vistaCliente = new VistaCliente();
        this.controladorMedicion = new ControladorMedicion();
    }

    ////////////////////////////////////////FUNCIONES
    /////////////////////////////////////////////////
    public void registrarCliente() throws GlobalException {
        FormularioCliente formulario = new FormularioCliente();
        boolean resultado = formulario.mostrarDialogo("Agregar Cliente"); // o "Modificar Cliente"

        if (!resultado) {
            return;
        }
        // El usuario hizo clic en OK, obtener los datos
        String cedula = formulario.getCedula();
        String nombre = formulario.getNombre();
        int telefono = formulario.getTelefono();
        String correo = formulario.getCorreo();
        String fechaNac = formulario.getFechaNac();
        String sexo = formulario.getSexo();
        String fechaInscrip = formulario.getFechaInscrip();
        int edad = formulario.getEdad();
        String instructorId = formulario.getInstructor();
        String sucursalCod = formulario.getSucursal();

        //Construccion de cliente, agregar a BD
        ControladorInstructor controlInstructor = new ControladorInstructor();
        ControladorSucursal controladorSucursal = new ControladorSucursal();
        Instructor instructor = controlInstructor.getServicioInstructor().buscarInstructor(instructorId);
        Sucursal sucursal = controladorSucursal.getServicioSucursal().buscarSucursal(sucursalCod);

        Cliente cliente = new Cliente.Builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .correo(correo)
                .fechaNac(fechaNac)
                .sexo(sexo)
                .fechaInscrip(fechaInscrip)
                .edad(edad)
                .instructor(instructor)
                .sucursal(sucursal)
                .build();
        try {
            servicioCliente.insertarCliente(cliente);
            vistaCliente.getTablaCliente().add(cliente);
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }


    public void modificarCliente() throws GlobalException, NoDataException {
        FormularioCliente formulario = new FormularioCliente();
        boolean resultado = formulario.mostrarDialogo("Agregar Cliente"); // o "Modificar Cliente"

        if (!resultado) {
            return;
        }
        // El usuario hizo clic en OK, obtener los datos
        String cedula = formulario.getCedula();
        String nombre = formulario.getNombre();
        int telefono = formulario.getTelefono();
        String correo = formulario.getCorreo();
        String fechaNac = formulario.getFechaNac();
        String sexo = formulario.getSexo();
        int edad = formulario.getEdad();
        String instructorId = formulario.getInstructor();
        String sucursalCod = formulario.getSucursal();

        //creacion del cliente, actualizar BD
        Cliente cliente = servicioCliente.buscarCliente(cedula);
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setFechaNac(fechaNac);
        cliente.setSexo(sexo);
        cliente.setEdad(edad);
        cliente.setInstructor(new Instructor.Builder()
                .cedula(instructorId).build()
        );
        cliente.setSucursal(new Sucursal.Builder()
                .cod(sucursalCod).build());
        vistaCliente.getTablaCliente().refrescarData(mostrarClientes());
        try{
            servicioCliente.actualizarCliente(cliente);
            vistaCliente.getTablaCliente().add(cliente);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public  boolean eliminarCliente() throws GlobalException, NoDataException {
        String cedula = vistaCliente.pedirDato("Ingrese la cedula");
        boolean seElimino = servicioCliente.eliminarCliente(cedula);
        if (seElimino) {
            vistaCliente.getTablaCliente().refrescarData(mostrarClientes());
            return true;
        }
        return false;
    }

    public Cliente buscarCliente(String cedula) throws GlobalException {
        return servicioCliente.buscarCliente(cedula);
    }

    public ArrayList<Cliente> mostrarClientes() throws GlobalException, NoDataException {
        return servicioCliente.listarClientes();
    }

    public ArrayList<Cliente> mostrarClientesPorInstructor(String cedulaInstructor) throws GlobalException {
        return servicioCliente.listarClientesPorInstructor(cedulaInstructor);
    }

    public void verRutina() throws GlobalException {
        String cedula = vistaCliente.pedirDato("Ingrese la cedula de cliente");
        ControladorRutina controladorRutina = new ControladorRutina();
        Rutina rutina = controladorRutina.buscarRutina(cedula);
        if (rutina != null) {
            try{
                vistaCliente.mostrarToSting("Rutina: ", rutina.toString());
                return;
            } catch (Exception e) {
                vistaCliente.mostrarError("Error al generar reporte: " + e.getMessage());
            }
        }
        vistaCliente.mostrarToSting("Error", "No tiene una rutina asignada");
    }


    ////////////////////////////////ActionListener/Handle
    /////////////////////////////////////////////////////

    public void handleRegistrar(){
        this.vistaCliente.addRegistrarListener(e ->
                {
                    try {
                        registrarCliente();
                    } catch (GlobalException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }
    public void handleModificar() {
        this.vistaCliente.addModificarListener(e ->
                {
                    try {
                        modificarCliente();
                    } catch (GlobalException | NoDataException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    public void handleEliminar() {
        this.vistaCliente.addEliminarListener(e ->
                {
                    try {
                        eliminarCliente();
                    }catch (GlobalException | NoDataException ex){
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    public void handleMedicion() {
        this.vistaCliente.addMedicionListener(e -> {
            // IMPORTANTE: Configurar el botón atrás ANTES de mostrar la ventana
            controladorMedicion.configurarBotonAtras(() -> {
                controladorMedicion.cerrarVentana();
                vistaCliente.setVisible(true); // Volver a clientes
            });

            vistaCliente.setVisible(false);
            controladorMedicion.mostrarVentana();
        });
    }

    public void handleVerRutina() {
        this.vistaCliente.addVerRutinaListener(e ->
                {
                    try {
                        verRutina();
                    } catch (GlobalException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    public void handleMatricular() {
        this.vistaCliente.addMatricularListener(e ->
                {
                    try {
                        ControladorClaseGrupal controladorClaseGrupal = new ControladorClaseGrupal();
                        int resultadoMatricula = controladorClaseGrupal.matricularCliente();
                        if (resultadoMatricula == 1) {vistaCliente.mostrarError("El cliente ya esta inscrito a 2 clases.");}
                        else if (resultadoMatricula == 2) {vistaCliente.mostrarError("No hay cupos disponibles en la clase");}
                        else if (resultadoMatricula == 0) {vistaCliente.mostrarToSting("Confimacion", "Cliente matriculado con exito.");}

                    } catch (NoDataException ex) {
                        throw new RuntimeException(ex);
                    } catch (GlobalException ex) {
                        throw new RuntimeException(ex);
                    }
                }

        );
    }

    /// ///////////////////////////Cargar datos a la tabla
    /// //////////////////////////////////////////////////////////////
    public void agregarTodosLosClientes() throws NoDataException, GlobalException {
        vistaCliente.getTablaCliente().refrescarData(mostrarClientes());
    }

    // Configurar para volver al menú principal
    public void configurarBotonAtras(Runnable accionAtras) {
        this.accionAtras = accionAtras;
        vistaCliente.addAtrasListener(e -> accionAtras.run());
    }

    public void mostrarVentana() {
        try {
            agregarTodosLosClientes();
            configurarListeners();
            vistaCliente.setVisible(true);
        } catch (Exception e) {
            vistaCliente.mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    public void cerrarVentana() {
        vistaCliente.setVisible(false);
        vistaCliente.dispose();
    }

    private void configurarListeners() {
        handleRegistrar();
        handleModificar();
        handleEliminar();
        handleMedicion();
        handleVerRutina();
        handleMatricular();
    }
}

package Controlador;
import AccesoADatos.*;
import Modelo.Cliente;
import Modelo.Instructor;
import Modelo.Rutina;
import Modelo.Sucursal;
import Vista.FormularioCliente;
import Vista.VistaCliente;

import javax.swing.*;
import java.util.ArrayList;

public class ControladorCliente {
    ////////////////////////////////Instancias
    //////////////////////////////////////////
    private final ServicioCliente servicioCliente;
    private final VistaCliente vistaCliente;

    //Constructor
    public ControladorCliente() {
        this.servicioCliente = new ServicioCliente();
        this.vistaCliente = new VistaCliente();
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
        Instructor instructor = controlInstructor.servicioInstructor.buscarInstructor(instructorId);
        Sucursal sucursal = controladorSucursal.servicioSucursal.buscarSucursal(sucursalCod);

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
        if (rutina == null) {
            vistaCliente.mostrarToSting("Error", "No tiene una rutina asignada");
        }
        try{
            vistaCliente.mostrarToSting("Rutina: ", rutina.toString());
        } catch (Exception e) {
            vistaCliente.mostrarError("Error al generar reporte: " + e.getMessage());
        }
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
        ControladorMedicion medicion = new ControladorMedicion();
        this.vistaCliente.addMedicionListener(e ->

                medicion.iniciarVentana()

        );
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

    /// ///////////////////////////Cargar datos a la tabla
    /// //////////////////////////////////////////////////////////////
    public void agregarTodosLosClientes() throws NoDataException, GlobalException {
        vistaCliente.getTablaCliente().refrescarData(mostrarClientes());
    }

    public void iniciacionDeVentana(){
        SwingUtilities.invokeLater(() -> {
            try {
                ControladorCliente controlador = new ControladorCliente();

                // Aquí conectamos el listener
                controlador.agregarTodosLosClientes();
                controlador.handleRegistrar();
                controlador.handleModificar();
                controlador.handleEliminar();
                controlador.handleMedicion();
                controlador.handleVerRutina();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    //main
    public static void main(String[] args) {
        ControladorCliente control = new ControladorCliente();
        control.iniciacionDeVentana();
    }

}

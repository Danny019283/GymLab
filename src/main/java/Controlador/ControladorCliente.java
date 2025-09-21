package Controlador;
import AccesoADatos.*;
import Modelo.Cliente;
import Modelo.Instructor;
import Modelo.Sucursal;
import Vista.VistaCliente;

public class ControladorCliente {

    private final ServicioCliente servicioCliente;
    private final VistaCliente vistaCliente;

    public ControladorCliente(ServicioCliente servicioCliente, VistaCliente vistaCliente) {
        this.servicioCliente = servicioCliente;
        this.vistaCliente = vistaCliente;
    }

    public boolean registrarCliente(String cedula, String nombre, int telefono, String correo, String fechaNac,
                     String sexo, String fechaInscrip, int edad, Instructor instructor, Sucursal sucursal) {

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
        try{
            servicioCliente.insertarCliente(cliente);
            return true;
        }catch(GlobalException | NoDataException e){
            throw  new RuntimeException(e);
        }
    }


    public boolean modificarCliente(String cedula, String nombre, int telefono, String correo, String fechaNac,
                     String sexo, String fechaInscrip, int edad, Instructor instructor){
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
                .build();
        try{
            servicioCliente.actualizarCliente(cliente);
            return true;
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public  boolean eliminarCliente(String cedula) throws GlobalException {
        return servicioCliente.eliminarCliente(cedula);
    }

    public Cliente buscarCliente(String cedula) throws GlobalException {
        return servicioCliente.buscarCliente(cedula);
    }

    public String mostrarClientes() throws GlobalException, NoDataException {
        return servicioCliente.listarClientes();
    }

    public String mostrarClientesPorInstructor(String cedulaInstructor) throws GlobalException {
        return servicioCliente.listarClientesPorInstructor(cedulaInstructor);
    }
    //main
    public static void main(String[] args) {
        //testing
        try {
            // Crear servicios
            ServicioCliente servicioCliente = new ServicioCliente();
            ServicioSucursal servicioSucursal = new ServicioSucursal();
            ServicioInstructor servicioInstructor = new ServicioInstructor();

            // Instanciar controlador (vista ignorada -> null)
            ControladorCliente controlador = new ControladorCliente(servicioCliente, null);

            // Traer sucursal existente desde la BD
            Sucursal sucursal = servicioSucursal.buscarSucursal("S001");

            // Traer instructor existente desde la BD
            Instructor instructor = servicioInstructor.buscarInstructor("123456789");

            // Registrar cliente con los objetos obtenidos de la BD
            /*
            boolean ok = controlador.registrarCliente(
                    "C001",                // cedula cliente
                    "Ana Rodríguez",       // nombre cliente
                    99998888,              // telefono
                    "ana.rodriguez@correo.com", // correo
                    "1990-03-15",          // fecha nacimiento
                    "F",                   // sexo
                    "2025-09-21",          // fecha inscripción
                    35,                    // edad
                    instructor,            // traído de la BD
                    sucursal               // traído de la BD
            );

            if (ok) {
                System.out.println("Cliente registrado correctamentE");
            }*/

            // Verificar consulta
            System.out.println("chamoy");
            Cliente cliente = controlador.buscarCliente("C001");
            System.out.println("Cliente encontrado: " + cliente.getNombre());

        } catch (GlobalException e) {
            System.err.println("Error global: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
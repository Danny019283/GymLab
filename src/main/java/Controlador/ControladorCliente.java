package Controlador;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.Cliente;
import AccesoADatos.ServicioCliente;
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

    public void mostrarClientes() throws GlobalException {
        //vistaCliente.mostrarClientes(servicioCliente.listarClientes());
        return;
    }

    public String mostrarClientesPorInstructor(String cedulaInstructor) throws GlobalException {
        //return vistaCliente.mostrarClientesPorInstructor(servicioCliente.listarClientesPorInstructor(cedulaInstructor));
        return "";
    }


}

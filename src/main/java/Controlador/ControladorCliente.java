package Controlador;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.Cliente;
import AccesoADatos.ServicioCliente;
import Modelo.Instructor;
import Vista.VistaCliente;

public class ControladorCliente {

    private final ServicioCliente servicioCliente;
    private final VistaCliente vistaCliente;

    public ControladorCliente(ServicioCliente servicioCliente, VistaCliente vistaCliente) {
        this.servicioCliente = servicioCliente;
        this.vistaCliente = vistaCliente;
    }

    public boolean registrarCliente(String cedula, String nombre, int telefono, String correo, String fechaNac,
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
            servicioCliente.insertarCliente(cliente);
            return true;
        }catch(GlobalException | NoDataException e){
            throw  new RuntimeException(e);
        }
    }

    /*
    public boolean modificarCliente(String cedula, String nombre, int telefono, String correo, String fechaNac,
                     String sexo, String fechaInscrip, int edad, Instructor instructor){
        Cliente c = new Cliente(cedula, nombre, edad, telefono, correo, fechaNac, sexo, fechaInscrip, instructor);
        return servicioCliente.actualizarCliente(c);
    }

    public  boolean eliminarCliente(String cedula){
        return servicioCliente.eliminarCliente(cedula);
    }

    public boolean mostrarPagina(int inicio, int fin, int pagina){
        servicioCliente.getPagina(inicio, fin, pagina);
        return true;
    }*/
}

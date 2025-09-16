package Controlador;
import Modelo.Cliente;
import AccesoADatos.ClienteDAO;
import Modelo.Instructor;
import Vista.VistaCliente;

public class ControladorCliente {

    private final ClienteDAO clienteDAO;
    private final VistaCliente vistaCliente;

    public ControladorCliente(ClienteDAO clienteDAO, VistaCliente vistaCliente) {
        this.clienteDAO = clienteDAO;
        this.vistaCliente = vistaCliente;
    }

    public boolean registrarCliente(String cedula, String nombre, int telefono, String correo, String fechaNac,
                     String sexo, String fechaInscrip, int edad, Instructor instructor){
        Cliente c = new Cliente(cedula, nombre, edad, telefono, correo, fechaNac, sexo, fechaInscrip, instructor);
        return clienteDAO.agregarCliente(c);
    }

    public boolean modificarCliente(String cedula, String nombre, int telefono, String correo, String fechaNac,
                     String sexo, String fechaInscrip, int edad, Instructor instructor){
        Cliente c = new Cliente(cedula, nombre, edad, telefono, correo, fechaNac, sexo, fechaInscrip, instructor);
        return clienteDAO.actualizarCliente(c);
    }

    public  boolean eliminarCliente(String cedula){
        return clienteDAO.eliminarCliente(cedula);
    }

    public boolean mostrarPagina(int inicio, int fin, int pagina){
        clienteDAO.getPagina(inicio, fin, pagina);
        return true;
    }
}

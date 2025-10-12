package Modelo.Servicios;

import AccesoADatos.DAOCliente;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.Cliente;
import Modelo.DTOs.ClienteDTO;
import Modelo.Rutina;

import java.util.ArrayList;

public class ServicioCliente {
    DAOCliente dao = new DAOCliente();

    public int insertarClienteEnBD(ClienteDTO dto) throws GlobalException {
        Cliente cliente = Cliente.fromDTO(dto);

        if(buscarClienteEnBD(cliente.getCedula()) != null) {
            return 1; // Cliente ya existe
        }
        //comprobar existencia de sucursal e instructor

        try {
            dao.insertarCliente(cliente);
            return 0;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean actualizarClienteEnBD(ClienteDTO dto) {
        Cliente cliente = Cliente.fromDTO(dto);
        if(buscarClienteEnBD(cliente.getCedula()) == null) {
            return false;// Cliente no existe
        }
        try {
            dao.actualizarCliente(cliente);
            return true;
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }
    public Cliente buscarClienteEnBD(String cedula) {
        try {
            return dao.buscarCliente(cedula);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarClienteEnBD(String cedula) {
        if(buscarClienteEnBD(cedula) == null) {
            return false;// Cliente no existe
        }
        try {
            dao.eliminarCliente(cedula);
            return true;
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ClienteDTO> obtenerClientesEnBD() {
        ArrayList<ClienteDTO> clientesDTO = new ArrayList<>();
        try {
            ArrayList<Cliente> clientes = dao.listarClientes();
            for (Cliente cliente : clientes) {
                clientesDTO.add(cliente.toDTO());
            }
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
        return clientesDTO;
    }
    public ArrayList<ClienteDTO> obtenerClientesPorInstructorEnBD(String cedulaInstructor) {
        return null; //pendiente
    }

    public ArrayList<ArrayList<ClienteDTO>> obtenerClientesPorInstructorEnBD() {
        return null;
    }

    public Rutina obtenerRutinaDeClienteEnBD(String cedulaCliente) {
        return null; //pendiente
    }



}

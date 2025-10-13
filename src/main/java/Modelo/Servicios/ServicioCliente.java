package Modelo.Servicios;

import AccesoADatos.DAOCliente;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.Cliente;
import Modelo.DTOs.ClienteDTO;
import Modelo.Rutina;

import java.util.ArrayList;

public class ServicioCliente {
    private DAOCliente dao;
    private ServicioRutina servicioRutina;
    private ServicioClaseGrupal servicioClaseGrupal;

    private DAOCliente getDao() {
        if (dao == null) {
            dao = new DAOCliente();
        }
        return dao;
    }

    private ServicioRutina getServicioRutina() {
        if (servicioRutina == null) {
            servicioRutina = new ServicioRutina();
        }
        return servicioRutina;
    }

    private ServicioClaseGrupal getServicioClaseGrupal() {
        if (servicioClaseGrupal == null) {
            servicioClaseGrupal = new ServicioClaseGrupal();
        }
        return servicioClaseGrupal;
    }

    public int insertarClienteEnBD(ClienteDTO dto) throws GlobalException {
        Cliente cliente = Cliente.fromDTO(dto);

        if(buscarClienteEnBD(cliente.getCedula()) != null) {
            return 1;
        }

        try {
            getDao().insertarCliente(cliente);
            return 0;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean actualizarClienteEnBD(ClienteDTO dto) {
        Cliente cliente = Cliente.fromDTO(dto);
        if(buscarClienteEnBD(cliente.getCedula()) == null) {
            return false;
        }
        try {
            getDao().actualizarCliente(cliente);
            return true;
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente buscarClienteEnBD(String cedula) {
        try {
            return getDao().buscarCliente(cedula);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarClienteEnBD(String cedula) {
        if(buscarClienteEnBD(cedula) == null) {
            return false;
        }
        try {
            getDao().eliminarCliente(cedula);
            return true;
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ClienteDTO> obtenerClientesEnBD() {
        ArrayList<ClienteDTO> clientesDTO = new ArrayList<>();
        try {
            ArrayList<Cliente> clientes = getDao().listarClientes();
            for (Cliente cliente : clientes) {
                clientesDTO.add(cliente.toDTO());
            }
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
        return clientesDTO;
    }

    public String obtenerClasesSegunCliente(String cedulaCliente) {
        return getServicioClaseGrupal().obtenerClasesSegunCliente(cedulaCliente);
    }
}
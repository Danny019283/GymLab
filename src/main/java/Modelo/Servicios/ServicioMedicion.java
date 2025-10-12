package Modelo.Servicios;

import AccesoADatos.DAOMedicion;
import AccesoADatos.GlobalException;
import Modelo.Cliente;
import Modelo.DTOs.MedicionDTO;
import Modelo.Medicion;

import java.util.ArrayList;

public class ServicioMedicion {
    private final DAOMedicion dao;
    private final ServicioCliente servicioCliente;

    public ServicioMedicion() {
        this.dao = new DAOMedicion();
        this.servicioCliente = new ServicioCliente();
    }

    public int insertarMedicionEnBD(MedicionDTO dto) throws GlobalException {

        Cliente cliente = servicioCliente.buscarClienteEnBD(dto.getCedulaCliente());
        if(cliente == null) {
            return 1; // Medicion ya existe
        }

        Medicion medicion = Medicion.fromDTO(dto, cliente);
        try {
            dao.insertarMedicion(medicion);
            dao.eliminarMasAntigua(dto.getCedulaCliente());
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public

    public ArrayList<Medicion> buscarHistorialDeMedicionEnBD(String clienteId) throws GlobalException {
        return dao.buscarMedicion(clienteId);
    }

}

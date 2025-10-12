package Modelo.Servicios;

import AccesoADatos.DAOMedicion;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
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
            return 1; // Cliente no existe
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

    public String generarReporte(MedicionDTO dto, boolean haceEjercicio) {
        Cliente cliente = servicioCliente.buscarClienteEnBD(dto.getCedulaCliente());
        Medicion medicion = Medicion.fromDTO(dto, cliente);
        return medicion.reporteDeMedicion(haceEjercicio);
    }

    public ArrayList<MedicionDTO> buscarHistorialDeMedicionEnBD(String clienteId) throws GlobalException {
        ArrayList<MedicionDTO> medicionesDTO = new ArrayList<>();
        try {
            ArrayList<Medicion> mediciones = dao.buscarMedicion(clienteId);
            for (Medicion medicion : mediciones) {
                medicionesDTO.add(medicion.toDTO());
            }
            return medicionesDTO;
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

}

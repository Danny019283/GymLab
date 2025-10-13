package Modelo.Servicios;

import AccesoADatos.DAOMedicion;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.Cliente;
import Modelo.DTOs.MedicionDTO;
import Modelo.Medicion;

import java.util.ArrayList;

public class ServicioMedicion {
    private DAOMedicion dao;
    private ServicioCliente servicioCliente;

    private DAOMedicion getDao() {
        if (dao == null) {
            dao = new DAOMedicion();
        }
        return dao;
    }

    private ServicioCliente getServicioCliente() {
        if (servicioCliente == null) {
            servicioCliente = new ServicioCliente();
        }
        return servicioCliente;
    }

    public int insertarMedicionEnBD(MedicionDTO dto) throws GlobalException {
        Cliente cliente = getServicioCliente().buscarClienteEnBD(dto.getCedulaCliente());
        if(cliente == null) {
            return 1;
        }

        Medicion medicion = Medicion.fromDTO(dto, cliente);
        try {
            getDao().insertarMedicion(medicion);
            getDao().eliminarMasAntigua(dto.getCedulaCliente());
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String generarReporte(MedicionDTO dto, boolean haceEjercicio) {
        Cliente cliente = getServicioCliente().buscarClienteEnBD(dto.getCedulaCliente());
        Medicion medicion = Medicion.fromDTO(dto, cliente);
        return medicion.reporteDeMedicion(haceEjercicio);
    }

    public ArrayList<MedicionDTO> buscarHistorialDeMedicionEnBD(String clienteId) throws GlobalException {
        ArrayList<MedicionDTO> medicionesDTO = new ArrayList<>();
        try {
            ArrayList<Medicion> mediciones = getDao().buscarMedicion(clienteId);
            for (Medicion medicion : mediciones) {
                medicionesDTO.add(medicion.toDTO());
            }
            return medicionesDTO;
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }
}
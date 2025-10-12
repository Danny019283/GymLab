package Modelo.Servicios;

import AccesoADatos.DAOSucursal;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.DTOs.SucursalDTO;
import Modelo.Sucursal;

import java.util.ArrayList;

public class ServicioSucursal {
    DAOSucursal dao;

    public ServicioSucursal() {
        dao = new DAOSucursal();
    }

    public int insertarSucursalEnBD(SucursalDTO dto) throws GlobalException, NoDataException {

        if (buscarSucursalEnBD(dto.getCod()) != null) {
            return 1; // Sucursal ya existe
        }
        if (dao.listarSucursales().size() == 30) {
            return 2; // Límite de sucursales alcanzado
        }
        Sucursal sucursal = Sucursal.fromDTO(dto);
        try {
            dao.insertarSucursal(sucursal);
            return 0;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }
public boolean actualizarSucursalEnBD(SucursalDTO dto) throws GlobalException {
        Sucursal sucursal = Sucursal.fromDTO(dto);
        if (buscarSucursalEnBD(sucursal.getCod()) == null) {
            return false;// Sucursal no existe
        }
        try {
            dao.modificarSucursal(sucursal);
            return true;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }


    public Sucursal buscarSucursalEnBD(String cod) throws GlobalException {
        try {
            return dao.buscarSucursal(cod);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<SucursalDTO> obtenerSucursalesEnBD() {
        ArrayList<SucursalDTO> sucursalesDTO = new ArrayList<>();
        try {
            ArrayList<Sucursal> sucursales = dao.listarSucursales();
            for (Sucursal sucursal : sucursales) {
                sucursalesDTO.add(sucursal.toDTO());
            }
            return sucursalesDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

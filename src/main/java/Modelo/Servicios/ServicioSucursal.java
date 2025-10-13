package Modelo.Servicios;

import AccesoADatos.DAOSucursal;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.DTOs.SucursalDTO;
import Modelo.Sucursal;

import java.util.ArrayList;

public class ServicioSucursal {
    private DAOSucursal dao;

    private DAOSucursal getDao() {
        if (dao == null) {
            dao = new DAOSucursal();
        }
        return dao;
    }

    public int insertarSucursalEnBD(SucursalDTO dto) throws GlobalException, NoDataException {
        if (buscarSucursalEnBD(dto.getCod()) != null) {
            return 1;
        }
        if (getDao().listarSucursales().size() == 30) {
            return 2;
        }
        Sucursal sucursal = Sucursal.fromDTO(dto);
        try {
            getDao().insertarSucursal(sucursal);
            return 0;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean actualizarSucursalEnBD(SucursalDTO dto) throws GlobalException {
        Sucursal sucursal = Sucursal.fromDTO(dto);
        if (buscarSucursalEnBD(sucursal.getCod()) == null) {
            return false;
        }
        try {
            getDao().modificarSucursal(sucursal);
            return true;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public Sucursal buscarSucursalEnBD(String cod) throws GlobalException {
        try {
            return getDao().buscarSucursal(cod);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<SucursalDTO> obtenerSucursalesEnBD() {
        ArrayList<SucursalDTO> sucursalesDTO = new ArrayList<>();
        try {
            ArrayList<Sucursal> sucursales = getDao().listarSucursales();
            for (Sucursal sucursal : sucursales) {
                sucursalesDTO.add(sucursal.toDTO());
            }
            return sucursalesDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
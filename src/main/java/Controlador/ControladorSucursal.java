package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioSucursal;
import Modelo.Sucursal;

public class ControladorSucursal {
    ServicioSucursal servicioSucursal;
    //vista sucursal;

    public void registrarSucursal(String cod, String provi, String canton, String correo, int telef) throws NoDataException, GlobalException {
        Sucursal sucursal = new Sucursal(cod, provi, canton, correo, telef);
        servicioSucursal.insertarSucursal(sucursal);
    }
}

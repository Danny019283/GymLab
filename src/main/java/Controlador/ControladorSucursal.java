package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioSucursal;
import Modelo.Sucursal;

public class ControladorSucursal {
    ServicioSucursal servicioSucursal = new ServicioSucursal();
    //vista sucursal;

    public void registrarSucursal(String cod, String provi, String canton, String correo, int telef) throws NoDataException, GlobalException {
        Sucursal sucursal = new Sucursal(cod, provi, canton, correo, telef);
        servicioSucursal.insertarSucursal(sucursal);
    }

    public static void main(String[] args){
        try {
            // Instanciar controlador
            ControladorSucursal controlador = new ControladorSucursal();

            // Probar registro de una sucursal
            controlador.registrarSucursal("S001", "San José", "Central", "sucursal@empresa.com", 22223333);

            System.out.println("Sucursal registrada correctamente");

        } catch (NoDataException e) {
            System.err.println("Error de datos: " + e.getMessage());
        } catch (GlobalException e) {
            System.err.println("Error global: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

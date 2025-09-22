package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioRutina;
import Modelo.Cliente;
import Modelo.Rutina;

public class ControladorRutina {
    //se debe permitir agregar ejercicios a un comboBox esto en la capa de vista

    ServicioRutina servicioRutina = new ServicioRutina();

    public String asignarRutinaACliente() {
        return null;
    }

    public Rutina buscarRutina(String cedula) throws GlobalException {
        return servicioRutina.buscarRutina(cedula);
    }



}

package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioRutina;
import Modelo.Cliente;
import Modelo.Rutina;

public class ControladorRutina {
    //se debe permitir agregar ejercicios a un comboBox esto en la capa de vista

    ServicioRutina servicioRutina = new ServicioRutina();
    //vista rutina;

    public String asignarRutinaACliente(Cliente cliente, String pecho, String triceps, String biceps, String piernas, String espalda) throws NoDataException, GlobalException {
        Rutina rutina = new Rutina(pecho, triceps, biceps, piernas, espalda);
        servicioRutina.insertarRutina(rutina, cliente.getCedula());
        return rutina.toString();
    }

}

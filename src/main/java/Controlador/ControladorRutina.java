package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioRutina;
import Modelo.Rutina;


public class ControladorRutina {
    private final ServicioRutina servicioRutina = new ServicioRutina();

    public void asignarRutinaACliente(String cedula, Rutina rutina) throws NoDataException, GlobalException {
        servicioRutina.insertarRutina(cedula, rutina);
    }

    public Rutina crearRutina(String pecho, String triceps, String biceps, String piernas, String espalda) {
        return new Rutina(pecho, triceps, biceps, piernas, espalda);
    }

    public Rutina buscarRutina(String cedula) throws GlobalException {
        return servicioRutina.buscarRutina(cedula);
    }



}

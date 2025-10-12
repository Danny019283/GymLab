package Modelo.Servicios;

import AccesoADatos.DAORutina;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.Rutina;

public class ServicioRutina {
    DAORutina dao = new DAORutina();

    public ServicioRutina() {
        dao = new DAORutina();
    }

    public void insertarRutinaEnBD(String cedula, Rutina rutina) throws NoDataException, GlobalException {
        dao.insertarRutina(cedula, rutina);
    }

    public Rutina buscarRutinaEnBD(String cedula) throws GlobalException {
        return dao.buscarRutina(cedula);
    }
}

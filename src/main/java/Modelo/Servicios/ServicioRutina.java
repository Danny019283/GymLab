package Modelo.Servicios;

import AccesoADatos.DAORutina;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.Rutina;

public class ServicioRutina {
    private DAORutina dao;

    private DAORutina getDao() {
        if (dao == null) {
            dao = new DAORutina();
        }
        return dao;
    }

    public void insertarRutinaEnBD(String cedula, Rutina rutina) throws NoDataException, GlobalException {
        getDao().insertarRutina(cedula, rutina);
    }

    public Rutina buscarRutinaEnBD(String cedula) throws GlobalException {
        return getDao().buscarRutina(cedula);
    }
}
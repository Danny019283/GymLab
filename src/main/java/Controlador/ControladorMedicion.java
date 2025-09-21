package Controlador;

import AccesoADatos.ServicioMedicion;
import Modelo.Cliente;
import Modelo.Medicion;

import java.time.LocalDate;

public class ControladorMedicion {
    ServicioMedicion servicioMedicion = new ServicioMedicion();
    //vista medicion;

    public boolean reguistrarMedicion(Cliente cliente, float peso, float altura, LocalDate fechaMedi, float cintura,
                                   float porcGrasa, float porcGrasaVis, float porcMusculo, float muslo, float pecho) throws Exception {
        //si el cliente tiene un instructor asignado, se puede registrar la medicion
        if (cliente.getInstructor() != null) {
            Medicion medicion = new Medicion.Builder()
                    .cliente(cliente)
                    .peso(peso)
                    .estatura(altura)
                    .fechaDeMedicion(fechaMedi)
                    .cintura(cintura)
                    .porcGrasa(porcGrasa)
                    .porcGrasaVis(porcGrasaVis)
                    .porcMusculo(porcMusculo)
                    .muslo(muslo)
                    .pecho(pecho)
                    .build();
            return true;
        }
        return false;
    }
}

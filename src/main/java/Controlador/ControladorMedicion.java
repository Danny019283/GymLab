package Controlador;

import AccesoADatos.ServicioMedicion;
import Modelo.Cliente;
import Modelo.Medicion;

import java.time.LocalDate;

public class ControladorMedicion {
    ServicioMedicion servicioMedicion = new ServicioMedicion();
    //vista medicion;

    public boolean registrarMedicion(Cliente cliente, float peso, float altura, LocalDate fechaMedi, float cintura,
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
            servicioMedicion.insertarMedicion(medicion);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        //testing
        try {
            // Instanciar controlador
            ControladorMedicion controlador = new ControladorMedicion();

            // Traer cliente desde la BD
            AccesoADatos.ServicioCliente servicioCliente = new AccesoADatos.ServicioCliente();
            Cliente cliente = servicioCliente.buscarCliente("C001"); // el cliente ya insertado antes

            if (cliente == null) {
                System.err.println("No se encontró el cliente en la BD ");
                return;
            }

            // Probar registro de medición
            boolean ok = controlador.registrarMedicion(
                    cliente,
                    70.5f,                     // peso
                    1.75f,                     // altura
                    LocalDate.now(),           // fecha medición
                    85.0f,                     // cintura
                    20.5f,                     // % grasa
                    10.2f,                     // % grasa visceral
                    40.0f,                     // % musculo
                    55.0f,                     // muslo
                    95.0f                      // pecho
            );

            if (ok) {
                System.out.println("Medición registrada correctamente ");
            } else {
                System.out.println("El cliente no tiene instructor asignado ");
            }

        } catch (Exception e) {
            System.err.println("Error al registrar medición: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

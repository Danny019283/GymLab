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

    public static void main(String[] args) {
        try {
            // Instanciar controlador de rutina
            ControladorRutina controlador = new ControladorRutina();

            // Traer cliente existente desde la BD
            AccesoADatos.ServicioCliente servicioCliente = new AccesoADatos.ServicioCliente();
            Cliente cliente = servicioCliente.buscarCliente("C001"); // cliente ya insertado antes

            if (cliente == null) {
                System.err.println("No se encontró el cliente en la BD");
                return;
            }

            // Asignar rutina al cliente
            String rutinaInfo = controlador.asignarRutinaACliente(
                    cliente,
                    "Press de banca, Aperturas con mancuernas",  // pecho
                    "Fondos, Jalón en polea invertido",          // triceps
                    "Curl con barra, Curl martillo",             // biceps
                    "Sentadillas, Prensa de pierna",             // piernas
                    "Dominadas, Remo con barra"                  // espalda
            );

            System.out.println("Rutina asignada correctamente");
            System.out.println("Detalle rutina: " + rutinaInfo);

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

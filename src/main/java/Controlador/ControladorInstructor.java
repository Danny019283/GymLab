package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioInstructor;
import Modelo.Instructor;

import java.util.ArrayList;

public class ControladorInstructor {

    ServicioInstructor servicioInstructor = new ServicioInstructor();
    //vista instructor

    public void registrar(String cedula, String nombreCom, int telefono, String correo, String fechaNac,
                          ArrayList<String> especialidad, String codigoSucursal) throws NoDataException, GlobalException {
        Instructor instructor = new Instructor.Builder()
                .cedula(cedula)
                .nombreCom(nombreCom)
                .telef(telefono)
                .correo(correo)
                .fechaNac(fechaNac)
                .especialidad(especialidad)
                .codigoSucursal(codigoSucursal)
                .build();

        servicioInstructor.insertarInstructor(instructor);
    }

    public static void main(String[] args) {
        //testing
        try {
            // Instanciar controlador
            ControladorInstructor controlador = new ControladorInstructor();

            // Crear lista de especialidades
            ArrayList<String> especialidades = new ArrayList<>();
            especialidades.add("Yoga");
            especialidades.add("Pilates");

            // Probar registro del instructor
            controlador.registrar(
                    "123456789",              // cedula
                    "Carlos Pérez",           // nombre completo
                    88887777,                 // telefono
                    "carlos.perez@empresa.com", // correo
                    "1985-05-10",             // fecha nacimiento
                    especialidades,           // especialidades
                    "S001"                    // código de sucursal ya registrada
            );

            System.out.println("Instructor registrado correctamente");

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

package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioRegistroClases;
import AccesoADatos.ServicioClaseGrupal;
import Modelo.ClaseGrupal;
import Modelo.Cliente;
import Modelo.Instructor;
import Vista.Formularios.FormularioMatricula;

import java.util.ArrayList;

public class ControladorClaseGrupal {
    private final ServicioClaseGrupal servicioClaseGrupal = new ServicioClaseGrupal();
    private final ServicioRegistroClases servicioClase = new ServicioRegistroClases(); //registros de los clientes inscritos
    //vista claseGrupal;

    public boolean registrarClaseGrupal(String codigo, int cupoMax, int numSalon,
                                     String especialidad, String horario, Instructor instructor) throws NoDataException, GlobalException {
        //comprueba que la especialidad del curso conicida con alguan el instructor
        boolean coincide = false;
        for (int i = 0; i < instructor.getEspecialidad().size(); i++) {
            if (!instructor.getEspecialidad().get(i).equals(especialidad)) {
                coincide = true;
                break;
            }
        }
        if (!coincide){return false;}
        ClaseGrupal claseGrupal = new ClaseGrupal(codigo, cupoMax, numSalon, especialidad, horario, instructor);
        servicioClaseGrupal.insertarClaseGrupal(claseGrupal);
        return true;
    }

    public int matricularCliente() throws NoDataException, GlobalException {
        FormularioMatricula formularioMatricula = new FormularioMatricula();
        boolean resultado = formularioMatricula.mostrarDialogo("Matricular Cliente");
        if (!resultado) {
            return 3;
        }

        String cedula = formularioMatricula.getCedulaCliente();
        String codClase = formularioMatricula.getCodClase();

        int clasesInscritas = servicioClase.verificarClasesCliente(cedula);
        if (clasesInscritas >= 3) {
            return 1; //el cliente ya está incrito en 3 clases
        }
        int cuposDisponibles = servicioClase.verificarCupoClase(codClase);
        if (cuposDisponibles <= 0) {
            return 2; //no hay cupo
        }
        servicioClase.insertarClaseCliente(codClase, cedula);
        return 0; //se inserto con exito
    }

    public ArrayList<Cliente> listarClientesPorClase(String codigoClase) throws GlobalException {
        return servicioClase.buscarClientesPorClase(codigoClase);
    }

    public static void main(String[] args) {
        try {
            ControladorClaseGrupal controlador = new ControladorClaseGrupal();

            // Traer instructor desde la BD
            /*
            AccesoADatos.ServicioInstructor servicioInstructor = new AccesoADatos.ServicioInstructor();
            Instructor instructor = servicioInstructor.buscarInstructor("123456789");

            if (instructor == null) {
                System.err.println("No se encontró el instructor en la BD ❌");
                return;
            }

            // Registrar clase grupal
            boolean claseOk = controlador.registrarClaseGrupal(
                    "CL001",       // código clase
                    20,            // cupo máximo
                    1,             // número de salón
                    "Yoga",        // especialidad
                    "Lunes 6PM",   // horario
                    instructor     // instructor desde la BD
            );

            if (claseOk) {
                System.out.println("Clase grupal registrada correctamente ✅");
            } else {
                System.out.println("El instructor no tiene la especialidad solicitada ❌");
                return;
            }

            // Traer cliente desde la BD
            AccesoADatos.ServicioCliente servicioCliente = new AccesoADatos.ServicioCliente();
            Cliente cliente = servicioCliente.buscarCliente("C001");

            if (cliente == null) {
                System.err.println("No se encontró el cliente en la BD ❌");
                return;
            }

            // Matricular cliente en la clase grupal
            ClaseGrupal clase = new ClaseGrupal("CL001", 20, 1, "Yoga", "Lunes 6PM", instructor);
            boolean matriculaOk = controlador.matricularCliente(cliente, clase);

            if (matriculaOk) {
                System.out.println("Cliente matriculado correctamente ✅");
            } else {
                System.out.println("No se pudo matricular (cupo lleno o límite de clases) ❌");
            }*/

            // Listar clientes por clase
            System.out.println("Clientes en la clase CL001:");
            for (Cliente c : controlador.listarClientesPorClase("CL001")) {
                System.out.println("- " + c.getNombre());
            }

        } catch (GlobalException e) {
            System.err.println("Error global: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }





}

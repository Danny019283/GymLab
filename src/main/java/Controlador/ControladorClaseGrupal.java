package Controlador;

import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import AccesoADatos.ServicioClaseClientes;
import AccesoADatos.ServicioClaseGrupal;
import Modelo.ClaseGrupal;
import Modelo.Cliente;
import Modelo.Instructor;

import java.util.ArrayList;

public class ControladorClaseGrupal {
    ServicioClaseGrupal servicioClaseGrupal = new ServicioClaseGrupal();
    ServicioClaseClientes servicioClase = new ServicioClaseClientes(); //registros de los clientes inscritos
    //vista claseGrupal;

    public boolean registrarClaseGrupal(String codigo, int cupoMax, int numSalon,
                                     String especialidad, String horario, Instructor instructor) throws NoDataException, GlobalException {
        //comprueba que la especialidad del curso conicida con alguan el instructor
        for (int i = 0; i < instructor.getEspecialidad().size(); i++) {
            if (!instructor.getEspecialidad().get(i).equals(especialidad)) {
                return false;
            }
        }
        ClaseGrupal claseGrupal = new ClaseGrupal(codigo, cupoMax, numSalon, especialidad, horario, instructor);
        servicioClaseGrupal.insertarClaseGrupal(claseGrupal);
        return true;
    }

    public boolean matricularCliente(Cliente cliente, ClaseGrupal claseGrupal) throws NoDataException, GlobalException {
        int clasesInscriptas = servicioClase.verificarClasesCliente(cliente.getCedula());
        if (clasesInscriptas >= 3) {
            return false;
        }
        int cupoMax = servicioClase.verificarCupoClase(claseGrupal.getCodigo());
        if (cupoMax >= claseGrupal.getCupoMax()) {
            return false;
        }
        servicioClase.insertarClaseCliente(claseGrupal.getCodigo(), cliente.getCedula());
        return true;
    }

    public ArrayList<Cliente> listarClientesPorClase(String codigoClase) throws GlobalException {
        return servicioClase.buscarClientesPorClase(codigoClase);
    }





}

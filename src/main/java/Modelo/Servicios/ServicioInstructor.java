package Modelo.Servicios;

import AccesoADatos.DAOInstructor;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.DTOs.InstructorDTO;
import Modelo.Instructor;
import Modelo.Rutina;

import java.util.ArrayList;

public class ServicioInstructor {
    DAOInstructor dao = new DAOInstructor();
    ServicioRutina servicioRutina = new ServicioRutina();

    public int insertarInstructorEnBD(InstructorDTO dto) {
        Instructor instructor = Instructor.fromDTO(dto);

        if(buscarInstructorEnBD(instructor.getCedula()) != null) {
            return 1; // Instructor ya existe
        }
        //comprobar existencia de sucursal\

        try {
            dao.insertarInstructor(instructor);
            return 0;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean actualizarInstructorEnBD(InstructorDTO dto) {
        Instructor instructor = Instructor.fromDTO(dto);
        if(buscarInstructorEnBD(instructor.getCedula()) == null) {
            return false;// Instructor no existe
        }
        try {
            dao.actualizarInstructor(instructor);
            return true;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    private Instructor buscarInstructorEnBD(String cedula) {
        try {
            return dao.buscarInstructor(cedula);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarInstructorEnBD(String cedula) {
        if(buscarInstructorEnBD(cedula) == null) {
            return false;// Instructor no existe
        }
        try {
            dao.eliminarInstructor(cedula);
            return true;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<InstructorDTO> obtenerInstructoresEnBD() {
        try {
            ArrayList<Instructor> instructores = dao.listarInstructores();
            ArrayList<InstructorDTO> dtoList = new ArrayList<>();
            for (Instructor instructor : instructores) {
                dtoList.add(instructor.toDTO());
            }
            return dtoList;
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean asignarRutinaACliente(String clienteId, Rutina rutina) throws NoDataException, GlobalException {
        if (buscarInstructorEnBD(clienteId) == null) {
            return false; // Cliente no existe
        }
        servicioRutina.insertarRutinaEnBD(clienteId, rutina);
        return true;
    }
}

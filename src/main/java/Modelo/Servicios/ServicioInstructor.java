package Modelo.Servicios;

import AccesoADatos.DAOInstructor;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.DTOs.InstructorDTO;
import Modelo.Instructor;
import Modelo.Rutina;

import java.util.ArrayList;

public class ServicioInstructor {
    private DAOInstructor dao;
    private ServicioRutina servicioRutina;

    private DAOInstructor getDao() {
        if (dao == null) {
            dao = new DAOInstructor();
        }
        return dao;
    }

    private ServicioRutina getServicioRutina() {
        if (servicioRutina == null) {
            servicioRutina = new ServicioRutina();
        }
        return servicioRutina;
    }

    public int insertarInstructorEnBD(InstructorDTO dto) {
        Instructor instructor = Instructor.fromDTO(dto);

        if(buscarInstructorEnBD(instructor.getCedula()) != null) {
            return 1;
        }

        try {
            getDao().insertarInstructor(instructor);
            return 0;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean actualizarInstructorEnBD(InstructorDTO dto) {
        Instructor instructor = Instructor.fromDTO(dto);
        if(buscarInstructorEnBD(instructor.getCedula()) == null) {
            return false;
        }
        try {
            getDao().actualizarInstructor(instructor);
            return true;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public Instructor buscarInstructorEnBD(String cedula) {
        try {
            return getDao().buscarInstructor(cedula);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean eliminarInstructorEnBD(String cedula) {
        if(buscarInstructorEnBD(cedula) == null) {
            return false;
        }
        try {
            getDao().eliminarInstructor(cedula);
            return true;
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<InstructorDTO> obtenerInstructoresEnBD() {
        try {
            ArrayList<Instructor> instructores = getDao().listarInstructores();
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
            return false;
        }
        getServicioRutina().insertarRutinaEnBD(clienteId, rutina);
        return true;
    }
}
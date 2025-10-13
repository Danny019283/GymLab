package Modelo.Servicios;

import AccesoADatos.DAOClaseGrupal;
import AccesoADatos.DAORegistroClases;
import AccesoADatos.GlobalException;
import AccesoADatos.NoDataException;
import Modelo.ClaseGrupal;
import Modelo.Cliente;
import Modelo.DTOs.ClaseGrupalDTO;
import Modelo.Instructor;

import java.util.ArrayList;

public class ServicioClaseGrupal {
    private DAOClaseGrupal dao;
    private DAORegistroClases daoRegristro;
    private ServicioInstructor servicioInstructor;
    private ServicioCliente servicioCliente;

    private DAOClaseGrupal getDao() {
        if (dao == null) {
            dao = new DAOClaseGrupal();
        }
        return dao;
    }

    private DAORegistroClases getDaoRegristro() {
        if (daoRegristro == null) {
            daoRegristro = new DAORegistroClases();
        }
        return daoRegristro;
    }

    private ServicioInstructor getServicioInstructor() {
        if (servicioInstructor == null) {
            servicioInstructor = new ServicioInstructor();
        }
        return servicioInstructor;
    }

    private ServicioCliente getServicioCliente() {
        if (servicioCliente == null) {
            servicioCliente = new ServicioCliente();
        }
        return servicioCliente;
    }

    public int insertarClaseGrupalEnBD(ClaseGrupalDTO dto) throws GlobalException {
        Instructor instructor = getServicioInstructor().buscarInstructorEnBD(dto.getCedulaInstructor());
        if(instructor == null) {
            return 1;
        }
        if(getDao().buscarClaseGrupal(dto.getCodigo()) != null) {
            return 2;
        }
        if(!verificarEspecialidadInstructor(instructor.getEspecialidad(), dto.getEspecialidad())) {
            return 3;
        }
        ClaseGrupal claseGrupal = ClaseGrupal.fromDTO(dto, instructor);
        try {
            getDao().insertarClaseGrupal(claseGrupal);
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int actualizarClaseGrupalEnBD(ClaseGrupalDTO dto) throws GlobalException {
        Instructor instructor = getServicioInstructor().buscarInstructorEnBD(dto.getCedulaInstructor());
        if(instructor == null) {
            return 1;
        }
        if(getDao().buscarClaseGrupal(dto.getCodigo()) == null) {
            return 2;
        }
        if(!verificarEspecialidadInstructor(instructor.getEspecialidad(), dto.getEspecialidad())) {
            return 3;
        }
        ClaseGrupal claseGrupal = ClaseGrupal.fromDTO(dto, instructor);
        try {
            getDao().modificarClaseGrupal(claseGrupal);
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ClaseGrupal buscarClaseGrupalEnBD(String codigo) throws GlobalException {
        try {
            return getDao().buscarClaseGrupal(codigo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificarEspecialidadInstructor(ArrayList<String> especialidades, String especialidadClase) throws GlobalException {
        for(String esp : especialidades) {
            if(esp.equals(especialidadClase)) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminarClaseGrupalEnBD(String codigo) throws GlobalException {
        if(getDao().buscarClaseGrupal(codigo) == null) {
            return false;
        }
        try {
            getDao().eliminarClaseGrupal(codigo);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ClaseGrupalDTO> obtenerClasesGrupalesEnBD() {
        ArrayList<ClaseGrupalDTO> clasesDTO = new ArrayList<>();
        try {
            ArrayList<ClaseGrupal> clases = getDao().listarClasesGrupales();
            for (ClaseGrupal clase : clases) {
                clasesDTO.add(clase.toDTO());
            }
            return clasesDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String listarClientesPorClase(String codigoClase) {
        StringBuilder mostrar = new StringBuilder();
        try {
            ArrayList<Cliente> clientes = getDaoRegristro().buscarClientesSegunClase(codigoClase);
            for(Cliente c : clientes) {
                mostrar.append(c.getClienteInfo()).append("\n");
            }
            return mostrar.toString();
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
    }

    public int matricularClienteEnClase(String codigoClase, String cedulaCliente) throws GlobalException, NoDataException {
        ClaseGrupal clase = buscarClaseGrupalEnBD(codigoClase);
        if (clase == null) {
            return 1;
        }
        if(getServicioCliente().buscarClienteEnBD(cedulaCliente) == null) {
            return 2;
        }
        if(getDaoRegristro().verificarCupoClase(codigoClase) >= clase.getCupoMax()) {
            return 3;
        }
        if(getDaoRegristro().verificarClasesCliente(cedulaCliente) >= 3) {
            return 4;
        }
        getDaoRegristro().insertarClaseCliente(codigoClase, cedulaCliente);
        return 0;
    }

    public String obtenerClasesSegunCliente(String cedulaCliente) {
        StringBuilder mostrar = new StringBuilder();
        try{
            for(ClaseGrupal clase : getDaoRegristro().buscarClasesSegunCliente(cedulaCliente)) {
                mostrar.append(clase.getInfo()).append("\n");
            }
            return mostrar.toString();
        } catch(GlobalException e) {
            throw new RuntimeException(e);
        }
    }
}
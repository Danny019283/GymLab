package AccesoADatos;
import Modelo.Instructor;
import oracle.jdbc.internal.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOInstructor extends Conexion {

    public static final String insertarInstructor = "{call insertar_instructor(?, ?, ?, ?, ?, ?, ?)}";
    public static final String actualizarInstructor = "{call actualizar_instructor(?, ?, ?, ?, ?, ?, ?)}";
    public static final String eliminarInstructor = "{call eliminar_instructor(?)}";
    public static final String buscarInstructor = "{? = call buscar_instructor(?)}";
    public static final String listarInstructores = "{? = call listar_instructores()}";
    public static final String obtenerEspecialidades = "{? = call obtener_especialidades(?)}";

    public DAOInstructor() {}

    public void insertarInstructor(Instructor instructor) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;

        try {
            // Convertir ArrayList<String> a ARRAY de Oracle
            List<String> especialidadesList = instructor.getEspecialidad();
            String[] especialidadesArray = especialidadesList.toArray(new String[0]);

            ArrayDescriptor desc = ArrayDescriptor.createDescriptor("T_ESPECIALIDADES", conexion);
            ARRAY arrayEspecialidades = new ARRAY(desc, conexion, especialidadesArray);

            pstmt = conexion.prepareCall(insertarInstructor);
            pstmt.setString(1, instructor.getCedula());
            pstmt.setString(2, instructor.getNombreCom());
            pstmt.setInt(3, instructor.getTelef());
            pstmt.setString(4, instructor.getCorreo());
            pstmt.setString(5, instructor.getFechaNac());
            pstmt.setString(6, instructor.getCodigoSucursal());
            pstmt.setArray(7, arrayEspecialidades);

            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al insertar instructor: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexión");
            }
        }
    }

    public void actualizarInstructor(Instructor instructor) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;

        try {
            // Convertir ArrayList<String> a ARRAY de Oracle
            List<String> especialidadesList = instructor.getEspecialidad();
            String[] especialidadesArray = especialidadesList.toArray(new String[0]);

            ArrayDescriptor desc = ArrayDescriptor.createDescriptor("T_ESPECIALIDADES", conexion);
            ARRAY arrayEspecialidades = new ARRAY(desc, conexion, especialidadesArray);

            pstmt = conexion.prepareCall(actualizarInstructor);
            pstmt.setString(1, instructor.getCedula());
            pstmt.setString(2, instructor.getNombreCom());
            pstmt.setInt(3, instructor.getTelef());
            pstmt.setString(4, instructor.getCorreo());
            pstmt.setString(5, instructor.getFechaNac());
            pstmt.setString(6, instructor.getCodigoSucursal());
            pstmt.setArray(7, arrayEspecialidades);


            int resultado = pstmt.executeUpdate();

            if (resultado == 0) {
                throw new NoDataException("No se realizó la actualización");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al actualizar instructor: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexión");
            }
        }
    }

    public void eliminarInstructor(String cedula) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(eliminarInstructor);
            pstmt.setString(1, cedula);

            int resultado = pstmt.executeUpdate();

            if (resultado == 0) {
                throw new NoDataException("No se realizó el borrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al eliminar instructor: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexión");
            }
        }
    }

    public Instructor buscarInstructor(String cedula) throws GlobalException {
        conectar();
        CallableStatement pstmt = null;
        ResultSet rs = null;
        String instructor = null;

        try {
            pstmt = conexion.prepareCall(buscarInstructor);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cedula);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);

            if (rs != null && rs.next()) {
                // Obtener datos básicos
                String nombre = rs.getString("nombreCom");
                int telefono = rs.getInt("telefono");
                String correo = rs.getString("correo");
                String fechaNac = rs.getString("fechaNac");
                // Obtener especialidades como ArrayList
                ArrayList<String> especialidades = obtenerEspecialidadesComoArrayList(cedula);
                String codSucursal = rs.getString("cod_sucursal");

                return new Instructor(cedula, nombre, telefono, correo, fechaNac, especialidades, codSucursal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al buscar instructor: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexión");
            }
        }
        return null;
    }

    // Método auxiliar para obtener especialidades como ArrayList
    private ArrayList<String> obtenerEspecialidadesComoArrayList(String cedula) throws GlobalException {
        conectar();
        CallableStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<String> especialidades = new ArrayList<>();

        try {
            pstmt = conexion.prepareCall(obtenerEspecialidades);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cedula);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);

            while (rs != null && rs.next()) {
                especialidades.add(rs.getString("especialidad"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al obtener especialidades: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexión");
            }
        }
        return especialidades;
    }

    public ArrayList<Instructor> listarInstructores() throws GlobalException {
        conectar();
        CallableStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Instructor> instructores = new ArrayList<>();

        try {
            pstmt = conexion.prepareCall(listarInstructores);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);

            while (rs != null && rs.next()) {
                String cedula = rs.getString("cedula");
                String nombre = rs.getString("nombreCom");
                int telefono = rs.getInt("telefono");
                String correo = rs.getString("correo");
                String fechaNac = rs.getString("fechaNac");
                // Obtener especialidades para cada instructor
                ArrayList<String> especialidades = obtenerEspecialidadesComoArrayList(cedula);
                String codSucursal = rs.getString("cod_sucursal");

                Instructor instructor = new Instructor(cedula, nombre, telefono, correo, fechaNac, especialidades, codSucursal);
                instructores.add(instructor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al listar instructores: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Error al cerrar conexión");
            }
        }
        return instructores;
    }
}
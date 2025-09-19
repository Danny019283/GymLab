package AccesoADatos;
import Modelo.Instructor;
import oracle.jdbc.internal.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioInstructor extends Servicio {

    public static final String insertarInstructor = " {call insertarinstructor(?,?,?,?,?,?)} ";
    public static final String actualizarInstructor = " {call actualizarinstructor(?,?,?,?,?,?)} ";
    public static final String eliminarInstructor = " {call eliminarinstructor(?)} ";
    public static final String buscarInstructor = " {?=call buscarinstructor(?)} ";
    public static final String listarInstructores = " {?=call listarinstructor()} ";

    public ServicioInstructor() {}

    public void insertarInstructor(Instructor instructor) throws GlobalException, NoDataException {
        conectar();

        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(insertarInstructor);
            pstmt.setString(1, instructor.getCedula());
            pstmt.setString(2, instructor.getNombreCom());
            pstmt.setInt(3, instructor.getTelef());
            pstmt.setString(4, instructor.getCorreo());
            pstmt.setString(5,instructor.getFechaNac());
            pstmt.setString(6, instructor.getEspecialidad());
            boolean resultado = pstmt.execute();
            if (resultado) {
                throw new NoDataException("No se realizo la inserci�n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public boolean actualizarInstructor(Instructor i){
        return false;
    }

    public boolean eliminarInstructor(String cedula){
        return false;
    }

    public Instructor buscarInstructor(String cedula) throws GlobalException {
        conectar();

        ResultSet rs = null;
        //ArrayList coleccion = new ArrayList();
        Instructor instructor = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarInstructor);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cedula);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            if (rs.next()) {
                instructor = new Instructor(rs.getString("cedula"), rs.getString("nombrecom"),
                        rs.getInt("telefono"), rs.getString("correo"),
                        rs.getString("fechanac"), rs.getString("especialidad"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        return instructor;
    }

    public Instructor listarInstructores(){
        return null;
    }

    //main:
    public static void main(String[] args) {
        ServicioInstructor si = new ServicioInstructor();
        System.out.println("Conexion");
        try {
            Instructor instructor = new Instructor("124", "Juan Perez", 12345678, "juanca@gmail.com", "19/01/2002", "yoga");
            si.insertarInstructor(instructor);
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }
}

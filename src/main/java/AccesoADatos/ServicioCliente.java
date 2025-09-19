package AccesoADatos;
import Modelo.Cliente;
import Modelo.Instructor;

import javax.imageio.stream.ImageInputStream;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ServicioCliente extends Servicio {

    private static final String insertarCliente = "{call insertarcliente(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String actualizarCliente = "{call actualizarcliente(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String eliminarCliente = "{call eliminarcliente(?)}";
    private static final String buscarCliente = "{?=call buscarcliente(?)}";
    private static final String listarClientes = "{call listarcliente()}";

    ServicioCliente() {}

    public void insertarCliente(Cliente cliente) throws GlobalException, NoDataException {
        conectar();

        CallableStatement pstmt = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            pstmt = conexion.prepareCall(insertarCliente);
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setInt(3, cliente.getTelef());
            pstmt.setString(4, cliente.getCorreo());
            pstmt.setString(5, cliente.getFechaNac());
            pstmt.setString(6, cliente.getSexo());
            pstmt.setString(7, cliente.getFechaInscrip());
            pstmt.setInt(8, cliente.getEdad());
            pstmt.setString(9, cliente.getInstructor().getCedula());
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
    public boolean actualizarCliente(Cliente c){
        return false;
    }

    public boolean eliminarCliente(String cedula){
        return false;
    }

    public Cliente buscarCliente(String cedula){
        return null;
    }

    public String listarClientes(){
        return null;
    }

    public static void main(String[] args) {
        ServicioCliente sc = new ServicioCliente();
        ServicioInstructor si = new ServicioInstructor();
        Instructor instructor = null;
        try{
           instructor = si.buscarInstructor("124");
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }

        Cliente c = new Cliente("123", "Makunga", 25, 5551234,
                "juanqui@gmail.com", "01/01/1999", "Masculino", "15/03/2023", instructor);
        try {
            sc.insertarCliente(c);
        } catch (GlobalException | NoDataException e) {
            throw new RuntimeException(e);
        }
    }

}

package AccesoADatos;
import Modelo.Cliente;
import Modelo.Instructor;


import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            pstmt = conexion.prepareCall(insertarCliente);
            pstmt.setString(1, cliente.getCedula());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setInt(3, cliente.getTelefono());
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
    public void actualizarCliente(Cliente cliente) throws GlobalException {
        conectar();

        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(actualizarCliente);
            pstmt.setString(1, cliente.getCedula());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setInt(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getCorreo());
            pstmt.setString(5, cliente.getFechaNac());
            pstmt.setString(6, cliente.getSexo());
            pstmt.setString(7, cliente.getFechaInscrip());
            pstmt.setInt(8, cliente.getEdad());
            pstmt.setString(9, cliente.getInstructor().getCedula());
            int resultado = pstmt.executeUpdate();

            //si es diferente de 0 es porq si afecto un registro o mas
            if (resultado == 0) {
                throw new NoDataException("No se realizo la actualizaci�n");
            } else {
                System.out.println("\nModificaci�n Satisfactoria!");
            }
        } catch (SQLException | NoDataException e) {
            throw new GlobalException("Sentencia no valida");
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

    public void eliminarCliente(String cedula) throws GlobalException {
        conectar();

        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(eliminarCliente);
            pstmt.setString(1, cedula);

            int resultado = pstmt.executeUpdate();

            if (resultado == 0) {
                throw new NoDataException("No se realizo el borrado");
            } else {
                System.out.println("\nEliminaci�n Satisfactoria!");
            }
        } catch (SQLException | NoDataException e) {
            throw new GlobalException("Sentencia no valida");
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

    private Cliente buscarClienteBD(String cedula) throws GlobalException {
        conectar();

        ResultSet rs = null;
        //ArrayList coleccion = new ArrayList();
        Cliente cliente = null;
        ServicioInstructor servicioIns = new ServicioInstructor();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarCliente);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cedula);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            if (rs.next()) {
                Cliente clinete = new Cliente.Builder()
                        .cedula(rs.getString("cedula"))
                        .nombre(rs.getString("nombrecom"))
                        .telefono(rs.getInt("telefono"))
                        .correo(rs.getString("correo"))
                        .fechaNac(rs.getString("fechanac"))
                        .sexo(rs.getString("sexo"))
                        .fechaInscrip(rs.getString("fechainscrip"))
                        .edad(rs.getInt("edad"))
                        .instructor(new Instructor.Builder()
                                .cedula(rs.getString("instructor_cedula"))
                                .build()
                        )
                        .build();
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
        return cliente;
    }

    public void buscarCliente(String cedula) throws GlobalException {
        Cliente cliente = buscarClienteBD(cedula);
        String cedulaIns = cliente.getInstructor().getCedula();
        ServicioInstructor servicioIns = new ServicioInstructor();
        try {
            Instructor instructor = servicioIns.buscarInstructor(cedulaIns);
            cliente.setInstructor(instructor);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
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

package AccesoADatos;
import Modelo.Cliente;
import Modelo.Instructor;


import Modelo.Sucursal;
import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCliente extends Conexion {

    private static final String insertarCliente = "{call insertarcliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String actualizarCliente = "{call actualizarcliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String eliminarCliente = "{call eliminarcliente(?)}";
    private static final String buscarCliente = "{?=call buscarcliente(?)}";
    private static final String listarClientes = "{?= call listarcliente()}";
    private static final String listarClientesPorInstructor = "{?= call listarclientesinstructor(?)}";

    public DAOCliente() {}

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
            pstmt.setString(10, cliente.getSucursal().getCod());
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
            pstmt.setString(10, cliente.getSucursal().getCod());
            int resultado = pstmt.executeUpdate();

            //si es diferente de 0 es porq si afecto un registro o mas
            if (resultado == 0) {
                throw new NoDataException("No se realizo la actualizacion");
            } else {
                System.out.println("\nModificacion Satisfactoria!");
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

    public boolean eliminarCliente(String cedula) throws GlobalException {
        conectar();

        CallableStatement pstmt = null;  // Cambiar a CallableStatement
        try {
            pstmt = conexion.prepareCall(eliminarCliente);  // Usar prepareCall
            pstmt.setString(1, cedula);

            int resultado = pstmt.executeUpdate();  // Esto debería funcionar con CallableStatement

            if (resultado == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Agregar esto para ver el error específico
            throw new GlobalException("Error al eliminar cliente: " + e.getMessage());
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
        Cliente cliente = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarCliente);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cedula);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            if (rs.next()) {
                    cliente = new Cliente.Builder()
                        .cedula(rs.getString("cedula"))
                        .nombre(rs.getString("nombre"))
                        .telefono(rs.getInt("telefono"))
                        .correo(rs.getString("correo"))
                        .fechaNac(rs.getString("fechanac"))
                        .sexo(rs.getString("sexo"))
                        .fechaInscrip(rs.getString("fechainscrip"))
                        .edad(rs.getInt("edad"))
                        .instructor(new Instructor.Builder()
                                .cedula(rs.getString("cedula_instructor"))
                                .build())
                        .sucursal(new Sucursal.Builder()
                                .cod(rs.getString("cod_sucursal"))
                                .build())
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

    public Cliente buscarCliente(String cedula) throws GlobalException {
        Cliente cliente = buscarClienteBD(cedula);
        String cedulaIns = cliente.getInstructor().getCedula();
        DAOInstructor servicioIns = new DAOInstructor();
        try {
            Instructor instructor = servicioIns.buscarInstructor(cedulaIns);
            cliente.setInstructor(instructor);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    public ArrayList<Cliente> listarClientes() throws GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList<Cliente> coleccion = new ArrayList<>();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(listarClientes);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                coleccion.add(new Cliente.Builder()
                        .cedula(rs.getString("cedula"))
                        .nombre(rs.getString("nombre"))
                        .telefono(rs.getInt("telefono"))
                        .correo(rs.getString("correo"))
                        .fechaNac(rs.getString("fechaNac"))
                        .sexo(rs.getString("sexo"))
                        .fechaInscrip(rs.getString("fechaInscrip"))
                        .edad(rs.getInt("edad"))
                        .instructor(new Instructor.Builder()
                                .cedula(rs.getString("cedula_instructor"))
                                .build()
                        )
                        .sucursal(new Sucursal.Builder()
                                .cod(rs.getString("cod_sucursal"))
                                .build())
                        .build());
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
        return coleccion;
    }

    public ArrayList<Cliente> listarClientesPorInstructor(String cedulaInstructor) throws GlobalException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            conectar();
            ResultSet rs = null;
            CallableStatement pstmt = null;

            try {
                pstmt = conexion.prepareCall(listarClientesPorInstructor);
                pstmt.registerOutParameter(1, OracleTypes.CURSOR);
                pstmt.setString(2, cedulaInstructor);
                pstmt.execute();
                rs = (ResultSet) pstmt.getObject(1);
                while (rs.next()) {
                    clientes.add(new Cliente.Builder()
                            .cedula(rs.getString("cedula"))
                            .nombre(rs.getString("nombre"))
                            .telefono(rs.getInt("telefono"))
                            .correo(rs.getString("correo"))
                            .fechaNac(rs.getString("fechanac"))
                            .sexo(rs.getString("sexo"))
                            .fechaInscrip(rs.getString("fechainscrip"))
                            .edad(rs.getInt("edad"))
                            .instructor(new Instructor.Builder()
                                    .cedula(rs.getString("cedula_instructor"))
                                    .build()
                            )
                            .sucursal(new Sucursal.Builder()
                                    .cod(rs.getString("cod_sucursal"))
                                    .build())
                            .build());
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
        if (clientes.size() == 0) {
            return null;
        }
        return clientes;
    }


}

package AccesoADatos;
import Modelo.Cliente;
import Modelo.Instructor;


import Modelo.Sucursal;
import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicioCliente extends Servicio {

    private static final String insertarCliente = "{call insertarcliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String actualizarCliente = "{call actualizarcliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String eliminarCliente = "{call eliminarcliente(?)}";
    private static final String buscarCliente = "{?=call buscarcliente(?)}";
    private static final String listarClientes = "{?= call listarclientes()}";
    private static final String listarClientesPorInstructor = "{?= call listarclientesinstructor(?)}";

    public ServicioCliente() {}

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

    public boolean eliminarCliente(String cedula) throws GlobalException {
        conectar();

        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(eliminarCliente);
            pstmt.setString(1, cedula);

            int resultado = pstmt.executeUpdate();

            if (resultado == 0) {
                throw new NoDataException("No se realizo el borrado");
            } else {
                return true;
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
        ServicioInstructor servicioIns = new ServicioInstructor();
        try {
            Instructor instructor = servicioIns.buscarInstructor(cedulaIns);
            cliente.setInstructor(instructor);
        } catch (GlobalException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    public String listarClientes() throws NoDataException, GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
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
                        .fechaNac(rs.getString("fechanac"))
                        .sexo(rs.getString("sexo"))
                        .fechaInscrip(rs.getString("fechainscrip"))
                        .edad(rs.getInt("edad"))
                        .instructor(new Instructor.Builder()
                                .cedula(rs.getString("instructor_cedula"))
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
        if (coleccion.size() == 0) {
            throw new NoDataException("No hay datos");
        }
        return coleccion.toString();
    }

    public String listarClientesPorInstructor(String cedulaInstructor) throws GlobalException {
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
                                    .cedula(rs.getString("instructor_cedula"))
                                    .build()
                            )
                            .sucursal(new Sucursal.Builder()
                                    .cod(rs.getString("cod_instructor"))
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
            return "No hay datos";
        }
        return clientes.toString();
    }

}

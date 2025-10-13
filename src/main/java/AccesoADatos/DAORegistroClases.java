package AccesoADatos;

import Modelo.Cliente;
import Modelo.ClaseGrupal;
import Modelo.Instructor;
import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAORegistroClases extends Conexion {

    private static final String insertarClaseCliente = "{call insertarClaseCliente(?, ?)}";
    private static final String eliminarClaseCliente = "{call eliminarClaseCliente(?, ?)}";
    private static final String buscarClasesPorCliente = "{?=call buscarClasesPorCliente(?)}";
    private static final String buscarClientesPorClase = "{?=call buscarClientesPorClase(?)}";
    private static final String verificarCupoClase = "{?=call verificarCupoClase(?)}";
    private static final String verificarClasesCliente = "{?=call verificarClasesCliente(?)}";

    public DAORegistroClases() {}

    public void insertarClaseCliente(String codigoClase, String cedulaCliente) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(insertarClaseCliente);
            pstmt.setString(1, codigoClase);
            pstmt.setString(2, cedulaCliente);
            boolean resultado = pstmt.execute();
            if (resultado) throw new NoDataException("No se realizó la inserción");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al insertar relación clase-cliente");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
    }

    public void eliminarClaseCliente(String codigoClase, String cedulaCliente) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(eliminarClaseCliente);
            pstmt.setString(1, codigoClase);
            pstmt.setString(2, cedulaCliente);
            int resultado = pstmt.executeUpdate();
            if (resultado == 0) throw new NoDataException("No se realizó la eliminación");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al eliminar relación clase-cliente");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
    }

    public ArrayList<ClaseGrupal> buscarClasesSegunCliente(String cedulaCliente) throws GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList<ClaseGrupal> clases = new ArrayList<>();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarClasesPorCliente);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cedulaCliente);  // Cambiado de 2 a 1
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);

            if (rs == null) {
                return clases; // Retorna lista vacía si no hay resultados
            }

            while (rs.next()) {
                clases.add(new ClaseGrupal(
                        rs.getString("codigo_clase"),
                        rs.getInt("cupomax"),
                        rs.getInt("numsalon"),
                        rs.getString("especialidad"),
                        rs.getString("horario"),
                        new Instructor.Builder()
                                .cedula(rs.getString("cedula_instructor"))
                                .build()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al buscar clases por cliente: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos: " + e.getMessage());
            }
        }
        return clases;
    }

    public ArrayList<Cliente> buscarClientesSegunClase(String codigoClase) throws GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList<Cliente> clientes = new ArrayList<>();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarClientesPorClase);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, codigoClase);  // Cambiado de 2 a 1
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);

            if (rs == null) {
                return clientes; // Retorna lista vacía si no hay resultados
            }

            while (rs.next()) {
                // CORREGIDO: Usar los nombres de columnas exactos del SELECT
                clientes.add(new Cliente.Builder()
                        .cedula(rs.getString("cedula_cliente"))  // Cambiado de "cedula" a "cedula_cliente"
                        .nombre(rs.getString("nombre"))
                        .telefono(rs.getInt("telefono"))
                        .correo(rs.getString("correo"))
                        .fechaNac(rs.getString("fechanac"))
                        .sexo(rs.getString("sexo"))
                        .fechaInscrip(rs.getString("fechainscrip"))
                        .edad(rs.getInt("edad"))
                        .instructor(null)
                        .sucursal(null)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al buscar clientes por clase: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos: " + e.getMessage());
            }
        }
        return clientes;
    }

    public int verificarCupoClase(String codigoClase) throws GlobalException {
        conectar();
        CallableStatement pstmt = null;
        int cuposDisponibles = 0;
        try {
            pstmt = conexion.prepareCall(verificarCupoClase);
            pstmt.registerOutParameter(1, OracleTypes.NUMBER);
            pstmt.setString(2, codigoClase);
            pstmt.execute();
            cuposDisponibles = pstmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al verificar cupo de clase");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
        return cuposDisponibles;
    }

    public int verificarClasesCliente(String cedulaCliente) throws GlobalException {
        conectar();
        CallableStatement pstmt = null;
        int totalClases = 0;
        try {
            pstmt = conexion.prepareCall(verificarClasesCliente);
            pstmt.registerOutParameter(1, OracleTypes.NUMBER);
            pstmt.setString(2, cedulaCliente);
            pstmt.execute();
            totalClases = pstmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Error al verificar clases del cliente");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
        return totalClases;
    }
}

package AccesoADatos;

import Modelo.Sucursal;
import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOSucursal extends Conexion {

    private static final String insertarSucursal = "{call insertarSucursal(?, ?, ?, ?, ?)}";
    private static final String modificarSucursal = "{call modificarSucursal(?, ?, ?, ?, ?)}";
    private static final String eliminarSucursal = "{call eliminarSucursal(?)}";
    private static final String buscarSucursal = "{?=call buscarSucursal(?)}";
    private static final String listarSucursales = "{?= call listarSucursal()}";

    public DAOSucursal() {}

    public void insertarSucursal(Sucursal sucursal) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(insertarSucursal);
            pstmt.setString(1, sucursal.getCod());
            pstmt.setString(2, sucursal.getProvi());
            pstmt.setString(3, sucursal.getCanton());
            pstmt.setString(4, sucursal.getCorreo());
            pstmt.setInt(5, sucursal.getTelef());

            boolean resultado = pstmt.execute();
            if (resultado) {
                throw new NoDataException("No se realizó la inserción");
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
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
    }

    public void modificarSucursal(Sucursal sucursal) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(modificarSucursal);
            pstmt.setString(1, sucursal.getCod());
            pstmt.setString(2, sucursal.getProvi());
            pstmt.setString(3, sucursal.getCanton());
            pstmt.setString(4, sucursal.getCorreo());
            pstmt.setInt(5, sucursal.getTelef());

            int resultado = pstmt.executeUpdate();
            if (resultado == 0) {
                throw new NoDataException("No se realizó la actualización");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no válida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
    }

    public void eliminarSucursal(String codigo) throws GlobalException, NoDataException {
        conectar();
        PreparedStatement pstmt = null;

        try {
            pstmt = conexion.prepareStatement(eliminarSucursal);
            pstmt.setString(1, codigo);

            int resultado = pstmt.executeUpdate();
            if (resultado == 0) {
                throw new NoDataException("No se realizó el borrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no válida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
    }

    public Sucursal buscarSucursal(String codigo) throws GlobalException {
        conectar();
        ResultSet rs = null;
        Sucursal sucursal = null;
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(buscarSucursal);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, codigo);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);
            if (rs.next()) {
                sucursal = new Sucursal(
                        rs.getString("cod"),
                        rs.getString("provi"),
                        rs.getString("canton"),
                        rs.getString("correo"),
                        rs.getInt("telef")
                );
            }
            return sucursal;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no válida");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
    }

    public ArrayList<Sucursal> listarSucursales() throws GlobalException, NoDataException {
        conectar();
        ResultSet rs = null;
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        CallableStatement pstmt = null;

        try {
            pstmt = conexion.prepareCall(listarSucursales);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();

            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                sucursales.add(new Sucursal(
                        rs.getString("cod"),
                        rs.getString("provi"),
                        rs.getString("canton"),
                        rs.getString("correo"),
                        rs.getInt("telef")
                ));
            }
            return sucursales;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no válida");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos inválidos o nulos");
            }
        }
    }
}
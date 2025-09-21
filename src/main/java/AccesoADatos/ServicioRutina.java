package AccesoADatos;
import Modelo.Rutina;
import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicioRutina extends Servicio {

    private static final String insertarRutina = "{call insertarrutina(?, ?, ?, ?, ?, ?)}";
    private static final String modificarRutina = "{call modificarrutina(?, ?, ?, ?, ?, ?)}";
    private static final String eliminarRutina = "{call eliminarrutina(?)}";
    private static final String buscarRutina = "{?=call buscarrutina(?)}";
    private static final String listarRutinas = "{?= call listarrutina()}";

    public ServicioRutina() {}

    public void insertarRutina(Rutina rutina, String id_cliente) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(insertarRutina);
            pstmt.setString(1, id_cliente);
            pstmt.setString(2, rutina.getPecho());
            pstmt.setString(3, rutina.getTriceps());
            pstmt.setString(4, rutina.getBiceps());
            pstmt.setString(5, rutina.getPiernas());
            pstmt.setString(6, rutina.getEspalda());
            boolean resultado = pstmt.execute();
            if (resultado) {
                throw new NoDataException("No se realizo la inserción");
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

    public void modificarRutina(Rutina rutina, String id_cliente) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(modificarRutina);
            pstmt.setString(1, id_cliente);
            pstmt.setString(2, rutina.getPecho());
            pstmt.setString(3, rutina.getTriceps());
            pstmt.setString(4, rutina.getBiceps());
            pstmt.setString(5, rutina.getPiernas());
            pstmt.setString(6, rutina.getEspalda());
            int resultado = pstmt.executeUpdate();
            if (resultado == 0) {
                throw new NoDataException("No se realizo la actualización");
            }
        } catch (SQLException e) {
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

    public void eliminarRutina(String id_cliente) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(eliminarRutina);
            pstmt.setString(1, id_cliente);
            int resultado = pstmt.executeUpdate();
            if (resultado == 0) {
                throw new NoDataException("No se realizo el borrado");
            }
        } catch (SQLException e) {
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

    public Rutina buscarRutina(String id_cliente) throws GlobalException {
        conectar();
        ResultSet rs = null;
        Rutina rutina = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarRutina);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id_cliente);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            if (rs.next()) {
                rutina = new Rutina(
                        rs.getString("pecho"),
                        rs.getString("triceps"),
                        rs.getString("biceps"),
                        rs.getString("piernas"),
                        rs.getString("espalda")
                );
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
        return rutina;
    }

    public String listarRutinas() throws NoDataException, GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList<Rutina> coleccion = new ArrayList<>();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(listarRutinas);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                coleccion.add(new Rutina(
                        rs.getString("pecho"),
                        rs.getString("triceps"),
                        rs.getString("biceps"),
                        rs.getString("piernas"),
                        rs.getString("espalda")
                ));
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
}
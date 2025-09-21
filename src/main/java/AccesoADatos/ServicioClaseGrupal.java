package AccesoADatos;
import Modelo.ClaseGrupal;
import Modelo.Instructor;
import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicioClaseGrupal extends Servicio {

    private static final String insertarClaseGrupal = "{call insertarclasegrupal(?, ?, ?, ?, ?, ?)}";
    private static final String modificarClaseGrupal = "{call modificarclasegrupal(?, ?, ?, ?, ?, ?)}";
    private static final String eliminarClaseGrupal = "{call eliminarclasegrupal(?)}";
    private static final String buscarClaseGrupal = "{?=call buscarclasegrupal(?)}";
    private static final String listarClasesGrupales = "{?= call listarclasegrupal()}";

    public ServicioClaseGrupal() {}

    public void insertarClaseGrupal(ClaseGrupal claseGrupal) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(insertarClaseGrupal);
            pstmt.setString(1, claseGrupal.getCodigo());
            pstmt.setInt(2, claseGrupal.getCupoMax());
            pstmt.setInt(3, claseGrupal.getNumSalon());
            pstmt.setString(4, claseGrupal.getEspecialidad());
            pstmt.setString(5, claseGrupal.getHorario());
            pstmt.setString(6, claseGrupal.getInstructor().getCedula());
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

    public void modificarClaseGrupal(ClaseGrupal claseGrupal) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(modificarClaseGrupal);
            pstmt.setString(1, claseGrupal.getCodigo());
            pstmt.setInt(2, claseGrupal.getCupoMax());
            pstmt.setInt(3, claseGrupal.getNumSalon());
            pstmt.setString(4, claseGrupal.getEspecialidad());
            pstmt.setString(5, claseGrupal.getHorario());
            pstmt.setString(6, claseGrupal.getInstructor().getCedula());
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

    public void eliminarClaseGrupal(String id) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(eliminarClaseGrupal);
            pstmt.setString(1, id);
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

    public ClaseGrupal buscarClaseGrupal(String id) throws GlobalException {
        conectar();
        ResultSet rs = null;
        ClaseGrupal claseGrupal = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarClaseGrupal);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            if (rs.next()) {
                claseGrupal = new ClaseGrupal(
                        rs.getString("id"),
                        rs.getInt("cupoMax"),
                        rs.getInt("numSalon"),
                        rs.getString("especialidad"),
                        rs.getString("horario"),
                        new Instructor.Builder()
                                .cedula(rs.getString("cedula_instructor"))
                                .build()
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
        return claseGrupal;
    }

    public String listarClasesGrupales() throws NoDataException, GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList<ClaseGrupal> coleccion = new ArrayList<>();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(listarClasesGrupales);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                coleccion.add(new ClaseGrupal(
                        rs.getString("id"),
                        rs.getInt("cupoMax"),
                        rs.getInt("numSalon"),
                        rs.getString("especialidad"),
                        rs.getString("horario"),
                        new Instructor.Builder()
                                .cedula(rs.getString("cedula_instructor"))
                                .build()
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
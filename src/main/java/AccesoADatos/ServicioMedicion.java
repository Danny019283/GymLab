package AccesoADatos;
import Modelo.Medicion;
import Modelo.Cliente;
import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicioMedicion extends Servicio {

    private static final String insertarMedicion = "{call insertarmedicion(? ,? ,? ,? ,? ,? ,? ,? ,? ,?)}";
    private static final String modificarMedicion = "{call modificarmedicion(? ,? ,? ,? ,? ,? ,? ,? ,? ,?)}";
    private static final String eliminarMedicion = "{call eliminarmedicion(?)}";
    private static final String buscarMedicion = "{?=call buscarmedicion(?)}";
    private static final String listarMediciones = "{?= call listarmedicion()}";

    public ServicioMedicion() {}

    public void insertarMedicion(Medicion medicion) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(insertarMedicion);
            pstmt.setString(1, medicion.getCliente().getCedula());
            pstmt.setFloat(2, medicion.getPeso());
            pstmt.setFloat(3, medicion.getEstatura());
            pstmt.setFloat(4, medicion.getPorcGrasa());
            pstmt.setFloat(5, medicion.getPorcMusculo());
            pstmt.setFloat(6, medicion.getPorcGrasaVis());
            pstmt.setFloat(7, medicion.getCintura());
            pstmt.setFloat(8, medicion.getPecho());
            pstmt.setFloat(9, medicion.getMuslo());
            pstmt.setDate(10, java.sql.Date.valueOf(medicion.getFechaDeMedicion()));
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

    public void modificarMedicion(Medicion medicion) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(modificarMedicion);
            pstmt.setFloat(1, medicion.getPeso());
            pstmt.setFloat(2, medicion.getEstatura());
            pstmt.setFloat(3, medicion.getPorcGrasa());
            pstmt.setFloat(4, medicion.getPorcMusculo());
            pstmt.setFloat(5, medicion.getPorcGrasaVis());
            pstmt.setFloat(6, medicion.getCintura());
            pstmt.setFloat(7, medicion.getPecho());
            pstmt.setFloat(8, medicion.getMuslo());
            pstmt.setDate(10, java.sql.Date.valueOf(medicion.getFechaDeMedicion()));
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

    public void eliminarMedicion(String cedulaCliente) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(eliminarMedicion);
            pstmt.setString(1, cedulaCliente);
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

    public ArrayList<Medicion> buscarMedicion(Cliente cliente) throws GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList<Medicion> mediciones = new ArrayList<>();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarMedicion);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cliente.getCedula());
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                mediciones.add(
                new Medicion(
                        cliente,
                        rs.getFloat("peso"),
                        rs.getFloat("estatura"),
                        rs.getFloat("porcGrasa"),
                        rs.getFloat("porcMusculo"),
                        rs.getFloat("porcGrasaVis"),
                        rs.getFloat("cintura"),
                        rs.getFloat("pecho"),
                        rs.getFloat("muslo"),
                        rs.getDate("fecha_de_medicion").toLocalDate())
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
        return mediciones;
    }

    public String listarMediciones() throws NoDataException, GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList<Medicion> coleccion = new ArrayList<>();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(listarMediciones);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
            }
                coleccion.add(new Medicion.Builder()
                        .peso(rs.getFloat("peso"))
                        .estatura(rs.getFloat("estatura"))
                        .porcGrasa(rs.getFloat("porcGrasa"))
                        .porcMusculo(rs.getFloat("porcMusculo"))
                        .porcGrasaVis(rs.getFloat("porcGrasaVis"))
                        .cintura(rs.getFloat("cintura"))
                        .pecho(rs.getFloat("pecho"))
                        .muslo(rs.getFloat("muslo"))
                        .fechaDeMedicion(rs.getDate("fecha_de_medicion").toLocalDate()).build()
                );
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
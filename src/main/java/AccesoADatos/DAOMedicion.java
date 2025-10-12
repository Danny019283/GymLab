package AccesoADatos;
import Modelo.Medicion;
import Modelo.Cliente;
import oracle.jdbc.internal.OracleTypes;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOMedicion extends Conexion {

    private static final String insertarMedicion = "{call insertarmedicion(? ,? ,? ,? ,? ,? ,? ,? ,? ,?)}";
    private static final String modificarMedicion = "{call modificarmedicion(? ,? ,? ,? ,? ,? ,? ,? ,? ,?)}";
    private static final String eliminarMedicion = "{call eliminarmedicion(?)}";
    private static final String buscarMedicion = "{?=call buscarmedicion(?)}";
    private static final String listarMediciones = "{?= call listarmedicion()}";
    private static final String eliminarMasAntigua = "{call eliminarmedicionmasvieja(?)}";

    public DAOMedicion() {}

    public void insertarMedicion(Medicion medicion) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        System.out.println(medicion.getPeso());
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

    public void eliminarMasAntigua(String cedulaCliente) throws GlobalException, NoDataException {
        conectar();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(eliminarMasAntigua);
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

    public ArrayList<Medicion> buscarMedicion(String cedula) throws GlobalException {
        conectar();
        ResultSet rs = null;
        ArrayList<Medicion> mediciones = new ArrayList<>();
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(buscarMedicion);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, cedula);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);

            while (rs.next()) {
                mediciones.add(
                        new Medicion.Builder()
                                .cliente(new Cliente.Builder()
                                        .cedula(rs.getString("cedula_cliente")).build())
                                .peso(rs.getFloat("peso"))
                                .estatura(rs.getFloat("estatura"))
                                .porcGrasa(rs.getFloat("porcGrasa"))
                                .porcMusculo(rs.getFloat("porcMusculo"))
                                .porcGrasaVis(rs.getFloat("porcGrasaVis"))
                                .cintura(rs.getFloat("cintura"))
                                .pecho(rs.getFloat("pecho"))
                                .muslo(rs.getFloat("muslo"))
                                .fechaDeMedicion(rs.getDate("fecha_de_medicion").toLocalDate())
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
        return mediciones;
    }

    public ArrayList<Medicion> listarMediciones() throws NoDataException, GlobalException {
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
                coleccion.add(new Medicion.Builder()
                        .cliente(new Cliente.Builder()
                                .cedula(rs.getString("cedula_cliente")).build())
                        .peso(rs.getFloat("peso"))
                        .estatura(rs.getFloat("estatura"))
                        .porcGrasa(rs.getFloat("porcGrasa"))
                        .porcMusculo(rs.getFloat("porcMusculo"))
                        .porcGrasaVis(rs.getFloat("porcGrasaVis"))
                        .cintura(rs.getFloat("cintura"))
                        .pecho(rs.getFloat("pecho"))
                        .muslo(rs.getFloat("muslo"))
                        .fechaDeMedicion(rs.getDate("fecha_de_medicion").toLocalDate())
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
        if (coleccion.isEmpty()) {
            throw new NoDataException("No hay datos");
        }
        return coleccion;
    }
}
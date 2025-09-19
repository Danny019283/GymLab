package AccesoADatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servicio {

    protected Connection conexion = null;

    public Servicio() {

    }

    protected void conectar()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@//localhost:1521/xe";
            String username = "System";
            String password = "7R807hur";
            conexion = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void desconectar() throws SQLException{
        if(!conexion.isClosed())
        {
            conexion.close();
        }
    }
    public static void main(String[] args) {
        Servicio s = new Servicio();
        try {
            s.conectar();
            System.out.println("Conexion");
            s.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package AccesoADatos;
import Modelo.Cliente;

import java.util.ArrayList;

public class ClienteDAO {
    public boolean agregarCliente(Cliente c){
        ;
    }

    public boolean actualizarCliente(Cliente c){
        ;
    }

    public boolean eliminarCliente(String cedula){
        ;
    }

    public Cliente buscarCliente(String cedula){
        ;
    }

    public String listarClientes(){
        ;
    }

    public ArrayList<Cliente> getPagina(int inicio, int fin, int pagina){
        ArrayList <Cliente> pagClientes = new ArrayList<>();
        //devuelve la copia
        return new ArrayList<>(pagClientes);
    }


}

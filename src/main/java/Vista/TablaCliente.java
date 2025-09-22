package Vista;//package Vista;


import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Modelo.Cliente;


/**
 *
 * @author Danny
 */

public class TablaCliente extends AbstractTableModel{
    private final String[] cols = {"Cedula", "Nombre", "Telefono", "Correo", "Fecha de Nacimiento",
            "Sexo", "Fecha de inscripción", "Edad", "Instructor", "Cod. Sucursal"}; //crea las colummnas
    private final List<Cliente> data = new ArrayList<>(); //data es la Lista donde se van almacenar los clientes

    public void refrescarData(List<Cliente> clientes) {
        data.clear(); //limpia antes de cargar los datos
        data.addAll(clientes); //agrega los clientes a la lista interna
        fireTableDataChanged(); //notifica que lo datos han sido actualizados entonces refresca la lista
    }

    public void add(Cliente cliente) {
        data.add(cliente); //agrega un producto a la tabla
        int fila = data.size() - 1;
        fireTableRowsInserted(fila, fila); //Notifica a todos los oyentes que se han insertado filas en la ultima posicion
    }

    //son metodos de un super tipo (propios de Table Model)
    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> cliente.getCedula();
            case 1 -> cliente.getNombre();
            case 2 -> cliente.getTelefono();
            case 3 -> cliente.getCorreo();
            case 4 -> cliente.getFechaNac();
            case 5 -> cliente.getSexo();
            case 6 -> cliente.getFechaInscrip();
            case 7 -> cliente.getEdad();
            case 8 -> cliente.getInstructor().getCedula();
            case 9 -> cliente.getSucursal().getCod();
            default -> "";
        };
    }
    //Devuelve la superclase más específica para todos los valores de celda de la columna.
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 3 ? Integer.class : String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // editamos desde el formulario
    }

}


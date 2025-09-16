package Vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


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
            "Sexo", "Fecha de inscripción", "Edad", "Instructor"}; //crea las colummnas
    private final List<Cliente> data = new ArrayList<>(); //data es la Lista donde se van almacenar los clientes

    public void setData(List<Cliente> clientes) {
        data.clear(); //limpia antes de cargar los datos
        data.addAll(clientes); //agrega los clientes a la lista interna
        fireTableDataChanged(); //notifica que lo datos han sigo actualizado entonces refresca la lista
    }

    public Cliente getAt(int row) {
        return data.get(row); //obtiene el clientes que se encuentra en una fila especifica
    }

    public void add(Cliente produc) {
        data.add(produc); //agrega un producto a la tabla
        int fila = data.size() - 1;
        fireTableRowsInserted(fila, fila); //Notifica a todos los oyentes que se han insertado filas en la ultima posicion
    }

    public void update(int fila, Cliente produc) {
        data.set(fila, produc);
        fireTableRowsUpdated(fila, fila); //Notifica a todos los oyentes que se han actualizado en una posicion especifica
    }

    public void remove(int fila) {
        data.remove(fila);
        fireTableRowsDeleted(fila, fila); //Notifica a todos los oyentes que se han eliminado filas en una posicion especifica
    }

    //son metodos de un super tipo (propios de Table Model)
    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getCodigo();
            case 1 -> c.getNombre();
            case 2 -> {if (c.isImportado()) {yield "si";} else{yield "no";}}
            case 3 -> c.getPrecio();
            case 4 -> c.getTipo().getDescripcion();
            case 5 -> c.getTipo().getPorcentajeImpuesto();
            case 6 -> c.PrecioFinal();
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


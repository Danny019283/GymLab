package Vista.Tablas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Modelo.Sucursal;

public class TablaSucursal extends AbstractTableModel {
    private final String[] cols = {"Código", "Provincia", "Cantón", "Correo", "Teléfono"};
    private final List<Sucursal> data = new ArrayList<>();

    public void refrescarData(List<Sucursal> sucursales) {
        data.clear();
        data.addAll(sucursales);
        fireTableDataChanged();
    }

    public void add(Sucursal sucursal) {
        data.add(sucursal);
        int fila = data.size() - 1;
        fireTableRowsInserted(fila, fila);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sucursal sucursal = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> sucursal.getCod();
            case 1 -> sucursal.getProvi();
            case 2 -> sucursal.getCanton();
            case 3 -> sucursal.getCorreo();
            case 4 -> sucursal.getTelef();
            default -> "";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 4 ? Integer.class : String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
package Vista;

import Modelo.Medicion;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TablaMedicion extends AbstractTableModel {
    private final String[] cols = {"Cédula", "Peso", "Estatura", "% Grasa", "% Músculo",
            "% Grasa Vis", "Cintura", "Pecho", "Muslo", "Fecha"};
    private final List<Medicion> data = new ArrayList<>();

    public void refrescarData(List<Medicion> mediciones) {
        data.clear();
        data.addAll(mediciones);
        fireTableDataChanged();
    }

    public void add(Medicion medicion) {
        data.add(medicion);
        int fila = data.size() - 1;
        fireTableRowsInserted(fila, fila);
    }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Medicion medicion = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> medicion.getCliente().getCedula();
            case 1 -> medicion.getPeso();
            case 2 -> medicion.getEstatura();
            case 3 -> medicion.getPorcGrasa();
            case 4 -> medicion.getPorcMusculo();
            case 5 -> medicion.getPorcGrasaVis();
            case 6 -> medicion.getCintura();
            case 7 -> medicion.getPecho();
            case 8 -> medicion.getMuslo();
            case 9 -> medicion.getFechaDeMedicion().toString();
            default -> "";
        };
    }

    public Medicion getMedicionAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            return data.get(rowIndex);
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex >= 1 && columnIndex <= 8 ? Float.class : String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
package Vista.Tablas;

import Modelo.DTOs.MedicionDTO;
import Modelo.Medicion;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TablaMedicion extends AbstractTableModel {
    private final String[] cols = {"Cédula", "Peso", "Estatura", "% Grasa", "% Músculo",
            "% Grasa Vis", "Cintura", "Pecho", "Muslo", "Fecha"};
    private final List<MedicionDTO> data = new ArrayList<>();

    public void refrescarData(List<MedicionDTO> mediciones) {
        data.clear();
        data.addAll(mediciones);
        fireTableDataChanged();
    }

    public void add(MedicionDTO medicion) {
        data.add(medicion);
        int fila = data.size() - 1;
        fireTableRowsInserted(fila, fila);
    }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MedicionDTO medicion = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> medicion.getCedulaCliente();
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

    public MedicionDTO getMedicionAt(int rowIndex) {
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
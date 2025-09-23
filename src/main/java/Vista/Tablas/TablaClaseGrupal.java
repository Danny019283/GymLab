package Vista.Tablas;

import Modelo.ClaseGrupal;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TablaClaseGrupal extends AbstractTableModel {
    private final String[] cols = {"Código", "Cupo Máximo", "Número Salon", "Especialidad",
            "Horario", "Instructor"};
    private final List<ClaseGrupal> data = new ArrayList<>();

    public void refrescarData(List<ClaseGrupal> clasesGrupales) {
        data.clear();
        data.addAll(clasesGrupales);
        fireTableDataChanged();
    }

    public void add(ClaseGrupal claseGrupal) {
        data.add(claseGrupal);
        int fila = data.size() - 1;
        fireTableRowsInserted(fila, fila);
    }

    @Override
    public int getRowCount() { return data.size(); }

    @Override
    public int getColumnCount() { return cols.length; }

    @Override
    public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClaseGrupal claseGrupal = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> claseGrupal.getCodigo();
            case 1 -> claseGrupal.getCupoMax();
            case 2 -> claseGrupal.getNumSalon();
            case 3 -> claseGrupal.getEspecialidad();
            case 4 -> claseGrupal.getHorario();
            case 5 -> claseGrupal.getInstructor() != null ?
                    claseGrupal.getInstructor().getNombreCom() : "N/A";
            default -> "";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 1 || columnIndex == 2 ? Integer.class : String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
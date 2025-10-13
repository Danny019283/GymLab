package Vista.Tablas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import Modelo.DTOs.InstructorDTO;
import Modelo.Instructor;

public class TablaInstructor extends AbstractTableModel {
    private final String[] cols = {"Cédula", "Nombre Completo", "Teléfono", "Correo", "Fecha de Nacimiento", "Especialidades", "Código Sucursal"};
    private final List<InstructorDTO> data = new ArrayList<>();

    public void refrescarData(List<InstructorDTO> instructores) {
        data.clear();
        data.addAll(instructores);
        fireTableDataChanged();
    }

    public void add(InstructorDTO instructor) {
        data.add(instructor);
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
        InstructorDTO instructor = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> instructor.getCedula();
            case 1 -> instructor.getNombreCom();
            case 2 -> instructor.getTelef();
            case 3 -> instructor.getCorreo();
            case 4 -> instructor.getFechaNac();
            case 5 -> String.join(", ", instructor.getEspecialidad());
            case 6 -> instructor.getCodigoSucursal();
            default -> "";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 2 ? Integer.class : String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
package Vista.Formularios;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioClaseGrupal {
    private final JTextField codigoField = new JTextField(15);
    private final JTextField cupoMaxField = new JTextField(15);
    private final JTextField numSalonField = new JTextField(15);
    private final JTextField especialidadField = new JTextField(15);
    private final JTextField horarioField = new JTextField(15);
    private final JTextField instructorField = new JTextField(15);

    private List<JComponent> componentes = new ArrayList<>();

    public FormularioClaseGrupal() {
        configurarComponentes();
    }

    private void configurarComponentes() {
        componentes.add(codigoField);
        componentes.add(cupoMaxField);
        componentes.add(numSalonField);
        componentes.add(especialidadField);
        componentes.add(horarioField);
        componentes.add(instructorField);
    }

    public boolean mostrarDialogo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        agregarCampo(panel, "Código:", codigoField);
        agregarCampo(panel, "Cupo Máximo:", cupoMaxField);
        agregarCampo(panel, "Número Salon:", numSalonField);
        agregarCampo(panel, "Especialidad:", especialidadField);
        agregarCampo(panel, "Horario:", horarioField);
        agregarCampo(panel, "Cédula Instructor:", instructorField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                titulo,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        return result == JOptionPane.OK_OPTION;
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo) {
        JPanel campoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(etiqueta);
        label.setPreferredSize(new Dimension(120, 25));

        campo.setPreferredSize(new Dimension(200, 25));

        campoPanel.add(label);
        campoPanel.add(campo);
        panel.add(campoPanel);
    }

    public boolean validarDatos() {
        if (getCodigo().isEmpty() || getEspecialidad().isEmpty() ||
                getHorario().isEmpty() || getInstructor().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int cupo = Integer.parseInt(cupoMaxField.getText().trim());
            int salon = Integer.parseInt(numSalonField.getText().trim());

            if (cupo <= 0 || salon <= 0) {
                JOptionPane.showMessageDialog(null, "Cupo máximo y número de salón deben ser mayores a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cupo máximo y número de salón deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Métodos get para obtener los valores
    public String getCodigo() {
        return codigoField.getText().trim();
    }

    public int getCupoMax() {
        return Integer.parseInt(cupoMaxField.getText().trim());
    }

    public int getNumSalon() {
        return Integer.parseInt(numSalonField.getText().trim());
    }

    public String getEspecialidad() {
        return especialidadField.getText().trim();
    }

    public String getHorario() {
        return horarioField.getText().trim();
    }

    public String getInstructor() {
        return instructorField.getText().trim();
    }
}
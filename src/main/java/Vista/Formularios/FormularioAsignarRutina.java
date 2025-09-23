package Vista.Formularios;

import javax.swing.*;
import java.awt.*;

public class FormularioAsignarRutina {

    private final JTextField cedulaClienteField = new JTextField(15);
    private final JTextField pechoField = new JTextField(15);
    private final JTextField tricepsField = new JTextField(15);
    private final JTextField bicepsField = new JTextField(15);
    private final JTextField piernasField = new JTextField(15);
    private final JTextField espaldaField = new JTextField(15);

    public boolean mostrarDialogo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Agregar campos al panel
        agregarCampo(panel, "Cédula Cliente:", cedulaClienteField);
        agregarCampo(panel, "Ejercicios Pecho:", pechoField);
        agregarCampo(panel, "Ejercicios Tríceps:", tricepsField);
        agregarCampo(panel, "Ejercicios Bíceps:", bicepsField);
        agregarCampo(panel, "Ejercicios Piernas:", piernasField);
        agregarCampo(panel, "Ejercicios Espalda:", espaldaField);

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

    // Métodos get para obtener los valores
    public String getCedulaCliente() {
        return cedulaClienteField.getText().trim();
    }

    public String getPecho() {
        return pechoField.getText().trim();
    }

    public String getTriceps() {
        return tricepsField.getText().trim();
    }

    public String getBiceps() {
        return bicepsField.getText().trim();
    }

    public String getPiernas() {
        return piernasField.getText().trim();
    }

    public String getEspalda() {
        return espaldaField.getText().trim();
    }
}
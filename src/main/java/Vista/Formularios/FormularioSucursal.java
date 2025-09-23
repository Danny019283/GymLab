package Vista.Formularios;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioSucursal {

    private final JTextField codField = new JTextField(15);
    private final JTextField proviField = new JTextField(15);
    private final JTextField cantonField = new JTextField(15);
    private final JTextField correoField = new JTextField(15);
    private final JTextField telefField = new JTextField(15);

    private List<JComponent> componentes = new ArrayList<>();

    public FormularioSucursal() {
        configurarComponentes();
    }

    private void configurarComponentes() {
        componentes.add(codField);
        componentes.add(proviField);
        componentes.add(cantonField);
        componentes.add(correoField);
        componentes.add(telefField);
    }

    public boolean mostrarDialogo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Agregar campos al panel
        agregarCampo(panel, "Código:", codField);
        agregarCampo(panel, "Provincia:", proviField);
        agregarCampo(panel, "Cantón:", cantonField);
        agregarCampo(panel, "Correo:", correoField);
        agregarCampo(panel, "Teléfono:", telefField);

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
    public String getCod() {
        return codField.getText().trim();
    }

    public String getProvi() {
        return proviField.getText().trim();
    }

    public String getCanton() {
        return cantonField.getText().trim();
    }

    public String getCorreo() {
        return correoField.getText().trim();
    }

    public int getTelef() {
        return Integer.parseInt(telefField.getText().trim());
    }
}
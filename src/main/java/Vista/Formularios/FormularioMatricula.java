package Vista.Formularios;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioMatricula {
    private final JTextField txtCedulaCliente = new JTextField(15);
    private final JTextField txtCodClase = new JTextField(15);

    private List<JComponent> componentes = new ArrayList<>();

    public FormularioMatricula() {
        configurarComponentes();
    }

    private void configurarComponentes() {
        componentes.add(txtCedulaCliente);
        componentes.add(txtCodClase);

    }

    public boolean mostrarDialogo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Agregar campos al panel
        agregarCampo(panel, "Cédula del Cliente:", txtCedulaCliente);
        agregarCampo(panel, "Codigo de la Clase:", txtCodClase);

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

    private void agregarCampoConScroll(JPanel panel, String etiqueta, JTextArea area) {
        JPanel campoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(etiqueta);
        label.setPreferredSize(new Dimension(120, 25));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(200, 60));

        campoPanel.add(label);
        campoPanel.add(scrollPane);
        panel.add(campoPanel);
    }

    public boolean validarDatos() {
        if (getCedulaCliente().isEmpty() || getCodClase().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (getCedulaCliente().length() < 9) {
            JOptionPane.showMessageDialog(null, "La cédula debe tener al menos 9 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Métodos get para obtener los valores
    public String getCedulaCliente() {
        return txtCedulaCliente.getText().trim();
    }

    public String getCodClase() {
        return txtCodClase.getText().trim();
    }

}

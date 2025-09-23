package Vista.Formularios;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioRutina {

    private final JTextField cedulaClienteField = new JTextField(15);
    private final JTextArea pechoArea = new JTextArea(3, 20);
    private final JTextArea tricepsArea = new JTextArea(3, 20);
    private final JTextArea bicepsArea = new JTextArea(3, 20);
    private final JTextArea piernasArea = new JTextArea(3, 20);
    private final JTextArea espaldaArea = new JTextArea(3, 20);

    private List<JComponent> componentes = new ArrayList<>();

    public FormularioRutina() {
        configurarComponentes();
    }

    private void configurarComponentes() {
        // Configurar áreas de texto con scroll
        pechoArea.setLineWrap(true);
        pechoArea.setWrapStyleWord(true);
        tricepsArea.setLineWrap(true);
        tricepsArea.setWrapStyleWord(true);
        bicepsArea.setLineWrap(true);
        bicepsArea.setWrapStyleWord(true);
        piernasArea.setLineWrap(true);
        piernasArea.setWrapStyleWord(true);
        espaldaArea.setLineWrap(true);
        espaldaArea.setWrapStyleWord(true);

        componentes.add(cedulaClienteField);
        componentes.add(pechoArea);
        componentes.add(tricepsArea);
        componentes.add(bicepsArea);
        componentes.add(piernasArea);
        componentes.add(espaldaArea);
    }

    public boolean mostrarDialogo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Agregar campos al panel
        agregarCampo(panel, "Cédula Cliente:", cedulaClienteField);
        agregarCampoConScroll(panel, "Ejercicios de Pecho:", pechoArea);
        agregarCampoConScroll(panel, "Ejercicios de Tríceps:", tricepsArea);
        agregarCampoConScroll(panel, "Ejercicios de Bíceps:", bicepsArea);
        agregarCampoConScroll(panel, "Ejercicios de Piernas:", piernasArea);
        agregarCampoConScroll(panel, "Ejercicios de Espalda:", espaldaArea);

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
        if (getCedulaCliente().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La cédula del cliente es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (getPecho().isEmpty() && getTriceps().isEmpty() && getBiceps().isEmpty() &&
                getPiernas().isEmpty() && getEspalda().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Al menos un grupo muscular debe tener ejercicios asignados", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Métodos get para obtener los valores
    public String getCedulaCliente() {
        return cedulaClienteField.getText().trim();
    }

    public String getPecho() {
        return pechoArea.getText().trim();
    }

    public String getTriceps() {
        return tricepsArea.getText().trim();
    }

    public String getBiceps() {
        return bicepsArea.getText().trim();
    }

    public String getPiernas() {
        return piernasArea.getText().trim();
    }

    public String getEspalda() {
        return espaldaArea.getText().trim();
    }
}
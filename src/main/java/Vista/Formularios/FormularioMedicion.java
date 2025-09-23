package Vista.Formularios;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FormularioMedicion {
    private final JTextField cedulaField = new JTextField(15);
    private final JTextField pesoField = new JTextField(15);
    private final JTextField estaturaField = new JTextField(15);
    private final JTextField porcGrasaField = new JTextField(15);
    private final JTextField porcMusculoField = new JTextField(15);
    private final JTextField porcGrasaVisField = new JTextField(15);
    private final JTextField cinturaField = new JTextField(15);
    private final JTextField pechoField = new JTextField(15);
    private final JTextField musloField = new JTextField(15);

    private List<JComponent> componentes = new ArrayList<>();

    public FormularioMedicion() {
        configurarComponentes();
    }

    private void configurarComponentes() {
        componentes.add(cedulaField);
        componentes.add(pesoField);
        componentes.add(estaturaField);
        componentes.add(porcGrasaField);
        componentes.add(porcMusculoField);
        componentes.add(porcGrasaVisField);
        componentes.add(cinturaField);
        componentes.add(pechoField);
        componentes.add(musloField);
    }

    public boolean mostrarDialogo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        agregarCampo(panel, "Cédula Cliente:", cedulaField);
        agregarCampo(panel, "Peso (kg):", pesoField);
        agregarCampo(panel, "Estatura (m):", estaturaField);
        agregarCampo(panel, "% Grasa:", porcGrasaField);
        agregarCampo(panel, "% Músculo:", porcMusculoField);
        agregarCampo(panel, "% Grasa Visceral:", porcGrasaVisField);
        agregarCampo(panel, "Cintura (cm):", cinturaField);
        agregarCampo(panel, "Pecho (cm):", pechoField);
        agregarCampo(panel, "Muslo (cm):", musloField);


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
        if (getCedula().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La cédula es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            float peso = getPeso();
            float estatura = getEstatura();
            float porcGrasa = getPorcGrasa();
            float porcMusculo = getPorcMusculo();
            float porcGrasaVis = getPorcGrasaVis();
            float cintura = getCintura();
            float pecho = getPecho();
            float muslo = getMuslo();

            if (peso <= 0 || estatura <= 0 || porcGrasa < 0 || porcMusculo < 0 ||
                    porcGrasaVis < 0 || cintura <= 0 || pecho <= 0 || muslo <= 0) {
                JOptionPane.showMessageDialog(null, "Todos los valores numéricos deben ser positivos", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (porcGrasa > 100 || porcMusculo > 100 || porcGrasaVis > 100) {
                JOptionPane.showMessageDialog(null, "Los porcentajes no pueden ser mayores a 100", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Todos los valores deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Getters
    public String getCedula() { return cedulaField.getText().trim(); }
    public float getPeso() { return Float.parseFloat(pesoField.getText().trim()); }
    public float getEstatura() { return Float.parseFloat(estaturaField.getText().trim()); }
    public float getPorcGrasa() { return Float.parseFloat(porcGrasaField.getText().trim()); }
    public float getPorcMusculo() { return Float.parseFloat(porcMusculoField.getText().trim()); }
    public float getPorcGrasaVis() { return Float.parseFloat(porcGrasaVisField.getText().trim()); }
    public float getCintura() { return Float.parseFloat(cinturaField.getText().trim()); }
    public float getPecho() { return Float.parseFloat(pechoField.getText().trim()); }
    public float getMuslo() { return Float.parseFloat(musloField.getText().trim()); }
    public LocalDate getFecha() {
        return LocalDate.now();}
}
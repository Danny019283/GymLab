package Vista.Formularios;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioInstructor {

    private final JTextField cedulaField = new JTextField(15);
    private final JTextField nombreField = new JTextField(15);
    private final JTextField telefonoField = new JTextField(15);
    private final JTextField correoField = new JTextField(15);
    private final JTextField fechaNacField = new JTextField(15);
    private final JTextField especialidadesField = new JTextField(15);
    private final JTextField sucursalField = new JTextField(15);

    private List<JComponent> componentes = new ArrayList<>();

    public FormularioInstructor() {
        configurarComponentes();
    }

    private void configurarComponentes() {
        componentes.add(cedulaField);
        componentes.add(nombreField);
        componentes.add(telefonoField);
        componentes.add(correoField);
        componentes.add(fechaNacField);
        componentes.add(especialidadesField);
        componentes.add(sucursalField);
    }

    public boolean mostrarDialogo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Agregar campos al panel
        agregarCampo(panel, "Cédula:", cedulaField);
        agregarCampo(panel, "Nombre Completo:", nombreField);
        agregarCampo(panel, "Teléfono:", telefonoField);
        agregarCampo(panel, "Correo:", correoField);
        agregarCampo(panel, "Fecha Nacimiento:", fechaNacField);
        agregarCampo(panel, "Especialidades (separar por coma):", especialidadesField);
        agregarCampo(panel, "Código Sucursal:", sucursalField);

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
        if (getCedula().isEmpty() || getNombreCom().isEmpty() || getCorreo().isEmpty() ||
                getFechaNac().isEmpty() || getEspecialidades().isEmpty() || getCodigoSucursal().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(telefonoField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Teléfono debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!getCorreo().contains("@")) {
            JOptionPane.showMessageDialog(null, "Correo electrónico inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Métodos get para obtener los valores
    public String getCedula() {
        return cedulaField.getText().trim();
    }

    public String getNombreCom() {
        return nombreField.getText().trim();
    }

    public int getTelefono() {
        return Integer.parseInt(telefonoField.getText().trim());
    }

    public String getCorreo() {
        return correoField.getText().trim();
    }

    public String getFechaNac() {
        return fechaNacField.getText().trim();
    }

    public String getEspecialidades() {
        return especialidadesField.getText().trim();
    }

    public String getCodigoSucursal() {
        return sucursalField.getText().trim();
    }
}
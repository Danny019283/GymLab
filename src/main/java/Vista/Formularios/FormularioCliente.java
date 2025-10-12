package Vista.Formularios;

import Modelo.DTOs.ClienteDTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FormularioCliente {

    private final JTextField cedulaField = new JTextField(15);
    private final JTextField nombreField = new JTextField(15);
    private final JTextField telefonoField = new JTextField(15);
    private final JTextField correoField = new JTextField(15);
    private final JTextField fechaNacField = new JTextField(15);
    private final JComboBox<String> sexoCombo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
    private final JTextField edadField = new JTextField(15);
    private final JTextField instructorField = new JTextField(15);
    private final JTextField sucursalField = new JTextField(15);

    private List<JComponent> componentes = new ArrayList<>();

    public FormularioCliente() {
        configurarComponentes();
    }

    private void configurarComponentes() {
        componentes.add(cedulaField);
        componentes.add(nombreField);
        componentes.add(telefonoField);
        componentes.add(correoField);
        componentes.add(fechaNacField);
        componentes.add(sexoCombo);
        componentes.add(edadField);
        componentes.add(instructorField);
        componentes.add(sucursalField);

    }

    public boolean mostrarDialogo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Agregar campos al panel
        agregarCampo(panel, "Cédula:", cedulaField);
        agregarCampo(panel, "Nombre:", nombreField);
        agregarCampo(panel, "Teléfono:", telefonoField);
        agregarCampo(panel, "Correo:", correoField);
        agregarCampo(panel, "Fecha Nacimiento:", fechaNacField);
        agregarCampo(panel, "Sexo:", sexoCombo);
        agregarCampo(panel, "Edad:", edadField);
        agregarCampo(panel, "Cédula Instructor:", instructorField);
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
        if (getCedula().isEmpty() || getNombre().isEmpty() || getCorreo().isEmpty() ||
                getFechaNac().isEmpty() || getInstructor().isEmpty() || getSucursal().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(telefonoField.getText().trim());
            Integer.parseInt(edadField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Teléfono y edad deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!getCorreo().contains("@")) {
            JOptionPane.showMessageDialog(null, "Correo electrónico inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public ClienteDTO toDTO() {
        return new ClienteDTO.Builder()
                .cedula(getCedula())
                .nombre(getNombre())
                .telefono(getTelefono())
                .correo(getCorreo())
                .fechaNac(getFechaNac())
                .sexo(getSexo())
                .fechaInscrip(getFechaInscrip())
                .edad(getEdad())
                .instructor(getInstructor())
                .sucursal(getSucursal())
                .build();
    }

    // Métodos get para obtener los valores
    public String getCedula() {
        return cedulaField.getText().trim();
    }

    public String getNombre() {
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

    public String getSexo() {
        return (String) sexoCombo.getSelectedItem();
    }

    public String getFechaInscrip() {
        LocalDate fechaActual = LocalDate.now();
        return String.valueOf(fechaActual);
    }

    public int getEdad() {
        return Integer.parseInt(edadField.getText().trim());
    }

    public String getInstructor() {
        return instructorField.getText().trim();
    }

    public String getSucursal() {
        return sucursalField.getText().trim();
    }
}

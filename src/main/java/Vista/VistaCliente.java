package Vista;

import javax.swing.*;

public class VistaCliente extends JFrame {

    public VistaCliente() {
        setTitle("Gestión de Clientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Aquí puedes agregar componentes como botones, tablas, formularios, etc.

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaCliente vista = new VistaCliente();
            vista.setVisible(true);
        });
    }
}

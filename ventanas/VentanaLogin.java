package ventanas;

import entidades.concretas.Usuario;
import entidades.concretas.Persona;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class VentanaLogin extends JFrame {

    public VentanaLogin() {
        setTitle("Login");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField(15);
        JLabel lblPassword = new JLabel("Contraseña:");
        JPasswordField txtPassword = new JPasswordField(15);
        JButton btnLogin = new JButton("Iniciar Sesion");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtUsuario, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(txtPassword, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String user = txtUsuario.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese usuario y contraseña");
                return;
            }

            try {
                sistema.SistemaLogin sistemaLogin = new sistema.SistemaLogin();
                Usuario u = sistemaLogin.login(user, pass);

                VentanaPrincipal app = new VentanaPrincipal(u);
                app.setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel, BorderLayout.CENTER);
    }
}

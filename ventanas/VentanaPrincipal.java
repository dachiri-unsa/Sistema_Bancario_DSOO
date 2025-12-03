package ventanas;

import javax.swing.*;
import java.awt.*;
import entidades.concretas.Usuario;
import ventanas.paneles.*;

public class VentanaPrincipal extends JFrame {
    private Usuario usuario;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Sistema Bancario");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Sistema Bancario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel lblUsuario = new JLabel("Usuario: " + usuario.getNombre());
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
        });

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightHeader.add(lblUsuario);
        rightHeader.add(btnCerrarSesion);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        JButton btnInicio = new JButton("Inicio");
        JButton btnCuentas = new JButton("Cuentas");
        JButton btnTarjetas = new JButton("Tarjetas");
        JButton btnClientes = new JButton("Clientes");
        JButton btnAdministradores = new JButton("Administradores");
        JButton btnEmpleados = new JButton("Empleados");
        JButton btnUsuarios = new JButton("Usuarios");
        JButton btnCajeros = new JButton("Cajeros");


        sidebar.add(btnInicio);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnCuentas);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnTarjetas);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnClientes);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnAdministradores);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnEmpleados);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnUsuarios);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnCajeros);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));


        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        PanelInicio panelInicio = new PanelInicio(usuario);
        PanelCuentas panelCuentas = new PanelCuentas(usuario);
        PanelTarjetas panelTarjetas = new PanelTarjetas(usuario);
        PanelClientes panelClientes = new PanelClientes();
        PanelAdministrador panelAdministrador = new PanelAdministrador();
        PanelEmpleados panelEmpleados = new PanelEmpleados();
        PanelUsuarios panelUsuarios = new PanelUsuarios();
        PanelCajeros panelCajeros = new PanelCajeros();

        contentPanel.add(panelInicio, "inicio");
        contentPanel.add(panelCuentas, "cuentas");
        contentPanel.add(panelTarjetas, "tarjetas");
        contentPanel.add(panelClientes, "clientes");
        contentPanel.add(panelAdministrador, "administradores");
        contentPanel.add(panelEmpleados, "empleados");
        contentPanel.add(panelUsuarios, "usuarios");
        contentPanel.add(panelCajeros, "cajeros");

        btnInicio.addActionListener(e -> cardLayout.show(contentPanel, "inicio"));
        btnCuentas.addActionListener(e -> cardLayout.show(contentPanel, "cuentas"));
        btnTarjetas.addActionListener(e -> cardLayout.show(contentPanel, "tarjetas"));
        btnClientes.addActionListener(e -> cardLayout.show(contentPanel, "clientes"));
        btnAdministradores.addActionListener(e -> cardLayout.show(contentPanel, "administradores"));
        btnEmpleados.addActionListener(e -> cardLayout.show(contentPanel, "empleados"));
        btnUsuarios.addActionListener(e -> cardLayout.show(contentPanel, "usuarios"));
        btnCajeros.addActionListener(e -> cardLayout.show(contentPanel, "cajeros"));

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "inicio");
    }
}

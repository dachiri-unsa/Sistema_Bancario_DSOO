package ventanas.paneles;

import entidades.concretas.Administrador;
import entidades.concretas.Persona;
import sincronizacion.SincronizacionCompartida;
import util.PasswordUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelAdministrador extends JPanel implements SincronizacionCompartida.Actualizable {
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelAdministrador() {
        setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        toolbar.add(btnAgregar);
        toolbar.add(btnEliminar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] { "DNI", "Nombre", "Apellido", "Usuario" }, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        SincronizacionCompartida.registrarListener(this);
        actualizarLista(sistema.SistemaBanco.getInstance().getBanco().getGestorAdministradores().listarTodos());
    }

    private void mostrarDialogoAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "DNI:", txtDni,
                "Usuario:", txtUsuario,
                "Contraseña:", txtPassword
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Nuevo Administrador", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Persona persona = new Persona(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtDni.getText(),
                        "",
                        "");

                Administrador admin = new Administrador(
                        persona,
                        txtUsuario.getText(),
                        PasswordUtil.hashPassword(new String(txtPassword.getPassword())),
                        true);

                sistema.SistemaBanco.getInstance().getBanco().getGestorAdministradores().agregar(admin);
                SincronizacionCompartida.notificarListeners();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear administrador: " + ex.getMessage());
            }
        }
    }

    private void eliminarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            sistema.SistemaBanco.getInstance().getBanco().getGestorAdministradores().eliminar(row);
            SincronizacionCompartida.notificarListeners();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un administrador para eliminar");
        }
    }

    @Override
    public void actualizarLista(List<Administrador> lista) {
        modelo.setRowCount(0);
        for (Administrador a : lista) {
            modelo.addRow(new Object[] {
                    a.getDNI(),
                    a.getNombre(),
                    a.getApellido(),
                    a.getNombreUsuario()
            });
        }
    }
}

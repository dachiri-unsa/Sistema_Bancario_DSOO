package ventanas.paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import entidades.concretas.Cliente;
import entidades.concretas.Persona;
import sincronizacion.SincronizacionCompartida;
import sistema.SistemaBanco;

public class PanelClientes extends JPanel implements SincronizacionCompartida.Actualizable {
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelClientes() {
        setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        toolbar.add(btnAgregar);
        toolbar.add(btnModificar);
        toolbar.add(btnEliminar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] {
            "DNI", "Nombre", "Apellido", "Teléfono", "Dirección", "Usuario"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        btnModificar.addActionListener(e -> mostrarDialogoModificar());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        SincronizacionCompartida.registrarListener(this);

        List<Cliente> inicial = SistemaBanco.getInstance().getBanco().getGestorClientes().listarTodos();
        actualizarClientes(inicial);
    }

    private void mostrarDialogoAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtContrasenia = new JPasswordField();

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "DNI:", txtDni,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion,
                "Nombre de usuario:", txtUsuario,
                "Contraseña:", txtContrasenia
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Crear Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String usuario = txtUsuario.getText().trim();
                String contrasenia = new String(txtContrasenia.getPassword()).trim();
                if (nombre.length() < 2) {
                    JOptionPane.showMessageDialog(this, "El nombre es demasiado corto.");
                    return;
                }
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios.");
                    return;
                }
                if (apellido.length() < 2) {
                    JOptionPane.showMessageDialog(this, "El apellido es demasiado corto.");
                    return;
                }
                if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    JOptionPane.showMessageDialog(this, "El apellido solo puede contener letras y espacios.");
                    return;
                }
                if (contrasenia.length() < 2) {
                    JOptionPane.showMessageDialog(this, "La contraseña es demasiado corta.");
                    return;
                }
                if (!dni.matches("\\d{8}")) {
                    JOptionPane.showMessageDialog(this, "El DNI debe contener exactamente 8 dígitos numéricos.");
                    return;
                }
                if(!telefono.isEmpty()){
                    if (!telefono.matches("\\d{9}")) {
                        JOptionPane.showMessageDialog(this, "El telefono debe contener exactamente 9 dígitos numéricos.");
                        return;
                    }
                }
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || usuario.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Los campos Nombre, Apellido, DNI y Usuario son obligatorios.");
                    return;
                }

                Persona persona = new Persona(nombre, apellido, dni, telefono, direccion);
                Cliente cliente = new Cliente(persona, usuario, contrasenia, true);

                SistemaBanco.getInstance().getBanco().getGestorClientes().agregarCliente(cliente);
                SincronizacionCompartida.notificarListeners();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear cliente: " + ex.getMessage());
            }
        }
    }

    private void mostrarDialogoModificar() {
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            String dni = (String) modelo.getValueAt(row, 0); // columna DNI está en índice 0
            Cliente cliente = SistemaBanco.getInstance().getBanco().getGestorClientes().buscarCliente(dni);

            if (cliente != null) {
                JTextField txtNombre = new JTextField(cliente.getNombre());
                JTextField txtApellido = new JTextField(cliente.getApellido());
                JTextField txtTelefono = new JTextField(cliente.getTelefono());
                JTextField txtDireccion = new JTextField(cliente.getDireccion());
                JTextField txtUsuario = new JTextField(cliente.getNombreUsuario());

                Object[] message = {
                        "Nombre:", txtNombre,
                        "Apellido:", txtApellido,
                        "Teléfono:", txtTelefono,
                        "Dirección:", txtDireccion,
                        "Nombre de usuario:", txtUsuario
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Modificar Cliente",
                        JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (txtNombre.getText().length() < 2) {
                        JOptionPane.showMessageDialog(this, "El nombre es demasiado corto.");
                        return;
                    }
                    else{
                        cliente.setNombre(txtNombre.getText().trim());
                    }

                    if (!txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios.");
                        return;
                    }
                    else{
                        cliente.setNombre(txtNombre.getText().trim());
                    }

                    if (txtApellido.getText().length() < 2) {
                        JOptionPane.showMessageDialog(this, "El apellido es demasiado corto.");
                        return;
                    }
                    else{
                        cliente.setApellido(txtApellido.getText().trim());
                    }

                    if (!txtApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        JOptionPane.showMessageDialog(this, "El apellido solo puede contener letras y espacios.");
                        return;
                    }
                    else{
                        cliente.setApellido(txtApellido.getText().trim());
                    }

                    if (!txtTelefono.getText().matches("\\d{9}")) {
                        JOptionPane.showMessageDialog(this, "El telefono debe contener exactamente 9 dígitos numéricos.");
                        return;
                    }
                    else {
                        cliente.setTelefono(txtTelefono.getText().trim());
                    }

                    if (txtUsuario.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "El nombre de Usuario es obligatorio.");
                        return;
                    }
                    else {
                        cliente.setNombreUsuario(txtUsuario.getText().trim());
                    }

                    SincronizacionCompartida.notificarListeners();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el cliente seleccionado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para modificar");
        }
    }

    private void eliminarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String dni = (String) modelo.getValueAt(row, 0);
                Cliente cliente = SistemaBanco.getInstance().getBanco().getGestorClientes().buscarCliente(dni);
                if (cliente != null) {
                    SistemaBanco.getInstance().getBanco().getGestorClientes().eliminarCliente(cliente);
                    SincronizacionCompartida.notificarListeners();
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el cliente para eliminar.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar");
        }
    }

    @Override
    public void actualizarClientes(List<Cliente> lista) {
        modelo.setRowCount(0);
        for (Cliente c : lista) {
            String usuario = null;
            try {
                usuario = c.getNombreUsuario();
            } catch (NoSuchMethodError | AbstractMethodError ex) {
                usuario = "";
            }

            modelo.addRow(new Object[] {
                    c.getDNI(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getTelefono(),
                    c.getDireccion(),
                    usuario
            });
        }
    }
}

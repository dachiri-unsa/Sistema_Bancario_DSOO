package ventanas.paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import entidades.concretas.Administrador;
import entidades.concretas.Cliente;
import entidades.concretas.Empleado;
import entidades.concretas.Persona;
import entidades.concretas.Usuario;
import sincronizacion.SincronizacionCompartida;
import sistema.SistemaBanco;
import gestores.GestorUsuarios;

public class PanelUsuarios extends JPanel implements SincronizacionCompartida.Actualizable {
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelUsuarios() {
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
                "Usuario", "Nombre", "Apellido", "DNI", "Rol", "Estado"
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

        recargarTablaUsuarios();
    }

    private GestorUsuarios getGestorUsuarios() {
        return SistemaBanco.getInstance().getGestorUsuarios();
    }

    private void mostrarDialogoAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtContrasenia = new JPasswordField();
        JComboBox<String> cmbRol = new JComboBox<>(new String[] {"Cliente", "Empleado", "Administrador", "Cajero"});

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "DNI:", txtDni,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion,
                "Nombre de usuario:", txtUsuario,
                "Contraseña:", txtContrasenia,
                "Rol (para crear subclase):", cmbRol
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Crear Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String usuarioTxt = txtUsuario.getText().trim();
                String contrasenia = new String(txtContrasenia.getPassword()).trim();
                String rol = (String) cmbRol.getSelectedItem();

                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || usuarioTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nombre, Apellido, DNI y Usuario son obligatorios.");
                    return;
                }

                if (getGestorUsuarios().buscarPorUsuario(usuarioTxt) != null) {
                    JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese nombre de usuario.");
                    return;
                }

                Persona persona = new Persona(nombre, apellido, dni, telefono, direccion);

                // Para siempre crear solo Usuario usar `new Usuario(persona, usuarioTxt, contrasenia, true)`.
                Usuario nuevo;
                switch (rol) {
                    case "Cliente":
                        nuevo = new Cliente(persona, usuarioTxt, contrasenia, true);
                        break;
                    case "Empleado":
                        // Empleado tiene constructor similar a Cliente en tu proyecto (Persona, usuario, pass, id?, estado)
                        // Pero para evitar romper si el constructor requiere ID, usamos el constructor (Persona, user, pass, estado)
                        nuevo = new Empleado(persona, usuarioTxt, contrasenia, true);
                        break;
                    case "Administrador":
                        nuevo = new Administrador(persona, usuarioTxt, contrasenia, true);
                        break;
                    default:
                        nuevo = new Usuario(persona, usuarioTxt, contrasenia, true);
                        break;
                }

                getGestorUsuarios().agregarUsuario(nuevo);
                recargarTablaUsuarios();

                SistemaBanco.getInstance().notificarCambios();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear usuario: " + ex.getMessage());
            }
        }
    }

    private void mostrarDialogoModificar() {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para modificar.");
            return;
        }

        String username = (String) modelo.getValueAt(row, 0);
        Usuario usuario = getGestorUsuarios().buscarPorUsuario(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario seleccionado.");
            return;
        }

        JTextField txtNombre = new JTextField(usuario.getNombre());
        JTextField txtApellido = new JTextField(usuario.getApellido());
        JTextField txtTelefono = new JTextField(usuario.getTelefono());
        JTextField txtDireccion = new JTextField(usuario.getDireccion());
        JTextField txtUsuario = new JTextField(usuario.getNombreUsuario());
        JCheckBox chkEstado = new JCheckBox("Activo", usuario.getEstado());

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion,
                "Nombre de usuario:", txtUsuario,
                "Activo:", chkEstado
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Modificar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Validar cambio de nombre de usuario (si lo cambian, no duplicar)
            String nuevoUsername = txtUsuario.getText().trim();
            if (!nuevoUsername.equals(usuario.getNombreUsuario())) {
                Usuario existente = getGestorUsuarios().buscarPorUsuario(nuevoUsername);
                if (existente != null) {
                    JOptionPane.showMessageDialog(this, "El nombre de usuario ya está en uso por otro usuario.");
                    return;
                }
            }

            usuario.setNombre(txtNombre.getText().trim());
            usuario.setApellido(txtApellido.getText().trim());
            usuario.setTelefono(txtTelefono.getText().trim());
            usuario.setDireccion(txtDireccion.getText().trim());
            usuario.setNombreUsuario(nuevoUsername);
            usuario.setEstado(chkEstado.isSelected());

            // Refrescar tabla
            recargarTablaUsuarios();

            // Notificar cambios globales (opcional)
            SistemaBanco.getInstance().notificarCambios();
        }
    }

    private void eliminarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.");
            return;
        }

        String username = (String) modelo.getValueAt(row, 0);
        Usuario usuario = getGestorUsuarios().buscarPorUsuario(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al usuario '" + username + "'?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Buscar índice en la lista y llamar a eliminar(index)
        List<Usuario> lista = getGestorUsuarios().listarTodos();
        int index = lista.indexOf(usuario);
        if (index != -1) {
            getGestorUsuarios().eliminar(index);
            recargarTablaUsuarios();
            SistemaBanco.getInstance().notificarCambios();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar: índice no encontrado.");
        }
    }

    // Recarga la tabla leyendo directamente del gestor (usa listarTodos())
    private void recargarTablaUsuarios() {
        modelo.setRowCount(0);
        List<Usuario> lista = getGestorUsuarios().listarTodos();
        for (Usuario u : lista) {
            String rol = deducirRol(u);
            String estado = u.getEstado() ? "Activo" : "Inactivo";
            modelo.addRow(new Object[] {
                    u.getNombreUsuario(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getDNI(),
                    rol,
                    estado
            });
        }
    }

    // Intentamos obtener rol: primero getTipoRol(), si es null, usamos instanceof
    private String deducirRol(Usuario u) {
        String rol = null;
        try {
            if (u.getTipoRol() != null) {
                rol = u.getTipoRol().toString();
            }
        } catch (Exception ignored) {}

        if (rol == null || rol.isEmpty()) {
            if (u instanceof Administrador) rol = "Administrador";
            else if (u instanceof Empleado) rol = "Empleado";
            else if (u instanceof Cliente) rol = "Cliente";
            else rol = "";
        }
        return rol;
    }

}

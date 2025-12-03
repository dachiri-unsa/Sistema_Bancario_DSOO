package ventanas.paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import entidades.concretas.Empleado;
import entidades.concretas.Persona;
import sincronizacion.SincronizacionCompartida;
import sistema.SistemaBanco;

public class PanelEmpleados extends JPanel implements SincronizacionCompartida.Actualizable {
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelEmpleados() {
        setLayout(new BorderLayout());

        // Toolbar
        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Contratar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Despedir");
        toolbar.add(btnAgregar);
        toolbar.add(btnModificar);
        toolbar.add(btnEliminar);
        add(toolbar, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel(new Object[] { "ID", "DNI", "Nombre", "Apellido", "Teléfono", "Dirección" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Listeners
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        btnModificar.addActionListener(e -> mostrarDialogoModificar());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        // Registrarse para actualizaciones
        SincronizacionCompartida.registrarListener(this);
        actualizarEmpleados(SistemaBanco.getInstance().getBanco().getGestorEmpleados().listarTodos());
    }

    private void mostrarDialogoAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtId = new JTextField();

        Object[] message = {
                "Nombre:", txtNombre,
                "Apellido:", txtApellido,
                "DNI:", txtDni,
                "Teléfono:", txtTelefono,
                "Dirección:", txtDireccion,
                "ID Empleado (Ej: E001):", txtId
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Contratar Empleado", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String id = txtId.getText().trim();

                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || id.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben ser llenados.");
                    return;
                }

                Persona persona = new Persona(nombre, apellido, dni, telefono, direccion);
                // Assuming default password or logic for user creation is handled elsewhere or
                // not needed for basic employee listing yet.
                // The original MenuEmpleados didn't seem to ask for password, but Empleado
                // extends Usuario.
                // Let's assume a default or empty password for now, or we might need to add it.
                // Checking MenuEmpleados: "Faltaria agregar rol empleado al momento de crear
                // Usuario tipo empleado... si no tiene cuenta para ingresar..."
                // It seems it creates it with just the ID.

                Empleado empleado = new Empleado(persona, dni, dni, true); // Using DNI as user/pass temporarily as
                                                                               // per some patterns or just placeholders

                SistemaBanco.getInstance().getBanco().getGestorEmpleados().agregarEmpleado(empleado);
                SincronizacionCompartida.notificarListeners();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear empleado: " + ex.getMessage());
            }
        }
    }

    private void mostrarDialogoModificar() {
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            String dni = (String) modelo.getValueAt(row, 1);
            Empleado empleado = SistemaBanco.getInstance().getBanco().getGestorEmpleados().buscarPorDni(dni);

            if (empleado != null) {
                JTextField txtNombre = new JTextField(empleado.getNombre());
                JTextField txtApellido = new JTextField(empleado.getApellido());
                JTextField txtTelefono = new JTextField(empleado.getTelefono());
                JTextField txtDireccion = new JTextField(empleado.getDireccion());

                Object[] message = {
                        "Nombre:", txtNombre,
                        "Apellido:", txtApellido,
                        "Teléfono:", txtTelefono,
                        "Dirección:", txtDireccion
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Modificar Empleado",
                        JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    empleado.setNombre(txtNombre.getText().trim());
                    empleado.setApellido(txtApellido.getText().trim());
                    empleado.setTelefono(txtTelefono.getText().trim());
                    empleado.setDireccion(txtDireccion.getText().trim());

                    // No need to explicitly "save" if objects are references, but notification is
                    // needed to refresh views
                    SincronizacionCompartida.notificarListeners();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para modificar");
        }
    }

    private void eliminarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de despedir a este empleado?",
                    "Confirmar Despido", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                SistemaBanco.getInstance().getBanco().getGestorEmpleados().eliminar(row);
                SincronizacionCompartida.notificarListeners();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para despedir");
        }
    }

    @Override
    public void actualizarEmpleados(List<Empleado> lista) {
        modelo.setRowCount(0);
        for (Empleado e : lista) {
            modelo.addRow(new Object[] {
                    e.getId(),
                    e.getDNI(),
                    e.getNombre(),
                    e.getApellido(),
                    e.getTelefono(),
                    e.getDireccion()
            });
        }
    }
}

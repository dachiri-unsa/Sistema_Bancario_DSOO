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

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Contratar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Despedir");
        toolbar.add(btnAgregar);
        toolbar.add(btnModificar);
        toolbar.add(btnEliminar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] { "ID", "DNI", "Nombre", "Apellido", "Teléfono", "Dirección" }, 0) {
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
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || id.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben ser llenados.");
                    return;
                }

                Persona persona = new Persona(nombre, apellido, dni, telefono, direccion);

                Empleado empleado = new Empleado(persona, dni, dni, true);

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
                    if (txtNombre.getText().length() < 2) {
                        JOptionPane.showMessageDialog(this, "El nombre es demasiado corto.");
                        return;
                    }
                    else{
                        empleado.setNombre(txtNombre.getText().trim());
                    }

                    if (!txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios.");
                        return;
                    }
                    else{
                        empleado.setNombre(txtNombre.getText().trim());
                    }

                    if (txtApellido.getText().length() < 2) {
                        JOptionPane.showMessageDialog(this, "El apellido es demasiado corto.");
                        return;
                    }
                    else{
                        empleado.setApellido(txtApellido.getText().trim());
                    }

                    if (!txtApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        JOptionPane.showMessageDialog(this, "El apellido solo puede contener letras y espacios.");
                        return;
                    }
                    else{
                        empleado.setApellido(txtApellido.getText().trim());
                    }

                    if (!txtTelefono.getText().matches("\\d{9}")) {
                        JOptionPane.showMessageDialog(this, "El telefono debe contener exactamente 9 dígitos numéricos.");
                        return;
                    }

                    empleado.setDireccion(txtDireccion.getText().trim());

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

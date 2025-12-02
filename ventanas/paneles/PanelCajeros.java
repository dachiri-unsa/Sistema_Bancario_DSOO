package ventanas.paneles;

import entidades.concretas.Cajero;
import sincronizacion.SincronizacionCompartida;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelCajeros extends JPanel implements SincronizacionCompartida.Actualizable {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtId;
    private JCheckBox chkDisponible;

    public PanelCajeros() {
        setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar();
        JButton btnEliminar = new JButton("Eliminar");
        toolbar.add(btnEliminar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] { "ID", "Disponible" }, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelAgregar = new JPanel(new FlowLayout());
        txtId = new JTextField(10);
        chkDisponible = new JCheckBox("Disponible");
        chkDisponible.setSelected(true);
        JButton btnAgregar = new JButton("Agregar");

        panelAgregar.add(new JLabel("ID Cajero:"));
        panelAgregar.add(txtId);
        panelAgregar.add(chkDisponible);
        panelAgregar.add(btnAgregar);

        add(panelAgregar, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarCajero());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        SincronizacionCompartida.registrarListener(this);
        actualizarCajeros(sistema.SistemaBanco.getInstance().getBanco().getGestorCajeros().listarTodos());
    }

    private void agregarCajero() {
        try {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El ID no puede estar vacío");
                return;
            }

            Cajero cajero = new Cajero(id, chkDisponible.isSelected());
            sistema.SistemaBanco.getInstance().getBanco().getGestorCajeros().agregarCajero(cajero);
            SincronizacionCompartida.notificarListeners();

            txtId.setText("");
            chkDisponible.setSelected(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear cajero: " + ex.getMessage());
        }
    }

    private void eliminarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            sistema.SistemaBanco.getInstance().getBanco().getGestorCajeros().eliminar(row);
            SincronizacionCompartida.notificarListeners();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cajero para eliminar");
        }
    }

    @Override
    public void actualizarLista(List<entidades.concretas.Administrador> lista) {
    }

    @Override
    public void actualizarCajeros(List<Cajero> lista) {
        modelo.setRowCount(0);
        for (Cajero c : lista) {
            modelo.addRow(new Object[] {
                    c.getId(),
                    c.getDisponible() ? "Si" : "No"
            });
        }
    }
}

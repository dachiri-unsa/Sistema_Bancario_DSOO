package ventanas.paneles;

import entidades.concretas.CuentaBancaria;
import entidades.concretas.Usuario;
import entidades.abstractas.Movimiento;
import entidades.concretas.Cliente;
import entidades.enumerables.TipoMoneda;
import gestores.GestorCuentas;
import movimientos.HistorialMovimientos;
import sincronizacion.SincronizacionCompartida;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.util.List;

public class PanelCuentas extends JPanel implements SincronizacionCompartida.Actualizable {
    private JTable tabla;
    private DefaultTableModel modelo;
    private final Cliente cliente;
    public PanelCuentas(Usuario usuario) {
        setLayout(new BorderLayout());
        // obtener la instancia Cliente a partir del usuario (por DNI)
        Cliente buscado = sistema.SistemaBanco.getInstance().getBanco()
                .getGestorClientes().buscarCliente(usuario.getDNI());
        this.cliente = buscado;

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Crear");
        JButton btnMovimientos = new JButton("Ver movimientos");
        JButton btnEliminar = new JButton("Eliminar");
        toolbar.add(btnAgregar);
        toolbar.add(btnMovimientos);
        toolbar.add(btnEliminar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[] { "Numero", "Tipo de Moneda", "Saldo", "DNI cliente" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado. No se puede crear cuenta.");
                return;
            }
            mostrarDialogoAgregar(cliente);
        });

        btnMovimientos.addActionListener(e -> verMovimientos());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        // registrar para recibir notificaciones; cuando se notifique usamos el gestor del cliente
        SincronizacionCompartida.registrarListener(this);

        // inicializar mostrando sólo las cuentas del cliente (si existe)
        if (cliente != null) {
            actualizarCuentas(cliente.getGestorCuentas().listarTodos());
        } else {
            actualizarCuentas(List.of()); // muestra vacío
        }
    }

    private void mostrarDialogoAgregar(Cliente cliente) {
        Object[] message = {
                "Seleccione el tipo de moneda para la nueva cuenta:"
        };
        String[] opciones = { "Soles", "Dólares", "Euros", "Cancelar" };
        int option = JOptionPane.showOptionDialog(
                this,
                message,
                "Crear Cuenta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        if (option == 3 || option == JOptionPane.CLOSED_OPTION) {
            return;
        }
        TipoMoneda tipo = switch (option) {
            case 0 -> TipoMoneda.Soles;
            case 1 -> TipoMoneda.Dolares;
            case 2 -> TipoMoneda.Euros;
            default -> null;
        };
        if (tipo == null) return;

        try {
            // crear cuenta y agregarla AL CLIENTE (que a su vez guarda en su GestorCuentas)
            CuentaBancaria cuenta = new CuentaBancaria(tipo, cliente.getDNI());
            // ejemplo: saldo inicial 0 o puedes pedir otro dato si quieres
            cliente.agregarCuenta(cuenta);

            // opcional: si quieres mantener el hash global en Banco (índice), sincronízalo
            sistema.SistemaBanco.getInstance().getBanco().getHashCuentas().put(cuenta.getNumeroCuenta(), cuenta);

            // notificar para que la UI (y otros listados) se actualicen
            SincronizacionCompartida.notificarListeners();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear cuenta: " + ex.getMessage());
        }
    }

    private void eliminarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row >= 0) {
            String numero = (String) modelo.getValueAt(row, 0);
            CuentaBancaria c = cliente.getGestorCuentas().buscarCuenta(numero);
            if (c != null) {
                cliente.getGestorCuentas().eliminarCuenta(c);
                sistema.SistemaBanco.getInstance().getBanco().getHashCuentas().remove(numero);
                SincronizacionCompartida.notificarListeners();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la cuenta seleccionada.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una cuenta para eliminar");
        }
    }

    private void verMovimientos() {
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no disponible.");
            return;
        }

        GestorCuentas gestor = cliente.getGestorCuentas();
        List<CuentaBancaria> cuentas = gestor.getCuentas();
        if (cuentas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El cliente no tiene cuentas disponibles.");
            return;
        }

        String numeroSeleccionado = null;

        // Si hay una fila seleccionada en la tabla, usar esa cuenta
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            Object val = modelo.getValueAt(fila, 0);
            if (val != null) numeroSeleccionado = val.toString();
        }

        // Si no hay fila seleccionada y solo hay una cuenta, usarla
        if (numeroSeleccionado == null && cuentas.size() == 1) {
            numeroSeleccionado = cuentas.get(0).getNumeroCuenta();
        }

        // Si no hay selección y hay varias cuentas
        if (numeroSeleccionado == null && cuentas.size() > 1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cuenta, por favor");
            return;
        }

        if (numeroSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No se seleccionó ninguna cuenta.");
            return;
        }

        // Buscar la cuenta en el gestor del cliente
        CuentaBancaria cuenta = gestor.buscarCuenta(numeroSeleccionado);
        if (cuenta == null) {
            JOptionPane.showMessageDialog(this, "Cuenta no encontrada: " + numeroSeleccionado);
            return;
        }

        HistorialMovimientos historial = cuenta.getHistorial();
        if (historial == null) {
            JOptionPane.showMessageDialog(this, "No hay historial para la cuenta " + numeroSeleccionado);
            return;
        }

        StringBuilder sb = new StringBuilder();
        try {
            // Si tu HistorialMovimientos tiene getMovimientos() que devuelve List<Movimiento>
            List<Movimiento> movimientos = historial.getMovimientos();
            if (movimientos == null || movimientos.isEmpty()) {
                sb.append("No hay movimientos registrados para la cuenta ").append(numeroSeleccionado);
            } else {
                for (Movimiento m : movimientos) {
                    sb.append(m.toString()).append("\n");
                }
            }
        } catch (NoSuchMethodError | AbstractMethodError | Exception e) {
            sb.append("No fue posible obtener movimientos (error interno).");
        }

        // Mostrar en un dialog con JTextArea y scroll
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        // Auto-scroll to top
        DefaultCaret caret = (DefaultCaret) area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scroll, "Movimientos - Cuenta " + numeroSeleccionado, JOptionPane.INFORMATION_MESSAGE);
    }
    @Override
    public void actualizarCuentas(List<CuentaBancaria> lista) {
        modelo.setRowCount(0);

        if (cliente == null) return;

        List<CuentaBancaria> cuentasCliente = cliente.getGestorCuentas().listarTodos();
        for (CuentaBancaria c : cuentasCliente) {
            String dni = c.getDniCliente() == null ? "N/A" : c.getDniCliente();
            modelo.addRow(new Object[] {
                    c.getNumeroCuenta(),
                    c.getTipoMoneda(),
                    c.getSaldo(),
                    dni
            });
        }
    }
}

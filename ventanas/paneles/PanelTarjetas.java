package ventanas.paneles;

import entidades.concretas.Cliente;
import entidades.concretas.CuentaBancaria;
import entidades.concretas.CuentaTarjeta;
import entidades.concretas.Tarjeta;
import entidades.concretas.Debito;
import entidades.concretas.Credito;
import entidades.concretas.Usuario;
import gestores.GestorClientes;
import gestores.GestorTarjetas;
import sincronizacion.SincronizacionCompartida;
import sistema.SistemaBanco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelTarjetas extends JPanel implements SincronizacionCompartida.Actualizable {
    private final Cliente cliente;
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelTarjetas(Usuario usuario) {
        setLayout(new BorderLayout());

        GestorClientes gestorClientes = SistemaBanco.getInstance().getBanco().getGestorClientes();
        this.cliente = usuario == null ? null : gestorClientes.buscarCliente(usuario.getDNI());

        JToolBar toolbar = new JToolBar();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnVincular = new JButton("Vincular a cuenta");
        JButton btnEliminar = new JButton("Eliminar");
        toolbar.add(btnAgregar);
        toolbar.add(btnVincular);
        toolbar.add(btnEliminar);
        add(toolbar, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new Object[]{"Nro. Tarjeta", "Compañía", "Tipo", "Cuentas afiliadas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado. No se puede crear tarjeta.");
                return;
            }
            mostrarDialogoAgregar(cliente);
        });

        btnVincular.addActionListener(e -> {
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado. No se puede vincular tarjeta.");
                return;
            }
            vincularTarjetaSeleccionado(cliente);
        });

        btnEliminar.addActionListener(e -> {
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado. No se puede eliminar tarjeta.");
                return;
            }
            eliminarSeleccionado(cliente);
        });

        SincronizacionCompartida.registrarListener(this);

        if (cliente != null) {
            actualizarTarjetas(cliente.getGestorTarjetas().listarTodos());
        } else {
            actualizarTarjetas(List.of());
        }
    }

    private void mostrarDialogoAgregar(Cliente cliente) {
        String[] tipos = {"Débito", "Crédito", "Cancelar"};
        int tipoSel = JOptionPane.showOptionDialog(
                this,
                "Seleccione el tipo de tarjeta a crear:",
                "Nueva Tarjeta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );
        if (tipoSel == 2 || tipoSel == JOptionPane.CLOSED_OPTION) return;

        JTextField txtCompania = new JTextField();
        Object[] message = {
                "Compañía:", txtCompania
        };
        int option = JOptionPane.showConfirmDialog(this, message, "Crear Tarjeta", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        String compania = txtCompania.getText().trim();
        if (compania.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La compañía no puede estar vacía.");
            return;
        }

        try {
            GestorTarjetas gestorTarjetasCliente = cliente.getGestorTarjetas();
            String numeroGenerado = gestorTarjetasCliente.generarNumeroTarjeta();

            Tarjeta tarjeta;
            if (tipoSel == 0) { // Débito
                tarjeta = new Debito(numeroGenerado, compania, 0.0);
            } else {
                tarjeta = new Credito(numeroGenerado, compania);
            }

            cliente.agregarTarjeta(tarjeta);
            SistemaBanco.getInstance().getBanco().getHashTarjetas().put(tarjeta.getNumeroTarjeta(), tarjeta);

            SincronizacionCompartida.notificarListeners();
            JOptionPane.showMessageDialog(this, "Tarjeta creada: " + numeroGenerado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear tarjeta: " + ex.getMessage());
        }
    }

    private void vincularTarjetaSeleccionado(Cliente cliente) {
        GestorTarjetas gestor = cliente.getGestorTarjetas();
        List<Tarjeta> tarjetas = gestor.listarTodos();
        if (tarjetas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El cliente no tiene tarjetas registradas.");
            return;
        }

        String nroSeleccionado = null;
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            Object val = modelo.getValueAt(fila, 0);
            if (val != null) nroSeleccionado = val.toString();
        }

        if (nroSeleccionado == null) {
            if (tarjetas.size() == 1) {
                nroSeleccionado = tarjetas.get(0).getNumeroTarjeta();
            } else {
                String[] opciones = new String[tarjetas.size()];
                for (int i = 0; i < tarjetas.size(); i++) {
                    Tarjeta t = tarjetas.get(i);
                    opciones[i] = t.getNumeroTarjeta() + " (" + t.getCompania() + ")";
                }
                String elegido = (String) JOptionPane.showInputDialog(
                        this,
                        "Seleccione la tarjeta a vincular:",
                        "Seleccionar tarjeta",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );
                if (elegido == null) return;
                nroSeleccionado = elegido.split(" ")[0].trim();
            }
        }

        Tarjeta tarjeta = gestor.buscarTarjeta(nroSeleccionado);
        if (tarjeta == null) {
            JOptionPane.showMessageDialog(this, "Tarjeta no encontrada: " + nroSeleccionado);
            return;
        }

        List<CuentaBancaria> cuentas = cliente.getGestorCuentas().listarTodos();
        if (cuentas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El cliente no tiene cuentas para vincular.");
            return;
        }

        String numeroCuenta = null;
        if (cuentas.size() == 1) {
            numeroCuenta = cuentas.get(0).getNumeroCuenta();
        } else {
            String[] opcionesCuentas = new String[cuentas.size()];
            for (int i = 0; i < cuentas.size(); i++) {
                CuentaBancaria c = cuentas.get(i);
                opcionesCuentas[i] = c.getNumeroCuenta() + " (" + c.getTipoMoneda() + " - Saldo: " + c.getSaldo() + ")";
            }
            String elegido = (String) JOptionPane.showInputDialog(
                    this,
                    "Seleccione la cuenta a afiliar:",
                    "Seleccionar cuenta",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opcionesCuentas,
                    opcionesCuentas[0]
            );
            if (elegido == null) return;
            numeroCuenta = elegido.split(" ")[0].trim();
        }

        CuentaBancaria cuenta = cliente.getGestorCuentas().buscarCuenta(numeroCuenta);
        if (cuenta == null) {
            JOptionPane.showMessageDialog(this, "Cuenta no encontrada: " + numeroCuenta);
            return;
        }

        try {
            tarjeta.afiliarCuenta(cuenta);
            CuentaTarjeta cuentaTarjeta = new CuentaTarjeta(tarjeta, cuenta);
            SistemaBanco banco = SistemaBanco.getInstance();
            banco.getBanco().getGestorClientes().buscarCliente(cuenta.getDniCliente()).afiliarCuenta(cuentaTarjeta);

            cliente.afiliarCuenta(cuentaTarjeta);

            SistemaBanco.getInstance().getBanco().getHashTarjetas().put(tarjeta.getNumeroTarjeta(), tarjeta);

            SincronizacionCompartida.notificarListeners();
            JOptionPane.showMessageDialog(this, "Tarjeta vinculada a la cuenta " + numeroCuenta);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al vincular tarjeta: " + ex.getMessage());
        }
    }

    private void eliminarSeleccionado(Cliente cliente) {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarjeta para eliminar");
            return;
        }
        String numero = (String) modelo.getValueAt(row, 0);

        GestorTarjetas gestor = cliente.getGestorTarjetas();
        boolean eliminado = gestor.eliminarTarjeta(numero);
        if (!eliminado) {
            JOptionPane.showMessageDialog(this, "No se encontró la tarjeta para eliminar.");
            return;
        }

        SistemaBanco.getInstance().getBanco().getHashTarjetas().remove(numero);

        SincronizacionCompartida.notificarListeners();
        JOptionPane.showMessageDialog(this, "Tarjeta eliminada.");
    }

    @Override
    public void actualizarTarjetas(List<Tarjeta> lista) {
        modelo.setRowCount(0);
        if (cliente == null) return;
        List<Tarjeta> tarjetas = cliente.getGestorTarjetas().listarTodos();

        for (Tarjeta t : tarjetas) {
            String tipo;
            if (t instanceof Debito) {
                tipo = "Débito";
            } else if (t instanceof Credito) {
                tipo = "Crédito";
            } else {
                tipo = "N/A";
            }
            List<CuentaBancaria> cuentasAfiliadas = t.getCuentasAfiliadas();
            String cuentas;
            if (cuentasAfiliadas.isEmpty()) {
                cuentas = "-";
            } else {
                StringBuilder sb = new StringBuilder();
                for (CuentaBancaria c : cuentasAfiliadas) {
                    sb.append(c.getNumeroCuenta()).append(", ");
                }
                cuentas = sb.substring(0, sb.length() - 2);
            }
            modelo.addRow(new Object[]{
                    t.getNumeroTarjeta(),
                    t.getCompania(),
                    tipo,
                    cuentas
            });
        }
    }
}

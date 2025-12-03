package sincronizacion;

import java.util.ArrayList;
import java.util.List;

import entidades.concretas.Administrador;
import entidades.concretas.Cajero;
import entidades.concretas.CuentaBancaria;
import entidades.concretas.Empleado;
import entidades.concretas.Tarjeta;
import entidades.concretas.Cliente;
import entidades.concretas.Usuario;
import sistema.SistemaBanco;

public class SincronizacionCompartida {

    private static final List<Actualizable> listeners = new ArrayList<>();

    public static synchronized void registrarListener(Actualizable listener) {
        listeners.add(listener);
    }

    public static synchronized void notificarListeners() {
        List<Administrador> administradores = SistemaBanco.getInstance().getGestorAdministradores().listarTodos();
        List<Cajero> cajeros = SistemaBanco.getInstance().getGestorCajeros().listarTodos();
        List<Empleado> empleados = SistemaBanco.getInstance().getGestorEmpleados().listarTodos();
        List<CuentaBancaria> cuentas = SistemaBanco.getInstance().getGestorCuentas().listarTodos();
        List<Tarjeta> tarjetas = SistemaBanco.getInstance().getGestorTarjetas().listarTodos();
        List<Cliente> clientes = SistemaBanco.getInstance().getGestorClientes().listarTodos();
        List<Usuario> usuarios = SistemaBanco.getInstance().getGestorUsuarios().listarTodos();
        for (Actualizable l : listeners) {
            l.actualizarAdministradores(administradores);
            l.actualizarCajeros(cajeros);
            l.actualizarEmpleados(empleados);
            l.actualizarCuentas(cuentas);
            l.actualizarTarjetas(tarjetas);
            l.actualizarClientes(clientes);
            l.actualizarUsuarios(usuarios);
        }
    }

    public interface Actualizable {
        default void actualizarAdministradores(List<Administrador> lista) {}
        default void actualizarCajeros(List<Cajero> lista) {}
        default void actualizarEmpleados(List<Empleado> lista) {}
        default void actualizarCuentas(List<CuentaBancaria> lista) {}
        default void actualizarTarjetas(List<Tarjeta> lista) {}
        default void actualizarClientes(List<Cliente> lista) {}
        default void actualizarUsuarios(List<Usuario> lista) {}
    }
}

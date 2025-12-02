package sincronizacion;

import java.util.ArrayList;
import java.util.List;

import entidades.concretas.Administrador;
import entidades.concretas.Cajero;
import entidades.concretas.CuentaBancaria;
import entidades.concretas.Empleado;
import sistema.SistemaBanco;

public class SincronizacionCompartida {

    private static final List<Actualizable> listeners = new ArrayList<>();

    public static synchronized void registrarListener(Actualizable listener) {
        listeners.add(listener);
    }

    public static synchronized void notificarListeners() {
        // Acceder al banco a traves de SistemaBanco
        List<Administrador> administradores = SistemaBanco.getInstance().getGestorAdministradores().listarTodos();
        List<Cajero> cajeros = SistemaBanco.getInstance().getGestorCajeros().listarTodos();
        List<Empleado> empleados = SistemaBanco.getInstance().getGestorEmpleados().listarTodos();
        List<CuentaBancaria> cuentas = SistemaBanco.getInstance().getGestorCuentas().listarTodos();
        for (Actualizable l : listeners) {
            l.actualizarAdministradores(administradores);
            l.actualizarCajeros(cajeros);
            l.actualizarEmpleados(empleados);
            l.actualizarCuentas(cuentas);
        }
    }

    public interface Actualizable {
        default void actualizarAdministradores(List<Administrador> lista) {}
        default void actualizarCajeros(List<Cajero> lista) {}
        default void actualizarEmpleados(List<Empleado> lista) {}
        default void actualizarCuentas(List<CuentaBancaria> lista) {}
    }
}

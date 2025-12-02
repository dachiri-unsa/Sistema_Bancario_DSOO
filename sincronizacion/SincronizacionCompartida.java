package sincronizacion;

import java.util.ArrayList;
import java.util.List;

import entidades.concretas.Administrador;
import entidades.concretas.Cajero;
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
        for (Actualizable l : listeners) {
            l.actualizarLista(administradores);
            l.actualizarCajeros(cajeros);
            l.actualizarEmpleados(empleados);
        }
    }

    public interface Actualizable {
        void actualizarLista(List<Administrador> lista);

        default void actualizarCajeros(List<Cajero> lista) {
        }

        default void actualizarEmpleados(List<Empleado> lista) {
        }
    }
}

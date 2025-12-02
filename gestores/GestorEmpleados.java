package gestores;

import java.util.List;

import entidades.concretas.Empleado;
import interfaces.Gestor;
import sincronizacion.SincronizadorEntidad;

public class GestorEmpleados implements Gestor<Empleado> {
    private final SincronizadorEntidad<Empleado> sincronizadorEmpleados;

    public GestorEmpleados(SincronizadorEntidad<Empleado> sincronizadorEmpleados) {
        this.sincronizadorEmpleados = sincronizadorEmpleados;
    }

    public GestorEmpleados() {
        this.sincronizadorEmpleados = new SincronizadorEntidad<>();
    }

    @Override
    public void agregar(Empleado empleado) {
        sincronizadorEmpleados.agregar(empleado);
    }

    public void agregarEmpleado(Empleado empleado) {
        agregar(empleado);
    }

    @Override
    public List<Empleado> listarTodos() {
        return sincronizadorEmpleados.listarTodos();
    }

    @Override
    public void eliminar(int index) {
        sincronizadorEmpleados.eliminar(index);
    }

    public Empleado buscarEmpleado(String id) {
        for (Empleado e : sincronizadorEmpleados.listarTodos()) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    public Empleado buscarPorDni(String dni) {
        for (Empleado e : sincronizadorEmpleados.listarTodos()) {
            if (e.getDNI().equals(dni)) {
                return e;
            }
        }
        return null;
    }

    public void listarEmpleados() {
        if (sincronizadorEmpleados.listarTodos().isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        for (Empleado e : sincronizadorEmpleados.listarTodos()) {
            System.out.println("- " + e.getNombre() + " (ID: " + e.getId() + ")");
        }
    }
}

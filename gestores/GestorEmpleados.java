package gestores;

import java.util.ArrayList;
import entidades.Empleado;
import java.util.List;

public class GestorEmpleados {
    private ArrayList<Empleado> listaEmpleados;
    private static List<Empleado> empleados = new ArrayList<>();

    public GestorEmpleados() {
        this.listaEmpleados = new ArrayList<>();
    }

    public void agregarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);
        empleados.add(empleado);
    }

    public Empleado buscarEmpleado(String id) {
        for (Empleado e : listaEmpleados) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    public void listarEmpleados() {
        if (listaEmpleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        for (Empleado e : listaEmpleados) {
            System.out.println("- " + e);
        }
    }

    public ArrayList<Empleado> getEmpleados() {
        return listaEmpleados;
    }

    public static Empleado buscarPorDni(String dni) {
        for (Empleado c : empleados) {
            if ( c.getDNI().equals(dni)) {
                return c;
            }
        }
        return null;
    }
}

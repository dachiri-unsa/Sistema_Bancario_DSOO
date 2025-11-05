package gestores;

import java.util.ArrayList;
import entidades.Empleado;

public class GestorEmpleados {
    private ArrayList<Empleado> empleados;

    public GestorEmpleados() {
        this.empleados = new ArrayList<>();
    }

    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public Empleado buscarEmpleado(String id) {
        for (Empleado e : empleados) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    public void listarEmpleados() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        for (Empleado e : empleados) {
            System.out.println("- " + e);
        }
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
}

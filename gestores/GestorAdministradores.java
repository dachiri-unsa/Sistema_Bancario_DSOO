package gestores;

import java.util.ArrayList;
import java.util.List;

import entidades.Administrador;
import entidades.Cliente;
import entidades.Empleado;

public class GestorAdministradores {
    private ArrayList<Administrador> listaAdministradores;
    private static List<Administrador> administradores = new ArrayList<>();

    public GestorAdministradores() {
        this.listaAdministradores = new ArrayList<>();
        this.administradores = new ArrayList<>();
    }

    public void agregarAdministrador(Administrador administrador) {
        listaAdministradores.add(administrador);
        administradores.add(administrador);
    }

    public Administrador buscarAdministradorById(String dni) {
        for (Administrador a : listaAdministradores) {
            if (a.getDNI().equalsIgnoreCase(dni)) {
                return a;
            }
        }
        return null;
    }

    public void listarAdministradores() {
        if (listaAdministradores.isEmpty()) {
            System.out.println("No hay administradores registrados.");
            return;
        }
        for (Administrador a : listaAdministradores) {
            System.out.println("- " + a);
        }
    }

    public ArrayList<Administrador> getAdministradores() {
        return listaAdministradores;
    }

    public static Administrador buscarPorDni(String dni) {
        for (Administrador a : administradores) {
            if (a.getDNI().equals(dni)) {
                return a;
            }
        }
        return null;
    }
}

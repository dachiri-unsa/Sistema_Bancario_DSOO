package gestores;

import java.util.ArrayList;
import java.util.List;

import entidades.concretas.Administrador;
import interfaces.Gestor;
import sincronizacion.SincronizadorEntidad;

public class GestorAdministradores implements Gestor<Administrador> {
    private final SincronizadorEntidad<Administrador> sincronizadorAdministradores;

    public GestorAdministradores(SincronizadorEntidad<Administrador> sincronizadorAdministradores) {
        this.sincronizadorAdministradores = sincronizadorAdministradores;
    }

    @Override
    public void agregar(Administrador administrador) {
        sincronizadorAdministradores.agregar(administrador);
    }

    @Override
    public List<Administrador> listarTodos() {
        return sincronizadorAdministradores.listarTodos();
    }

    @Override
    public void eliminar(int index) {
        sincronizadorAdministradores.eliminar(index);
    }

    public Administrador buscarAdministradorById(String dni) {
        for (Administrador a : sincronizadorAdministradores.listarTodos()) {
            if (a.getDNI().equalsIgnoreCase(dni)) {
                return a;
            }
        }
        return null;
    }

    public void listarAdministradores() {
        if (sincronizadorAdministradores.listarTodos().isEmpty()) {
            System.out.println("No hay administradores registrados.");
            return;
        }
        for (Administrador a : sincronizadorAdministradores.listarTodos()) {
            System.out.println("- " + a);
        }
    }

    public Administrador buscarPorDni(String dni) {
        for (Administrador a : sincronizadorAdministradores.listarTodos()) {
            if (a.getDNI().equals(dni)) {
                return a;
            }
        }
        return null;
    }

    public int buscarIndexAdministrador(Administrador admin) {
        for (int i = 0; i < sincronizadorAdministradores.listarTodos().size(); i++) {
            if (sincronizadorAdministradores.listarTodos().get(i).getDNI().equals(admin.getDNI())) {
                return i;
            }
        }
        return -1;
    }

    public void eliminarAdministrador(Administrador admin) {
        if (admin != null) {
            int index = buscarIndexAdministrador(admin);
            if (index != -1) {
                eliminar(index);
            }
        }
    }
}

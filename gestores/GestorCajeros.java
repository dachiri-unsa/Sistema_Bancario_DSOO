package gestores;

import java.util.List;
import entidades.concretas.Cajero;
import sincronizacion.SincronizadorEntidad;

public class GestorCajeros {
    private final SincronizadorEntidad<Cajero> sincronizadorCajeros;

    public GestorCajeros(SincronizadorEntidad<Cajero> sincronizadorCajeros) {
        this.sincronizadorCajeros = sincronizadorCajeros;
    }

    public GestorCajeros() {
        this.sincronizadorCajeros = new SincronizadorEntidad<>();
    }

    public void agregarCajero(Cajero cajero) {
        sincronizadorCajeros.agregar(cajero);
    }

    public Cajero buscarCajero(String id) {
        for (Cajero c : sincronizadorCajeros.listarTodos()) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public int cajerosDisponibles() {
        int cajeroDisponibles = 0;
        for (Cajero c : sincronizadorCajeros.listarTodos()) {
            if (c.getDisponible()) {
                cajeroDisponibles++;
            }
        }
        return cajeroDisponibles;
    }

    public void listarCajerosDisponibles() {
        List<Cajero> lista = sincronizadorCajeros.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay cajeros registrados.");
            return;
        }
        int cajeroDisponibles = 0;
        for (Cajero c : lista) {
            if (c.getDisponible()) {
                cajeroDisponibles++;
                System.out.println("- " + c);
            }
        }
        if (cajeroDisponibles == 0) {
            System.out.println("No hay cajeros disponibles.");
        }
    }

    public void listarCajeros() {
        List<Cajero> lista = sincronizadorCajeros.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay cajeros registrados.");
            return;
        }
        for (Cajero c : lista) {
            System.out.println("- " + c);
        }
    }

    public List<Cajero> listarTodos() {
        return sincronizadorCajeros.listarTodos();
    }

    public void eliminar(int index) {
        sincronizadorCajeros.eliminar(index);
    }

    public void eliminar(Cajero cajero) {
        List<Cajero> lista = sincronizadorCajeros.listarTodos();
        int index = lista.indexOf(cajero);
        if (index != -1) {
            sincronizadorCajeros.eliminar(index);
        }
    }
}

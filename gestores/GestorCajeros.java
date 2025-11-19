package gestores;

import java.util.ArrayList;
import entidades.Cajero;

public class GestorCajeros {
    private ArrayList<Cajero> cajeros;

    public GestorCajeros() {
        this.cajeros = new ArrayList<>();
    }

    public void agregarCajero(Cajero cajero) {
        cajeros.add(cajero);
    }

    public Cajero buscarCajero(String id) {
        for (Cajero c : cajeros) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public int cajerosDisponibles() {
        if (cajeros.isEmpty()) {
            return 0;
        }

        int cajeroDisponibles = 0;
        for (Cajero c : cajeros) {
            if (c.getDisponible()) {
                cajeroDisponibles++;
            }
        }

        return cajeroDisponibles;
    }

    public void listarCajerosDisponibles() {
        if (cajeros.isEmpty()) {
            System.out.println("No hay cajeros registrados.");
            return;
        }
        int cajeroDisponibles = 0;
        for (Cajero c : cajeros) {
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
        if (cajeros.isEmpty()) {
            System.out.println("No hay cajeros registrados.");
            return;
        }
        for (Cajero c : cajeros) {
            System.out.println("- " + c);
        }
    }

    public ArrayList<Cajero> getCajeros() {
        return cajeros;
    }
}

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

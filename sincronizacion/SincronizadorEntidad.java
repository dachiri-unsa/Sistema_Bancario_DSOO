package sincronizacion;

import java.util.ArrayList;
import java.util.List;

import entidades.Entidad;

public class SincronizadorEntidad<T extends Entidad> {
    private final List<T> entidades = new ArrayList<>();

    public synchronized void agregar(T entidad) {
        entidades.add(entidad);
    }

    public synchronized List<T> listarTodos() {
        return new ArrayList<>(entidades);
    }

    public synchronized void modificar(int index, T entidad) {
        entidades.set(index, entidad);
    }

    public synchronized void eliminar(int index) {
        entidades.remove(index);
    }
}

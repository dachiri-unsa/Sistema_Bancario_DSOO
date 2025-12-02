package gestores;

import java.util.List;

import entidades.concretas.Tarjeta;
import interfaces.Gestor;
import sincronizacion.SincronizadorEntidad;

public class GestorTarjetas implements Gestor<Tarjeta> {
    private final SincronizadorEntidad<Tarjeta> sincronizadorTarjetas;

    public GestorTarjetas(SincronizadorEntidad<Tarjeta> sincronizadorTarjetas) {
        this.sincronizadorTarjetas = sincronizadorTarjetas;
    }

    public GestorTarjetas() {
        this.sincronizadorTarjetas = new SincronizadorEntidad<>();
    }

    @Override
    public void agregar(Tarjeta tarjeta) {
        sincronizadorTarjetas.agregar(tarjeta);
    }

    public void agregarTarjeta(Tarjeta tarjeta) {
        agregar(tarjeta);
    }

    @Override
    public List<Tarjeta> listarTodos() {
        return sincronizadorTarjetas.listarTodos();
    }

    @Override
    public void eliminar(int index) {
        sincronizadorTarjetas.eliminar(index);
    }

    public Tarjeta buscarTarjeta(String numeroTarjeta) {
        for (Tarjeta tarjeta : sincronizadorTarjetas.listarTodos()) {
            if (tarjeta.getNumeroTarjeta().equals(numeroTarjeta)) {
                return tarjeta;
            }
        }
        return null;
    }

    public boolean eliminarTarjeta(String numeroTarjeta) {
        for (Tarjeta tarjeta : sincronizadorTarjetas.listarTodos()) {
            if (tarjeta.getNumeroTarjeta().equals(numeroTarjeta)) {
                int index = sincronizadorTarjetas.listarTodos().indexOf(tarjeta);
                if (index != -1) {
                    sincronizadorTarjetas.eliminar(index);
                    return true;
                }
            }
        }
        return false;
    }

    public void listarTarjetas() {
        if (sincronizadorTarjetas.listarTodos().isEmpty()) {
            System.out.println("No hay tarjetas registradas.");
            return;
        }
        for (Tarjeta t : sincronizadorTarjetas.listarTodos()) {
            System.out.println("- " + t.getNumeroTarjeta() + " (" + t.getCompania() + ")");
        }
    }

    public String generarNumeroTarjeta() {
        String numero;
        do {
            numero = "";
            for (int i = 0; i < 16; i++) {
                numero += (int) (Math.random() * 10);
            }
        } while (buscarTarjeta(numero) != null);
        return numero;
    }
}

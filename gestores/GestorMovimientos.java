package gestores;

import java.util.List;

import entidades.abstractas.Movimiento;
import interfaces.Gestor;
import sincronizacion.SincronizadorEntidad;

public class GestorMovimientos implements Gestor<Movimiento> {
    private final SincronizadorEntidad<Movimiento> sincronizadorMovimientos;

    public GestorMovimientos(SincronizadorEntidad<Movimiento> sincronizadorMovimientos) {
        this.sincronizadorMovimientos = sincronizadorMovimientos;
    }

    public GestorMovimientos() {
        this.sincronizadorMovimientos = new SincronizadorEntidad<>();
    }

    @Override
    public void agregar(Movimiento movimiento) {
        sincronizadorMovimientos.agregar(movimiento);
    }

    public void registrarMovimiento(Movimiento movimiento) {
        agregar(movimiento);
    }

    @Override
    public List<Movimiento> listarTodos() {
        return sincronizadorMovimientos.listarTodos();
    }

    @Override
    public void eliminar(int index) {
        sincronizadorMovimientos.eliminar(index);
    }

    public void listarMovimientos() {
        if (sincronizadorMovimientos.listarTodos().isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }
        for (Movimiento m : sincronizadorMovimientos.listarTodos()) {
            System.out.println(m);
        }
    }

    public void listarMovimientosPorCuenta(String numeroCuenta) {
        boolean encontrado = false;
        for (Movimiento m : sincronizadorMovimientos.listarTodos()) {
            if (m.getOrigen().getNumeroCuenta().equals(numeroCuenta)) {
                System.out.println(m);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No hay movimientos para la cuenta: " + numeroCuenta);
        }
    }
}

package gestores;

import java.util.ArrayList;
import entidades.Movimiento;

public class GestorMovimientos {
    private ArrayList<Movimiento> listaMovimientos;

    public GestorMovimientos() {
        this.listaMovimientos = new ArrayList<>();
    }

    public void registrarMovimiento(Movimiento movimiento) {
        listaMovimientos.add(movimiento);
    }

    public void listarMovimientos() {
        if (listaMovimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }
        for (Movimiento m : listaMovimientos) {
            System.out.println(m);
        }
    }

    public ArrayList<Movimiento> getMovimientos() {
        return listaMovimientos;
    }

    public void listarMovimientosPorCuenta(String numeroCuenta) {
        for (Movimiento m : listaMovimientos) {
            if (m.getOrigen().getNumeroCuenta().equals(numeroCuenta)) {
                System.out.println(m);
            }
        }
    }
}

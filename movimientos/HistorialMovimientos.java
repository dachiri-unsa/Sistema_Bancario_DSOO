package movimientos;

import entidades.Movimiento;
import java.util.ArrayList;

public class HistorialMovimientos {
    private ArrayList<Movimiento> movimientos;

    public HistorialMovimientos() {
        this.movimientos = new ArrayList<>();
    }
    public void agregarMovimiento(Movimiento movimiento) {
        movimientos.add(movimiento);
    }
    public void listarMovimientos() {
        for (Movimiento m : movimientos) {
            System.out.println(m);
        }
    }
}
